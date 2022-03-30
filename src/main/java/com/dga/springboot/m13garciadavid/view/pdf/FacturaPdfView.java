package com.dga.springboot.m13garciadavid.view.pdf;


import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

//Se debe anotar con component para que se pueda linkar a una vista. Y en vez que se vea la factura, se muestre por pdf
//Spring sabe como pasar la vista (html, xml, pdf, excel...) es por el parámetro Format
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        //Hacemos un cast porque el model devuelve un objeto
        Factura factura = (Factura) model.get("factura");

        //Creamos un vista igual que la que muestra html (factura/ver)

        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingAfter(20);

        //Para poder modificar los atributos de las celdas, creamos objetos PdfPCell
        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase("Datos del cliente"));
        cell.setBackgroundColor(new Color(173, 208, 255));
        cell.setPadding(8f);
        tabla.addCell(cell);

        tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        tabla.addCell(factura.getCliente().getEmail());

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase("Datos de la factura"));
        cell.setBackgroundColor(new Color(191, 255, 173));
        cell.setPadding(8f);
        tabla2.addCell(cell);

        tabla2.addCell("Número factura: " + factura.getId());
        tabla2.addCell("Descripción: " + factura.getDescripcion());
        tabla2.addCell("Fecha: " + factura.getCreateAt());

        //Guardamos las tablas a través de Document
        document.add(tabla);
        document.add(tabla2);

        PdfPTable tabla3 = new PdfPTable(4);
        tabla3.setWidths(new float[] {3.5f, 1, 1, 1});
        tabla3.addCell("Producto");
        tabla3.addCell("Precio");
        tabla3.addCell("Cantidad");
        tabla3.addCell("Total");

        //Añadir los elementos de la factura
        for(ItemFactura item: factura.getItems()) {
            tabla3.addCell(item.getProducto().getNombre());
            tabla3.addCell(item.getProducto().getPrecio().toString());

            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla3.addCell(cell);
            tabla3.addCell(item.calcularImporte().toString());
        }

        //Footer. Para ello creamos una celda
        cell = new PdfPCell(new Phrase("Total"));
        //Numero columnas
        cell.setColspan(3);
        //Alinear texto a la derecha
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);

        //Añadir a la tabla3
        tabla3.addCell(cell);
        tabla3.addCell(factura.getTotal().toString());

        document.add(tabla3);



    }
}
