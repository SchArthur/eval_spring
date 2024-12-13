package com.eval.demo.model;

import com.eval.demo.view.ConventionView;
import com.eval.demo.view.EntrepriseView;
import com.eval.demo.view.UtilisateurView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EntrepriseView.class, ConventionView.class, UtilisateurView.class})
    Integer id;

    @Column(length = 100, unique = true)
    @NotBlank(message = "Le nom de l'entreprise ne peut pas être vide")
    @JsonView({EntrepriseView.class, ConventionView.class, UtilisateurView.class})
    String nom;

    @OneToOne
    @JsonView({EntrepriseView.class, ConventionView.class})
    Utilisateur  utilisateur;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    @JsonView(EntrepriseView.class)
    List<Convention> conventions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Le nom de l'entreprise ne peut pas être vide") String getNom() {
        return nom;
    }

    public void setNom(@NotBlank(message = "Le nom de l'entreprise ne peut pas être vide") String nom) {
        this.nom = nom;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Convention> getConventions() {
        return conventions;
    }

    public void setConventions(List<Convention> conventions) {
        this.conventions = conventions;
    }
}
