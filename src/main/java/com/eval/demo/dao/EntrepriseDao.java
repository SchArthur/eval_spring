package com.eval.demo.dao;

import com.eval.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseDao extends JpaRepository<Entreprise, Integer> {



}
