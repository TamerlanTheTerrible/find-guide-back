package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TransportRepository extends JpaRepository<Transport, String> {
    Set<Transport> findAllByEngNameIn(Collection<String> engNames);
}