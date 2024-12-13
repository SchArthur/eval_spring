package com.eval.demo.controller;

import com.eval.demo.dao.EntrepriseDao;
import com.eval.demo.model.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class EntrepriseController {

    @Autowired
    private EntrepriseDao entrepriseDao;

    public EntrepriseController(EntrepriseDao entrepriseDao) {
        this.entrepriseDao = entrepriseDao;
    }

    @GetMapping("/entreprise")
    public List<Entreprise> getAllEntreprise() {

        return entrepriseDao.findAll();

    }

}
