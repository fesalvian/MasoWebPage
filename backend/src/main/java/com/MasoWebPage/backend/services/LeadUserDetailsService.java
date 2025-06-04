package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeadUserDetailsService  implements UserDetailsService {
    @Autowired
    private LeadRepository leadRepository;


    @Override
    public Lead loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Lead> opt = leadRepository.findByEmail(email);
        Lead lead;
        if(opt.isPresent()){
            lead = opt.get();
            return lead;
        }else {
            lead = null;
            return lead;
        }
    }
}
