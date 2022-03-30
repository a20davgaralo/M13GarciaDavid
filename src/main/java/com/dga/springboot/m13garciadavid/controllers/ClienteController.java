package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.service.IClienteService;
import com.dga.springboot.m13garciadavid.util.paginator.PageRender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per tota la part de la gestio de clients
 */
@Controller
@SessionAttributes("cliente")
public class ClienteController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IClienteService clienteService;

    /**
     * Per mostrar les dades d'un client determinat
     *
     * @param id
     * @param model
     * @param flash
     * @return
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"}) //Per a diversos rols d'usuari
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        //Forma nueva
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);

        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }

    /**
     * Per mostrar tot el llistat de clients
     * Amb "/" fem que aquesta sigui la pàgina d'inici
     *
     * @param page
     * @param model
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model, Authentication authentication,
                         HttpServletRequest request) {

        /*//Validem si l'usuari s'ha autenticat
        if (authentication != null) {
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        //Validar de forma estàtica, sense l'Authtentication per paràmetre
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Hola usuario autenticado, tu username es: ".concat(auth.getName()));
        }*/

        //Validem fent servir el mètode hasRole
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso como administrador!"));
        } else if (hasRole("ROLE_USER")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso como usuario!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }


        //Per mostrar un nombre (size) determinat de registres per pàgina
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", "Listado de clientes");

        //Passam el llistat de clients a la vista
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }


    /**
     * Crear un client
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de Cliente");
        return "form";
    }


    /**
     * Editar un client
     *
     * @param id
     * @param model
     * @param flash
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    /**
     * Gestió de la creació d'un client
     *
     * @param cliente
     * @param result
     * @param model
     * @param foto
     * @param flash
     * @param status
     * @return
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito";
        clienteService.save(cliente);
        status.setComplete(); //Amb això eliminem l'objecte client de la sessió
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }

    /**
     * Eliminació d'un client
     * @param id
     * @param flash
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.eliminar(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
        }
        return "redirect:/listar";
    }


    /**
     * Comproba si un rol passat per paràmetre està a la taula de rols
     * @param role
     * @return true si el rol està a la col·leció o fals si no ho està
     */
    private boolean hasRole(String role) {

        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
    }


}

