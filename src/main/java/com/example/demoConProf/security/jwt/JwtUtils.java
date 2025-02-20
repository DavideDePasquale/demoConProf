package com.example.demoConProf.security.jwt;

import com.example.demoConProf.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;


// Funzionalità Utilities del TOKEN
public class JwtUtils {

    // Agganciamo le costanti legate al JWT
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirations;

    // Creazione del JWT

    public String creaJwtToken(Authentication autenticazione){
        //Recupero il dettaglio principal (username)
       UserDetailsImpl utentePrincipal = (UserDetailsImpl) autenticazione.getPrincipal(); //ci restituisce lo username

        // creazione del jwt
        return Jwts.builder().setSubject(utentePrincipal.getUsername())
                 .setIssuedAt(new Date())
                 .setExpiration(new Date (new Date().getTime()+jwtExpirations))
                 .signWith(recuperoChiave(), SignatureAlgorithm.HS256).compact();
    }


    // Recupera l'username dal JWT

    public String recuperoUsernameDaToken(String token){
      String username =  Jwts.parserBuilder().setSigningKey(recuperoChiave()).build().parseClaimsJwt(token).getBody().getSubject();
      return username;
    }




    // Validazione del TOKEN JWT

    public boolean validazioneJwtToken(String token){
      Jwts.parserBuilder().setSigningKey(recuperoChiave()).build().parse(token);
      return true;
    }


    // Recupero della chiave
    public Key recuperoChiave(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }



}
