
package Services;

import Models.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class EmployeeService {
    
    public JSONArray getAllEmployees(EntityManager em) {
        
        List<Employee> employees = Employee.getAllEmployees(em);
        
        JSONArray employeeList = new JSONArray();
        
        for (Employee employee : employees) {
            
            employeeList.put(employee.toJson());
        }
        return employeeList;
    }
    
    public JSONArray getDebtOfAllEmployeesByMonth(EntityManager em, int month){        
    
        JSONArray employeeDebts = Employee.getDebtOfAllEmployeesByMonth(em, month);
        
        return employeeDebts;
    }
}
