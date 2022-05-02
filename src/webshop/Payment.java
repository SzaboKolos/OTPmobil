package webshop;

import java.time.LocalDate;

public class Payment {

    private String webshopId;
    private String customerId;
    private PaymentMethod paymentMethod;
    private Integer price;
    private Long accountNumber;
    private Long cardNumber;
    private LocalDate paymentDate;

    public String getWebshopId() {
        return webshopId;
    }
    public void setWebshopId(String webshopId) {
        this.webshopId = webshopId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Payment(){

    }
    public Payment(String webshopId, String customerId, PaymentMethod paymentMethod, Integer price, Long accountNumber, Long cardNumber, LocalDate paymentDate) {
        this.webshopId = webshopId;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return (webshopId + ";" + customerId + ";" + paymentMethod + ";" + price + ";" + accountNumber + ";" + cardNumber + ";" + paymentDate);
    }
}
