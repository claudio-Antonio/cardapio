package com.example.cardapio.repositories;

import com.example.cardapio.domain.UserIfood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIfoodRepository extends JpaRepository<UserIfood, Long> {
    UserDetails findByUsername(String username); // consulta o usuário pelo username.
}
