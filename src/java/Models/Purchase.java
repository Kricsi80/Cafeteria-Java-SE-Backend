
package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "purchase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p")
    , @NamedQuery(name = "Purchase.findById", query = "SELECT p FROM Purchase p WHERE p.id = :id")
    , @NamedQuery(name = "Purchase.findByCreatedAt", query = "SELECT p FROM Purchase p WHERE p.createdAt = :createdAt")})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    private Employee employeeId;
    @OneToMany(mappedBy = "purchaseId")
    private Collection<PurchaseHasProduct> purchaseHasProductCollection;

    public Purchase() {
    }

    public Purchase(Integer id) {
        this.id = id;
    }

    public Purchase(Integer id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    @XmlTransient
    public Collection<PurchaseHasProduct> getPurchaseHasProductCollection() {
        return purchaseHasProductCollection;
    }

    public void setPurchaseHasProductCollection(Collection<PurchaseHasProduct> purchaseHasProductCollection) {
        this.purchaseHasProductCollection = purchaseHasProductCollection;
    }
    
    //own code
    
    //create purchase
    public static int createPurchase(EntityManager em, int employeeId) {
        
        int purchaseId = -1;
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("create_purchase");
            spq.registerStoredProcedureParameter("employee_id_in", Integer.class, ParameterMode.IN);
            spq.setParameter("employee_id_in", employeeId);
            
            List<Object[]> resultList = spq.getResultList();
        
            for (Object[] object : resultList) {
                
                purchaseId = Integer.parseInt(object[0].toString());               
            }
        } catch (Exception e) {            
        }
        
        return purchaseId;
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
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Purchase[ id=" + id + " ]";
    }
    
}
