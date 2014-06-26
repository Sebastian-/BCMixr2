package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CheckIfRecentlyParsedAsync {
	public void check(AsyncCallback<String> callback)
			throws IllegalArgumentException, IOException;
}
