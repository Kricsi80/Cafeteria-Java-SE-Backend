
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id")
    , @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @OneToMany(mappedBy = "productId")
    private Collection<PurchaseHasProduct> purchaseHasProductCollection;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<PurchaseHasProduct> getPurchaseHasProductCollection() {
        return purchaseHasProductCollection;
    }

    public void setPurchaseHasProductCollection(Collection<PurchaseHasProduct> purchaseHasProductCollection) {
        this.purchaseHasProductCollection = purchaseHasProductCollection;
    }
    
    //own code
    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        j.put("id", this.id);
        j.put("name", this.name);
        j.put("price", this.price);
        return j;
    }
    
    //get all products
    public static List<Product> getAllPRoducts(EntityManager em) {
        
        List<Product> products = new ArrayList();
        
        StoredProcedureQuery spq = em.createStoredProcedureQuery("get_all_products");
        
        List<Object[]> resultList = spq.getResultList();
        
        for (Object[] result : resultList) {
            int id = Integer.parseInt(result[0].toString());
            Product product = em.find(Product.class, id);
            products.add(product);            
        }
        
        return products;        
    }
    
    //get product amount sold
    public static JSONArray getProductAmountSoldByMonth(EntityManager em, int month) {
    
        StoredProcedureQuery spq = em.createStoredProcedureQuery("get_product_amount_sold_by_month");
        spq.registerStoredProcedureParameter("month_in", Integer.class, ParameterMode.IN);
        spq.setParameter("month_in", month);
        
        JSONArray productAmountSold = new JSONArray();
        
        List<Object[]> resultList = spq.getResultList();
        
        for (Object[] result : resultList) {
            
            JSONObject prodSold = new JSONObject();
            String productName = result[0].toString();
            int amountSold = Integer.parseInt(result[1].toString());
            
            prodSold.put("productName", productName);
            prodSold.put("amountSold", amountSold);
            productAmountSold.put(prodSold);            
        }
        
        return productAmountSold;        
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Product[ id=" + id + " ]";
    }
    
}
