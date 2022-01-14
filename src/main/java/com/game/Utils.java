package com.game;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static void validateId(String id) {
        if (id.split("\\.").length != 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            long idLong = Long.parseLong(id);
            if (idLong <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException numberFormatException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static Specification<Player> createSpecification(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return Specification.where((Specification<Player>) (root, query, criteriaBuilder) -> name != null ? criteriaBuilder.gt(criteriaBuilder.locate(root.get("name"), name), 0) : null)
                .and((root, query, criteriaBuilder) -> title != null ? criteriaBuilder.gt(criteriaBuilder.locate(root.get("title"), title), 0) : null)
                .and((root, query, criteriaBuilder) -> race != null ? criteriaBuilder.equal(root.get("race"), race) : null)
                .and((root, query, criteriaBuilder) -> profession != null ? criteriaBuilder.equal(root.get("profession"), profession) : null)
                .and((root, query, criteriaBuilder) -> {
                    if (after == null && before == null) return null;
                    if (before == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
                    if (after == null) return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date(before));
                    calendar.set(Calendar.HOUR, 0);
                    calendar.add(Calendar.MILLISECOND, -1);
                    return criteriaBuilder.between(root.get("birthday"), new Date(after), calendar.getTime());
                })
                .and(((root, query, criteriaBuilder) -> banned != null ? criteriaBuilder.equal(root.get("banned"), banned) : null))
                .and((root, query, criteriaBuilder) -> {
                    if (minExperience == null && maxExperience == null) return null;
                    if (minExperience == null) return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience);
                    if (maxExperience == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience);
                    return criteriaBuilder.between(root.get("experience"), minExperience, maxExperience);
                })
                .and(((root, query, criteriaBuilder) -> {
                    if (minLevel == null && maxLevel == null) return null;
                    if (minLevel == null) return criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel);
                    if (maxLevel == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel);
                    return criteriaBuilder.between(root.get("level"), minLevel, maxLevel);
                }));
    }
}
