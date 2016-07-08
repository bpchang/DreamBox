/**
 * <p>Title: SpellcheckerServiceImpl.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox;

import java.io.FileNotFoundException;
import java.io.IOException;

//import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;


/**
 * This class implements the method declared in SpellcheckerService interface, which uses the JAX-RS annotation
 * to define the url path, http method, and data format. 
 */
public class SpellcheckerServiceImpl implements SpellcheckerService{

	
	private SpellcheckerDAO dao;
	
	
	public void setSpellcheckerDAO(SpellcheckerDAO dao){
		this.dao = dao;
	}
	
	
	public SpellcheckerDAO getSpellcheckerDAO(){
		return this.dao;
	}
	
	
	/**
	 * This method services a request for spell checking a word through using the 
	 * http GET service method.  Please see the SpellcheckerService interface for 
	 * the corresponding RESTful method signature.
	 * 
	 * @param word             This is the input word from the user
	 * @return Response        Response object the wraps the Spellchecker or error code
	 */
	public Response getSpellchecker(String word) {
		Spellchecker spellchecker = null;
		ResponseBuilder builder = null;
		try{
			spellchecker = (Spellchecker) getSpellcheckerDAO().getSpellchecker(word);
		}catch (FileNotFoundException e){
			System.out.println("ERROR: That file cannot be found.");
			 builder = Response.status(Status.NOT_FOUND);
			return builder.build();// generate Response object			
		}catch (IOException e) {
			System.out.println("ERROR: It cannot be read; or no words found in dictionary");
			builder = Response.status(Status.NOT_FOUND);
			return builder.build();// generate Response object	
		} 		
		return Response.ok(spellchecker).build();
	}
	
}
