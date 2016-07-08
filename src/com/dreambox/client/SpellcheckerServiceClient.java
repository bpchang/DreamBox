/**
 * <p>Title: SpellcheckerServiceClient.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox.client;

import java.util.Iterator;
import org.apache.cxf.jaxrs.client.WebClient;
import com.dreambox.Spellchecker;

public class SpellcheckerServiceClient {

	public static void main(String[] args) {
		String inputWord1 = "balloon";
		String inputWord2 = "BaLloon";
		String inputWord3 = "BalLoooon";

		// Service instance
		WebClient client = WebClient.create("http://localhost:8080/");
		Spellchecker spellchecker = client.path("spelling/" + inputWord1).accept(
				"application/xml").get(Spellchecker.class);
		System.out.println("Spellchecker details from REST TEST service #1.");
		System.out.println("Spellchecker Input Word: " + inputWord1);
		System.out.println("Spellchecker Correct: " + spellchecker.getCorrect());
		System.out.println("Spellchecker word suggestions: " + spellchecker.getSuggestions());
		System.out.println("");
		
		WebClient client1 = WebClient.create("http://localhost:8080/");
		Spellchecker spellchecker1 = client1.path("spelling/" + inputWord2).accept(
				"application/xml").get(Spellchecker.class);
		System.out.println("Spellchecker details from REST TEST service #2.");
		System.out.println("Spellchecker Input Word:  "  + inputWord2);
		System.out.println("Spellchecker Correct: " + spellchecker1.getCorrect());
		System.out.println("Spellchecker word suggestions: " + spellchecker1.getSuggestions());
		System.out.println("");
		
		WebClient client2 = WebClient.create("http://localhost:8080/");
		Spellchecker spellchecker2 = client2.path("spelling/" + inputWord3).accept(
				"application/xml").get(Spellchecker.class);
		System.out.println("Spellchecker details from REST TEST service #3.");
		System.out.println("Spellchecker Input Word:  " + inputWord3);
		System.out.println("Spellchecker Correct: " + spellchecker2.getCorrect());
		System.out.println("Spellchecker word suggestions: " + spellchecker2.getSuggestions());		
	}

}
