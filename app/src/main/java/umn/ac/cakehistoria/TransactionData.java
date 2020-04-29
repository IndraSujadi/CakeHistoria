package umn.ac.cakehistoria;

public class TransactionData {

    private String orderno;
    private String orderitem;
    private String orderdate;
    private String price;
    private String doneestimation;

    public TransactionData(String orderno, String orderitem, String orderdate, String price, String doneestimation) {
        this.orderno = orderno;
        this.orderitem = orderitem;
        this.orderdate = orderdate;
        this.price = price;
        this.doneestimation = doneestimation;
    }

    public String getOrderno() {
        return orderno;
    }

    public String getOrderitem() {
        return orderitem;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public String getPrice() {
        return price;
    }

    public String getDoneestimation() {
        return doneestimation;
    }
}
