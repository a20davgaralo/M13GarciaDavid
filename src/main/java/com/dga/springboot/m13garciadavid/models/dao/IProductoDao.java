package com.dga.springboot.m13garciadavid.models.dao;

import com.dga.springboot.m13garciadavid.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto, Long> {

    //Query a nivel de entity, no de tabla
    @Query("select p from Producto p where p.nombre like %?1%")
    public List<Producto> findByNombre(String term);

    //Srping data crea estos métodos automáticamente (Query creation) en spring data jpa
    public List<Producto> findByNombreLikeIgnoreCase(String term);

}
