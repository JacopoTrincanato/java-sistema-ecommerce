package org.java.e_commerce.shop.rest_controllers;

import org.java.e_commerce.shop.models.Prodotto;
import org.java.e_commerce.shop.paginations.ProdottoPagination;
import org.java.e_commerce.shop.services.ProdottoService;
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
@RequestMapping("/api/prodotti")
public class ProdottoRestController {

    @Autowired
    private ProdottoService prodottoService;

    // metodi
    @GetMapping
    public ProdottoPagination index(@RequestParam(value = "numeroPagina") int numeroPagina,
            @RequestParam(value = "elementiPagina") int elementiPagina) {

        return prodottoService.recuperaProdotti(numeroPagina, elementiPagina);
    }

    @PostMapping
    public ResponseEntity<Prodotto> store(@Valid @RequestBody Prodotto prodotto) {
        return new ResponseEntity<Prodotto>(prodottoService.aggiungiProdotto(prodotto), HttpStatus.OK);
    }
}
