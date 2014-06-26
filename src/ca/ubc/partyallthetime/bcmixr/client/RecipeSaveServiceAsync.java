package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.partyallthetime.bcmixr.shared.Recipe;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RecipeSaveServiceAsync {
	public void saveRecipe(Recipe myrecipe, AsyncCallback<String> callback)
			throws IllegalArgumentException, IOException;
}