package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
    Order order;

    public Payment(String id, String method, String status, Map<String, String> paymentData, Order order) {
        this.id = id;

        if (!PaymentMethod.contains(method)
                || !PaymentStatus.contains(status)
                || order == null) {
            throw new IllegalArgumentException();
        }

        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
        this.order = order;

        if (this.method.equals("BANK_TRANSFER")) {
            if (!paymentData.containsKey("bankName")
                    || !paymentData.containsKey("referenceCode")
                    || paymentData.get("bankName") == null
                    || paymentData.get("referenceCode") == null) {
                this.status = PaymentStatus.REJECTED.getValue();
            }
        } else if (this.method.equals("VOUCHER_CODE")) {
            if (!paymentData.containsKey("voucherCode")
                    || paymentData.get("voucherCode") == null
                    || paymentData.get("voucherCode").length() != 16
                    || !paymentData.get("voucherCode").startsWith("ESHOP")) {
                this.status = PaymentStatus.REJECTED.getValue();
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
                this.status = PaymentStatus.REJECTED.getValue();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}