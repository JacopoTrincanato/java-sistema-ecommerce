package org.java.e_commerce.shop.paginations;

import java.util.List;

import org.java.e_commerce.shop.models.Ordine;

public class OrdinePagination {

    // attributi
    private List<Ordine> contenuto;
    private int numeroPagina;
    private int elementiPagina;
    private long totaleElementi;
    private int totalePagine;

    // metodi
    public List<Ordine> getContenuto() {
        return this.contenuto;
    }

    public void setContenuto(List<Ordine> contenuto) {
        this.contenuto = contenuto;
    }

    public int getNumeroPagina() {
        return this.numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public int getElementiPagina() {
        return this.elementiPagina;
    }

    public void setElementiPagina(int elementiPagina) {
        this.elementiPagina = elementiPagina;
    }

    public long getTotaleElementi() {
        return this.totaleElementi;
    }

    public void setTotaleElementi(long totaleElementi) {
        this.totaleElementi = totaleElementi;
    }

    public int getTotalePagine() {
        return this.totalePagine;
    }

    public void setTotalePagine(int totalePagine) {
        this.totalePagine = totalePagine;
    }

}
