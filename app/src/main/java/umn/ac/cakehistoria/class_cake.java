package umn.ac.cakehistoria;

public class class_cake {
    private String cakeID;
    private String imageURL;
    private int likes;
    private String ownerID;
    private String testimonyID;
    private String orderID;

    public class_cake(String cakeID, String imageURL, int likes, String ownerID, String testimonyID, String orderID) {
        this.cakeID = cakeID;
        this.imageURL = imageURL;
        this.likes = likes;
        this.ownerID = ownerID;
        this.testimonyID = testimonyID;
        this.orderID = orderID;
    }

    public class_cake() {

    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
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

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getTestimonyID() {
        return testimonyID;
    }

    public void setTestimonyID(String testimonyID) {
        this.testimonyID = testimonyID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
