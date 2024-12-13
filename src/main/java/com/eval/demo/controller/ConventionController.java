package com.eval.demo.controller;

import com.eval.demo.dao.ConventionDao;
import com.eval.demo.model.Convention;
import com.eval.demo.security.AppUserDetails;
import com.eval.demo.security.IsAdmin;
import com.eval.demo.security.IsEntreprise;
import com.eval.demo.view.ConventionView;
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
public class ConventionController {

    @Autowired
    private ConventionDao conventionDao;

    public ConventionController(ConventionDao conventionDao) {
        this.conventionDao = conventionDao;
    }

    // READ
    @JsonView(ConventionView.class)
    @GetMapping("/convention")
    public List<Convention> getAllConvention() {

        return conventionDao.findAll();

    }

    // READ
    @JsonView(ConventionView.class)
    @GetMapping("/convention/{id}")
    public ResponseEntity<Convention> getConventionById(@PathVariable int id) {

        Optional<Convention> optionalConvention = conventionDao.findById(id);

        if(optionalConvention.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalConvention.get(), HttpStatus.OK);
    }

    // CREATE
    @JsonView(ConventionView.class)
    @IsAdmin
    @PostMapping("/convention")
    public ResponseEntity<Convention> createConvention(@RequestBody @Valid Convention convention) {

        //on force l'id à null au cas où le client en aurait fourni un
        convention.setId(null);

        conventionDao.save(convention);

        return new ResponseEntity<>(convention, HttpStatus.CREATED);
    }

    // DELETE
    @JsonView(ConventionView.class)
    @IsEntreprise
    @DeleteMapping("/convention/{id}")
    public ResponseEntity<Convention> deleteConvention(@PathVariable Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        //On vérifie que l'Convention existe bien dans la base de donnée
        Optional<Convention> optionalConvention = conventionDao.findById(id);

        //si l'convention n'existe pas
        if(optionalConvention.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalConvention.get().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        conventionDao.deleteById(id);

        return new ResponseEntity<>(optionalConvention.get(), HttpStatus.OK);

    }

    // UPDATE
    @JsonView(ConventionView.class)
    @IsEntreprise
    @PutMapping("/convention/{id}")
    public ResponseEntity<Convention> updateConvention(@RequestBody @Valid Convention conventionEnvoye, @PathVariable Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        /*System.out.println(appUserDetails.getUtilisateur().getDroit());*/

        conventionEnvoye.setId(id);

        Optional<Convention> optionalConvention = conventionDao.findById(id);

        if(optionalConvention.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalConvention.get().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        conventionDao.save(conventionEnvoye);

        return new ResponseEntity<>(optionalConvention.get(), HttpStatus.OK);

    }

}
