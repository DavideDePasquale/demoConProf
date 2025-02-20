package com.example.demoConProf.controller;

import com.example.demoConProf.exception.EmailDuplicateException;
import com.example.demoConProf.exception.UsernameDuplicateException;
import com.example.demoConProf.payload.UtenteDto;
import com.example.demoConProf.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService servizi;

    @PostMapping("/new")
    public ResponseEntity<String> inserisciUtente(@RequestBody @Validated UtenteDto nuovoUtente, BindingResult validazione){

        try {
            if (validazione.hasErrors()){
                String errori = "Problemi nella validazione dati : \n";
                for (ObjectError errore : validazione.getAllErrors()){
                    errori += errore.getDefaultMessage()+"\n";
                }
                return new ResponseEntity<>(errori,HttpStatus.BAD_REQUEST);
            }


            String messaggio = servizi.insertUtente(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException e) {
           return new ResponseEntity<>("Username già utilizzato",HttpStatus.BAD_REQUEST);
        } catch (EmailDuplicateException e) {
           return new ResponseEntity<>("Email non disponibile", HttpStatus.BAD_REQUEST);
        }
    }
}
