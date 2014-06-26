package ca.ubc.partyallthetime.bcmixr.shared;

public class Ingredient {
	
	
	private String ingredientName;
	private String amount;
	private final String NOT_SET = "No Amount Specified";
	
	public Ingredient(String name) {
		this.ingredientName = name;
		this.amount = NOT_SET;
	}
	
	public String getName(){
		return ingredientName;
	}
	
	public void setName(String name) {
		this.ingredientName = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
