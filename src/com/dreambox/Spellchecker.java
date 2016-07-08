/**
 * <p>Title: Spellchecker.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "spellchecker")
public class Spellchecker {
	
	private boolean correct;
	
	private Set<String> suggestions;
	
	public boolean getCorrect() {
		return this.correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public Set<String> getSuggestions() {
		return this.suggestions;
	}

	public void setSuggestions(Set<String> suggestions) {
		this.suggestions = suggestions;
	}
	
} 
