package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {
    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
        Order order3 = new Order("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                products, 1708570000L, "Bambang Sudrajat");
        orders.add(order3);
    }

    @Test
    void testCreatePaymentBankEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), Collections.emptyMap(), orders.get(1));

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankSuccess() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "ABC");
        paymentData1.put("referenceCode", "12324");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentBankUncompletedPaymentData() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "ABC");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentBankContainsNullPaymentData() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", null);
        paymentData1.put("referenceCode", null);
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherEmptyData() {
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), Collections.emptyMap(), orders.get(1));

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedLength() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedPrefix() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde12345678DEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNumerical() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");
        Payment payment = new Payment("p23135-2132-abc2-2315vb",
                "VOUCHER_CODE", PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));

        assertEquals("p23135-2132-abc2-2315vb", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(paymentData1, payment.getPaymentData());
        assertEquals(orders.get(1), payment.getOrder());
    }

    @Test
    void testCreatePaymentWrongMethod() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p23135-2132-abc2-2315vb",
                    "XXXX", PaymentStatus.SUCCESS.getValue(), paymentData1, orders.get(1));
        });
    }

    @Test
    void testCreatePaymentWrongStatus() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p23135-2132-abc2-2315vb",
                    "BANK_TRANSFER", "MEOW", paymentData1, orders.get(1));
        });
    }

    @Test
    void testCreatePaymentWithOrderEmpty() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "abcde1234567ADEF");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p23135-2132-abc2-2315vb",
                    "BANK_TRANSFER", "MEOW", paymentData1, null);
        });
    }
}