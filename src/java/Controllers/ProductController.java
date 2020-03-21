
package Controllers;

import Services.ProductService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

public class ProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            
            ProductService ps = new ProductService();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("innoteq_cafeteriaPU");
            EntityManager em = emf.createEntityManager();
            
            //get all products
            if (request.getParameter("task").equals("getAllProducts")) {
                
                JSONArray employees = ps.getAllProducts(em);
                out.print(employees.toString());                
            }
            
            //get all product sold amount
            if (request.getParameter("task").equals("getProductAmountSoldByMonth")) {
                
                Integer month = null;
                
                //simple validation just for type
                try {
                    
                month = Integer.parseInt(request.getParameter("month"));
                    
                } catch (Exception e) {
                    //message to server
                    System.out.println("getProductAmountSoldByMonth: invalid input type");
                
                }                
                
                if (month != null) {
                JSONArray productAmountSold = ps.getProductAmountSoldByMonth(em, month);
                out.print(productAmountSold.toString());
                }
            }            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setContentType("application:json;charset=UTF-8");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
