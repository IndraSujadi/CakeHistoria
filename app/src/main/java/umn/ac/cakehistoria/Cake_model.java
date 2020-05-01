package umn.ac.cakehistoria;

public class Cake_model {

    // Cake Details
    private String addtText;
    private String cakeCategory;
    private String cakeColor;
    private String cakeDecor;
    private String cakeFlavor;
    private String cakeShape;
    private String cakeSize;
    private String cakeTheme;
    private String cakeTier;
    private String cakeType;
    private String figureURL;
    private boolean includeLetterCard;
    private String letterMessage;
    private String specialOrders;

    // Cakes
    //private String cakeCategory;
    private String owner;
    private int cakePrice;
    private String imageURL;
    private int likes;
    private String orderID;

    // Testimony
    private int rating;
    private String testimonyText;


    public Cake_model() {

    }

    public Cake_model(String addtText, String cakeCategory, String cakeColor, String cakeDecor, String cakeFlavor,
                      String cakeShape, String cakeSize, String cakeTheme, String cakeTier, String cakeType, String figureURL,
                      boolean includeLetterCard, String letterMessage, String specialOrders, String owner, int cakePrice, String imageURL,
                      int likes, String orderID, int rating, String testimonyText) {
        this.addtText = addtText;
        this.cakeCategory = cakeCategory;
        this.cakeColor = cakeColor;
        this.cakeDecor = cakeDecor;
        this.cakeFlavor = cakeFlavor;
        this.cakeShape = cakeShape;
        this.cakeSize = cakeSize;
        this.cakeTheme = cakeTheme;
        this.cakeTier = cakeTier;
        this.cakeType = cakeType;
        this.figureURL = figureURL;
        this.includeLetterCard = includeLetterCard;
        this.letterMessage = letterMessage;
        this.specialOrders = specialOrders;
        this.owner = owner;
        this.cakePrice = cakePrice;
        this.imageURL = imageURL;
        this.likes = likes;
        this.orderID = orderID;
        this.rating = rating;
        this.testimonyText = testimonyText;
    }

    public String getAddtText() {
        return addtText;
    }

    public void setAddtText(String addtText) {
        this.addtText = addtText;
    }

    public String getCakeCategory() {
        return cakeCategory;
    }

    public void setCakeCategory(String cakeCategory) {
        this.cakeCategory = cakeCategory;
    }

    public String getCakeColor() {
        return cakeColor;
    }

    public void setCakeColor(String cakeColor) {
        this.cakeColor = cakeColor;
    }

    public String getCakeDecor() {
        return cakeDecor;
    }

    public void setCakeDecor(String cakeDecor) {
        this.cakeDecor = cakeDecor;
    }

    public String getCakeFlavor() {
        return cakeFlavor;
    }

    public void setCakeFlavor(String cakeFlavor) {
        this.cakeFlavor = cakeFlavor;
    }

    public String getCakeShape() {
        return cakeShape;
    }

    public void setCakeShape(String cakeShape) {
        this.cakeShape = cakeShape;
    }

    public String getCakeSize() {
        return cakeSize;
    }

    public void setCakeSize(String cakeSize) {
        this.cakeSize = cakeSize;
    }

    public String getCakeTheme() {
        return cakeTheme;
    }

    public void setCakeTheme(String cakeTheme) {
        this.cakeTheme = cakeTheme;
    }

    public String getCakeTier() {
        return cakeTier;
    }

    public void setCakeTier(String cakeTier) {
        this.cakeTier = cakeTier;
    }

    public String getCakeType() {
        return cakeType;
    }

    public void setCakeType(String cakeType) {
        this.cakeType = cakeType;
    }

    public String getFigureURL() {
        return figureURL;
    }

    public void setFigureURL(String figureURL) {
        this.figureURL = figureURL;
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

    public String getSpecialOrders() {
        return specialOrders;
    }

    public void setSpecialOrders(String specialOrders) {
        this.specialOrders = specialOrders;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(int cakePrice) {
        this.cakePrice = cakePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTestimonyText() {
        return testimonyText;
    }

    public void setTestimonyText(String testimonyText) {
        this.testimonyText = testimonyText;
    }
}
