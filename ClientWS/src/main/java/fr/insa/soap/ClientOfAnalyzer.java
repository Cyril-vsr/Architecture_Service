package fr.insa.soap;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import fr.insa.soap.wsdltojava.AnalyserChaineWS;
import fr.insa.soap.wsdltojava.Analyzer;

public class ClientOfAnalyzer {

	public static void main(String[] args) throws MalformedURLException {
		// l'adresse du service web
		final String adresse="http://localhost:8089/analyser";
		// création URL
		final URL url = URI.create(adresse).toURL();
		// instanciation de l'image de service
		final Analyzer service = new Analyzer(url);
		// création du proxi
		final AnalyserChaineWS port = service.getPort(AnalyserChaineWS.class);
		String chaine="aaaaaaa";
		// appel de méthode compare via le port
		System.out.println("la taille de la chaine "+chaine+" est "+port.compare(chaine));

	}

}
