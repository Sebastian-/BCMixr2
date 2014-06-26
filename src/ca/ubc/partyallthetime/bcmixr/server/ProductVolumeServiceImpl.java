package ca.ubc.partyallthetime.bcmixr.server;


import ca.ubc.partyallthetime.bcmixr.client.ProductVolumeService;
import ca.ubc.partyallthetime.bcmixr.shared.Alcohol;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;



/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProductVolumeServiceImpl extends RemoteServiceServlet implements ProductVolumeService {
	
	public String getVolume(String SKU) {

	
	// ObjectifyService.register(Alcohol.class);   // not sure if i actually need this line. looks like i don't!
    Objectify ofy = ObjectifyService.begin();
    Alcohol tempAlcohol = ofy.query(Alcohol.class).filter("skuNumber", SKU).get();
    
    String litersPerContainer = tempAlcohol.getLitresPerContainer();
    String containersPerSellUnit = tempAlcohol.getContainersPerSellUnit();
    String productPrice = tempAlcohol.getPrice();
    
    double liters_per_container = Double.parseDouble(litersPerContainer);
    double containers = Double.parseDouble(containersPerSellUnit);
    double price = Double.parseDouble(productPrice);
    
    double price_per_ml = price / ((liters_per_container * 1000) * containers);
    
    return Double.toString(price_per_ml);
    
	}
    
    
   
	
	
	
	/*  *** GOING TO TRY TO USE OBJECTIFY. HOPEFULLY DON'T NEED THIS ANY MORE.
	
	public static ArrayList<Alcohol> priceList;

	public String getPrice(String SKU) {
		
		String price = "Price not found.";
		
		for ( Alcohol alcohol : priceList ){
			if (alcohol.getSkuNumber().equals(SKU)){
				price = alcohol.getPrice();
			}
		}
		
		return price;
				
	}
	
	
	public static void setList(ArrayList<Alcohol> list) {
		
		priceList = list;
	}
	
	*/
}
