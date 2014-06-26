package ca.ubc.partyallthetime.bcmixr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import ca.ubc.partyallthetime.bcmixr.shared.Alcohol;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/*
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.net.URL;
 import java.util.ArrayList;


 import com.googlecode.objectify.Objectify;
 import com.googlecode.objectify.ObjectifyService;
 */

public class AlcoholParser {

	
	
	ArrayList<Alcohol> alcoholList;;

	public AlcoholParser(){
		alcoholList = new ArrayList<Alcohol>();
	}
	
	public String parse(boolean isTest) throws IllegalArgumentException,
			IOException {
		Objectify ofy = ObjectifyService.begin();

		URL liquor_data = new URL(
				"http://pub.data.gov.bc.ca/datasets/176284/BC_Liquor_Store_Product_Price_List.csv");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				liquor_data.openStream()));
		CSVReader reader = new CSVReader(in);
		String[] nextLine;
		reader.readNext();
		if (isTest) {
			while ((nextLine = reader.readNext()) != null) {
				String className = nextLine[1];
				String subClassName = nextLine[2];
				String minorClassName = nextLine[3];
	//			String origin = nextLine[4];
				String skuNumber = nextLine[5];
				String name = nextLine[6];
				String litresPerContainer = nextLine[8];
				String containersPerSellUnit = nextLine[9];
	//			String alcoholPercent = nextLine[10];
				String price = nextLine[11];
				Alcohol alTemp = new Alcohol();
				alTemp.setClassName(className);
				alTemp.setSubclassName(subClassName);
				alTemp.setMinorclassName(minorClassName);
		//		alTemp.setOrigin(origin);
				alTemp.setSkuNumber(skuNumber);
				alTemp.setName(name);
				alTemp.setLitresPerContainer(litresPerContainer);
				alTemp.setContainersPerSellUnit(containersPerSellUnit);
		//		alTemp.setAlcoholPercent(alcoholPercent);
				alTemp.setPrice(price);
				alcoholList.add(alTemp);
			}
		} else {
			while ((nextLine = reader.readNext()) != null) {
				String className = nextLine[1];
				String subClassName = nextLine[2];
				String minorClassName = nextLine[3];
	//			String origin = nextLine[4];
				String skuNumber = nextLine[5];
				String name = nextLine[6];
				String litresPerContainer = nextLine[8];
				String containersPerSellUnit = nextLine[9];
		//		String alcoholPercent = nextLine[10];
				String price = nextLine[11];
				Alcohol alTemp = new Alcohol();
				alTemp.setClassName(className);
				alTemp.setSubclassName(subClassName);
				alTemp.setMinorclassName(minorClassName);
		//		alTemp.setOrigin(origin);
				alTemp.setSkuNumber(skuNumber);
				alTemp.setName(name);
				alTemp.setLitresPerContainer(litresPerContainer);
				alTemp.setContainersPerSellUnit(containersPerSellUnit);
		//		alTemp.setAlcoholPercent(alcoholPercent);
				alTemp.setPrice(price);
				ofy.put(alTemp);
			}
		}
		return "Data successfully parsed.";
	}
	
	public ArrayList<Alcohol> getAlcohols(){
		return this.alcoholList;
		
	}

}
