
package Controllers;

import Services.PurchaseHasProductService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PurchaseHasProductController", urlPatterns = {"/PurchaseHasProductController"})
public class PurchaseHasProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            PurchaseHasProductService ps = new PurchaseHasProductService();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("innoteq_cafeteriaPU");
            EntityManager em = emf.createEntityManager();
            
            //create purchaseHasProduct
            if(request.getParameter("task").equals("createPurchaseHasProduct")) {                
                
                Integer productId = null;
                Integer amount = null;
                Integer purchaseId = null;
                Integer purchasePriceTotal = null;
                
                //simple validation just for type
                try {
                productId = Integer.parseInt(request.getParameter("productId"));
                amount = Integer.parseInt(request.getParameter("amount"));
                purchaseId = Integer.parseInt(request.getParameter("purchaseId"));
                purchasePriceTotal = Integer.parseInt(request.getParameter("purchasePriceTotal"));
                    
                } catch (Exception e) {
                    //message to server
                    System.out.println("createPurchaseHasProduct: invalid input type(s)");
                }
                
                if (productId != null && amount != null && purchaseId != null && purchasePriceTotal != null) {
                    
                    ps.createPurchaseHasProduct(em, productId, amount, purchaseId, purchasePriceTotal);                    
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
