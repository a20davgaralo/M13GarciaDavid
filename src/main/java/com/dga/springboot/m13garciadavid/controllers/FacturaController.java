package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.ItemFactura;
import com.dga.springboot.m13garciadavid.models.entity.Producto;
import com.dga.springboot.m13garciadavid.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


//Anotamos la clase con @Secured("ROLE_ADMIN") para que toda la clase se pueda acceder siendo ADMIN
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class  FacturaController {

    @Autowired
    private IClienteService clienteService;

    //Para debugear en el log
    private final Logger log = LoggerFactory.getLogger(getClass());

    //Implementar el ver factura
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash) {

        //Forma antigua de hacerlo, sin el método JOIN FETCH implementado en IFacturaDao
        //Factura factura = clienteService.findFacturaById(id);
        Factura factura = clienteService.fetchFacturabyIdfetchByIdWithClienteWithItemFacturaWithProdcuto(id);

        //Si el parámetro id que pasamos en url no existe, lo indicamos
        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura : ".concat(factura.getDescripcion()));

        return "factura/ver";
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = clienteService.findOne(clienteId);

        if(cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        //Asginamos la factura a un cliente
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", "Crear factura");


        return "factura/form";
    }

    //Suprime cargar vista, retorna el resultado en json y lo guarda en el ResponseBody
    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status) {

        //Valida los datos y comprueba errores
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Crear Factura");
            return "factura/form";
        }

        //Comprueba si el id es null o los elementos
        if(itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error", "Error: La factura debe contener alguna línea");
            return "factura/form";
        }

        for(int i = 0; i < itemId.length; i++) {
            //Obtenemos producto de cada línea de factura
            Producto producto = clienteService.findProductoById(itemId[i]);

            //Pasamos valores al item de la factura
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);

            //Agregamos la línea de factura a la factura
            factura.addItemFactura(linea);

            //Para hacer debug y mostrar en consola valor de id y cantidad
            log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        //Guardamos factura en BBDD
        clienteService.saveFactura(factura);

        //Finalizamos el proceso SessionAttribute de la factura
        status.setComplete();

        //Pasamos mensaje flash
        flash.addFlashAttribute("succes", "Factura creada con éxito");

        //Redirigimos al estado del cliente
        return "redirect:/ver/" + factura.getCliente().getId();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        Factura factura = clienteService.findFacturaById(id);

        if (factura != null) {
            clienteService.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada con éxito");
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "La factura no existe en la base de datos");
        return "redirect:/listar";
    }
}
