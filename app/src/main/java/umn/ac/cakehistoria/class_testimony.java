package umn.ac.cakehistoria;

public class class_testimony {
    private String testimonyID;
    private String transactionID;
    private String userID;
    private String testimonyText;
    private int rating;

    public class_testimony(String testimonyID, String transactionID, String userID, String testimonyText, int rating) {
        this.testimonyID = testimonyID;
        this.transactionID = transactionID;
        this.userID = userID;
        this.testimonyText = testimonyText;
        this.rating = rating;
    }

    public class_testimony() {
    }

    public String getTestimonyID() {
        return testimonyID;
    }

    public void setTestimonyID(String testimonyID) {
        this.testimonyID = testimonyID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTestimonyText() {
        return testimonyText;
    }

    public void setTestimonyText(String testimonyText) {
        this.testimonyText = testimonyText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
