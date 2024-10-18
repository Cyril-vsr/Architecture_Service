package fr.insa.ws.soap.RestProject;

import fr.insa.ws.soap.RestProject.Stage;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;

@Path("stage")
public class StageRessource {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Stage getStage (int idEtudiant) {
		Stage stage=new Stage();
		stage.SetEvaluation(16);
		stage.SetCompetence("SOA, Rest");
		stage.SetAnnée(2021);
		return stage;
	}
}
