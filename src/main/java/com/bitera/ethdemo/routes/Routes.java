package com.bitera.ethdemo.routes;

import com.bitera.ethdemo.domain.BlockchainTransaction;
import com.bitera.ethdemo.services.Web3jService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
public class Routes {

    private final Web3jService service;

    public Routes(Web3jService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/eth/transfer")
    @ResponseBody
    public String send(@RequestParam String address) throws IOException {
        BlockchainTransaction response = service.sendEth(address);
        return response.getId();
    }

    @GetMapping("/eth/gas")
    public String gasPrice() throws IOException {
        return service.gasPrice();
    }

    @GetMapping("/eth/clientVersion")
    public String clientVersion() throws IOException {
        return service.getClientVersion();
    }
}
