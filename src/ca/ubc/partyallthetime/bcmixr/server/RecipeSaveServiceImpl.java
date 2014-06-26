
// ****************I'm still working on this. I've disabled some code so that it works in a basic way.
// ****************I'm still trying to make it cache the parsing data for 12 hours (that's the complicated part).


package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.client.DisplayNameService;
import ca.ubc.partyallthetime.bcmixr.client.RecipeSaveService;
import ca.ubc.partyallthetime.bcmixr.shared.Recipe;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RecipeSaveServiceImpl extends RemoteServiceServlet implements
		RecipeSaveService {
	
	static { 
     	ObjectifyService.register(Recipe.class);
	}
	
	public String saveRecipe(Recipe myrecipe) throws IllegalArgumentException, IOException {


		//DAO dao = new DAO();
		
        Objectify ofyRecipe = ObjectifyService.begin();
        
        try{
        ofyRecipe.put(myrecipe);
        } catch (Exception e){
        	return "There was some kind of failure in ofyRecipe.put(myrecipe)";
        }
        return "success saving user-inputted recipe!";
        
        
		
	}
	

	}

	        
		
	




		
		



