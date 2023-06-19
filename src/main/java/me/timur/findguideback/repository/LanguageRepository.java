package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    Set<Language> findAllByEngNameIn(Collection<String> engNames);
    Optional<Language> findByEngName(String engName);
}