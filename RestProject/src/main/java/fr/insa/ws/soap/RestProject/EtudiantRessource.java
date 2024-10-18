package fr.insa.ws.soap.RestProject;

import fr.insa.ws.soap.RestProject.Etudiant;
import fr.insa.ws.soap.RestProject.Binome;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;


@Path("etudiant")
public class EtudiantRessource {
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Etudiant getEtudiant(@Context UriInfo uriInfo) {
		Etudiant etudiant=new Etudiant();
		etudiant.setNom("N");
		etudiant.setPrenom("G");
		etudiant.setiD(1);
		etudiant.addLink(getUriforSelf(uriInfo,etudiant),"self", "GET");
		etudiant.addLink(getUriforStage(uriInfo),"Stage", "GET");
		return etudiant;
		
	}
	
	private String getUriforStage(UriInfo uriInfo) {
		String url=uriInfo.getBaseUriBuilder().path(StageRessource.class).build().toString();
		return url;
	}
	
	private String getUriforSelf(UriInfo uriInfo, Etudiant etudiant) {
		String url=uriInfo.getBaseUriBuilder().path(EtudiantRessource.class).path(Long.toString(etudiant.getiD())).build().toString();
		return url;
	}
 
	@POST
	@Path("/{idEtudiant}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEtudiant(Etudiant etudNouv) {
		System.out.println("Ajout de l'étudiant"+etudNouv.getNom()+" "+etudNouv.getPrenom()+ " réussi!");
		System.out.println("Son binôme est"+etudNouv.getBinome().getPrenom()+" "+etudNouv.getBinome().getNom());

	}

}
