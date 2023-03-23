package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

   List<Client> findAll();
   Client findById(Long id);

   Client save(Client client);
   Client findByEmail(String email);
}
