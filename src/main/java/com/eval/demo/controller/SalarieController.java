package com.eval.demo.controller;

import com.eval.demo.dao.ConventionDao;
import com.eval.demo.dao.SalarieDao;
import com.eval.demo.model.Convention;
import com.eval.demo.model.Salarie;
import com.eval.demo.security.AppUserDetails;
import com.eval.demo.security.IsAdmin;
import com.eval.demo.security.IsEntreprise;
import com.eval.demo.view.SalarieView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class SalarieController {

    @Autowired
    private SalarieDao salarieDao;

    @Autowired
    private ConventionDao conventionDao;

    public SalarieController(SalarieDao salarieDao) {
        this.salarieDao = salarieDao;
    }

    // READ
    @JsonView(SalarieView.class)
    @IsAdmin
    @GetMapping("/salarie")
    public List<Salarie> getAllSalarie() {

        return salarieDao.findAll();

    }

    // READ
    @JsonView(SalarieView.class)
    @IsEntreprise
    @GetMapping("/salarie/{id}")
    public ResponseEntity<Salarie> getSalarieById(@PathVariable int id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalSalarie.get().getConvention().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);
    }

    // CREATE
    @JsonView(SalarieView.class)
    @IsEntreprise
    @PostMapping("/salarie")
    public ResponseEntity<Salarie> createSalarie(@RequestBody @Valid Salarie salarie, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        Integer conventionId = salarie.getConvention().getId();

        Optional<Convention> optionalConvention = conventionDao.findById(conventionId);

        if(optionalConvention.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalConvention.get().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Integer maxSalaries = optionalConvention.get().getSalarieMaximum();

        Integer countSalaries = optionalConvention.get().getSalaries().size();

        if (countSalaries >= maxSalaries) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //on force l'id à null au cas où le client en aurait fourni un
        salarie.setId(null);

        salarie.setConvention(optionalConvention.get());

        salarieDao.save(salarie);

        return new ResponseEntity<>(salarie, HttpStatus.CREATED);
    }

    // DELETE
    @JsonView(SalarieView.class)
    @IsEntreprise
    @DeleteMapping("/salarie/{id}")
    public ResponseEntity<Salarie> deleteSalarie(@PathVariable Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        //On vérifie que l'Salarie existe bien dans la base de donnée
        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        //si l'salarie n'existe pas
        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalSalarie.get().getConvention().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        salarieDao.deleteById(id);

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);

    }

    // UPDATE
    @JsonView(SalarieView.class)
    @IsEntreprise
    @PutMapping("/salarie/{id}")
    public ResponseEntity<Salarie> updateSalarie(@RequestBody @Valid Salarie salarieEnvoye, @PathVariable Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {

        salarieEnvoye.setId(id);

        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!appUserDetails.getUtilisateur().getDroit().equals("ADMINISTRATEUR") &&
                !optionalSalarie.get().getConvention().getEntreprise().getUtilisateur().getId().equals(appUserDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        salarieDao.save(salarieEnvoye);

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);

    }

}
