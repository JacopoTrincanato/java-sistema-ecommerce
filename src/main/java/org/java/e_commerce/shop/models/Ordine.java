package org.java.e_commerce.shop.models;

import java.util.List;

import org.java.e_commerce.shop.enums.StatoOrdine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordini")
public class Ordine {

    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "ordine")
    private List<OrdineProdotto> prodotti;

    @Enumerated(EnumType.STRING)
    private StatoOrdine status = StatoOrdine.ORDINATO;

    // metodi
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<OrdineProdotto> getProdotti() {
        return this.prodotti;
    }

    public void setProdotti(List<OrdineProdotto> prodotti) {
        this.prodotti = prodotti;
    }

    public StatoOrdine getStatus() {
        return this.status;
    }

    public void setStatus(StatoOrdine status) {
        this.status = status;
    }

}
