package com.dga.springboot.m13garciadavid.models.dao;

import com.dga.springboot.m13garciadavid.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície de tipus Dao (Data Acces Object) que gestiona certs aspectes de la classe Entity Producto.
 */

public interface IProductoDao extends CrudRepository<Producto, Long> {

    /**
     * Mètode que implementa la query anotada amb @Query sobre la taula Producte
     * Actualment, el mètode no es fa servir. Es feia servir en una fase previa del desenvolupament
     * @param term
     * @return
     */
    //Query a nivel de entity, no de tabla
    @Query("select p from Producto p where p.nombre like %?1%")
    public List<Producto> findByNombre(String term);

    /**
     * Mètode implementat per Spring data jpa. Busca un producte amb nombre similar al que passem per paràmetre
     * @param term
     * @return
     */
    public List<Producto> findByNombreLikeIgnoreCase(String term);

}
