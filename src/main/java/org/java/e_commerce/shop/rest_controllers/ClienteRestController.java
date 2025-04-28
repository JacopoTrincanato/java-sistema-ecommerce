package org.java.e_commerce.shop.rest_controllers;

import org.java.e_commerce.shop.models.Cliente;
import org.java.e_commerce.shop.paginations.ClientePagination;
import org.java.e_commerce.shop.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clienti")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Fornisce le API per il recupero dei clienti in base ai filtri inseriti nella
     * ricerca
     * 
     * @param numeroPagina
     * @param elementiPagina
     * @return recupero dei clienti con dettagli impaginazione
     */
    @GetMapping
    public ClientePagination index(@RequestParam(value = "numeroPagina") int numeroPagina,
            @RequestParam(value = "elementiPagina") int elementiPagina) {

        return clienteService.recuperaClienti(numeroPagina, elementiPagina);
    }

    /**
     * Fornisce le API per la creazione di un cliente, restituendo una risposta con
     * status 200 ok
     * 
     * @param cliente
     * @return creazione di una nuova entità cliente
     */
    @PostMapping
    public ResponseEntity<Cliente> store(@Valid @RequestBody Cliente cliente) {
        return new ResponseEntity<Cliente>(clienteService.aggiungiCliente(cliente), HttpStatus.OK);
    }
}
