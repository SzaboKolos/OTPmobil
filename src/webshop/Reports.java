package webshop;

import java.util.ArrayList;
import java.util.List;

public class Reports {
    public static void main(String[] args) {
        List<Customer> cs = FileOperations.readCustomers("src\\customer.csv");
        List<Payment> ps = FileOperations.readPayments("src\\payments.csv");

        report1(cs,ps);
        report2(cs,ps);
    }
    public static void report1(List<Customer> customers, List<Payment> payments){
        String path = "src\\report01.csv";
        List<String> customersAllPurchases = new ArrayList<>();
        int spentMoney=0;
        for (var c: customers) {
            for ( var p : payments){
                if (c.getCustomerId().equals(p.getCustomerId()) && c.getWebshopId().equals(p.getWebshopId())){
                    spentMoney+=p.getPrice();
                }
            }
            customersAllPurchases.add(c.getName()+", "+c.getAddress()+", "+spentMoney);
            spentMoney =0;
        }
        FileOperations.writeReport(path,customersAllPurchases);
    }

    public static void report2(List<Customer> customers, List<Payment> payments){
        String path = "src\\report02.csv";
        List<String> customersAllPurchases = new ArrayList<>();
        List<String> countedWebshops = new ArrayList<>();
        int transferMoney = 0;
        int cardMoney = 0;
        for (var c: customers) {
            if (!countedWebshops.contains(c.getWebshopId())) {
                for (var p : payments) {
                    if (c.getWebshopId().equals(p.getWebshopId()) && p.getPaymentMethod().name().equals(PaymentMethod.transfer.name())) {
                        transferMoney += p.getPrice();
                    }
                    if (c.getWebshopId().equals(p.getWebshopId()) && p.getPaymentMethod().name().equals(PaymentMethod.card.name())) {
                        cardMoney += p.getPrice();
                    }
                }
                    countedWebshops.add(c.getWebshopId());
                    customersAllPurchases.add(c.getWebshopId() + ", " + cardMoney + ", " + transferMoney);
                    cardMoney = 0;
                    transferMoney = 0;
            }
        }
        FileOperations.writeReport(path,customersAllPurchases);
    }
}

