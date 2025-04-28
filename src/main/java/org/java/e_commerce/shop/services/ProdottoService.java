package org.java.e_commerce.shop.services;

import java.util.List;
import java.util.Optional;

import org.java.e_commerce.shop.models.Prodotto;
import org.java.e_commerce.shop.paginations.ProdottoPagination;
import org.java.e_commerce.shop.repositories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    /**
     * Recupera tutti i prodotti attraverso prodottoRepository.findAll() e aggiunge
     * una paginazione customizzata
     * 
     * @param numeroPagina
     * @param elementiPagina
     * @return l'impaginazione dei prodotti
     */
    public ProdottoPagination recuperaProdotti(int numeroPagina, int elementiPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, elementiPagina);
        Page<Prodotto> prodotti = prodottoRepository.findAll(pageable);
        List<Prodotto> listaProdotti = prodotti.getContent();

        ProdottoPagination prodottoPagination = new ProdottoPagination();
        prodottoPagination.setContenuto(listaProdotti);
        prodottoPagination.setNumeroPagina(prodotti.getNumber());
        prodottoPagination.setElementiPagina(prodotti.getSize());
        prodottoPagination.setTotaleElementi(prodotti.getTotalElements());
        prodottoPagination.setTotalePagine(prodotti.getTotalPages());
        return prodottoPagination;
    }

    /**
     * Recupera il singolo prodotto attraverso prodottoRepository.findById(id)
     * 
     * @param id
     * 
     * @return il prodotto per id
     */
    public Optional<Prodotto> recuperaIdProdotto(Integer id) {
        return prodottoRepository.findById(id);
    }

    /**
     * Permette l'aggiunta di un prodotto attraverso
     * prodottoRepository.save(prodotto)
     * 
     * @param prodotto
     * 
     * @return aggiunta di un prodotto
     */
    public Prodotto aggiungiProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
}
