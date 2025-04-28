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

    // metodi
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

    public Optional<Prodotto> recuperaIdProdotto(Integer id) {
        return prodottoRepository.findById(id);
    }

    public Prodotto aggiungiProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
}
