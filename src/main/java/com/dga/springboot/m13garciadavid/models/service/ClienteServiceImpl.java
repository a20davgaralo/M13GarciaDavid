package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.dao.IClienteDao;
import com.dga.springboot.m13garciadavid.models.dao.IFacturaDao;
import com.dga.springboot.m13garciadavid.models.dao.IProductoDao;
import com.dga.springboot.m13garciadavid.models.dao.I_InformeDao;
import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.Informe;
import com.dga.springboot.m13garciadavid.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe de tipus Service que implementa els mètodes de l'interfície IClienteService
 * Aquest mètodes podran fer-se servir a la classe ClienteController per tractar amb les dades
 */
@Service
public class ClienteServiceImpl implements IClienteService {

    //Interfaces injectades
    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

    @Autowired
    private I_InformeDao informeDao;

    @Transactional(readOnly=true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void save(Cliente Cliente) {
        clienteDao.save(Cliente);
    }

    @Transactional (readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteDao.fetchByIdWithFacturas(id);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        clienteDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Transactional
    @Override
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findProductoById(Long id) {
        //findById Retorna un opcional, podem gestionar el resultat, d'això el mètode orElse
        return productoDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Transactional
    @Override
    public Factura fetchFacturabyIdfetchByIdWithClienteWithItemFacturaWithProdcuto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProdcuto(id);
    }

    /*@Transactional
    @Override
    public void borraInforme(Long id) {
        clienteDao.setInformeVoid(id);
    }*/

    @Transactional
    @Override
    public Informe fetchByIdWithCliente(Long id) {
       return informeDao.fetchByIdWithCliente(id);
    }

    @Override
    public void saveInforme(Informe informe) {
        informeDao.save(informe);
    }

    @Override
    public Informe finInformeById(Long id) {
        return informeDao.findById(id).orElse(null);
    }

    @Override
    public void deleteInforme(Long id) {
        informeDao.deleteById(id);
    }

    @Override
    public Cliente fetchByIdWithInformes(Long id) {
        return clienteDao.fetchByIdWithInformes(id);
    }
}
