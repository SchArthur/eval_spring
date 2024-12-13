package com.eval.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void recupererLesUtilisateursEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(get("/utilisateur"))
				.andExpect(status().isOk());
	}

	@Test
	void recupererLesUtilisateursEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(get("/utilisateur"))
				.andExpect(status().isForbidden());

	}

	@Test
	void supprimerUnUtilisateurEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(delete("/utilisateur/1"))
				.andExpect(status().isForbidden());

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void supprimerUnUtilisateurEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(delete("/utilisateur/1"))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void recupererLesConventionsEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(get("/convention"))
				.andExpect(status().isOk());
	}

	@Test
	void recupererLesConventionsEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(get("/convention"))
				.andExpect(status().isForbidden());

	}

	@Test
	void supprimerUneConventionEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(delete("/convention/1"))
				.andExpect(status().isForbidden());

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void supprimerUneConventionEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(delete("/convention/1"))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void recupererLesEntreprisesEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(get("/entreprise"))
				.andExpect(status().isOk());
	}

	@Test
	void recupererLesEntreprisesEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(get("/entreprise"))
				.andExpect(status().isForbidden());

	}

	@Test
	void supprimerUneEntrepriseEnTantQueAnonyme_reponse403() throws Exception{

		mvc.perform(delete("/entreprise/1"))
				.andExpect(status().isForbidden());

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMINISTRATEUR"})
	void supprimerUneEntrepriseEnTantQueAdministrateur_reponse200oK() throws Exception{

		mvc.perform(delete("/entreprise/1"))
				.andExpect(status().isOk());

	}

}
