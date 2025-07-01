package org.example.gestionchampionnatthymeleaf.repository;

import org.example.gestionchampionnatthymeleaf.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipRepository extends JpaRepository<Championship, Long>{
}
