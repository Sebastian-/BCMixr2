package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface PriceServiceAsync {
	public void getPrice(String SKU, AsyncCallback<String> callback);
}
