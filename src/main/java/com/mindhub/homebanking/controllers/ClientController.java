package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
        @Autowired
        private ClientService clientService;
        @Autowired
        private AccountService accountService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/clients")
        public List<ClientDTO> getClients(){
                return clientService.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        }
        @GetMapping("/clients/{id}")
        public ClientDTO getClient(@PathVariable Long id){
                return new ClientDTO(clientService.findById(id));
        }
        @PostMapping(path = "/clients")
        public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String email, @RequestParam String password ) {


                if (firstName.isEmpty() ) {
                        return new ResponseEntity<>("FISTNAME SPACE IS EMPTY", HttpStatus.FORBIDDEN);
                }
                if ( lastName.isEmpty()  ) {
                        return new ResponseEntity<>("LASTNAME SPACE IS EMPTY", HttpStatus.FORBIDDEN);
                }
                if ( email.isEmpty() ) {
                        return new ResponseEntity<>("EMAIL SPACE IS EMPTYy", HttpStatus.FORBIDDEN);
                }
                if ( password.isEmpty() ) {
                        return new ResponseEntity<>("PASSWORD SPACE IS EMPTY", HttpStatus.FORBIDDEN);
                }

                if (clientService.findByEmail(email) != null ){
                        return new ResponseEntity<>("THE EMAIL ENTERED IS ALREADY IN USE",HttpStatus.FORBIDDEN);
                }

                String number;
                do { number = AccountUtils.getAccountNumber();}
                while(accountService.findByNumber(number) != null);

                Client client= new Client(firstName,lastName,email,passwordEncoder.encode(password));
                Account account = new Account( number, LocalDateTime.now(),AccountType.SAVING, 0.0,client, true);
                client.addAccounts(account);
                clientService.save(client);
                accountService.save(account);


                return new ResponseEntity<> ("SUCCESSFUL REGISTRATION",HttpStatus.CREATED);
        }
        @GetMapping("/clients/current")
        public ClientDTO getAll(Authentication authentication){

                ClientDTO  clientDTO= new ClientDTO(clientService.findByEmail(authentication.getName()));

                return clientDTO;

        }

}
