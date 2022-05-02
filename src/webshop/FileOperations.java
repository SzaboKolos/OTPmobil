package webshop;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FileOperations {
    public static List<Customer> readCustomers(String path){
        List<Customer> customers = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null){
                String[] customerData = line.split(";");
                String wsId = customerData[0];
                String csId = customerData[1];
                String csNm = customerData[2];
                String csAd = customerData[3];

                if(inspectCustomer(customerData,lineNum,path))
                    customers.add(new Customer(wsId,csId,csNm,csAd));
                lineNum++;
            }
            reader.close();

        } catch (IOException e){
            e.getStackTrace();
        }
        return customers;
    }
    public static List<Payment> readPayments(String path){
        List<Payment> payments = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null){
                String[] paymentData = line.split(";");
                if (inspectPayment(paymentData,lineNum,path)){
                    String wsId = paymentData[0];
                    String usId = paymentData[1];
                    PaymentMethod paymentMethod= PaymentMethod.valueOf(paymentData[2]);
                    Integer paymentAmount = Integer.parseInt(paymentData[3]);
                    Long accountNum = (!paymentData[4].equals("")) ? Long.parseLong(paymentData[4]) : null;
                    Long cardNum = (!paymentData[5].equals("")) ? Long.parseLong(paymentData[5]) : null;

                    LocalDate paymentDate = LocalDate.of(Integer.parseInt(paymentData[6].split("\\.")[0]),
                            Integer.parseInt(paymentData[6].split("\\.")[1]),
                            Integer.parseInt(paymentData[6].split("\\.")[2]));

                    payments.add(new Payment(wsId,usId,paymentMethod,paymentAmount,accountNum,cardNum,paymentDate));
                }
                lineNum++;
            }
            reader.close();

        } catch (Exception e){
            e.getStackTrace();

        }
        return payments;
    }

    public static boolean inspectCustomer(String[] cData,int lineNum,String path){ //returns true, if the current customer line's meet the expected values.
        //webshopId,customerId,customerName,customerAddress
        if(cData[0].isEmpty() || cData[1].isEmpty() || cData[2].isEmpty() || cData[3].isEmpty()){
            exceptionLogger("Empty data-field",lineNum,path);
            return false;
        }
        return true;
    }
    public static boolean inspectPayment(String[] pData,int lineNum,String path){
            if (!pData[2].equals(PaymentMethod.card.name()) && !pData[2].equals(PaymentMethod.transfer.name())){
                exceptionLogger("Invalid payment method",lineNum,path);
                return false;
            }
            if (!isInt(pData[3])){
                exceptionLogger("Invalid amount",lineNum,path);
                return false;
            }
            if (!pData[5].isEmpty() && !isLong(pData[5])){
                exceptionLogger("Invalid card number",lineNum,path);
                return false;
            }
            if (!pData[4].isEmpty() && !isLong(pData[4])){
                exceptionLogger("Invalid account number",lineNum,path);
                return false;
            }
            if ((pData[2].equals(PaymentMethod.card.name()))){
                if(pData[5].isEmpty()) {
                    exceptionLogger("Inconsistent payment method, card number field is empty",lineNum,path);
                    return false;
                }
            } else if(pData[2].equals(PaymentMethod.transfer.name())){
                if (pData[4].isEmpty()) {
                    exceptionLogger("Inconsistent payment method, account number field is empty", lineNum, path);
                    return false;
                }
            }

            var date = pData[6].split("\\.");
            if (!isInt(date[0]) || !isInt(date[1]) || !isInt(date[2])) {
                exceptionLogger("Invalid date", lineNum, path);
                return false;
            }
            try {
                LocalDate paymentDate = LocalDate.of(Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]));
            }catch (DateTimeException dex){
                dex.getStackTrace();
                exceptionLogger("Invalid date", lineNum, path);
                return false;
            }
        return true;
    }


    public static boolean isLong(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfex) {
            return false;
        }
        return true;
    }
    public static boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum,10);
        } catch (NumberFormatException nfex) {
            return false;
        }
        return true;
    }

    public static void exceptionLogger(String e,int lineNum,String file){
        String path = "src\\application.log";
        try {
            File logFile = new File(path);
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));

            writer.write("An exception occurred while reading file "+ file +"! Cause: "+ e + " at line: "+ lineNum + ", time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.newLine();

            writer.close();
        } catch (Exception ex){
            ex.getStackTrace();
        }
    }
    public static void writeReport(String path,List<?> items){
        try {
            File report = new File(path);
            if (report.createNewFile()) {
                System.out.println("File created: " + report.getName());
            }
            else{
                System.out.println("Updated file: " + report.getName());
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (var item: items ) {
                writer.write(item.toString());
                writer.newLine();
            }

            writer.close();
        } catch (Exception ex){
            ex.getStackTrace();
        }
    }
}
