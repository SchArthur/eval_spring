package com.eval.demo.dao;

import com.eval.demo.model.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalarieDao extends JpaRepository<Salarie, Integer> {
}
