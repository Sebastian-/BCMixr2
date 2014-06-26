package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("getParse")
public interface CheckIfRecentlyParsed extends RemoteService {
	public String check() throws IllegalArgumentException, IOException;
}
