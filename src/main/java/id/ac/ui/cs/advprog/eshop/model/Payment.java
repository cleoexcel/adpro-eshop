package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;

        if (this.method.equals("BANK_TRANSFER")) {
            if (!paymentData.containsKey("bankName")
                    || !paymentData.containsKey("referenceCode")
                    || paymentData.get("bankName") == null
                    || paymentData.get("referenceCode") == null) {
                this.status = "REJECTED";
            }
        } else if (this.method.equals("VOUCHER_CODE")) {
            if (!paymentData.containsKey("voucherCode")
                    || paymentData.get("voucherCode") == null
                    || paymentData.get("voucherCode").length() != 16
                    || !paymentData.get("voucherCode").startsWith("ESHOP")) {
                this.status = "REJECTED";
                return;
            }

            int numericalCount = 0;
            for (int i=0; i<paymentData.get("voucherCode").length(); i++) {
                char currentChar = paymentData.get("voucherCode").charAt(i);
                if (currentChar >= '0' && currentChar <= '9') {
                    numericalCount++;
                }
            }
            if (numericalCount != 8) {
                this.status = "REJECTED";
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}