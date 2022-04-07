package com.dga.springboot.m13garciadavid.view.pdf;


import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.ItemFactura;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe per gesionar l'exportació d'una factura a un arxiu PDF.
 * Anotem amb component perque es pugui veure a la vista "factura/ver"
 * A la vista afegim el format en el que s'exporta (línea 14), en aquest cas pdf
 * D'aquesta manera, la factura no es mostrarà en format HTML, sino en PDF
 */
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

    private static Font tituloF = new Font(Font.BOLD, 18, Font.TIMES_ROMAN);
    private static Font peque = new Font(Font.ITALIC, 10, Font.TIMES_ROMAN);
    private static Font normal = new Font(Font.TIMES_ROMAN, 12);
    private static Font tituloP = new Font(Font.BOLD, 13, Font.TIMES_ROMAN);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {


        //Fem cast perque el mètode model torna un objecte
        Factura factura = (Factura) model.get("factura");

        //Creem una vista amb la mateixa estructura que es mostra a l'html (factura/ver)


        //Afegim imatge al document
        Image logo = Image.getInstance("src/main/resources/static/images/logoPetit.png");
        logo.scalePercent(20);
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.setSpacingAfter(40);
        document.add(logo);

        //Títol
        Paragraph titulo = new Paragraph("FACTURA", tituloF);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        Paragraph datosFis = new Paragraph();
        datosFis.add(new Paragraph("NIF - 49098111C", peque));
        datosFis.add(new Paragraph("C/ Guillem Tell, 24", peque));
        datosFis.add(new Paragraph("08006 - Barcelona", peque));
        datosFis.setAlignment(Element.ALIGN_RIGHT);
        datosFis.setIndentationRight(20);
        document.add(datosFis);

        //Creem la primera taula
        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingBefore(40);
        tabla.setSpacingAfter(20);

        //Per poder modificar els atributs de les cel·les, creem objectes de la classe PdfPCell
        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase("Datos del cliente"));
        cell.setBackgroundColor(new Color(173, 208, 255));
        cell.setPadding(8f);
        tabla.addCell(cell);

        tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        tabla.addCell(factura.getCliente().getIdentificacionFiscal());
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

        //Guardem les taules creades a través de Document
        document.add(tabla);
        document.add(tabla2);

        PdfPTable tabla3 = new PdfPTable(4);
        tabla3.setWidths(new float[] {3.5f, 1, 1, 1});
        tabla3.addCell(new Paragraph("Producto", tituloP));
        tabla3.addCell(new Paragraph("Precio", tituloP));
        tabla3.addCell(new Paragraph("Cantidad", tituloP));
        tabla3.addCell(new Paragraph("Total", tituloP));

        //Afegir els elements de la factura
        for(ItemFactura item: factura.getItems()) {
            tabla3.addCell(item.getProducto().getNombre());
            tabla3.addCell(item.getProducto().getPrecio().toString());

            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla3.addCell(cell);
            tabla3.addCell(item.calcularImporte().toString());
        }

        //Footer. Creem una cel·la per ella
        cell = new PdfPCell(new Phrase("Total"));
        //Numero columnas
        cell.setColspan(3);
        //Alinear text a la dreta
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);

        //Afegir a la tabla3
        tabla3.addCell(cell);
        tabla3.addCell(factura.getTotal().toString().concat("€"));

        document.add(tabla3);

        Paragraph pago = new Paragraph();
        pago.setSpacingBefore(40);
        pago.add(new Paragraph("Puede hacer efectivo el ingreso del importe por transferencia al número de cuenta siguiente:", normal));
        pago.add(new Paragraph("IBAN: ES49 XXXX XXXX XXXX XXXX", normal));
        pago.setAlignment(Element.ALIGN_LEFT);
        pago.setIndentationLeft(40);
        document.add(pago);

        Paragraph firma = new Paragraph();
        firma.setSpacingBefore(40);
        firma.add(new Paragraph("Firmado", peque));
        firma.setAlignment(Element.ALIGN_RIGHT);
        firma.setIndentationRight(40);
        document.add(firma);

    }
}
