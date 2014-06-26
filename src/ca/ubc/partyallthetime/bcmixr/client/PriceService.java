package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("getPrice")
public interface PriceService extends RemoteService {
	public String getPrice(String SKU);
}
