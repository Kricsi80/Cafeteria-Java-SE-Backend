
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
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id")
    , @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name")})
public class Employee implements Serializable {

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
    @OneToMany(mappedBy = "employeeId")
    private Collection<Purchase> purchaseCollection;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public Collection<Purchase> getPurchaseCollection() {
        return purchaseCollection;
    }

    public void setPurchaseCollection(Collection<Purchase> purchaseCollection) {
        this.purchaseCollection = purchaseCollection;
    }
    
    //own code
    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        j.put("id", this.id);
        j.put("name", this.name);
        return j;
    }
    
    
    //get all employees
    public static List<Employee> getAllEmployees(EntityManager em) {
        
        List<Employee> employees = new ArrayList();
        
        StoredProcedureQuery spq = em.createStoredProcedureQuery("get_all_employees");
        
        List<Object[]> resultList = spq.getResultList();
        
        for (Object[] result : resultList) {
            int id = Integer.parseInt(result[0].toString());
            Employee employee = em.find(Employee.class, id);
            employees.add(employee);
        }
        
        return employees;
    }
    
    //get all employees debt
    public static JSONArray getDebtOfAllEmployeesByMonth(EntityManager em, int month) {
        
        StoredProcedureQuery spq = em.createStoredProcedureQuery("get_debt_of_all_employees_by_month");
        spq.registerStoredProcedureParameter("month_in", Integer.class, ParameterMode.IN);
        spq.setParameter("month_in", month);        
        
        JSONArray employeeDebts = new JSONArray();
        
        List<Object[]> resultList = spq.getResultList();
        
        for (Object[] result : resultList) {
            
            JSONObject employeeDebt = new JSONObject();
            String employeeName = result[0].toString();
            int debt = Integer.parseInt(result[1].toString());
            
            employeeDebt.put("employeeName", employeeName);
            employeeDebt.put("debt", debt);
            employeeDebts.put(employeeDebt);
        }
        
        return employeeDebts;    
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Employee[ id=" + id + " ]";
    }
    
}
