package it.unisa.Control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import it.unisa.Model.UserBean;
import it.unisa.Model.AccessorioBean;
import it.unisa.Model.AccessorioModel;
import it.unisa.Model.AccessorioModelDM;
import it.unisa.Model.AccessorioModelDS;
import it.unisa.Model.Cart;
import it.unisa.Model.ConsoleBean;
import it.unisa.Model.ConsoleModel;
import it.unisa.Model.ConsoleModelDM;
import it.unisa.Model.ConsoleModelDS;
import it.unisa.Model.DettaglioOrdineBean;
import it.unisa.Model.DettaglioOrdineModel;
import it.unisa.Model.DettaglioOrdineModelDS;
import it.unisa.Model.GiocoBean;
import it.unisa.Model.GiocoModel;
import it.unisa.Model.GiocoModelDM;
import it.unisa.Model.GiocoModelDS;
import it.unisa.Model.Item;
import it.unisa.Model.OrdineBean;
import it.unisa.Model.OrdineModel;
import it.unisa.Model.OrdineModelDM;
import it.unisa.Model.OrdineModelDS;

public class OrdineControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;
    static OrdineModel model;

    static {
        if (isDataSource) {
            model = new OrdineModelDS();
        } else {
            model = new OrdineModelDM();
        }
    }

    public OrdineControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String fromParam = request.getParameter("from");
    	String toParam   = request.getParameter("to");

    	Date fromDate = null;
    	Date toDate   = null;

    	try {
    	    if (fromParam != null && !fromParam.isEmpty()) {
    	        LocalDate ldFrom = LocalDate.parse(fromParam);
    	        fromDate = Date.valueOf(ldFrom);
    	    }
    	    if (toParam != null && !toParam.isEmpty()) {
    	        LocalDate ldTo = LocalDate.parse(toParam);
    	        toDate = Date.valueOf(ldTo);
    	    }
    	} catch (DateTimeParseException e) {
    	    request.setAttribute("dateError", "Formato data non valido (yyyy-MM-dd)");
    	}

    	HttpSession session = request.getSession();
    	UserBean user = (UserBean) session.getAttribute("user");
    	if (user == null) {
    	    response.sendRedirect(request.getContextPath() + "/login.jsp");
    	    return;
    	}
    	String emailUtente = user.getEmail();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        try {
            if (action != null) {

                if (action.equalsIgnoreCase("insert")) {
                	if (cart == null || cart.getTotalQuantity() == 0) {
                        request.setAttribute("msgErrore", "Il carrello è vuoto. Aggiungi almeno un articolo.");
                        RequestDispatcher rd = request.getRequestDispatcher("/CartView.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    String paese = request.getParameter("paese");
                    String citta = request.getParameter("citta");
                    String cap = request.getParameter("cap");
                    String provincia = request.getParameter("provincia");
                    String via = request.getParameter("via");
                    String numeroCivico = request.getParameter("numeroCivico");

                    String tipoPagamentoParam = request.getParameter("tipoPagamento");
                    char tipoPagamento;
                    if (tipoPagamentoParam != null && !tipoPagamentoParam.isEmpty()) {
                        tipoPagamento = tipoPagamentoParam.charAt(0);
                    } else {
                        tipoPagamento = 'C';
                    }

                    String numeroCarta = request.getParameter("numeroCarta");

                    Date scadenzaCarta = null;
                    String scadenzaStr = request.getParameter("scadenzaCarta");
                    if (scadenzaStr != null && !scadenzaStr.isEmpty()) {
                        String[] parts = scadenzaStr.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year  = Integer.parseInt(parts[1]);
                        LocalDate ld = LocalDate.of(year, month, 1);
                        scadenzaCarta = Date.valueOf(ld);
                    }

                    String cvv = request.getParameter("cvv");
                    String emailPaypal = request.getParameter("emailPaypal");

                    int quantita = cart.getTotalQuantity();
                    double importo = cart.getTotalPrice();
                    double ivaRate = 0.22;
                    double totaleIva = importo + (importo * ivaRate);
                    double totaleFattura = totaleIva;

                    Date dataOrdine = new Date(System.currentTimeMillis());

                    OrdineBean ordine = new OrdineBean();
                    ordine.setEmailUtente(emailUtente);
                    ordine.setDataOrdine(dataOrdine);
                    ordine.setQuantita(quantita);
                    ordine.setImporto(importo);
                    ordine.setIva(ivaRate);
                    ordine.setTotaleIva(totaleIva);
                    ordine.setTotaleFattura(totaleFattura);
                    ordine.setPaese(paese);
                    ordine.setCitta(citta);
                    ordine.setCap(cap);
                    ordine.setProvincia(provincia);
                    ordine.setVia(via);
                    ordine.setNumeroCivico(numeroCivico);
                    ordine.setTipoPagamento(tipoPagamento);
                    ordine.setNumeroCarta(numeroCarta);
                    ordine.setScadenzaCarta(scadenzaCarta);
                    ordine.setCvv(cvv);
                    ordine.setEmailPaypal(emailPaypal);                    
                
                    model.doSave(ordine);
                    GiocoModel giocoModel = isDataSource ? new GiocoModelDS() : new GiocoModelDM();
                    ConsoleModel consoleModel = isDataSource ? new ConsoleModelDS() : new ConsoleModelDM();
                    AccessorioModel accessorioModel = isDataSource ? new AccessorioModelDS() : new AccessorioModelDM();
                    for (Item item : cart.getProducts()) {
                        if (item instanceof GiocoBean) {
                            GiocoBean giocoInCart = (GiocoBean) item;
                            GiocoBean original = giocoModel.doRetrieveByKey(giocoInCart.getCode());
                            if (original != null) {
                                giocoModel.updateStock(original.getCode(), giocoInCart.getQuantity());
                            }
                        } else if (item instanceof ConsoleBean) {
                            ConsoleBean consoleInCart = (ConsoleBean) item;
                            ConsoleBean original = consoleModel.doRetrieveByKey(consoleInCart.getCode());
                            if (original != null) {
                                consoleModel.updateStock(original.getCode(), consoleInCart.getQuantity());
                            }
                        } else if (item instanceof AccessorioBean) {
                            AccessorioBean accessoryInCart = (AccessorioBean) item;
                            AccessorioBean original = accessorioModel.doRetrieveByKey(accessoryInCart.getCode());
                            if (original != null) {
                                accessorioModel.updateStock(original.getCode(), accessoryInCart.getQuantity());
                            }
                        }
                    }
                    DettaglioOrdineModel dettaglioModel = new DettaglioOrdineModelDS();
                    for (Item item : cart.getProducts()) {
                        DettaglioOrdineBean d = new DettaglioOrdineBean();
                        d.setOrderId(ordine.getId());
                        d.setEmailUtente(emailUtente);
                        d.setTipoProdotto(item.getPrefissoId().charAt(0));
                        d.setIdProdotto(item.getCode());
                        d.setQuantita(item.getQuantity());
                        d.setPrezzoUnitario(item.getPrice());
                        dettaglioModel.doSave(d);
                    }
                    cart.clear();
                    session.setAttribute("cart", cart);
                    
                } else if (action.equalsIgnoreCase("delete")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    model.doDelete(id, emailUtente);
                    
                } else if (action.equalsIgnoreCase("read")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    OrdineBean ordine;
                    if (user.isAdmin()) {
                        ordine = model.doRetrieveByKeyAdmin(id);
                      } else {
                        ordine = model.doRetrieveByKey(id, user.getEmail());
                      }
                    request.setAttribute("ordine", ordine);
                    DettaglioOrdineModel dettaglioModel = new DettaglioOrdineModelDS();
                    Collection<DettaglioOrdineBean> dettagli = user.isAdmin()
                    	    ? dettaglioModel.doRetrieveByOrderAdmin(id)
                    	    : dettaglioModel.doRetrieveByOrder(id, user.getEmail());
                    request.setAttribute("dettagli", dettagli);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userlogged/OrdineDetail.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
        	Collection<OrdineBean> ordini;
        	if (fromDate != null || toDate != null) {
        	    ordini = model.doRetrieveByUserAndPeriod(emailUtente, fromDate, toDate);
        	} else {
        	    ordini = model.doRetrieveByUser(emailUtente);
        	}
        	request.setAttribute("ordini", ordini);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userlogged/OrderView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
