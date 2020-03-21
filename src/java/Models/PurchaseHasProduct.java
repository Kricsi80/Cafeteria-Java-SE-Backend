
package Models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "purchase_has_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchaseHasProduct.findAll", query = "SELECT p FROM PurchaseHasProduct p")
    , @NamedQuery(name = "PurchaseHasProduct.findById", query = "SELECT p FROM PurchaseHasProduct p WHERE p.id = :id")
    , @NamedQuery(name = "PurchaseHasProduct.findByAmount", query = "SELECT p FROM PurchaseHasProduct p WHERE p.amount = :amount")
    , @NamedQuery(name = "PurchaseHasProduct.findByPurchasePriceTotal", query = "SELECT p FROM PurchaseHasProduct p WHERE p.purchasePriceTotal = :purchasePriceTotal")})
public class PurchaseHasProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purchase_price_total")
    private int purchasePriceTotal;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne
    private Product productId;
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    @ManyToOne
    private Purchase purchaseId;

    public PurchaseHasProduct() {
    }

    public PurchaseHasProduct(Integer id) {
        this.id = id;
    }

    public PurchaseHasProduct(Integer id, int amount, int purchasePriceTotal) {
        this.id = id;
        this.amount = amount;
        this.purchasePriceTotal = purchasePriceTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPurchasePriceTotal() {
        return purchasePriceTotal;
    }

    public void setPurchasePriceTotal(int purchasePriceTotal) {
        this.purchasePriceTotal = purchasePriceTotal;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Purchase getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Purchase purchaseId) {
        this.purchaseId = purchaseId;
    }
    
    //own code
    
    //create purchaseHasProduct
    public static boolean createPurchaseHasProduct(EntityManager em, int productId, int amount, int purchaseId, int purchasePriceTotal){
        try {
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("create_purchase_has_product");
            spq.registerStoredProcedureParameter("product_id_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("amount_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("purchase_id_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("purchase_price_total_in", Integer.class, ParameterMode.IN);
            spq.setParameter("product_id_in", productId);
            spq.setParameter("amount_in", amount);
            spq.setParameter("purchase_id_in", purchaseId);
            spq.setParameter("purchase_price_total_in", purchasePriceTotal);
            
            spq.execute();
            
            return true;
            
        } catch (Exception e) {
            
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseHasProduct)) {
            return false;
        }
        PurchaseHasProduct other = (PurchaseHasProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.PurchaseHasProduct[ id=" + id + " ]";
    }
    
}
