/**
 * <p>Title: SpellcheckerTestClient.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox.client;

import com.dreambox.Spellchecker;
import com.dreambox.SpellcheckerDAO;

public class SpellcheckerTestClient {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		
		String word = "Balloooon";
		SpellcheckerDAO check = new SpellcheckerDAO();
		Spellchecker spellChecker = new Spellchecker();
	     try{
			check.loadDictionary("C:/Dreambox/englishwordslist.txt");
	     } catch (Exception e){
	    	 System.out.println("error: " + e.getMessage());
	    	 
	     }
			// get the closest suggestion for the word entered.
		try{
			check.findClosestMatch(word, spellChecker);
		} catch (Exception e){
			System.out.println("error:  + No words found in dictionary");
		}
			System.out.println("Correct word found: " + spellChecker.getCorrect());
			System.out.println("Suggested words: " + spellChecker.getSuggestions());		
			System.out.println("END");		
	} 	

}
