package com.game.controller;

import com.game.Utils;
import com.game.entity.*;
import com.game.service.PlayersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayersController {

    private final PlayersService playersService;

    public PlayersController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("/players")
    public List<Player> getPlayersFiltered(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        return playersService.getAllPlayersFiltered(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel
    ) {
        return playersService.getPlayersCount(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable("id") String id) {
        Utils.validateId(id);
        return playersService.getPlayerById(Long.parseLong(id));
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody PlayerCreateDto playerDto) {
        System.out.println(playerDto);
        playerDto.validate();
        return playersService.createPlayer(playerDto);
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable("id") String id, @RequestBody PlayerUpdateDto playerDto) {
        Utils.validateId(id);
        playerDto.validate();
        return playersService.updatePlayer(Long.parseLong(id), playerDto);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") String id) {
        Utils.validateId(id);
        try {
            playersService.deletePlayer(Long.parseLong(id));
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}
