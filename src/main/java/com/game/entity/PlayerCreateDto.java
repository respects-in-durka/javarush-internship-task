package com.game.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PlayerCreateDto {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned = false;
    private Integer experience;

    public PlayerCreateDto(String name, String title, Race race, Profession profession, Long birthday, Boolean banned, Integer experience) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
    }

    public PlayerCreateDto() {}

    public void validate() {
        if (name == null || name.equals("") || title == null || race == null || profession == null || birthday == null || experience == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (name.length() > 12) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (title.length() > 30) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (experience < 0 || experience > 10000000) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (birthday < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LocalDateTime birthdayDateTime = LocalDateTime.ofInstant(new Date(birthday).toInstant(), ZoneId.systemDefault());
        if (birthdayDateTime.getYear() < 2000 || birthdayDateTime.getYear() > 3000) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", birthday=" + birthday +
                ", banned=" + banned +
                ", experience=" + experience +
                '}';
    }
}
