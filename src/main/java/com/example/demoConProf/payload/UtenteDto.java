package com.example.demoConProf.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UtenteDto {

    private String nome;


    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "Il campo username è obbligatorio")
    private String username;

    @NotBlank(message = "Il campo password è obbligatorio")
    private String password;

    @NotBlank(message = "Il campo email è obbligatorio")
    @Email(message = "Indirizzo email non valido")
    private String email;

    // gesione ruoli
    // private String avatar;


}
