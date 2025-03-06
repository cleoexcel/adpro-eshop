package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {
    @Test
    void testCreatePaymentBankEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), Collections.emptyMap());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankSuccess() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "ABC");
        paymentData1.put("referenceCode", "12324");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentBankUncompletedPaymentData() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "ABC");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentBankContainsNullPaymentData() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", null);
        paymentData1.put("referenceCode", null);
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), Collections.emptyMap());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedLength() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedPrefix() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNumerical() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWrongMethod() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p23135-2132-abc2-2315vb",
                    "XXXX", PaymentStatus.SUCCESS.getValue(), paymentData1);
        });
    }

    @Test
    void testCreatePaymentWrongStatus() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p23135-2132-abc2-2315vb",
                    "BANK_TRANSFER", "MEOW", paymentData1);
        });
    }
}