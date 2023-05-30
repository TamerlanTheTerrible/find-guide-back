package me.timur.findguideback.repository;

import me.timur.findguideback.entity.GuideSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

@Repository
public interface GuideSearchRepository extends JpaRepository<GuideSearch, Long> {
}
