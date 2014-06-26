package ca.ubc.partyallthetime.bcmixr;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ca.ubc.partyallthetime.bcmixr.server.AlcoholParser;
import ca.ubc.partyallthetime.bcmixr.shared.Alcohol;

//does not test objectify
public class AlcoholParserTest {
	ArrayList<Alcohol> alcohols;

	@Before
	public void initializeTest() {
		AlcoholParser ap = new AlcoholParser();
		try {
			ap.parse(true);
			alcohols = ap.getAlcohols();
		} catch (IllegalArgumentException e) {
			fail("IllegalArgumentException");
		} catch (IOException e) {
			fail("IOException");
		}
	}

	@Test
	public void NoNullTest() {
		for (int i = 0; i < alcohols.size(); i++) {
			Alcohol testing = alcohols.get(i);
			assertNotNull(testing);
			assertNotNull(testing.getSkuNumber());
			assertNotNull(testing.getName());
			assertNotNull(testing.getClassName());
			assertNotNull(testing.getPrice());
		}
	}

	@Test
	public void SKUtoNameTest() {
		for (int i = 0; i < alcohols.size(); i++) {
			Alcohol testing = alcohols.get(i);
			if (testing.getSkuNumber().equals("914564")) {
				assertEquals("MILLER GENUINE DRAFT CAN",testing.getName());
			}
			if (testing.getSkuNumber().equals("560474")) {
				assertEquals("ARDBEG - 10 YEAR OLD",testing.getName());
			}
			if (testing.getSkuNumber().equals("204529")) {
				assertEquals("TAWSE - SKETCHES OF NIAGARA CHARDONNAY 09",testing.getName());
			}
			if (testing.getSkuNumber().equals("365361")) {
				assertEquals("GROWERS - CRANBERRY",testing.getName());
			}
			if (testing.getSkuNumber().equals("344218")) {
				assertEquals("CHATEAU PICHON COMTESSE DE LALANDE 2005",testing.getName());
			}

		}
	}
}
