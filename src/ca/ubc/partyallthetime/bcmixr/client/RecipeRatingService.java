package ca.ubc.partyallthetime.bcmixr.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("getRating")
public interface RecipeRatingService extends RemoteService {
	public String getRating(String recipeTitle, String upOrDown);
}
