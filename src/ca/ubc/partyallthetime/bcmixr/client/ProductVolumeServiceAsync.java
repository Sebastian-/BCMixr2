package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ProductVolumeServiceAsync {
	public void getVolume(String SKU, AsyncCallback<String> callback);
}
