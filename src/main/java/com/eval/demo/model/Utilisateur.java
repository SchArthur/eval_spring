package com.eval.demo.model;

import com.eval.demo.view.ConventionView;
import com.eval.demo.view.EntrepriseView;
import com.eval.demo.view.UtilisateurView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EntrepriseView.class, ConventionView.class, UtilisateurView.class})
    Integer id;

    @Column(length = 100, unique = true)
    @NotBlank(message = "L'adresse email ne peut pas être vide")
    @JsonView({EntrepriseView.class, ConventionView.class, UtilisateurView.class})
    String email;

    String password;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JsonView(UtilisateurView.class)
    Entreprise entreprise;

    public String getDroit(){
        if (entreprise == null){
            return "ADMINISTRATEUR";
        } else {
            return "ENTREPRISE";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "L'adresse email ne peut pas être vide") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "L'adresse email ne peut pas être vide") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
