package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {
    Set<Region> findAllByEngNameIn(Collection<String> engNames);
    Optional<Region> findByEngName(String engName);
}