
package Services;

import Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class ProductService {
    
    public JSONArray getAllProducts(EntityManager em) {
        
        List<Product> products = Product.getAllPRoducts(em);
        
        JSONArray productList = new JSONArray();
        
        for (Product product : products) {
            
            productList.put(product.toJson());
        }
        return productList;
    }
    
    public JSONArray getProductAmountSoldByMonth(EntityManager em, int month){
    
        JSONArray productAmountSold = Product.getProductAmountSoldByMonth(em, month);
        
        return productAmountSold;
    }
}
