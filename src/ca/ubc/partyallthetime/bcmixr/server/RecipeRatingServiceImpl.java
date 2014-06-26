package ca.ubc.partyallthetime.bcmixr.server;



import ca.ubc.partyallthetime.bcmixr.client.RecipeRatingService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;



/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RecipeRatingServiceImpl extends RemoteServiceServlet implements RecipeRatingService {
	
	static { 
     	ObjectifyService.register(RecipeRating.class);
	}
	
	
	
	public String getRating(String recipeTitle, String upOrDown) {
		
		
    	Objectify ofyRating = ObjectifyService.begin();
    	
    	
    	try{
    		// checks if there is any existing record of when the DataBC data was last parsed.
    		@SuppressWarnings("unused")
    		int testGetUpvotes = ofyRating.get(RecipeRating.class, recipeTitle).getUpVotes(); 
    	} catch (NotFoundException e) {
    		// if there is no record, stores a neutral rating for that recipe (0 upvotes, 0 downvotes)
    		RecipeRating newRecipeRating = new RecipeRating();
    		newRecipeRating.setRecipeTitle(recipeTitle);
    		newRecipeRating.setUpVotes(0);
    		newRecipeRating.setDownVotes(0);
    		ofyRating.put(newRecipeRating);
    		
    	}
    	
    	RecipeRating tempRecipeRating = ofyRating.query(RecipeRating.class).filter("recipeTitle", recipeTitle).get();
    	
    	int upVotes = tempRecipeRating.getUpVotes();
    	int downVotes = tempRecipeRating.getDownVotes();
    	
    	if (upOrDown.equals("up")){
    		tempRecipeRating.setUpVotes(upVotes + 1);
        	ofyRating.put(tempRecipeRating);
        	int updatedUpVotes = tempRecipeRating.getUpVotes();
        	String updatedUpVotesString = Integer.toString(updatedUpVotes);
        	return updatedUpVotesString;


    		
    	} if (upOrDown.equals("down")){
    		tempRecipeRating.setDownVotes(downVotes + 1);
        	ofyRating.put(tempRecipeRating);
        	int updatedDownVotes = tempRecipeRating.getDownVotes();
        	String updatedDownVotesString = Integer.toString(updatedDownVotes);
        	return updatedDownVotesString;

    	}   if (upOrDown.equals("get up votes")){
        	int updatedUpVotes = tempRecipeRating.getUpVotes();
        	String updatedUpVotesString = Integer.toString(updatedUpVotes);
        	return updatedUpVotesString;

    		
    	} if (upOrDown.equals("get down votes")){
        	int updatedDownVotes = tempRecipeRating.getDownVotes();
        	String updatedDownVotesString = Integer.toString(updatedDownVotes);
        	return updatedDownVotesString;

    	} else return "nothing returned from server.";
    	
		
    	

	}
}
	
	
