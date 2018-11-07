package com.d8games.web.services.service;

import com.d8games.web.services.model.dto.AuthenticationDto;
import com.d8games.web.services.model.entity.Authentication;
import com.d8games.web.services.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unused")
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public List<Authentication> getAll() {
        return authenticationRepository.findAll();
    }

    public Authentication getById(String id) {
        return authenticationRepository.getAuthenticationById(id);
    }

    public AuthenticationDto getAuthenticationDto(String authenticationId) {
        return authenticationRepository.getAuthenticationDto(authenticationId);
    }

    public void save(Authentication authentication) {
        authenticationRepository.save(authentication);
    }
}