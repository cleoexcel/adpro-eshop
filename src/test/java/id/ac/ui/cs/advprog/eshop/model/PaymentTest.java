package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {
    @Test
    void testCreatePaymentBankEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "BANK_TRANSFER", "SUCCESS", Collections.emptyMap());

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankSuccess() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("bankName", "ABC");
        paymentData1.put("referenceCode", "12324");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "BANK_TRANSFER", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentBankUncompletedPaymentData() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("bankName", "ABC");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "BANK_TRANSFER", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentBankContainsNullPaymentData() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("bankName", null);
        paymentData1.put("referenceCode", null);
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "BANK_TRANSFER", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", "SUCCESS", Collections.emptyMap());

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "ESHOP12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeREJECTEDLength() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "ESHOP12345678");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeREJECTEDPrefix() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "abcde12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeREJECTEDNumerical() {
        Map<String, String> paymentData1 = new HashMap<String, String>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", "SUCCESS", paymentData1);

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
    }
}