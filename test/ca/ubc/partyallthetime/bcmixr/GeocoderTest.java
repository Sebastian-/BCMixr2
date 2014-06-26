package ca.ubc.partyallthetime.bcmixr;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ca.ubc.partyallthetime.bcmixr.server.Geocoder;

@SuppressWarnings("unused")
public class GeocoderTest {
	
//	@Test
//	public void UBCCoordinatesTest() {
//		Geocoder geotester = new Geocoder("UBC");
//		try {
//			String lnglat = geotester.Geocode();
//			assertEquals("-123.2446700,49.2579582",lnglat);
//		} catch (IOException e) {
//			fail("ioException Thrown");
//		} catch (SAXException e) {
//			fail("SAXException Thrown");
//		} catch (ParserConfigurationException e) {
//			fail("ParserConfigurationException Thrown");
//		}
//		
//		double lattitude = geotester.getLattitude();
//		double ubcLattitude = 49.257957;
//		double canBeOffBy = 0.00010;
//		assertEquals(ubcLattitude, lattitude, canBeOffBy);
//		
//		double longitude = geotester.getLongitude();
//		double ubcLongitude = -123.24467;
//		assertEquals(ubcLongitude, longitude,  canBeOffBy);
//		
//	}
//	@Test
//	public void InvalidAddressTest(){
//		Geocoder geotester = new Geocoder("No Where Land, No Where City, I9N 9L2");
//		try {
//			String lnglat = geotester.Geocode();
//			assertEquals("invalid address", lnglat);
//		} catch (IOException e) {
//			fail("ioException Thrown");
//		} catch (SAXException e) {
//			fail("SAXException Thrown");
//		} catch (ParserConfigurationException e) {
//			fail("ParserConfigurationException Thrown");
//		}
//		
//		double lattitude = geotester.getLattitude();
//		double canBeOffBy = 0.00000001;
//		assertEquals(0, lattitude, canBeOffBy);
//		
//		double longitude = geotester.getLongitude();
//		assertEquals(0, longitude, canBeOffBy);
//		
//	}

}
