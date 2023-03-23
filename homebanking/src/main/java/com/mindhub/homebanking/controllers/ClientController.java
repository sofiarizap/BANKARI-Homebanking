package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/clients")
        public List<ClientDTO> getClients(){
                return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        }
        @GetMapping("/clients/{id}")
        public ClientDTO getClient(@PathVariable Long id){
                return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
        }
        @PostMapping(path = "/clients")
        public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String email, @RequestParam String password ) {


                if (firstName.isEmpty() ) {
                        return new ResponseEntity<>("FirstName space is empty ", HttpStatus.FORBIDDEN);
                }
                if ( lastName.isEmpty()  ) {
                        return new ResponseEntity<>("LastName space is empty", HttpStatus.FORBIDDEN);
                }
                if ( email.isEmpty() ) {
                        return new ResponseEntity<>("Email space is empty", HttpStatus.FORBIDDEN);
                }
                if ( password.isEmpty() ) {
                        return new ResponseEntity<>("Password space is empty", HttpStatus.FORBIDDEN);
                }

                if (clientRepository.findByEmail(email) != null ){
                        return new ResponseEntity<>("The email entered is already in use",HttpStatus.FORBIDDEN);
                }

                Client client= new Client(firstName,lastName,email,passwordEncoder.encode(password));
                clientRepository.save(client);

                return new ResponseEntity<> ("Successful registration",HttpStatus.CREATED);
        }
        @GetMapping("/clients/current")
        public ClientDTO getAll(Authentication authentication){

                ClientDTO  clientDTO= new ClientDTO(clientRepository.findByEmail(authentication.getName()));

                return clientDTO;
        }

}
