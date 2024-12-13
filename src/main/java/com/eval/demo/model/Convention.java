package com.eval.demo.model;

import com.eval.demo.view.ConventionView;
import com.eval.demo.view.EntrepriseView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Min;
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
public class Convention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EntrepriseView.class, ConventionView.class})
    Integer id;

    @JsonView({EntrepriseView.class, ConventionView.class})
    String nom;

    @PositiveOrZero(message = "La subvention ne peut pas être négative.")
    @JsonView(ConventionView.class)
    Float subvention;

    @Min(value = 1, message = "Le nombre de salariés maximum doit être au moins 1.")
    @JsonView(ConventionView.class)
    Integer salarieMaximum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ConventionView.class)
    Entreprise entreprise;

    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL)
    @JsonView(ConventionView.class)
    List<Salarie> salaries;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public @PositiveOrZero(message = "La subvention ne peut pas être négative.") Float getSubvention() {
        return subvention;
    }

    public void setSubvention(@PositiveOrZero(message = "La subvention ne peut pas être négative.") Float subvention) {
        this.subvention = subvention;
    }

    public @Min(value = 1, message = "Le nombre de salariés maximum doit être au moins 1.") Integer getSalarieMaximum() {
        return salarieMaximum;
    }

    public void setSalarieMaximum(@Min(value = 1, message = "Le nombre de salariés maximum doit être au moins 1.") Integer salarieMaximum) {
        this.salarieMaximum = salarieMaximum;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }
}
