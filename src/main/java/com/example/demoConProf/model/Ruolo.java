package com.example.demoConProf.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idruolo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ERuolo nome;



}
