package com.cashmanager.controller;

import com.cashmanager.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class Payment {

    @Autowired
    private PaymentService service;

    @PostMapping
    public ResponseEntity<Object> pay(@RequestParam(value = "account") String account, @RequestParam(value = "amount") String amount) {
        String message = service.pay(account, amount);
        JSONObject json = new JSONObject();
        json.put("message", message);
        return new ResponseEntity<Object>(json, new HttpHeaders(), HttpStatus.OK);
    }
}
