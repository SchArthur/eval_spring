package com.eval.demo.dao;

import com.eval.demo.model.Convention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConventionDao extends JpaRepository<Convention, Integer> {
}
