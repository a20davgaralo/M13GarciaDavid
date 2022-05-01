package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.ItemFactura;
import com.dga.springboot.m13garciadavid.models.entity.Producto;
import com.dga.springboot.m13garciadavid.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per tota la part de la gestio de les factures
 */


@Secured("ROLE_ADMIN") //Anotem la classe amb @Secured("ROLE_ADMIN") perque aquesta classe només es pot fer servir sent rol ADMIN
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class  FacturaController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    MessageSource messageSource;


    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Implementa poder veure una factura passant un id de factura determinat
     * @param id
     * @param model
     * @param flash
     * @return
     */
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash,
                      Locale locale) {

        Factura factura = clienteService.fetchFacturabyIdfetchByIdWithClienteWithItemFacturaWithProdcuto(id);

        //Primer es comprova si el paràmetre factura (id) que pasem per la url existeix, sino, redirigim a la vista principal
        if (factura == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.factura.flash.db.error", null, locale));
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", messageSource.getMessage("texto.factura.form.factura", null, locale).concat(": ").concat(factura.getDescripcion()));

        return "factura/ver";
    }

    /**
     * Gestió del formulari per crear una factura. Passem un id de client per la url
     * @param clienteId
     * @param model
     * @param flash
     * @return
     */
    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

        Cliente cliente = clienteService.findOne(clienteId);

        //Primer es comprova si el paràmetre de client que pasem per la url existeix, sino, redirigim a la vista principal
        if(cliente == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error", null, locale));
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        //Assignem la factura a un client
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", messageSource.getMessage("texto.cliente.factura.crear", null, locale));

        return "factura/form";
    }

    /**
     * Suprimeix carregar la vista, retorna el resultat en json i el guarda en el ResponseBody
     * @param term
     * @return
     */
    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre(term);
    }

    /**
     * Formulari per guardar la factura
     * @param factura
     * @param result
     * @param model
     * @param itemId
     * @param cantidad
     * @param flash
     * @param status
     * @return
     */
    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status,
                          Locale locale) {

        //Valida les dades i comprova errors
        if(result.hasErrors()) {
            model.addAttribute("titulo", messageSource.getMessage("texto.cliente.factura.crear", null, locale));
            return "factura/form";
        }

        //Comprova si l'id es null o no hi han elements
        if(itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", messageSource.getMessage("texto.cliente.factura.crear", null, locale));
            model.addAttribute("error", messageSource.getMessage("texto.factura.flash.lineas.error", null, locale));
            return "factura/form";
        }

        for(int i = 0; i < itemId.length; i++) {
            //Obtenim el producte de cada línea de factura
            Producto producto = clienteService.findProductoById(itemId[i]);

            //Passem valors a l'item de la factura
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);

            //Afegim la línea de factura a la factura
            factura.addItemFactura(linea);

            //Amb això podem debuggar l'aplicació i mostar per consola el valor de l'id i la quantitat
            log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        //Guardem factura en BBDD
        clienteService.saveFactura(factura);

        //Finalitzem procés SessionAttribute de la factura
        status.setComplete();

        //Passem missatge flash indicant que la factura s'ha creat
        flash.addFlashAttribute("success", messageSource.getMessage("texto.factura.flash.crear.success", null, locale));

        //Redirigim a l'estat del client
       return "redirect:/ver/" + factura.getCliente().getId();
    }

    /**
     * Gestió de l'eliminació de la factura
     * @param id
     * @param flash
     * @return
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

        Factura factura = clienteService.findFacturaById(id);

        if (factura != null) {
            clienteService.deleteFactura(id);
            flash.addFlashAttribute("success", messageSource.getMessage("texto.factura.flash.eliminar.success", null, locale));
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", messageSource.getMessage("texto.factura.flash.db.error", null, locale));
        return "redirect:/listar";
    }
}
