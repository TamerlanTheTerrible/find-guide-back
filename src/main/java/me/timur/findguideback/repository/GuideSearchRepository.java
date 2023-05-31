package me.timur.findguideback.repository;

import me.timur.findguideback.entity.GuideSearch;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.model.enums.SearchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

@Repository
public interface GuideSearchRepository extends JpaRepository<GuideSearch, Long> {
    List<GuideSearch> findAllByClientAndStatusIn(User client, Collection<SearchStatus> status);
}
