package umn.ac.cakehistoria;

import java.util.Date;

public class class_order {
    private String orderID;
    private String userID;
    private Date orderDateTime;
    private int paymentMethod;
    private String paymentID;

    // Cake Details
    private String category;
    private String cakeType;
    private String color;
    private String coatingDecor;
    private String theme;
    private String flavor;
    private String tier;
    private String shape;
    private String size;
    private String figureURL;
    private String topText;
    private String specialOrders;
    private String requestDate;
    private boolean includeLetterCard;
    private String letterMessage;

    // Prices
    private int productPrice;
    private int deliveryPrice;
    private int totalPrice;

    // Receiver's Info
    private String receiverName;
    private String phone;
    private String email;
    private String addressLine;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private int kodePos;
    private String fullAddress;
    private boolean asuransi;

    public class_order(String orderID, String userID, Date orderDateTime, int paymentMethod, String paymentID, String category,
                       String cakeType, String color, String coatingDecor, String theme, String flavor, String tier, String shape,
                       String size, String figureURL, String topText, String specialOrders, String requestDate, boolean includeLetterCard,
                       String letterMessage, int productPrice, int deliveryPrice, int totalPrice, String receiverName, String phone,
                       String email, String addressLine, String provinsi, String kota, String kecamatan, int kodePos, String fullAddress,
                       boolean asuransi) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDateTime = orderDateTime;
        this.paymentMethod = paymentMethod;
        this.paymentID = paymentID;
        this.category = category;
        this.cakeType = cakeType;
        this.color = color;
        this.coatingDecor = coatingDecor;
        this.theme = theme;
        this.flavor = flavor;
        this.tier = tier;
        this.shape = shape;
        this.size = size;
        this.figureURL = figureURL;
        this.topText = topText;
        this.specialOrders = specialOrders;
        this.requestDate = requestDate;
        this.includeLetterCard = includeLetterCard;
        this.letterMessage = letterMessage;
        this.productPrice = productPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.receiverName = receiverName;
        this.phone = phone;
        this.email = email;
        this.addressLine = addressLine;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kodePos = kodePos;
        this.fullAddress = fullAddress;
        this.asuransi = asuransi;
    }

    public class_order() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCakeType() {
        return cakeType;
    }

    public void setCakeType(String cakeType) {
        this.cakeType = cakeType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCoatingDecor() {
        return coatingDecor;
    }

    public void setCoatingDecor(String coatingDecor) {
        this.coatingDecor = coatingDecor;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFigureURL() {
        return figureURL;
    }

    public void setFigureURL(String figureURL) {
        this.figureURL = figureURL;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getSpecialOrders() {
        return specialOrders;
    }

    public void setSpecialOrders(String specialOrders) {
        this.specialOrders = specialOrders;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isIncludeLetterCard() {
        return includeLetterCard;
    }

    public void setIncludeLetterCard(boolean includeLetterCard) {
        this.includeLetterCard = includeLetterCard;
    }

    public String getLetterMessage() {
        return letterMessage;
    }

    public void setLetterMessage(String letterMessage) {
        this.letterMessage = letterMessage;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public int getKodePos() {
        return kodePos;
    }

    public void setKodePos(int kodePos) {
        this.kodePos = kodePos;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isAsuransi() {
        return asuransi;
    }

    public void setAsuransi(boolean asuransi) {
        this.asuransi = asuransi;
    }
}
