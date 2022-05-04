package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.service.IClienteService;
import com.dga.springboot.m13garciadavid.models.service.IUploadFileService;
import com.dga.springboot.m13garciadavid.models.service.UserService;
import com.dga.springboot.m13garciadavid.util.paginator.PageRender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;
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

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Gestió de la pujada d'arxius
     * @param filename
     * @return
     */
    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verInforme(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    /**
     * Vista principal
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model, Locale locale) {

        model.addAttribute("titulo", messageSource.getMessage("texto.homePage.titulo", null, locale));
        return "home";
    }

    /**
     * Per mostrar les dades d'un client determinat
     *
     * @param id
     * @param model
     * @param flash
     * @return
     */
    @Secured("ROLE_ADMIN") //Per a diversos rols d'usuari //TODO CANVI AQUI. EL ROLE_USER NOMES PODRIA VEURE EL SEU ID
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

        //Forma nueva
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);

        if (cliente == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error ", null, locale));
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("texto.cliente.detalle.titulo", null, locale) + ": " + cliente.getNombre());
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
    @Secured("ROLE_ADMIN") //TODO CANVI AQUI, NOMES POT VEURE-HO ROLE_ADMIN
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model, Authentication authentication,
                         HttpServletRequest request,
                         Locale locale) {

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

        model.addAttribute("titulo", messageSource.getMessage("texto.cliente.listar.titulo", null, locale));

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
    public String crear(Map<String, Object> model, Locale locale) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("texto.cliente.form.titulo.crear", null, locale));
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
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error", null, locale));
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.id.error", null, locale));
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("texto.cliente.form.titulo.editar", null, locale));
        return "form";
    }

    /**
     * Gestió de la creació d'un client
     *
     * @param cliente
     * @param result
     * @param model
     * @param informe
     * @param flash
     * @param status
     * @return
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
                          @RequestParam("file") MultipartFile informe, RedirectAttributes flash,
                          SessionStatus status, Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", messageSource.getMessage("texto.cliente.form.titulo", null, locale));
            return "form";
        }

        if (!informe.isEmpty()) {

            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getInforme() != null
                    && cliente.getInforme().length() > 0) {

                uploadFileService.delete(cliente.getInforme());
            }

            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(informe);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", messageSource.getMessage("texto.cliente.flash.informe.subir.success", null, locale) + "'" + uniqueFilename + "'");

            cliente.setInforme(uniqueFilename);
        }

        String mensajeFlash = (cliente.getId() != null) ? messageSource.getMessage("texto.cliente.flash.editar.success", null, locale) : messageSource.getMessage("texto.cliente.flash.crear.success", null, locale);
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
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.eliminar(id);
            flash.addFlashAttribute("success", messageSource.getMessage("texto.cliente.flash.eliminar.success", null, locale));

            if (uploadFileService.delete(cliente.getInforme())) {
                String mensajeInformeEliminar = String.format(messageSource.getMessage("texto.cliente.flash.informe.eliminar.success", null, locale), cliente.getInforme());
                flash.addFlashAttribute("info", mensajeInformeEliminar);
            }
        }
        return "redirect:/listar";
    }

    /**
     * Eliminar un informe en pdf pujat per un client
     * @param id
     * @param flash
     * @return
     */
    @GetMapping("/eliminarInforme/{id}")
    public String eliminarInforme(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

        Cliente cliente = clienteService.findOne(id);

        if (uploadFileService.delete(cliente.getInforme())) {
            clienteService.borraInforme(id);
            String mensajeInformeEliminar = String.format(messageSource.getMessage("texto.cliente.flash.informe.eliminar.success", null, locale), cliente.getInforme());
            flash.addFlashAttribute("info", mensajeInformeEliminar);
        }

        return "redirect:/ver/{id}";
    }


    /**
     * Per mostrar les dades d'un client determinat
     *
     * @param model
     * @param authentication
     * @param request
     * @return
     */
    @Secured("ROLE_USER")
    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public String listarCliente(Model model, Authentication authentication,
                                HttpServletRequest request,
                                Locale locale) {

        //Validem fent servir el mètode hasRole
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso como administrador!"));
        } else if (hasRole("ROLE_USER")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso como usuario!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }

        model.addAttribute("titulo", messageSource.getMessage("texto.cliente.listar.titulo", null, locale));

        logger.info("Datos de objeto auth".concat(auth.getName()));

        logger.info("Cliente obtenido número " + UserService.getIDClient(auth.getName()));

        Cliente cliente = clienteService.findOne((long)UserService.getIDClient(auth.getName()));

        model.addAttribute("clientes", cliente);
        return "cliente";
    }

    /**
     * Per mostrar les dades d'un client determinat
     *
     * @param id
     * @param model
     * @param flash
     * @return
     */
    @Secured("ROLE_USER") //Per a diversos rols d'usuari //TODO CANVI AQUI. EL ROLE_USER NOMES PODRIA VEURE EL SEU ID
    @GetMapping(value = "/cliente/ver/{id}")
    public String verCliente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

        //Forma nueva
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);

        if (cliente == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error ", null, locale));
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("texto.cliente.detalle.titulo", null, locale) + ": " + cliente.getNombre());
        return "ver";
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

