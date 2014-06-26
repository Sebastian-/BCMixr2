package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String SKU, String location) throws IllegalArgumentException {
	//	// Verify that the input is valid. 
	//	if (!FieldVerifier.isValidName(input)) {
	//		// If the input is not valid, throw an IllegalArgumentException back to
	//		// the client.
	//		throw new IllegalArgumentException(
	//				"Name must be at least 4 characters long");
	//	}

		
		
		// the next few lines build the URL that will be used to query the BC Liquor website.
		String baseURL = "http://www.bcliquorstores.com/store/locator/location_data_js/";
		String generatedURL = baseURL.concat(SKU).concat("/product");
		
		System.out.println(generatedURL.toString());
		// connects to the BC Liquor website and captures the query response as a string.
		 URL bc_liquor;
		try {
			bc_liquor = new URL(generatedURL);

		    URLConnection connection = bc_liquor.openConnection();
		    BufferedReader input = new BufferedReader(
		                            new InputStreamReader(
		                                connection.getInputStream()));
		    StringBuffer stringbuffer = new StringBuffer();
		    String inputLine;
		    while ((inputLine = input.readLine()) != null) 
		        stringbuffer.append(inputLine);
		    input.close();
		    String response = stringbuffer.toString();
		    

		    
		    //*** debug println:
	//	    System.out.println(response);
		    
		    // checks if the current store is mentioned in the response from the BC Liquor website.
		    // if it is mentioned, it means that the product is available at that store. 
		    
		    String regularExpression = "(?i).*".concat(location).concat(".*");
		    
		    boolean b = response.matches(regularExpression);
		    
		    if (b)
		    	return "In stock ✓";
		    return "Not in stock ✘";
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return "Error: Malformed URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "Error: IOException";
		}
		
		
		
		
		
		
		
		
//		String serverInfo = getServletContext().getServerInfo();
//		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
//
//		// Escape data from the client to avoid cross-site script vulnerabilities.
//		input = escapeHtml(input);
//		userAgent = escapeHtml(userAgent);
//
//		return "Hello, " + input + "!<br><br>I am running " + serverInfo
//				+ ".<br><br>It looks like you are using:<br>" + userAgent;
//	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
//	private String escapeHtml(String html) {
//		if (html == null) {
//			return null;
//		}
//		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
//				.replaceAll(">", "&gt;");
	}
}
