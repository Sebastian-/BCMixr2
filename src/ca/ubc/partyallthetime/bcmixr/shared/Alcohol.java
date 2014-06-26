package ca.ubc.partyallthetime.bcmixr.shared;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

public class Alcohol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
    Long id; // Can be Long, long, or String
	@Unindexed
	private String className;
	@Unindexed
	private String subclassName;
	@Unindexed
	private String minorclassName;
	@Unindexed
	private String origin;
	
	private String skuNumber;
	private String name;
	private String litresPerContainer;
	
	@Unindexed
	private String containersPerSellUnit;
	@Unindexed
	private String alcoholPercent;
	@Unindexed
	private String price;
	
	/*
	public Alcohol (String className, String subclassName, String minorclassName,
					String origin, String skuNumber, String name, String litresPerContainer, String containersPerSellUnit,
					String alcoholPercent, String price){
		this.className = className;
		this.subclassName = subclassName;
		this.minorclassName = minorclassName;
		this.origin = origin;
		this.skuNumber = skuNumber;
		this.name = name;
		this.litresPerContainer = litresPerContainer;
		this.containersPerSellUnit = containersPerSellUnit;
		this.alcoholPercent = alcoholPercent;
		this.price = price;
	}
	*/
	
	public void setupAlcohol (String className, String subclassName, String minorclassName, String skuNumber, String name,
			String litresPerContainer, String containersPerSellUnit, String price) {
		this.className = className;
		this.subclassName = subclassName;
		this.minorclassName = minorclassName;
		this.skuNumber = skuNumber;
		this.name = name;
		this.litresPerContainer = litresPerContainer;
		this.containersPerSellUnit = containersPerSellUnit;
		this.price = price;
	}
	
	public String getDisplayName() {
		int volume = Integer.parseInt(this.litresPerContainer);
		String displayName = this.litresPerContainer;
		
		if (volume < 1) {
			volume = volume * 1000;
			displayName = this.name + " - " + Integer.toString(volume) + " mL";
		}
		else
			displayName = this.name + " - " + Integer.toString(volume) + " L";
		
		return displayName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubclassName() {
		return subclassName;
	}

	public void setSubclassName(String subclassName) {
		this.subclassName = subclassName;
	}

	public String getMinorclassName() {
		return minorclassName;
	}

	public void setMinorclassName(String minorclassName) {
		this.minorclassName = minorclassName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLitresPerContainer() {
		return litresPerContainer;
	}

	public void setLitresPerContainer(String litresPerContainer) {
		this.litresPerContainer = litresPerContainer;
	}

	public String getContainersPerSellUnit() {
		return containersPerSellUnit;
	}

	public void setContainersPerSellUnit(String containersPerSellUnit) {
		this.containersPerSellUnit = containersPerSellUnit;
	}

	public String getAlcoholPercent() {
		return alcoholPercent;
	}

	public void setAlcoholPercent(String alcoholPercent) {
		this.alcoholPercent = alcoholPercent;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
