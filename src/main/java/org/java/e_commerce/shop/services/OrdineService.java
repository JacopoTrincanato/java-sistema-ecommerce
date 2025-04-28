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

    /**
     * Recupera tutti gli ordini attraverso ordineRepository.findAll() e aggiunge
     * una paginazione customizzata
     * 
     * @param numeroPagina
     * @param elementiPagina
     * @return l'impaginazione degli ordini
     */
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

    /**
     * Recupera il singolo ordine attraverso ordineRepository.findById(id)
     * 
     * @param id
     * 
     * @return l'ordine per id
     */
    public Optional<Ordine> recuperaIdOrdine(Integer id) {
        return ordineRepository.findById(id);
    }

    /**
     * Permette l'aggiunta di un ordine attraverso
     * ordineRepository.save(ordine)
     * Recuper anche il cliente tramite
     * clienteService.recuperaIdCliente(ordine.getCliente().getId()).orElse(null).
     * Se è null non viene trovato e il prodotto tramite
     * prodottoService.recuperaIdProdotto(prodottoOrdinato.getProdotto().getId()).orElse(null).
     * Se è null non viene trovato
     * 
     * Diminuisce la quantità in stock del prodotto in caso di ordine effettuato
     * correttamente
     * 
     * Permette di effettuare più ordini contemporaneamente grazie a @Transactional
     * 
     * @param ordine
     * 
     * @return aggiunta di un ordine
     */
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

    /**
     * Metodo simile al precedente, ma utilizzato per aggiornare un ordine
     * 
     * @param ordine
     * 
     * @return aggiornamento di un ordine
     */
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

    /**
     * Elimina il singolo ordine, a meno che il suo stato non sia CONSEGNATO,
     * attraverso ordineRepository.findById(id).get()
     * 
     * @param id
     * 
     * @return la cancellazione dell'ordine
     */
    public void cancellaOrdine(Integer id) {
        Ordine ordine = ordineRepository.findById(id).get();
        if (ordine.getStatus() == StatoOrdine.CONSEGNATO) {
            throw new RuntimeException("Impossibile cancellare l'ordine");
        }
        ordineRepository.delete(ordine);
    }
}
