package com.mindhub.homebanking.repositories;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.stream.DoubleStream;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository< Client, Long>{
 Client findByEmail( String email);

}
