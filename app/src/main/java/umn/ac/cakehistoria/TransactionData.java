package umn.ac.cakehistoria;

public class TransactionData {

    private String orderID;
    private String orderitem;
    private String orderDate;
    private String orderPrice;
    private String orderReqDate;
    private String orderStatus;

    public TransactionData(String orderID, String orderitem, String orderDate, String orderPrice, String orderReqDate, String orderStatus) {
        this.orderID = orderID;
        this.orderitem = orderitem;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderReqDate = orderReqDate;
        this.orderStatus = orderStatus;
    }

    public TransactionData() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(String orderitem) {
        this.orderitem = orderitem;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderReqDate() {
        return orderReqDate;
    }

    public void setOrderReqDate(String orderReqDate) {
        this.orderReqDate = orderReqDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
