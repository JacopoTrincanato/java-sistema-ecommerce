package org.java.e_commerce.shop.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "prodotti")
public class Prodotto {

    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "prodotto")
    private List<OrdineProdotto> ordiniProdotto;

    @NotNull
    @Min(value = 1)
    private Integer codiceProdotto;

    @Size(min = 3, max = 30, message = "il campo nome prodotto non può avere meno di 3 caratteri e più di 30")
    @NotBlank(message = "il campo nome prodotto non può essere vuoto o nullo")
    private String nomeProdotto;

    @NotNull
    @Min(value = 0, message = "la quantità in stock non può essere inferiore a 0")
    private Integer stock;

    // metodi
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrdineProdotto> getOrdiniProdotto() {
        return this.ordiniProdotto;
    }

    public void setOrdiniProdotto(List<OrdineProdotto> ordiniProdotto) {
        this.ordiniProdotto = ordiniProdotto;
    }

    public Integer getCodiceProdotto() {
        return this.codiceProdotto;
    }

    public void setCodiceProdotto(Integer codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }

    public String getNomeProdotto() {
        return this.nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
