package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        payments = new ArrayList<>();
        orders = new ArrayList<>();

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        Order order3 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb079",
                products, 1708580000L, "Safira Sudrajat");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "ABC");
        paymentData1.put("referenceCode", "12324");
        Payment payment1 = new Payment("p23135-2132-abc2-2315vb",
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData1,
                order1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678DEF");
        Payment payment2 = new Payment("p23135-2132-abc2-12313b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData2,
                order2);
        payments.add(payment2);

        Map<String, String> paymentData3 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP123DEF");
        Payment payment3 = new Payment("p23135-2132-abc2-a3gf2b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.REJECTED.getValue(),
                paymentData3,
                order3);
        payments.add(payment3);
    }

    @Test
    void testCreatePayment() {
        Payment payment = payments.get(1);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getOrder().getId(), result.getOrder().getId());
    }

    @Test
    void testSetStatusPaymentToSuccess() {
        Payment payment = payments.get(2);
        Order order = orders.get(2);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(payment).when(paymentRepository).findById(any(String.class));
        Payment result = paymentService.setStatus(payment, "SUCCESS");

        verify(paymentRepository, times(1)).findById(any(String.class));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentToRejected() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);

        doReturn(payment).when(paymentRepository).save(payment);
        doReturn(payment).when(paymentRepository).findById(any(String.class));
        assertEquals(order.getStatus(), payment.getOrder().getStatus());

        Payment result = paymentService.setStatus(payment, "REJECTED");

        verify(paymentRepository, times(1)).findById(any(String.class));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentInvalid() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);

        doReturn(payment).when(paymentRepository).findById(any(String.class));
        assertEquals(order.getStatus(), payment.getOrder().getStatus());

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "zczc"));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);

        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");

        Payment result = paymentService.getPayment("zczc");
        assertNull(result);
        verify(paymentRepository, times(1)).findById("zczc");
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        assertEquals(payments.size(), paymentService.getAllPayments().size());
        for (int i=0; i<payments.size(); i++) {
            assertEquals(payments.get(i), paymentService.getAllPayments().get(i));
        }
    }
}