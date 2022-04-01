package com.dga.springboot.m13garciadavid.util.paginator;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe per gestionar la paginació de la vista de clients i com es mostren els resultats
 */
public class PageItem {

    private int numero;
    private boolean actual;

    public PageItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isActual() {
        return actual;
    }
}
