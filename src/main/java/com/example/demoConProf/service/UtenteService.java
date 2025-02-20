package com.example.demoConProf.service;

import com.example.demoConProf.exception.EmailDuplicateException;
import com.example.demoConProf.exception.UsernameDuplicateException;
import com.example.demoConProf.model.Utente;
import com.example.demoConProf.payload.UtenteDto;
import com.example.demoConProf.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
// con transactional noi apriamo una "transazione". è comodo! Jpa vuole lavorare in modalità transazionale. Quindi se non mettiamo transactional, Jpa dice che in automatico ci fa soltanto i get. Si fanno tanti controlli automatici e, se il tutto va Ok, il sistema(database) fa il metodo commit e rende tutto definitivo. Se ci sono errori x, il db fa roleback (torna tutto come prima).
public class UtenteService {

    @Autowired
    UtenteRepository repoUtente;

    public String insertUtente(UtenteDto dto) throws UsernameDuplicateException, EmailDuplicateException {
         //gestiremo l'errore
        checkDuplicateKey(dto.getUsername(), dto.getEmail());

        Utente user = dto_entity(dto);
        Long id = repoUtente.save(user).getIdutente();
        //faccio il save perchè è una semplice operazione di salvataggio di un oggetto non presente nel database.
        return "L'utente "+ user.getUsername() + " con id " + id +" è stato inserito correttamente";
    }


//    public UtenteDto insertUtente(UtenteDto dto){
//        Utente user = dto_entity(dto);
//        user = repoUtente.save(user);
//        dto = entity_dto(user);
//        return dto;
//    }



    public String updateUtente(String username,Long id){
        Optional<Utente> utenteTrovato = repoUtente.findById(id);

        //l'oggetto è agganciato al database
       Utente user = utenteTrovato.orElseThrow();

       // hibernate effetta un update sulla tabella utente
       user.setUsername(username);

        // non facciamo il save perche appena esce da questo metodo qui, fa il commit in automatico per il transactional.
       return "Username aggiornato correttamente";
    }
    public void checkDuplicateKey(String username,String email) throws UsernameDuplicateException,EmailDuplicateException{
        if(repoUtente.existsByUsername(username)){
            throw new UsernameDuplicateException();
        }
        if(repoUtente.existsByEmail(email)){
            throw new EmailDuplicateException();
        }
        //return true;
    }


    // *********METODI DI UTILITIES********
    public Utente dto_entity(UtenteDto dto){
        Utente user = new Utente();
        user.setEmail(dto.getEmail());
        user.setNome(dto.getNome());
        user.setCognome(dto.getCognome());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;
    }
    public UtenteDto entity_dto(Utente utente){
        UtenteDto dto = new UtenteDto();
        dto.setEmail(utente.getEmail());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setPassword(utente.getPassword());
       // dto.setUsername(utente.getUsername());
        return dto;
    }



}
