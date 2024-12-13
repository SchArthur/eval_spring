package com.eval.demo.model;

import com.eval.demo.view.ConventionView;
import com.eval.demo.view.SalarieView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Salarie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ConventionView.class, SalarieView.class})
    Integer id;

    @Size(min = 3, max = 10, message = "Le matricule doit contenir entre 3 et 10 caractères.")
    @JsonView({ConventionView.class, SalarieView.class})
    String matricule;

    @NotBlank(message = "Le code barre ne peut pas être vide")
    @JsonView({ConventionView.class, SalarieView.class})
    String codeBarre;

    @ManyToOne
    @JsonView(SalarieView.class)
    Convention convention;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Size(min = 3, max = 10, message = "Le matricule doit contenir entre 3 et 10 caractères.") String getMatricule() {
        return matricule;
    }

    public void setMatricule(@Size(min = 3, max = 10, message = "Le matricule doit contenir entre 3 et 10 caractères.") String matricule) {
        this.matricule = matricule;
    }

    public @NotBlank(message = "Le code barre ne peut pas être vide") String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(@NotBlank(message = "Le code barre ne peut pas être vide") String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public Convention getConvention() {
        return convention;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }
}
