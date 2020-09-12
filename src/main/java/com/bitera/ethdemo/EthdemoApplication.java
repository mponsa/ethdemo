// este archivo (y el proyecto) los creaste a mano o usando la cli de springboot?
package com.bitera.ethdemo;

import java.awt.*;
import java.util.Arrays;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;


@SpringBootApplication
@EnableEncryptableProperties
public class EthdemoApplication {

	public static void main(String[] args) {
		//web3j = initializeNode();
		SpringApplication.run(EthdemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
//
//  te lo pregunte por otro lado indirectamente, pero por que cambiaste esto?
//	public static Web3j initializeNode(){
//	    System.out.println("Connecting to Ethereum...");
//        Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/fb6f63b2dff942859fc3d4f668ef92f9"));
//        System.out.println("Successfully connected to Ethereum.");
//
//        try {
//            // web3_clientVersion returns the current client version.
//            Web3ClientVersion clientVersion = web3.web3ClientVersion().send();
//
//            // eth_blockNumber returns the number of most recent block.
//            EthBlockNumber blockNumber = web3.ethBlockNumber().send();
//
//            // eth_gasPrice, returns the current price per gas in wei.
//            EthGasPrice gasPrice = web3.ethGasPrice().send();
//
//            // Print result
//            System.out.println("Client version: " + clientVersion.getWeb3ClientVersion());
//            System.out.println("Block number: " + blockNumber.getBlockNumber());
//            System.out.println("Gas price: " + gasPrice.getGasPrice());
//
//            return web3;
//        } catch (IOException ex) {
//            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
//        }
//    }

}