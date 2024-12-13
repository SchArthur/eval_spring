package com.eval.demo.controller;

import com.eval.demo.dao.EntrepriseDao;
import com.eval.demo.model.Entreprise;
import com.eval.demo.security.AppUserDetails;
import com.eval.demo.security.IsAdmin;
import com.eval.demo.security.IsEntreprise;
import com.eval.demo.view.EntrepriseView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // READ
    @JsonView(EntrepriseView.class)
    @IsEntreprise
    @GetMapping("/entreprise")
    public List<Entreprise> getAllEntreprise() {

        return entrepriseDao.findAll();

    }

    // READ
    @JsonView(EntrepriseView.class)
    @IsEntreprise
    @GetMapping("/entreprise/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable int id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        Optional<Entreprise> optionalEntreprise = entrepriseDao.findById(id);

        if(optionalEntreprise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalEntreprise.get().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(optionalEntreprise.get(), HttpStatus.OK);
    }

    // CREATE
    @JsonView(EntrepriseView.class)
    @IsAdmin
    @PostMapping("/entreprise")
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody @Valid Entreprise entreprise) {

        //on force l'id à null au cas où le client en aurait fourni un
        entreprise.setId(null);

        entrepriseDao.save(entreprise);

        return new ResponseEntity<>(entreprise, HttpStatus.CREATED);
    }

    // DELETE
    @JsonView(EntrepriseView.class)
    @IsAdmin
    @DeleteMapping("/entreprise/{id}")
    public ResponseEntity<Entreprise> deleteEntreprise(@PathVariable Integer id) {

        //On vérifie que l'Entreprise existe bien dans la base de donnée
        Optional<Entreprise> optionalEntreprise = entrepriseDao.findById(id);

        //si l'entreprise n'existe pas
        if(optionalEntreprise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        entrepriseDao.deleteById(id);

        return new ResponseEntity<>(optionalEntreprise.get(), HttpStatus.OK);

    }

    // UPDATE
    @JsonView(EntrepriseView.class)
    @IsAdmin
    @PutMapping("/entreprise/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@RequestBody @Valid Entreprise entrepriseEnvoye, @PathVariable Integer id) {

        entrepriseEnvoye.setId(id);

        Optional<Entreprise> optionalEntreprise = entrepriseDao.findById(id);

        if(optionalEntreprise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        entrepriseDao.save(entrepriseEnvoye);

        return new ResponseEntity<>(optionalEntreprise.get(), HttpStatus.OK);

    }

}
