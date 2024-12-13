package com.eval.demo.controller;

import com.eval.demo.dao.EntrepriseDao;
import com.eval.demo.model.Entreprise;
import com.eval.demo.view.EntrepriseView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EntrepriseController {

    @Autowired
    private EntrepriseDao entrepriseDao;

    public EntrepriseController(EntrepriseDao entrepriseDao) {
        this.entrepriseDao = entrepriseDao;
    }

    @JsonView(EntrepriseView.class)
    @GetMapping("/entreprise")
    public List<Entreprise> getAllEntreprise() {

        return entrepriseDao.findAll();

    }

    @JsonView(EntrepriseView.class)
    @GetMapping("/entreprise/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable int id) {

        Optional<Entreprise> optionalEntreprise = entrepriseDao.findById(id);

        if(optionalEntreprise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalEntreprise.get(), HttpStatus.OK);
    }

}
