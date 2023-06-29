package me.timur.findguideback.repository;

import me.timur.findguideback.entity.File;
import me.timur.findguideback.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    void deleteAllByGuide(Guide guide);
}