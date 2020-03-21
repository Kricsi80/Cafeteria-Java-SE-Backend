
package Services;

import Models.Purchase;
import javax.persistence.EntityManager;

public class PurchaseService {
    
    public int createPurchase(EntityManager em, int employeeId){
    
        int purchaseId = Purchase.createPurchase(em, employeeId);
        
        return purchaseId;
    }
    
}
