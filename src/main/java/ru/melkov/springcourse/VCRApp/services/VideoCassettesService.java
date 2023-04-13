package ru.melkov.springcourse.VCRApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melkov.springcourse.VCRApp.models.Person;
import ru.melkov.springcourse.VCRApp.models.Videocassette;
import ru.melkov.springcourse.VCRApp.repositories.VideocassettesRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VideoCassettesService {

    private final VideocassettesRepository videocassettesRepository;

    @Autowired
    public VideoCassettesService(VideocassettesRepository videocassettesRepository) {
        this.videocassettesRepository = videocassettesRepository;
    }

    public List<Videocassette> findAll() {
        return videocassettesRepository.findAll();
    }

    public Videocassette findOne(int id) {
        Optional<Videocassette> foundVideocassette = videocassettesRepository.findById(id);
        return foundVideocassette.orElse(null);
    }

    @Transactional
    public void save(Videocassette videocassette) {
        videocassettesRepository.save(videocassette);
    }

    @Transactional
    public void update(int id, Videocassette updatedVideocassette) {
        updatedVideocassette.setId(id);
        videocassettesRepository.save(updatedVideocassette);
    }

    @Transactional
    public void delete(int id) {
        videocassettesRepository.deleteById(id);
    }

    public Optional<Person> getVideocassetteOwner(int id) {
        return Optional.ofNullable(findOne(id).getOwner());
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        // jdbcTemplate.update("update videocassette set person_id=? WHERE id=?", selectedPerson.getId(), id);
        videocassettesRepository.findById(id).get().setOwner(selectedPerson);
        //findOne(id).setOwner(selectedPerson);
    }

    @Transactional
    public void release(int id) {
        // jdbcTemplate.update("update videocassette set person_id=null where id=?", id);
        findOne(id).setOwner(null);
    }


}
