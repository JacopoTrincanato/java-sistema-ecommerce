package org.java.e_commerce.shop.rest_controllers;

import org.java.e_commerce.shop.models.Ordine;
import org.java.e_commerce.shop.paginations.OrdinePagination;
import org.java.e_commerce.shop.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ordini")
public class OrdineRestController {

    @Autowired
    private OrdineService ordineService;

    /**
     * Fornisce le API per il recupero degli ordini in base ai filtri inseriti nella
     * ricerca
     * 
     * @param numeroPagina
     * @param elementiPagina
     * @return recupero degli ordini con dettagli impaginazione
     */
    @GetMapping
    public OrdinePagination index(@RequestParam(value = "numeroPagina") int numeroPagina,
            @RequestParam(value = "elementiPagina") int elementiPagina) {

        return ordineService.recuperaOrdini(numeroPagina, elementiPagina);
    }

    /**
     * Fornisce le API per la creazione di un ordine, restituendo una risposta con
     * status 200 ok
     * 
     * @param ordine
     * @return creazione di un'entità ordine
     */
    @PostMapping
    public ResponseEntity<Ordine> store(@Valid @RequestBody Ordine ordine) {

        return new ResponseEntity<Ordine>(ordineService.aggiungiOrdine(ordine), HttpStatus.OK);
    }

    /**
     * Fornisce le API per la modifica di un ordine, restituendo una risposta con
     * status 200 ok in caso di aggiornamento corretto, altrimenti restituisce una
     * risposta con status 404 not found
     * 
     * @param ordine
     * @param id
     * @return restituzione dell'entità ordine modificata
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ordine> update(@Valid @RequestBody Ordine ordine, @PathVariable Integer id) {

        if (ordineService.recuperaIdOrdine(id).isEmpty()) {
            return new ResponseEntity<Ordine>(HttpStatus.NOT_FOUND);
        }

        ordine.setId(id);

        return new ResponseEntity<Ordine>(ordineService.aggiornaOrdine(ordine), HttpStatus.OK);
    }

    /**
     * Fornisce le API per la cancellazione di un ordine, restituendo una risposta
     * con
     * status 200 ok in caso di cancellazione corretta, altrimenti restituisce una
     * risposta con status 404 not found
     * 
     * @param id
     * @return cancellazione di un ordine
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Ordine> delete(@PathVariable Integer id) {
        if (ordineService.recuperaIdOrdine(id).isEmpty()) {
            return new ResponseEntity<Ordine>(HttpStatus.NOT_FOUND);
        }

        ordineService.cancellaOrdine(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
