package org.java.e_commerce.shop.services;

import java.util.List;
import java.util.Optional;

import org.java.e_commerce.shop.models.Cliente;
import org.java.e_commerce.shop.paginations.ClientePagination;
import org.java.e_commerce.shop.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClientePagination recuperaClienti(int numeroPagina, int elementiPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, elementiPagina);
        Page<Cliente> clienti = clienteRepository.findAll(pageable);
        List<Cliente> listaClienti = clienti.getContent();

        ClientePagination clientePagination = new ClientePagination();
        clientePagination.setContenuto(listaClienti);
        clientePagination.setNumeroPagina(clienti.getNumber());
        clientePagination.setElementiPagina(clienti.getSize());
        clientePagination.setTotaleElementi(clienti.getTotalElements());
        clientePagination.setTotalePagine(clienti.getTotalPages());
        return clientePagination;
    }

    public Optional<Cliente> recuperaIdCliente(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente aggiungiCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
