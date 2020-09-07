package com.bitera.ethdemo.routes;

import com.bitera.ethdemo.domain.BlockchainTransaction;
import com.bitera.ethdemo.domain.DefaultResponse;
import com.bitera.ethdemo.services.Web3jService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DefaultResponse> index() {
        return ResponseEntity.ok(new DefaultResponse("Greetings from Spring Boot!"));
    }

    @RequestMapping("/ping")
    public ResponseEntity<DefaultResponse> ping() {
        return ResponseEntity.ok(new DefaultResponse("pong"));
    }

    @PostMapping("/eth/transfer")
    @ResponseBody
    public ResponseEntity<BlockchainTransaction> send(@RequestParam String address) throws IOException {
        return ResponseEntity.ok(service.sendEth(address));
    }

    // estas para que las agregaste?
    @GetMapping("/eth/gas")
    public ResponseEntity<DefaultResponse> gasPrice() throws IOException {
        return ResponseEntity.ok(new DefaultResponse(service.gasPrice()));
    }
    @GetMapping("/eth/clientVersion")
    public ResponseEntity<DefaultResponse> clientVersion() throws IOException {
        return ResponseEntity.ok(new DefaultResponse(service.getClientVersion()));
    }
}
