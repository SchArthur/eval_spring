package com.eval.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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
    Integer id;

    String nom;

    @PositiveOrZero(message = "La subvention ne peut pas être négative.")
    Float subvention;

    @Min(value = 1, message = "Le nombre de salariés maximum doit être au moins 1.")
    Integer salarieMaximum;

    @ManyToOne(fetch = FetchType.EAGER)
    Entreprise entreprise;

    @OneToMany(mappedBy = "convention")
    List<Salarie> salaries;

}
