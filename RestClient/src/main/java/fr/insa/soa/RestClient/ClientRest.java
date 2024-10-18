package fr.insa.soa.RestClient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

public class ClientRest {

	public static void main(String[] args) {
		//instanciation de client, necessaire pour l'exécution des requétes et la consomation des réponses.
		Client client=ClientBuilder.newClient();
		//appel du service Rest, invocation de la méthode get correspondant à l'url
		Response response=client.target("http://localhost:8080/RestProject/webapi/comparator/longueur/Toulouse").request().get();
		//lecture de la réponse récupérée
		System.out.println(response.readEntity(String.class));
	}

}
