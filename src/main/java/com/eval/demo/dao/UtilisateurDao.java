package com.eval.demo.dao;

import com.eval.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
}
