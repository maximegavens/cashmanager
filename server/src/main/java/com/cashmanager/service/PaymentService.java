package com.cashmanager.service;

import com.cashmanager.model.FakeBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
@Transactional(timeout = 30)
public class PaymentService {

    private int number_of_attempt = 0;
    private int max_attempt = 3;
    private static FakeBank[] banks = new FakeBank[]{new FakeBank(0, "1234567890123456", 10000.0)};

    public String pay(String account, String amount) {
        for (int i = 0; i < banks.length; i++) {
            if (banks[i].getAccountRef().equals(account)) {
                Double diff;
                if ((diff = banks[i].getAmount() - Double.parseDouble(amount)) >= 0 && number_of_attempt <= this.max_attempt) {
                    banks[i].setAmount(diff);
                    number_of_attempt = 0;
                    return "payment succeed";
                }
                else if (number_of_attempt > this.max_attempt) {
                    return "too much attempts, deconnexion...";
                } else {
                    number_of_attempt++;
                    return "payment refused";
                }
            }
        }
        number_of_attempt++;
        return "account not found";
    }
}
