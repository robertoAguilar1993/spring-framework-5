package com.bolsadeideas.springboot.app.view.pdf;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Locale;
import java.util.Map;

@Component("factura/ver")
public class FacturaPdfView  extends AbstractPdfView{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        Factura factura =(Factura) map.get("factura");

        Locale locale = localeResolver.resolveLocale(httpServletRequest);

        MessageSourceAccessor mesajes = getMessageSourceAccessor();

        //messageSource.getMessage("",null,locale)

        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);

        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente",null,locale)));
        cell.setBackgroundColor(new Color(184,218,255));
        cell.setPadding(8f);
        table.addCell(cell);
        table.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        table.addCell(factura.getCliente().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura",null,locale)));
        cell.setBackgroundColor(new Color(195,230,203));
        cell.setPadding(8f);
        table2.addCell(cell);
        table2.addCell(mesajes.getMessage("text.cliente.factura.folio")+": " + factura.getId());
        table2.addCell(mesajes.getMessage("text.cliente.factura.descripcion")+": "+factura.getDescripcion());
        table2.addCell(mesajes.getMessage("text.cliente.factura.fecha")+": "+factura.getCreateAt());
        document.add(table);
        document.add(table2);

        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[]{3.5f,1,1,1});
        table3.addCell(mesajes.getMessage("text.factura.form.item.nombre"));
        table3.addCell(mesajes.getMessage("text.factura.form.item.precio"));
        table3.addCell(mesajes.getMessage("text.factura.form.item.cantidad"));
        table3.addCell(mesajes.getMessage("text.factura.form.item.total"));
        for (ItemFactura item: factura.getItems()){
            table3.addCell(item.getProducto().getNombre());
            table3.addCell(item.getProducto().getPrecio().toString());
            table3.addCell(item.getCantidad().toString());
            table3.addCell(item.calcularImporte().toString());
        }
        cell = new PdfPCell(new Phrase(mesajes.getMessage("text.factura.form.total")+": "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        table3.addCell(cell);
        table3.addCell(factura.getTotal().toString());
        document.add(table3);
    }
}
