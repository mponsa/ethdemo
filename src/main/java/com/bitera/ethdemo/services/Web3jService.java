package com.bitera.ethdemo.services;

import com.bitera.ethdemo.domain.BlockchainTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Configurable
public class Web3jService {

    @Autowired
    private Web3j web3j;

    @Value("${eth.defaultAccount.privateKey}")
    private String privateKey;

    public String getClientVersion() throws IOException {
    Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        return web3ClientVersion.getWeb3ClientVersion();
    }

    // hubiese estado bueno meter un validador para la address en este nivel
    // y que tire alguna custom exception o algo acorde al error
    // (me ahorraria el esfuerzo de enviar la tx con una address que ya se que no es valida y va a fallar)
    public BlockchainTransaction sendEth(String address) throws IOException {
        // copy paste lo de abajo? dejo un comment en cada una explicando...
        /*
        Construct transaction object
        Sign it with private key
        Publish signed transaction
         */
        BlockchainTransaction trx = new BlockchainTransaction(address);
        // cada tx va con un nonce que es igual a la cantidad de txs de la account  
        // esto es porque las txs de un usuario tienen un orden en pos de evitar el double spending
        // lectura recomendada: https://kb.myetherwallet.com/en/transactions/what-is-nonce/
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(
                trx.getFromId(),
                DefaultBlockParameterName.LATEST).send();

        // gas en wei, lo que solemos ver en etherscan esta en gwei
        // 1 eth = 1000000000000 wei (si no me comi ningun cero), los wei son como los sats de bitcoin
        BigInteger wei = Convert.toWei(BigDecimal.valueOf(trx.getValue()),Convert.Unit.ETHER).toBigInteger();
        // cada tx va con un gas limit asociado
        // para este caso, al enviar una tx plana siempre van a ser 21000 unidades de gas
        // si estuviesemos interactuando con un contrato, por ejemplo el de DAI,
        // el gas limit lo deberiamos setear bastante mas alto cerca de 120000 unidades
        // esto varia segun el poder computacional que use la tx, que esta dado por el codigo de  el/los smart contracts
        BigInteger gasLimit = BigInteger.valueOf(21000);

        // luego, cuanto estamos dispuestos a pagar por cada unidad de gas
        // aca pusiste 1 gwei, fiijate en ethgasstation.io el precio actual 
        // esto esta atado a oferta/demanda = cuanta mas gente quiera usar la red, mas alto el precio del gas
        BigInteger gasPrice = Convert.toWei(
                BigDecimal.ONE, Convert.Unit.GWEI).toBigInteger();

        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                transactionCount.getTransactionCount(), //nonce
                gasPrice,
                gasLimit,
                trx.getToId(),
                wei);

        //Don't do this in prod :B --> como se te ocurre que podria hacerse?
        // por qué usaste credentials.create y no otra cosa como fromPrivateKey (also, de donde sacaste la data?)
        Credentials credentials = Credentials.create(this.privateKey);

        // Sign the transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexSignedMessage = Numeric.toHexString(signedMessage);


        EthSendTransaction response = web3j.ethSendRawTransaction(hexSignedMessage).send();

        if(response.getError() != null){
            // seria bueno incluir qué error puntual estoy teniendo con la tx ademas del accepted
            // ej: out of gas
            trx.setAccepted(false);
            return trx;
        }

        trx.setAccepted(true);
        String txHash = response.getTransactionHash();

        trx.setId(txHash);
        return trx;
    }

    public String gasPrice() throws IOException {
        EthGasPrice gasPrice =  web3j.ethGasPrice().send();
        return "Gas price: " + gasPrice.getGasPrice().toString();
    }




}
