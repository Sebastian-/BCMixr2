package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.partyallthetime.bcmixr.shared.Recipe;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("saveRecipe")
public interface RecipeSaveService extends RemoteService {
	public String saveRecipe(Recipe myrecipe) throws IllegalArgumentException, IOException;
}
