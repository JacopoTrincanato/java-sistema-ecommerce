package org.java.e_commerce.shop.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "clienti")
public class Cliente {

    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 20, message = "il campo nome non può avere meno di 3 caratteri e più di 20")
    @NotBlank(message = "il campo nome non può essere vuoto o nullo")
    private String nome;

    @Size(min = 3, max = 30, message = "il campo cognome non può avere meno di 3 caratteri e più di 30")
    @NotBlank(message = "il campo cognome non può essere vuoto o nullo")
    private String cognome;

    @NotNull(message = "il campo data di nascita non può essere nullo")
    @PastOrPresent(message = "la data di nascita non può essere impostata nel futuro")
    private LocalDate dataDiNascita;

    @Size(max = 16)
    @NotBlank(message = "il campo codice fiscale non può essere vuoto o nullo")
    private String codiceFiscale;

    @Size(min = 10, max = 30, message = "il campo email non può avere meno di 10 caratteri e più di 30")
    @NotBlank(message = "il campo email non può essere vuoto o nullo")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Ordine> ordiniEffettuati;

    // metodi
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return this.dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ordine> getOrdiniEffettuati() {
        return this.ordiniEffettuati;
    }

    public void setOrdiniEffettuati(List<Ordine> ordiniEffettuati) {
        this.ordiniEffettuati = ordiniEffettuati;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.nome, this.cognome);
    }

}
