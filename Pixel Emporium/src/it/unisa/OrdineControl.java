package it.unisa;

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

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("insert")) {
                    String emailUtente = request.getParameter("emailUtente");
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
                        scadenzaCarta = Date.valueOf(scadenzaStr);
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


                    cart.clear();
                    session.setAttribute("cart", cart);
                    
                    model.doSave(ordine);
                    
                    
                } else if (action.equalsIgnoreCase("delete")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String emailUtente = request.getParameter("emailUtente");
                    model.doDelete(id, emailUtente);
                    
                } else if (action.equalsIgnoreCase("read")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String emailUtente = request.getParameter("emailUtente");
                    OrdineBean ordine = model.doRetrieveByKey(id, emailUtente);
                    request.setAttribute("ordine", ordine);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrdineDetail.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            String sort = request.getParameter("sort");
            Collection<OrdineBean> ordini = model.doRetrieveAll(sort);
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrderView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
