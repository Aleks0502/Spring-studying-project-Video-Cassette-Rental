package ru.melkov.springcourse.VCRApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 1923, message = "Год рождения должен быть больше, чем 1923!")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "whom_created_by")
    private String whomCreatedBy;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Videocassette> videocassetteList;


    // Spring'у обязательно нужен конструкитор по умолчанию.
    // @ModelAttribute создает пустой объект и с помощью сеттеров устанваливает значения полям.
    public Person() {

    }

    public Person(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getWhomCreatedBy() {
        return whomCreatedBy;
    }

    public void setWhomCreatedBy(String whomCreatedBy) {
        this.whomCreatedBy = whomCreatedBy;
    }

    public List<Videocassette> getVideocassetteList() {
        return videocassetteList;
    }

    public void setVideocassetteList(List<Videocassette> videocassetteList) {
        this.videocassetteList = videocassetteList;
    }
}
