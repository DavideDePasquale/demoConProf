package com.example.demoConProf.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthEnrtyPoint implements AuthenticationEntryPoint {

    // Implementiamo l'interfaccia che rileva eventuali errori di autenticazione

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Settiamo il formato di ritorno verso il client
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Settiamo lo status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //Settiamo il contenuto di ritorno verso il BODY del client
        final Map<String,Object> infoErrori = new HashMap<>();
        infoErrori.put("stato",HttpServletResponse.SC_UNAUTHORIZED);
        infoErrori.put("errore", "Autorizzazione non valida");
        infoErrori.put("message",authException.getMessage());
        infoErrori.put("path", request.getServletPath());


        /***   ESEMPIO DI RITORNO NEL BODY DEL CLIENT
         * "stato" : "SC_UNAUTHORIZED",
         * "errore" : Autorizzazione non valida,
         * "messaggio" : ..........
         * "path" : /api/auth/patchUtente
         */




        // mappare da oggetto ad oggetto -> conversione da un Map Java e Json
        final ObjectMapper mappaturaErrori = new ObjectMapper();
        mappaturaErrori.writeValue(response.getOutputStream(), infoErrori);


    }
}
