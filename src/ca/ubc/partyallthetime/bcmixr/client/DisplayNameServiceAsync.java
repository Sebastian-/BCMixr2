package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DisplayNameServiceAsync {
	public void getDisplayNames(AsyncCallback<ArrayList<String>> callback)
			throws IllegalArgumentException, IOException;
}
