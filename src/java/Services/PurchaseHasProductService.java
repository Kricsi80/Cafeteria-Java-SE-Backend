
package Services;

import Models.PurchaseHasProduct;
import javax.persistence.EntityManager;

public class PurchaseHasProductService {
    
    public boolean createPurchaseHasProduct(EntityManager em, int productId, int amount, int purchaseId, int purchasePriceTotal) {
    
        return PurchaseHasProduct.createPurchaseHasProduct(em, productId, amount, purchaseId,purchasePriceTotal);
    }
    
}
