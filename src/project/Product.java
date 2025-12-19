package project;

import java.util.Date;

public class Product {
    
    private String id;
    private String name;
    private Date entryDate;
    private int quantity;
    private ActivityList activities;
    private int lastActivityIndex;
    
    public Product() {
        activities = new ActivityList();
        lastActivityIndex = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ActivityList getActivities() {
        return activities;
    }

    public void setActivities(ActivityList activities) {
        this.activities = activities;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Qty: %d", id, name, quantity);
    }
    
    public void addToQuantity(int quantity) {
        this.quantity += quantity;
    }
    
    public void removeFromQuantity(int quantity) {
        int newQuantity = this.quantity - quantity;
        if (newQuantity < 0) {
            return;
        }
        this.quantity = newQuantity;
    }
    
    public void insertAddToStockActivity(int quantity, Date date) {
        insertActivity("AddToStock", quantity, date);
    }
    
    public void insertRemoveFromStockActivity(int quantity, Date date) {
        insertActivity("RemoveFromStock", quantity, date);
    }
    
    private void insertActivity(String name, int quantity, Date date) {
        
        String activityId = "ACT" + lastActivityIndex;
        
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setName(name);
        activity.setQuantity(quantity);
        activity.setDate(date);
        
        activities.addLast(activity);
        
        lastActivityIndex++;
    }
    
}    
