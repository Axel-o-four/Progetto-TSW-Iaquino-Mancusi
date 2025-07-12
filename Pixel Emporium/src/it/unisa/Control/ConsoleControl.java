package it.unisa.Control;

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
import it.unisa.Model.AccessorioModel;
import it.unisa.Model.AccessorioModelDS;
import it.unisa.Model.Cart;
import it.unisa.Model.ConsoleBean;
import it.unisa.Model.ConsoleModel;
import it.unisa.Model.ConsoleModelDM;
import it.unisa.Model.ConsoleModelDS;
import it.unisa.Model.GiocoBean;
import it.unisa.Model.GiocoModel;
import it.unisa.Model.GiocoModelDM;
import it.unisa.Model.GiocoModelDS;
import com.google.gson.Gson;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,    
    maxFileSize = 1024 * 1024 * 10,          
    maxRequestSize = 1024 * 1024 * 50         
)
public class ConsoleControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;
    static ConsoleModel model;

    static {
        if (isDataSource) {
            model = new ConsoleModelDS();
        } else {
            model = new ConsoleModelDM();
        }
    }

    public ConsoleControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        
        String action = request.getParameter("action");
	    if ("search".equalsIgnoreCase(action)) {
	        String term = request.getParameter("term");
	        if (term == null) term = "";
	
         // invoca il model (vedi punto 3)
	        Collection<ConsoleBean> risultati;
	        try {
	            risultati = model.doRetrieveByNameOrDescription(term);
	        } catch (SQLException e) {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB error");
	            return;
	        }
	        String json = new Gson().toJson(risultati);
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	        return;
	    }

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("addC")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String qtyStr = request.getParameter("quantity");
                    int quantity = 1;
                    if(qtyStr != null && !qtyStr.isEmpty()){
                        quantity = Integer.parseInt(qtyStr);
                    }
                    ConsoleBean product = model.doRetrieveByKey(id);
                    if(product != null) {
                        cart.addProduct(product, quantity);
                    }
                } else if (action.equalsIgnoreCase("deleteC")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    ConsoleBean prod = model.doRetrieveByKey(id);
                    if(prod != null) {
                        cart.deleteProduct(prod);
                    }
                } else if (action.equalsIgnoreCase("read")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.removeAttribute("console");
                    request.setAttribute("console", model.doRetrieveByKey(id));
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ConsoleDetail.jsp");
                    dispatcher.forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("edit")) {
                	  int id = Integer.parseInt(request.getParameter("id"));
                	  ConsoleBean bean = model.doRetrieveByKey(id);
                	  request.setAttribute("console", bean);
                	  RequestDispatcher rd = request.getRequestDispatcher("/admin/ConsoleForm.jsp");
                	  rd.forward(request, response);
                	  return;
                } else if ("update".equalsIgnoreCase(action)) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String name        = request.getParameter("name");
                    String description = request.getParameter("description");
                    Part filePart      = request.getPart("image");
                    String fileName    = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String image;
                    if (fileName == null || fileName.isEmpty()) {
                        image = request.getParameter("currentImage");
                    } else {
                        String imagesPath = getServletContext().getRealPath("") + File.separator + "images";
                        filePart.write(imagesPath + File.separator + fileName);
                        image = "images" + File.separator + fileName;
                    }
                    String brand       = request.getParameter("brand");
                    double price       = Double.parseDouble(request.getParameter("price"));
                    int releaseYear    = Integer.parseInt(request.getParameter("releaseYear"));
                    String support     = request.getParameter("support");
                    boolean retro      = Boolean.parseBoolean(request.getParameter("retroCompatibility"));
                    String storage     = request.getParameter("storage");
                    int generation     = Integer.parseInt(request.getParameter("generation"));
                    int quantity       = Integer.parseInt(request.getParameter("quantity"));

                    ConsoleBean bean = new ConsoleBean();
                    bean.setCode(id);
                    bean.setName(name);
                    bean.setDescription(description);
                    bean.setImage(image);
                    bean.setBrand(brand);
                    bean.setPrice(price);
                    bean.setReleaseYear(releaseYear);
                    bean.setSupport(support);
                    bean.setRetroCompatibility(retro);
                    bean.setStorage(storage);
                    bean.setGeneration(generation);
                    bean.setQuantity(quantity);

                    model.doUpdate(bean);
                }  else if (action.equalsIgnoreCase("delete")) {
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
                    int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
                    String support = request.getParameter("support");
                    boolean retroCompatibility = Boolean.parseBoolean(request.getParameter("retroCompatibility"));
                    String storage = request.getParameter("storage");
                    int generation = Integer.parseInt(request.getParameter("generation"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    ConsoleBean bean = new ConsoleBean();
                    bean.setName(name);
                    bean.setDescription(description);
                    bean.setImage(image);
                    bean.setBrand(brand);
                    bean.setPrice(price);
                    bean.setReleaseYear(releaseYear);
                    bean.setSupport(support);
                    bean.setRetroCompatibility(retroCompatibility);
                    bean.setStorage(storage);
                    bean.setGeneration(generation);
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
            request.removeAttribute("consoles");
            request.setAttribute("consoles", model.doRetrieveAll(sort));
            GiocoModel giocoModel = isDataSource ? new GiocoModelDS() : new GiocoModelDM();
            request.setAttribute("giochi", giocoModel.doRetrieveAll(sort));
            AccessorioModel accessorioModel = isDataSource ? new AccessorioModelDS() : new AccessorioModelDS();
            request.setAttribute("accessori", accessorioModel.doRetrieveAll(sort));
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
