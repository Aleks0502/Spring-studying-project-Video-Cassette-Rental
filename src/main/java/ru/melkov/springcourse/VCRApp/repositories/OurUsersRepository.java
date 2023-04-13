package ru.melkov.springcourse.VCRApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.melkov.springcourse.VCRApp.models.OurUser;

import java.util.Optional;

@Repository
public interface OurUsersRepository extends JpaRepository<OurUser, Integer> {

    Optional<OurUser> findByUsername(String username);
}
