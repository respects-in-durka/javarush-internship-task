package com.game.service;

import com.game.Utils;
import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.repository.PlayersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class PlayersService {

    private final PlayersRepository playersRepository;

    public PlayersService(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    public List<Player> getAllPlayersFiltered(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        return playersRepository.findAll(Utils.createSpecification(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel), PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()))).toList();
    }

    public Integer getPlayersCount(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return getAllPlayersFiltered(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, PlayerOrder.ID, 0, Integer.MAX_VALUE).size();
    }

    public Player getPlayerById(Long id) {
        Player player = playersRepository.getPlayerById(id);
        if (player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return player;
    }

    public Player createPlayer(PlayerCreateDto dto) {
        Player player = new Player();
        player.setName(dto.getName());
        player.setTitle(dto.getTitle());
        player.setRace(dto.getRace());
        player.setProfession(dto.getProfession());
        player.setBirthday(new Date(dto.getBirthday()));
        player.setBanned(dto.getBanned());
        player.setExperience(dto.getExperience());
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);
        player.setUntilNextLevel(50 * (level + 1) * (level + 2) - player.getExperience());
        return playersRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playersRepository.deleteById(id);
    }

    public Player updatePlayer(Long id, PlayerUpdateDto dto) {
        Player player = getPlayerById(id);
        if (player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (dto.getName() != null) player.setName(dto.getName());
        if (dto.getTitle() != null) player.setTitle(dto.getTitle());
        if (dto.getRace() != null) player.setRace(dto.getRace());
        if (dto.getProfession() != null) player.setProfession(dto.getProfession());
        if (dto.getBirthday() != null) player.setBirthday(new Date(dto.getBirthday()));
        if (dto.getBanned() != null) player.setBanned(dto.getBanned());
        if (dto.getExperience() != null) player.setExperience(dto.getExperience());
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);
        player.setUntilNextLevel(50 * (level + 1) * (level + 2) - player.getExperience());
        return playersRepository.save(player);
    }
}
