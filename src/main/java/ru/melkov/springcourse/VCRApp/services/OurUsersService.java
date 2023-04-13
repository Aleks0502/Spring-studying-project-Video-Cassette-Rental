package ru.melkov.springcourse.VCRApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melkov.springcourse.VCRApp.models.OurUser;
import ru.melkov.springcourse.VCRApp.repositories.OurUsersRepository;
import ru.melkov.springcourse.VCRApp.security.OurUserDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OurUsersService {

    private final OurUsersRepository ourUsersRepository;
    private final PasswordEncoder passwordEncoder;
    

    @Autowired
    public OurUsersService(OurUsersRepository ourUsersRepository, PasswordEncoder passwordEncoder) {
        this.ourUsersRepository = ourUsersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Для OurUsersValidator:
    public Optional<OurUser> getOurUserByUsername(String username) {
        return ourUsersRepository.findByUsername(username);
    }

    @Transactional
    public void save(OurUser ourUser) {
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        ourUser.setRole("ROLE_USER");
        ourUsersRepository.save(ourUser);
    }

    @Transactional
    public void delete(int id) {
        ourUsersRepository.deleteById(id);
    }

}
