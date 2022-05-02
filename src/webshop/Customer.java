package webshop;

public class Customer {
    public String getWebshopId() {
        return webshopId;
    }

    public void setWebshopId(String webshopId) {
        this.webshopId = webshopId;
    }

    private String webshopId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String customerId;
    private String name;
    private String address;

    public Customer(){

    }
    public Customer(String webshopId, String customerId, String name, String address){
        this.webshopId = webshopId;
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return (webshopId+";"+customerId+";"+name+";"+address);
    }
}
