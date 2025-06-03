package it.unisa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import it.unisa.AccessorioBean;
import it.unisa.AccessorioModel;
import it.unisa.AccessorioModelDS;
import it.unisa.AccessorioModelDM;
import it.unisa.Cart;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   
    maxFileSize = 1024 * 1024 * 10,          
    maxRequestSize = 1024 * 1024 * 50         
)
public class AccessorioControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;
    static AccessorioModel model;

    static {
        if (isDataSource) {
            model = new AccessorioModelDS();
        } else {
            model = new AccessorioModelDM();
        }
    }

    public AccessorioControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("addC")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String qtyStr = request.getParameter("quantity");
                    int quantity = 1;
                    if (qtyStr != null && !qtyStr.isEmpty()) {
                        quantity = Integer.parseInt(qtyStr);
                    }
                    cart.addProduct(model.doRetrieveByKey(id), quantity);
                } else if (action.equalsIgnoreCase("deleteC")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    cart.deleteProduct(model.doRetrieveByKey(id));
                } else if (action.equalsIgnoreCase("read")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.removeAttribute("accessorio");
                    request.setAttribute("accessorio", model.doRetrieveByKey(id));
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AccessorioDetail.jsp");
                    dispatcher.forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("delete")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    model.doDelete(id);
                } else if (action.equalsIgnoreCase("insert")) {
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");

                    Part filePart = request.getPart("image");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String imagesPath = getServletContext().getRealPath("") + File.separator + "images";
                    File uploadDir = new File(imagesPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    filePart.write(imagesPath + File.separator + fileName);
                    String image = "images" + File.separator + fileName;
                    
                    String brand = request.getParameter("brand");
                    double price = Double.parseDouble(request.getParameter("price"));
                    String accessoryType = request.getParameter("accessoryType");
                    
                    String quantityStr = request.getParameter("quantity");
                    int quantity = 1;
                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        quantity = Integer.parseInt(quantityStr);
                    }

                    AccessorioBean bean = new AccessorioBean();
                    bean.setName(name);
                    bean.setDescription(description);
                    bean.setImage(image);
                    bean.setBrand(brand);
                    bean.setPrice(price);
                    bean.setAccessoryType(accessoryType);
                    bean.setQuantity(quantity);

                    model.doSave(bean);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        request.getSession().setAttribute("cart", cart);
        request.setAttribute("cart", cart);

        String sort = request.getParameter("sort");
        try {
            request.removeAttribute("accessori");
            Collection<AccessorioBean> accessori = model.doRetrieveAll(sort);
            request.setAttribute("accessori", accessori);
            GiocoModel giocoModel = isDataSource ? new GiocoModelDS() : new GiocoModelDM();
            request.setAttribute("giochi", giocoModel.doRetrieveAll(sort));
            ConsoleModel consoleModel = isDataSource ? new ConsoleModelDS() : new ConsoleModelDM();
            request.setAttribute("consoles", consoleModel.doRetrieveAll(sort));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CatalogView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
