package umn.ac.cakehistoria;

import java.util.Date;

public class class_order {
    private String orderID;
    private String cakeID;
    private String userID;
    private Date orderDateTime;
    private String orderName; // Belom masuk ke db
    private int cakePrice;
    private int delivPrice;
    private int totalPrice;

//    private Date requestDate;
    private String requestDate;

    private String orderStatus;

    // Receiver's Detail:
    private String receiverName;
    private String receiverPhone;
    private String receiverEmail;
    private String receiverProvinsi;
    private String receiverKota;
    private String receiverKecamatan;
    private String receiverZip;
    private String receiverFullAddress;

    private Boolean includeInsurance;

    // Kalo paymentnya kelar:
//    private String paymentID;
//    private int paymentMethod;


    public class_order() {

    }

    public class_order(String orderID, String cakeID, String userID, Date orderDateTime, String orderName, int cakePrice
            , int delivPrice, int totalPrice, String requestDate, String orderStatus, String receiverName, String receiverPhone
            , String receiverEmail, String receiverProvinsi, String receiverKota, String receiverKecamatan, String receiverZip
            , String receiverFullAddress, Boolean includeInsurance) {
        this.orderID = orderID;
        this.cakeID = cakeID;
        this.userID = userID;
        this.orderDateTime = orderDateTime;
        this.orderName = orderName;
        this.cakePrice = cakePrice;
        this.delivPrice = delivPrice;
        this.totalPrice = totalPrice;
        this.requestDate = requestDate;
        this.orderStatus = orderStatus;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverEmail = receiverEmail;
        this.receiverProvinsi = receiverProvinsi;
        this.receiverKota = receiverKota;
        this.receiverKecamatan = receiverKecamatan;
        this.receiverZip = receiverZip;
        this.receiverFullAddress = receiverFullAddress;
        this.includeInsurance = includeInsurance;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(int cakePrice) {
        this.cakePrice = cakePrice;
    }

    public int getDelivPrice() {
        return delivPrice;
    }

    public void setDelivPrice(int delivPrice) {
        this.delivPrice = delivPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverProvinsi() {
        return receiverProvinsi;
    }

    public void setReceiverProvinsi(String receiverProvinsi) {
        this.receiverProvinsi = receiverProvinsi;
    }

    public String getReceiverKota() {
        return receiverKota;
    }

    public void setReceiverKota(String receiverKota) {
        this.receiverKota = receiverKota;
    }

    public String getReceiverKecamatan() {
        return receiverKecamatan;
    }

    public void setReceiverKecamatan(String receiverKecamatan) {
        this.receiverKecamatan = receiverKecamatan;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getReceiverFullAddress() {
        return receiverFullAddress;
    }

    public void setReceiverFullAddress(String receiverFullAddress) {
        this.receiverFullAddress = receiverFullAddress;
    }

    public Boolean getIncludeInsurance() {
        return includeInsurance;
    }

    public void setIncludeInsurance(Boolean includeInsurance) {
        this.includeInsurance = includeInsurance;
    }
}
