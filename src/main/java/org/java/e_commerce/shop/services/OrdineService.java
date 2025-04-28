package org.java.e_commerce.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.java.e_commerce.shop.enums.StatoOrdine;
import org.java.e_commerce.shop.models.Cliente;
import org.java.e_commerce.shop.models.Ordine;
import org.java.e_commerce.shop.models.OrdineProdotto;
import org.java.e_commerce.shop.models.Prodotto;
import org.java.e_commerce.shop.paginations.OrdinePagination;
import org.java.e_commerce.shop.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private ClienteService clienteService;

    // metodi
    public OrdinePagination recuperaOrdini(int numeroPagina, int elementiPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, elementiPagina);
        Page<Ordine> ordini = ordineRepository.findAll(pageable);
        List<Ordine> listaOrdini = ordini.getContent();

        OrdinePagination ordinePagination = new OrdinePagination();
        ordinePagination.setContenuto(listaOrdini);
        ordinePagination.setNumeroPagina(ordini.getNumber());
        ordinePagination.setElementiPagina(ordini.getSize());
        ordinePagination.setTotaleElementi(ordini.getTotalElements());
        ordinePagination.setTotalePagine(ordini.getTotalPages());
        return ordinePagination;
    }

    public Optional<Ordine> recuperaIdOrdine(Integer id) {
        return ordineRepository.findById(id);
    }

    @Transactional
    public Ordine aggiungiOrdine(Ordine ordine) {
        List<OrdineProdotto> prodottiDaOrdinare = ordine.getProdotti();
        Cliente cliente = clienteService.recuperaIdCliente(ordine.getCliente().getId()).orElse(null);
        ordine.setCliente(cliente);

        List<OrdineProdotto> prodottiAggiornati = new ArrayList<>();

        for (OrdineProdotto prodottoOrdinato : prodottiDaOrdinare) {
            Prodotto prodotto = prodottoService.recuperaIdProdotto(prodottoOrdinato.getProdotto().getId()).orElse(null);

            Integer quantitaInStock = prodotto.getStock();

            if (quantitaInStock < prodottoOrdinato.getQuantita()) {
                throw new RuntimeException("Stock insufficiente per il prodotto " + prodotto.getNomeProdotto());
            } else {
                prodotto.setStock(quantitaInStock - prodottoOrdinato.getQuantita());

                prodottoOrdinato.setProdotto(prodotto);
                prodottoOrdinato.setOrdine(ordine);
                prodottiAggiornati.add(prodottoOrdinato);
            }
        }

        ordine.setProdotti(prodottiAggiornati);

        return ordineRepository.save(ordine);
    }

    public Ordine aggiornaOrdine(Ordine ordine) {
        List<OrdineProdotto> prodottiDaOrdinare = ordine.getProdotti();
        Cliente cliente = clienteService.recuperaIdCliente(ordine.getCliente().getId()).orElse(null);
        ordine.setCliente(cliente);
        List<OrdineProdotto> prodottiAggiornati = new ArrayList<>();

        for (OrdineProdotto prodottoOrdinato : prodottiDaOrdinare) {
            Prodotto prodotto = prodottoService.recuperaIdProdotto(prodottoOrdinato.getProdotto().getId()).orElse(
                    null);
            Integer quantitaInStock = prodotto.getStock();

            if (quantitaInStock < prodottoOrdinato.getQuantita()) {
                throw new RuntimeException(
                        "Stock insufficiente per il prodotto " + prodotto.getNomeProdotto());
            } else {
                prodotto.setStock(quantitaInStock - prodottoOrdinato.getQuantita());

                prodottoOrdinato.setProdotto(prodotto);
                prodottoOrdinato.setOrdine(ordine);
                prodottiAggiornati.add(prodottoOrdinato);
            }
        }

        ordine.setProdotti(prodottiAggiornati);

        return ordineRepository.save(ordine);
    }

    public void cancellaOrdine(Integer id) {
        Ordine ordine = ordineRepository.findById(id).get();
        if (ordine.getStatus() == StatoOrdine.CONSEGNATO) {
            throw new RuntimeException("Impossibile cancellare l'ordine");
        }
        ordineRepository.delete(ordine);
    }
}
