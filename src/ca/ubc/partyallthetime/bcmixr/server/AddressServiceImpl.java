package ca.ubc.partyallthetime.bcmixr.server;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import ca.ubc.partyallthetime.bcmixr.client.AddressService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AddressServiceImpl extends RemoteServiceServlet implements
		AddressService {

	Objectify ofyAdd;
	
	
	
	static {
		ObjectifyService.register(Address.class);
		ObjectifyService.register(AddressList.class);
	}

	public ArrayList<Address> addressList;
	public ArrayList<String> tempResults;
	public String[] results;
	public String[] getAddress() throws IOException {

		Objectify ofyTime = ObjectifyService.begin();
		ofyAdd = ObjectifyService.begin();
		long CACHE_EXPIRATION_TIME = 432000000; // The cached values expire 12
												// hours (432000000ms) after
												// parsing.
		Date currentDate = new Date();
		long currentTime = currentDate.getTime();

		try {
			@SuppressWarnings("unused")
			long testAddressParse = ofyTime.get(TimeOfLastParse.class,
					"AddressList parsed 23").getTimeOfLastParse();

		} catch (NotFoundException e) {

			TimeOfLastParse addressParseTime = new TimeOfLastParse();
			long timeAddressLastParsed = currentDate.getTime()
					- CACHE_EXPIRATION_TIME;
			addressParseTime.setId("AddressList parsed 23");
			addressParseTime.setTimeOfLastParse(timeAddressLastParsed);
			ofyTime.put(addressParseTime);
		}

		long lastAddressParse = ofyTime.get(TimeOfLastParse.class,
				"AddressList parsed 23").getTimeOfLastParse();

		String currentTimeString = String.valueOf(currentTime);
		String CACHE_EXPIRATION_TIME_String = String
				.valueOf(CACHE_EXPIRATION_TIME);
		BigInteger bigCACHE_EXPIRATION_TIME = new BigInteger(
				CACHE_EXPIRATION_TIME_String);
		BigInteger bigCurrentTime = new BigInteger(currentTimeString);
		String timeOfLastAddressParseString = String.valueOf(lastAddressParse);
		BigInteger bigTimeOfLastAddressParse = new BigInteger(
				timeOfLastAddressParseString);
		BigInteger addressDifference = bigCurrentTime
				.subtract(bigTimeOfLastAddressParse);

		if (addressDifference.compareTo(bigCACHE_EXPIRATION_TIME) == 1) {
			TimeOfLastParse parseAddressTime = ofyTime.get(
					TimeOfLastParse.class, "AddressList parsed 23");
			parseAddressTime.setTimeOfLastParse(currentTime);
			ofyTime.put(parseAddressTime);
		}

			results = getFromOfy();
		
		//parseAddress();
		return results;
	}

	

	private String[] getFromOfy() throws IOException {
		
		
		AddressList tempAddress = null;
		try {
			tempAddress= ofyAdd.get(AddressList.class, "456345");
		} catch (NotFoundException e) {
			AddressParser ap = new AddressParser();
			tempAddress = ap.parseAddress();
		}
		
		
		
		
		
		String[] Addresses = new String[tempAddress.size()];

		// CHANGE
		for (int i = 0; i < tempAddress.size(); i++) {
			Address ad = tempAddress.get(i);
			
			String addressStr = ad.getName() + " | " + ad.getFullAddress() + " / "
					+ ad.getTlatlng();
			Addresses[i] = addressStr;
		}

		System.out.println("address size: " + Addresses.length);
		
		return Addresses;

	}

}
