package com.example.cardapio.services;

import com.example.cardapio.repositories.UserIfoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserIfoodService implements UserDetailsService {
    private final UserIfoodRepository userIfoodRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userIfoodRepository.findByUsername(username);
    }
}
