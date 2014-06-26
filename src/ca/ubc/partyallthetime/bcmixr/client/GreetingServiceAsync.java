package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String SKU, String location, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
