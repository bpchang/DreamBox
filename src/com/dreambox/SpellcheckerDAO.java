/**
 * <p>Title: SpellcheckerDAO.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *  This class is the data access layer, which loads the dictionary and searches for
 *  an existing word in the dictionary. If the correct word is not found, it will return
 *  a list of suggested words to the user. 
 *  
 *  This class uses the Levenshtein distance algorithm to determine which word within a 
 *  dictionary is the closest to a provided, misspelled word. The Levenshtein distance 
 *  measures the number of changes required to form one word from another.
 */
public class SpellcheckerDAO {
	
	private static boolean PRODUCTION = false;
	private static String LINUX_SPELL_PATH = 
								"/usr/share/tomcat8/webapps/spelling/WEB-INF/classes/com/dreambox/englishwordslist.txt";
	private static String LOCAL_SPELL_PATH = 
								"C:/Tomcat 8.0/webapps/spelling/WEB-INF/classes/com/dreambox/englishwordslist.txt";

	private ArrayList<String> words;
	
	public SpellcheckerDAO(){
		words = new ArrayList<String>();
	}
	
	/**
	 * @param  word                    This is the input search word. 
	 * @return Spellchecker            This is the REST resource that contains suggested words and correction status.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Spellchecker getSpellchecker(String word) throws FileNotFoundException, IOException{
		Spellchecker spellChecker = new Spellchecker();		
		if(PRODUCTION){
			loadDictionary(LINUX_SPELL_PATH);
		}else{
			loadDictionary(LOCAL_SPELL_PATH);
		}
		
		// get the closest suggestion for the word entered
		findClosestMatch(word, spellChecker);				
		return spellChecker;
	}
	
	/**
	 * @param  fileName                  This is the filename that contains all the dictionary words for lookup.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadDictionary(String fileName) throws FileNotFoundException, IOException {
			// create a new filereader to open the given input.
			FileReader freader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(freader);			
			String holder = reader.readLine();
			
			// continue until the end of the file.
			while (holder != null) {
				// add the read-in value to the array of words.
				words.add(holder);
				holder = reader.readLine();
			}		
	}
	
	
	private int findTheMinimum(int a, int b, int c) {
		int minimum = a;
		
		// find the minimum of the three values a, b, and c.
		if (b < minimum) {
			minimum = b;
		} // if
		
		if (c < minimum) {
			minimum = c;
		} // if
		
		return minimum;
	}
	
	
	/**
	 * This method provides the Levenshtein Distance Algorithm, which begin by accepting 
	 * two strings.  The matrix is formed with n rows and m columns, where n is the length
	 * of input word and m is the length of test word from dictionary.  When the algorithm 
	 * has processed each empty cell, the Levenshtein distance is located at the bottom 
	 * right hand corner.
	 * 
	 * @param inputWord   This is the input search word.
	 * @param testWord    This is the dictionary word.
	 * @return int        Return Levenshtein distance value 
	 */
	private int findDifference(String inputWord, String testWord) {
		int n, m;
		char word1Holder, word2Holder;
		int above, left, diagonal, cost;
		
		n = inputWord.length();
		m = testWord.length();
		
		// if one of the words is empty (a length of zero), the levenshtein distance
		// is simply the length of the input word.
		if (n == 0) {
			return m;
		}
		
		if (m == 0) {
			return n;
		} 
		
		// create a new matrix with n rows and m columns.
		int[][] testMatrix = new int[n + 1][m + 1];

		// instantiate the rows with the numbers 0..n
		for (int x = 0; x <= n; x++) {
			testMatrix[x][0] = x;
		}
		
		// instatiate the columns with the numbers 0..m
		for (int x = 0; x <= m; x++) {
			testMatrix[0][x] = x;
		}

		// go through the entire matrix. get the character at i column of the input word and 
		// j row of the test word.
		for (int i = 1; i <= n; i++) {
			word1Holder = inputWord.charAt (i - 1);
			
			for (int j = 1; j <= m; j++) {
				word2Holder = testWord.charAt (j - 1);
				
				// find the values of the cell directly above and to the left of the current cell.
				// add one to each of these variables.
				above = testMatrix[i - 1][j] + 1;
				left = testMatrix[i][j - 1] + 1;
				
				// if the two characters are equal, their cost is zero. find the value of the diagonal.
				if (word1Holder == word2Holder) {
					diagonal = testMatrix[i - 1][j - 1];
				} else {
					// if they are not, the cost is 1. find the value of the diagonal + 1.
					diagonal = testMatrix[i - 1][j - 1] + 1;
				} // else

				// find the minimum of the three values (above, left, and diagonal.
				testMatrix[i][j] = findTheMinimum(above, left, diagonal);
			}
		} 
		
		// return the value of the cell to the bottom right of the matrix. this is the levenshtein distance.
		return testMatrix[n][m];
	}
	
	
	/**
	 * @param inputWord      This is the input word for search.
	 * @param spellChecker   This is the Spellchecker that contains suggested words and status
	 * @throws IOException
	 */
	public void findClosestMatch(String inputWord, Spellchecker spellChecker) throws IOException{
		int smallestWord = -1, smallestDistance = 100;
		Set<String> suggestions = new HashSet<String>();
		
		// Go through each word to find the word with the least levenshtein distance.
		// If the current word in the list has a shorter levenshtein distance,
		// than the last minimum, set it to the minimum.
		for (int x = 0; x < words.size(); x++) {
			int holder = findDifference(inputWord, words.get(x).toString());		
			if (holder < smallestDistance) {
				smallestDistance = holder;
				smallestWord = x;
				String resultWord = words.get(smallestWord).toString();
				if(resultWord != null && resultWord.length() > inputWord.length() - 3){
					suggestions.add(resultWord);
				}
			} 
		} 
			
		// if the minimum distance is 0, the word is spelled correctly
		if (smallestDistance == 0) {
			//SPELLED CORRECTLY
			spellChecker.setCorrect(true);	
		} else {
			spellChecker.setCorrect(false);		
			if (suggestions.size() == 0 ) {
				//no word found, general exception
				throw new IOException();
			}else{
				//return the closest matching word.
				spellChecker.setSuggestions(suggestions);	
			}			
		} 
	} 
	

}
