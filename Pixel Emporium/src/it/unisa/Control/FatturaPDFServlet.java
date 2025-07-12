package it.unisa.Control;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.unisa.Model.DettaglioOrdineBean;
import it.unisa.Model.DettaglioOrdineModel;
import it.unisa.Model.DettaglioOrdineModelDS;
import it.unisa.Model.OrdineBean;
import it.unisa.Model.OrdineModel;
import it.unisa.Model.OrdineModelDM;
import it.unisa.Model.OrdineModelDS;
import it.unisa.Model.UserBean;
import it.unisa.Model.UserModel;
import it.unisa.Model.UserModelDM;
import it.unisa.Model.UserModelDS;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

@WebServlet("/invoicePDF")
public class FatturaPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderIdParam = request.getParameter("id");
        String emailUtente = request.getParameter("emailUtente");
        if(orderIdParam == null || orderIdParam.isEmpty() || emailUtente == null || emailUtente.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ordine o email mancante");
            return;
        }
        
        int orderId = Integer.parseInt(orderIdParam);
        
        try {
            OrdineModel ordineModel = new OrdineModelDS();
            OrdineBean ordine = ordineModel.doRetrieveByKey(orderId, emailUtente);
            
            DettaglioOrdineModel dettaglioModel = new DettaglioOrdineModelDS();
            Collection<DettaglioOrdineBean> dettagli = dettaglioModel.doRetrieveByOrder(orderId, emailUtente);
            UserModel userModel = new UserModelDS();
            UserBean cliente;
            try {
                cliente = userModel.doRetrieveByKey(emailUtente);
            } catch(SQLException ex) {
                throw new ServletException("Impossibile recuperare dati utente: " + ex.getMessage(), ex);
            }
            
            response.setContentType("application/pdf");
            
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            Paragraph companyInfo = new Paragraph(
                    "Tech Solutions S.r.l.\n" +
                    "Via delle Innovazioni, 123\n" +
                    "20100 Milano (MI) - Italia\n" +
                    "P.IVA: 12345678901\n" +
                    "Tel: +39 02 1234567 – Email: info@techsolutions.it"
            );
            document.add(companyInfo);
            
            document.add(new Paragraph("\n"));
            
            Paragraph invoiceDetails = new Paragraph();
            invoiceDetails.add("FATTURA N° " + ordine.getId() + "\n");
            invoiceDetails.add("Data: " + sdf.format(ordine.getDataOrdine()) + "\n\n");
            invoiceDetails.add("Cliente: " + ordine.getEmailUtente() + "\n");
            invoiceDetails.add("Nome: "      + cliente.getNome() 
			            + " " + cliente.getCognome() + "\n");
			invoiceDetails.add("Indirizzo: " + cliente.getIndirizzo() 
			            + ", " + cliente.getCitta() 
			            + " (" + cliente.getProv() + ") "
			            + cliente.getCap() + "\n");
			invoiceDetails.add("P.IVA: 98765432100\n\n");
            document.add(invoiceDetails);
            
            document.add(new Paragraph("\n"));
            
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);

            table.addCell("Immagine");
            table.addCell("Prodotto");
            table.addCell("Prezzo Unitario");
            table.addCell("Quantità");
            table.addCell("Totale");
            table.addCell("IVA %");
            table.addCell("IVA");
            table.addCell("Totale IVA Inclusa");

            for (DettaglioOrdineBean d : dettagli) {
                try {
                    Image img = Image.getInstance(getServletContext().getRealPath("/") + d.getImmagine());
                    img.scaleToFit(50, 50);
                    PdfPCell cellImg = new PdfPCell(img);
                    cellImg.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellImg);
                } catch(Exception ex) {
                    table.addCell("No Image");
                }
                
                table.addCell(d.getNome());
                double prezzoUnitario = d.getPrezzoUnitario();
                table.addCell(String.format("%.2f €", prezzoUnitario));
                table.addCell(String.valueOf(d.getQuantita()));
                double totale = prezzoUnitario * d.getQuantita();
                table.addCell(String.format("%.2f €", totale));
                
                int ivaPercent = 22;
                table.addCell(ivaPercent + " %");
                double ivaImporto = totale * ivaPercent / 100.0;
                table.addCell(String.format("%.2f €", ivaImporto));
                double totaleConIva = totale + ivaImporto;
                table.addCell(String.format("%.2f €", totaleConIva));
            }
            document.add(table);

            
            document.add(new Paragraph("\n"));
            
            Paragraph summary = new Paragraph();
            summary.add("Totale imponibile (IVA esclusa): " + String.format("%.2f €", ordine.getImporto()) + "\n");
            summary.add("Totale IVA: " + String.format("%.2f €", ordine.getTotaleIva()) + "\n");
            summary.add("Totale fattura (IVA inclusa): " + String.format("%.2f €", ordine.getTotaleFattura()) + "\n");
            document.add(summary);
            
            document.close();
            
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Errore durante la generazione del PDF: " + e.getMessage(), e);
        }
    }
}
