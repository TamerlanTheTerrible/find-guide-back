package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long>, GuideSearchRepository {
    Optional<Guide> findByUserTelegramId(Long telegramId);
    Optional<Guide> findByUserIdOrUserTelegramId(Long userId, Long telegramId);
}