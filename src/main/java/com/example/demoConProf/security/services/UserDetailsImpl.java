package com.example.demoConProf.security.services;

import com.example.demoConProf.model.Utente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    // stiamo personalizzando i dettagli da inserire nel token JWT

    private Long id;
    private String username;
    private String email;

    @JsonIgnore // quando andrò a mettere i dettagli di token, non considero la password!
    private String password;

    private Collection<? extends GrantedAuthority> ruoli;


    public static UserDetailsImpl costruisciDettagli(Utente user){

        // Conversione Set<Ruolo> -> List <GrantedAuthority>
        //recuperiamo il set di tipo ruolo, vado a scansionare tutto il set. Ogni ruolo che trovo in questo set, lo vado a convertire direttamente e dentro voglio avere il ruolo.
        List<GrantedAuthority> ruoliUtente = user.getRuolo().stream()
                .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome().name())).collect(Collectors.toList());


       return new UserDetailsImpl( user.getIdutente(), user.getUsername(), user.getEmail(), user.getPassword(), ruoliUtente);
        // ruoli

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return ruoli;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
