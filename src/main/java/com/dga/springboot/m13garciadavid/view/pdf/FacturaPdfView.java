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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Locale;
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;


    private static final Font TITULO_F = new Font(Font.BOLD, 18, Font.TIMES_ROMAN);
    private static final Font PEQUE_F = new Font(Font.ITALIC, 10, Font.TIMES_ROMAN);
    private static final Font NORMAL_F = new Font(Font.TIMES_ROMAN, 12);
    private static final Font TITULO_PEQ_F = new Font(Font.BOLD, 13, Font.TIMES_ROMAN);

    public static final float TITULO_CELL_PADDING = 8f;
    public static final float CELL_PADDING = 5f;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        //Gestió del multillenguatge
        Locale locale = localeResolver.resolveLocale(request);

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
        Paragraph titulo = new Paragraph(messageSource.getMessage("texto.cliente.factura", null, locale), TITULO_F);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        Paragraph datosFis = new Paragraph();
        datosFis.add(new Paragraph("NIF - 49098111C", PEQUE_F));
        datosFis.add(new Paragraph("C/ Guillem Tell, 24", PEQUE_F));
        datosFis.add(new Paragraph("08006 - Barcelona", PEQUE_F));
        datosFis.setAlignment(Element.ALIGN_RIGHT);
        datosFis.setIndentationRight(20);
        document.add(datosFis);

        //Creem la primera taula
        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingBefore(40);
        tabla.setSpacingAfter(20);

        //Per poder modificar els atributs de les cel·les, creem objectes de la classe PdfPCell
        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.ver.datos.cliente", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(173, 208, 255));
        cell.setPadding(TITULO_CELL_PADDING);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido()));
        cell.setPadding(CELL_PADDING);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getCliente().getIdentificacionFiscal()));
        cell.setPadding(CELL_PADDING);
        tabla.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getCliente().getEmail()));
        cell.setPadding(CELL_PADDING);
        tabla.addCell(cell);

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.ver.datos.factura", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(191, 255, 173));
        cell.setPadding(TITULO_CELL_PADDING);
        tabla2.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.cliente.factura.folio", null, locale) + ": " + factura.getId()));
        cell.setPadding(CELL_PADDING);
        tabla2.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.cliente.factura.descripcion", null, locale) + ": " + factura.getDescripcion()));
        cell.setPadding(CELL_PADDING);
        tabla2.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.cliente.factura.fecha", null, locale) + ": " + factura.getCreateAt()));
        cell.setPadding(CELL_PADDING);
        tabla2.addCell(cell);

        //Guardem les taules creades a través de Document
        document.add(tabla);
        document.add(tabla2);

        PdfPTable tabla3 = new PdfPTable(4);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.ver.datos.detalle", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(255, 173, 185));
        cell.setPadding(TITULO_CELL_PADDING);
        cell.setColspan(4); //Fa que la cel·la ocupi quatre columnes
        tabla3.addCell(cell);

        tabla3.setWidths(new float[] {3.5f, 1, 1, 1});

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.form.item.nombre", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(CELL_PADDING);
        tabla3.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.form.item.precio", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(CELL_PADDING);
        tabla3.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.form.item.cantidad", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(CELL_PADDING);
        tabla3.addCell(cell);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.factura.form.item.total", null, locale), TITULO_PEQ_F));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(CELL_PADDING);
        tabla3.addCell(cell);

        //Afegir els elements de la factura
        for(ItemFactura item: factura.getItems()) {

            cell = new PdfPCell(new Phrase(item.getProducto().getNombre()));
            cell.setPadding(CELL_PADDING);
            tabla3.addCell(cell);

            cell = new PdfPCell(new Phrase(item.getProducto().getPrecio().toString()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(CELL_PADDING);
            tabla3.addCell(cell);

            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setPadding(CELL_PADDING);
            tabla3.addCell(cell);

            cell = new PdfPCell(new Phrase(item.calcularImporte().toString()));
            cell.setPadding(CELL_PADDING);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla3.addCell(cell);
        }

        //Footer. Creem una cel·la per ella
        cell = new PdfPCell(new Phrase(messageSource.getMessage("texto.cliente.factura.total", null, locale), TITULO_PEQ_F));
        //Numero columnas
        cell.setColspan(3);
        cell.setPadding(CELL_PADDING);
        //Alinear text a la dreta
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        //Afegir a la tabla3
        tabla3.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getTotal().toString().concat("€"), TITULO_PEQ_F));
        cell.setPadding(CELL_PADDING);
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        tabla3.addCell(cell);

        document.add(tabla3);

        Paragraph pago = new Paragraph();
        pago.setSpacingBefore(40);
        pago.add(new Paragraph(messageSource.getMessage("texto.factura.ver.datos.ingreso", null, locale), NORMAL_F));
        pago.add(new Paragraph(messageSource.getMessage("texto.factura.ver.datos.IBAN", null, locale), NORMAL_F));
        pago.setAlignment(Element.ALIGN_LEFT);
        pago.setIndentationLeft(40);
        document.add(pago);

        Paragraph firma = new Paragraph();
        firma.setSpacingBefore(40);
        firma.add(new Paragraph(messageSource.getMessage("texto.factura.ver.datos.firma", null, locale), PEQUE_F));
        firma.setAlignment(Element.ALIGN_RIGHT);
        firma.setIndentationRight(40);
        document.add(firma);

    }
}
