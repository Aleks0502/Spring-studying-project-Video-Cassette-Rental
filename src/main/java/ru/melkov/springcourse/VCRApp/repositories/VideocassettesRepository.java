package ru.melkov.springcourse.VCRApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.melkov.springcourse.VCRApp.models.Videocassette;

@Repository
public interface VideocassettesRepository extends JpaRepository<Videocassette, Integer> {
}
