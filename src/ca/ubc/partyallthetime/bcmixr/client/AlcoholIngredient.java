package ca.ubc.partyallthetime.bcmixr.client;

public class AlcoholIngredient {

	private String displayName;
	private String SKU;
	private String volume;
	
	public AlcoholIngredient(String displayName, String SKU){
		this.displayName = displayName;
		this.SKU = SKU;
	}
	
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getSKU() {
		return SKU;
	}
	
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	
	public String getVolume() {
		return volume;
	}
	
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
}
