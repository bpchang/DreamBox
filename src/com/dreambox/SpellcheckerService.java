/**
 * <p>Title: SpellcheckerService.java
 * @author bchang
 * @version 1.0
 */
package com.dreambox;

//JAX-RS Imports
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface SpellcheckerService {
	
	@GET
	@Path("/{word}")
	@Produces({"application/json", "application/xml"})
	public Response getSpellchecker(@PathParam("word")String word);

}
