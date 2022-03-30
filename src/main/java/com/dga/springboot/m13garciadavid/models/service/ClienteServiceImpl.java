package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.dao.IClienteDao;
import com.dga.springboot.m13garciadavid.models.dao.IFacturaDao;
import com.dga.springboot.m13garciadavid.models.dao.IProductoDao;
import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    //Interfaces inyectadas
    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

    @Transactional(readOnly=true) //Solo de lectura, no va a escribir. Toma el contenido del método y lo envuelve en una transacción
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Transactional //Sin el readonly ya que es de escritura
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
        //findById Retorna un opcional, podemos manejar el resultado, por eso el orElse
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




}
