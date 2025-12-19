package project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Stock {

    private Map<String, Product> products;
    private int lastIndex;
    
    public Stock() {
        products = new HashMap<>();
        lastIndex = 1;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }
    
    public void addProduct(String name, int quantity) {
        
        String id = "PROD" + lastIndex;
        Date currentDate = new Date();
        
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setEntryDate(currentDate);
        product.setQuantity(quantity);
        product.insertAddToStockActivity(quantity, currentDate);
        
        products.put(id, product);
        
        lastIndex++;
    }
    
    public Product removeProduct(String id) {
        return products.remove(id);
    }
    
    public void addToStock(String id, int quantity){
        
        Product product = search(id);
        
        if (product != null) {
            Date currentDate = new Date(); 
            product.addToQuantity(quantity);
            product.insertAddToStockActivity(quantity, currentDate);
        }
    }
    
    public void removeFromStock(String id, int quantity){
        
        Product product = search(id);
        
        if (product != null) {
            Date currentDate = new Date(); 
            product.removeFromQuantity(quantity);
            product.insertRemoveFromStockActivity(quantity, currentDate);
        }
    }
    
    private Product search(String id) {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            String key = entry.getKey();
            if (key.equals(id)) {
                return entry.getValue();
            }
        }
        return null;
    }
       
}
