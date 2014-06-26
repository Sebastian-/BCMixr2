package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("getDisplayNames")
public interface DisplayNameService extends RemoteService {
	public ArrayList<String> getDisplayNames() throws IllegalArgumentException, IOException;
}
