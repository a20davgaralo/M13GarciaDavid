package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.Informe;
import com.dga.springboot.m13garciadavid.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície que determina els mètodes que ha d'implementar la classe que la implementi
 * Els mètodes estan proporcionats automàticament per Hibernate. Ens permeten fer les accions
 * de tipus CRUD (Create, Read, Update, Delete)
 */
public interface IClienteService {

    //Metodo para listar
    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    //Metodo para guardar un cliente
    public void save(Cliente cliente);

    //Método para editar un cliente
    public Cliente findOne(Long id);

    //Método para eliminar cliente
    public void eliminar(Long id);

    public List<Producto> findByNombre(String term);

    public void saveFactura(Factura factura);

    public Producto findProductoById(Long id);

    public Factura findFacturaById(Long id);

    public void deleteFactura(Long id);


    public Factura fetchFacturabyIdfetchByIdWithClienteWithItemFacturaWithProdcuto(Long id);

    public Cliente fetchByIdWithFacturas(Long id);

    public void borraInforme(Long id);

    public Informe fetchByIdWithCliente(Long id);

}