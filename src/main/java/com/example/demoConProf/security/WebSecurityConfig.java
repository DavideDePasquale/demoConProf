package com.example.demoConProf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity // oltre ad essere un file di configurazione, qui dentro verrà gestita la sicurezza globale dell'app. Spring trova la classe, la gestisce e configura l'ambiente di sicurezza
public class WebSecurityConfig {




}
