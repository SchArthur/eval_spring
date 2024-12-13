package com.eval.demo.controller;

import com.eval.demo.dao.SalarieDao;
import com.eval.demo.model.Salarie;
import com.eval.demo.view.SalarieView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class SalarieController {

    @Autowired
    private SalarieDao salarieDao;

    public SalarieController(SalarieDao salarieDao) {
        this.salarieDao = salarieDao;
    }

    // READ
    @JsonView(SalarieView.class)
    @GetMapping("/salarie")
    public List<Salarie> getAllSalarie() {

        return salarieDao.findAll();

    }

    // READ
    @JsonView(SalarieView.class)
    @GetMapping("/salarie/{id}")
    public ResponseEntity<Salarie> getSalarieById(@PathVariable int id) {

        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);
    }

    // CREATE
    @JsonView(SalarieView.class)
    @PostMapping("/salarie")
    public ResponseEntity<Salarie> createSalarie(@RequestBody @Valid Salarie salarie) {

        //on force l'id à null au cas où le client en aurait fourni un
        salarie.setId(null);

        salarieDao.save(salarie);

        return new ResponseEntity<>(salarie, HttpStatus.CREATED);
    }

    // DELETE
    @JsonView(SalarieView.class)
    @DeleteMapping("/salarie/{id}")
    public ResponseEntity<Salarie> deleteSalarie(@PathVariable Integer id) {

        //On vérifie que l'Salarie existe bien dans la base de donnée
        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        //si l'salarie n'existe pas
        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        salarieDao.deleteById(id);

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);

    }

    // UPDATE
    @JsonView(SalarieView.class)
    @PutMapping("/salarie/{id}")
    public ResponseEntity<Salarie> updateSalarie(@RequestBody @Valid Salarie salarieEnvoye, @PathVariable Integer id) {

        salarieEnvoye.setId(id);

        Optional<Salarie> optionalSalarie = salarieDao.findById(id);

        if(optionalSalarie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        salarieDao.save(salarieEnvoye);

        return new ResponseEntity<>(optionalSalarie.get(), HttpStatus.OK);

    }

}
