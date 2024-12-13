package com.eval.demo.controller;

import com.eval.demo.dao.UtilisateurDao;
import com.eval.demo.model.Utilisateur;
import com.eval.demo.security.IsAdmin;
import com.eval.demo.view.UtilisateurView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    public UtilisateurController(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    // READ
    @IsAdmin
    @JsonView(UtilisateurView.class)
    @GetMapping("/utilisateur")
    public List<Utilisateur> getAllUtilisateur() {

        return utilisateurDao.findAll();

    }

    // READ
    @JsonView(UtilisateurView.class)
    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {

        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findById(id);

        if(optionalUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUtilisateur.get(), HttpStatus.OK);
    }

    // CREATE
    @JsonView(UtilisateurView.class)
    @IsAdmin
    @PostMapping("/utilisateur")
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody @Valid Utilisateur utilisateur) {

        //on force l'id à null au cas où le client en aurait fourni un
        utilisateur.setId(null);

        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));

        utilisateurDao.save(utilisateur);

        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    // DELETE
    @JsonView(UtilisateurView.class)
    @IsAdmin
    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> deleteUtilisateur(@PathVariable Integer id) {

        //On vérifie que l'Utilisateur existe bien dans la base de donnée
        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findById(id);

        //si l'utilisateur n'existe pas
        if(optionalUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        utilisateurDao.deleteById(id);

        return new ResponseEntity<>(optionalUtilisateur.get(), HttpStatus.OK);

    }

    // UPDATE
    @JsonView(UtilisateurView.class)
    @IsAdmin
    @PutMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@RequestBody @Valid Utilisateur utilisateurEnvoye, @PathVariable Integer id) {

        utilisateurEnvoye.setId(id);

        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findById(id);

        if(optionalUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        utilisateurDao.save(utilisateurEnvoye);

        return new ResponseEntity<>(optionalUtilisateur.get(), HttpStatus.OK);

    }

}
