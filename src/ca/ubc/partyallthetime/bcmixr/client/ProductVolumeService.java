package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("getVolume")
public interface ProductVolumeService extends RemoteService {
	public String getVolume(String SKU);
}
