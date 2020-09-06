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

    public BlockchainTransaction sendEth(String address) throws IOException {
        /*
        Construct transaction object
        Sign it with private key
        Publish signed transaction
         */
        BlockchainTransaction trx = new BlockchainTransaction(address);
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(
                trx.getFromId(),
                DefaultBlockParameterName.LATEST).send();

        BigInteger wei = Convert.toWei(BigDecimal.valueOf(trx.getValue()),Convert.Unit.ETHER).toBigInteger();

        BigInteger gasLimit = BigInteger.valueOf(21000);

        BigInteger gasPrice = Convert.toWei(
                BigDecimal.ONE, Convert.Unit.GWEI).toBigInteger();

        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                transactionCount.getTransactionCount(), //nonce
                gasPrice,
                gasLimit,
                trx.getToId(),
                wei);

        //Don't do this in prod :B
        Credentials credentials = Credentials.create(this.privateKey);

        // Sign the transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexSignedMessage = Numeric.toHexString(signedMessage);


        EthSendTransaction response = web3j.ethSendRawTransaction(hexSignedMessage).send();

        if(response.getError() != null){
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
