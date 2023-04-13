package ru.melkov.springcourse.VCRApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.melkov.springcourse.VCRApp.models.OurUser;
import ru.melkov.springcourse.VCRApp.repositories.OurUsersRepository;
import ru.melkov.springcourse.VCRApp.security.OurUserDetails;

import java.util.Optional;

// Это нужно SS, чтобы юзать метод loadUserByUsername.
@Service
public class OurUsersDetailsService implements UserDetailsService {

    private final OurUsersRepository ourUsersRepository;


    @Autowired
    public OurUsersDetailsService(OurUsersRepository ourUsersRepository) {
        this.ourUsersRepository = ourUsersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUser> appUser = ourUsersRepository.findByUsername(username);

        if (appUser.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new OurUserDetails(appUser.get());
    }
}