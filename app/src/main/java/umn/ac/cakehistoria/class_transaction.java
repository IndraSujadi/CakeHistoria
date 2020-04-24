package umn.ac.cakehistoria;

import java.util.Date;

public class class_transaction {
    private String transactionID;
    private String transactionStatus;
    private Date receivedDateTime;
    private String testimonyID;

    public class_transaction(String transactionID, String transactionStatus, Date receivedDateTime, String testimonyID) {
        this.transactionID = transactionID;
        this.transactionStatus = transactionStatus;
        this.receivedDateTime = receivedDateTime;
        this.testimonyID = testimonyID;
    }

    public class_transaction() {
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public String getTestimonyID() {
        return testimonyID;
    }

    public void setTestimonyID(String testimonyID) {
        this.testimonyID = testimonyID;
    }
}
