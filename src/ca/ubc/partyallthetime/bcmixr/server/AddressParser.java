package ca.ubc.partyallthetime.bcmixr.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;



public class AddressParser {
	
	AddressList aList = new AddressList( );

    /**
     * @param args
     * @throws IOException
     */
	

	public AddressParser() {
	}
	
	public AddressList parseAddress() throws IOException {
		URL liquor_data = new URL(
				"http://pub.data.gov.bc.ca/datasets/176004/BC_Liquor_Store_Locations.csv");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				liquor_data.openStream()));
		CSVReader reader = new CSVReader(in);
		String[] nextLine;
		reader.readNext();
		aList = new AddressList();
		while ((nextLine = reader.readNext()) != null) {
			String name = nextLine[1];
			String address = nextLine[2];
			
			String city = nextLine[3];
			String postalCode = nextLine[4];
			Address a = new Address();
			a.setAll(name, address, city, postalCode);
			aList.add(a);
		}
		AddressList tempList = aList;
		Geocoder myGeocoder = new Geocoder(tempList);
		try {
			aList = myGeocoder.Geocode();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		aList.setId("456345");
		return aList;
	}


	
}
