package it.unisa;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;

    static ProductModel model;

    static {
        if (isDataSource) {
            model = new ProductModelDS();
        } else {
            model = new ProductModelDM();
        }
    }

    public ProductControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("read")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String prefissoId = request.getParameter("prefissoId");
                    request.removeAttribute("product");
                    request.setAttribute("product", model.doRetrieveByKey(id, prefissoId));
                } else if (action.equalsIgnoreCase("delete")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String prefissoId = request.getParameter("prefissoId");
                    model.doDelete(id, prefissoId);
                } else if (action.equalsIgnoreCase("insert")) {
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    double price = Double.parseDouble(request.getParameter("price"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    String prefissoId = request.getParameter("prefissoId");
                    int id = Integer.parseInt(request.getParameter("id"));

                    ProductBean bean = new ProductBean();
                    bean.setName(name);
                    bean.setDescription(description);
                    bean.setPrice(price);
                    bean.setQuantity(quantity);
                    bean.setPrefissoId(prefissoId);
                    bean.setCode(id);
                    model.doSave(bean);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");
            request.setAttribute("products", model.doRetrieveAll(sort));
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
