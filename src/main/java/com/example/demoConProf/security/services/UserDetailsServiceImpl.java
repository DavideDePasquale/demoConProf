package com.example.demoConProf.security.services;

import com.example.demoConProf.model.Utente;
import com.example.demoConProf.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtenteRepository repoUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> utente = repoUser.findByUsername(username);

        //recupero tutte le info dell'utente presente sul db
        Utente user = utente.orElseThrow();


        // Ritorna un oggetto di tipo UserDetailsImpl(-> implements UserDetails)
        // Contenitore delle info che vogliamo inserire nel token
        return UserDetailsImpl.costruisciDettagli(user);



    }
}
