package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplementation implements ClientService {

  @Autowired
  ClientRepository clientRepository;

  @Override
  public List<Client> findAll() {
    return clientRepository.findAll();
  }


  @Override
  public Client save (Client client){
    return clientRepository.save(client);
  }

  @Override
  public Client findByEmail(String email){
    return clientRepository.findByEmail(email);

  }

  @Override
  public Client findById(Long id){
    return clientRepository.findById(id).orElse(null);
  }



}
