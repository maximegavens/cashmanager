package com.cashmanager.service;

import com.cashmanager.model.FakeBank;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static FakeBank[] banks = new FakeBank[]{new FakeBank(0, "1234567890123456", 10000.0)};

    public String pay(String account, String amount) {
        for (int i = 0; i < banks.length; i++) {
            if (banks[i].getAccountRef().equals(account)) {
                Double diff;
                if ((diff = banks[i].getAmount() - Double.parseDouble(amount)) >= 0) {
                    banks[i].setAmount(diff);
                    return "payment succeed";
                } else {
                    return "payment refused";
                }
            }
        }
        return "account not found";
    }
}
