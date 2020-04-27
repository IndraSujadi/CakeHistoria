package umn.ac.cakehistoria;

import java.util.Date;

public class class_order {
    private String orderID;
    private String cakeID;
    private String userID;
    private Date orderDateTime;
    private String orderName;
    private int cakePrice;
    private int deliveryPrice;
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

    private Boolean asuransiPengiriman;

    // Kalo paymentnya kelar:
    private String paymentID;
    private int paymentMethod;
}
