package ca.ubc.partyallthetime.bcmixr.client;

import java.io.IOException;
import java.util.ArrayList;
import ca.ubc.partyallthetime.bcmixr.shared.Ingredient;
import ca.ubc.partyallthetime.bcmixr.shared.Recipe;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.FocusEvent;


public class RecipeCreationPopUp extends PopupPanel {
	//Formatting Objects
	
	private VerticalPanel mainPanel;
	private CaptionPanel RecipeOverviewCaptionPanel;
	private VerticalPanel recipeOverviewPanel;
	private Label RecipeTitleLabel;
	private FlexTable alcoholIngredientsTable;
	private HorizontalPanel directionsOverviewPanel;
	private Label directionsLabel;
	private FlexTable directionsTable;
	private Button submitRecipeButton;
	private VerticalPanel ingredientPanel;
	private VerticalPanel productBrowsingPanel;
	private HorizontalPanel horizontalPanel_1;
	private TextBox RecipeTitleInputBox;
	private Button RecipeTitleAddButton;
	private HorizontalPanel LiquorInputPanel;
	private Label LiquorSearchLabel;
	private SuggestBox LiquorSuggestionBox;
	private Button LiquorAddButton;
	private HorizontalPanel miscIngredientPanel;
	private Label miscInputLabel;
	private TextBox miscIngredientTextBox;
	private Button addMiscIngredientButton;
	private VerticalPanel directionsPanel;
	private Label directionsInputLabel;
	private TextArea directionsTextBox;
	private Button directionsSubmissionButton;
	private Label RecipeTitleInputLabel;
	
	private final DisplayNameServiceAsync displayNameService = (DisplayNameServiceAsync) GWT.create(DisplayNameService.class); 
	private final RecipeSaveServiceAsync recipeSaveService = (RecipeSaveServiceAsync) GWT.create(RecipeSaveService.class); 

	// Recipe Object
	private Recipe recipe;
	
	//Boolean Flag to signal that the Ingredients table is empty
	private boolean isEmpty = true;
	
	//Boolean Flags to signal that a title and method have been added to the recipe
	private boolean hasTitle = false;
	private boolean hasMethod = false; 
	
	// SuggestionBox Oracle
	private MultiWordSuggestOracle oracle;
	
	//Recipe Overview FlexTable Arrays
	private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
	private ArrayList<AlcoholIngredient> liquorArray = new ArrayList<AlcoholIngredient>();
	private int lastAlcoholEntry = 0;

	
	public RecipeCreationPopUp() {
	      super(true);
	      
	    
	  	// Configuring Suggestion Box Oracle
	      oracle = new MultiWordSuggestOracle();

			try {
				 displayNameService.getDisplayNames(new AsyncCallback<ArrayList<String>>() {
					public void onFailure(Throwable caught) {
						Window.alert("displayNameService failed. Error: "
								+ caught.getMessage());
					}

					public void onSuccess(ArrayList<String> result) {
						
						ArrayList<String> displayNames = result;
						
						System.out.println("in RecipeCreationPopUp, before adding to oracle");

						oracle.addAll(displayNames);
					     
							System.out.println("in RecipeCreationPopUp, after adding to oracle");

						
					}
				});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
	    		 
	 
	      
	      this.recipe = new Recipe();
	      
			mainPanel = new VerticalPanel();
			mainPanel.setSize("580px", "457px");
			
			RecipeOverviewCaptionPanel = new CaptionPanel("Recipe Preview");
			RecipeOverviewCaptionPanel.setStyleName("yellowBackground");
			mainPanel.add(RecipeOverviewCaptionPanel);
			mainPanel.setCellVerticalAlignment(RecipeOverviewCaptionPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			mainPanel.setCellHorizontalAlignment(RecipeOverviewCaptionPanel, HasHorizontalAlignment.ALIGN_CENTER);
			RecipeOverviewCaptionPanel.setWidth("410px");
			
			recipeOverviewPanel = new VerticalPanel();
			recipeOverviewPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			RecipeOverviewCaptionPanel.setContentWidget(recipeOverviewPanel);
			recipeOverviewPanel.setSize("365px", "3cm");
			
			RecipeTitleLabel = new Label("Enter a Recipe Name");
			RecipeTitleLabel.setStyleName("italicText");
			recipeOverviewPanel.add(RecipeTitleLabel);
			recipeOverviewPanel.setCellVerticalAlignment(RecipeTitleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			recipeOverviewPanel.setCellHorizontalAlignment(RecipeTitleLabel, HasHorizontalAlignment.ALIGN_CENTER);
			
			alcoholIngredientsTable = new FlexTable();
			alcoholIngredientsTable.setStyleName("italicText");
			alcoholIngredientsTable.setText(0, 0, "Ingredients");
			alcoholIngredientsTable.setText(0, 1, "Amount");
			alcoholIngredientsTable.setText(0, 2, "Remove");
			alcoholIngredientsTable.setText(1, 0, "-");
			alcoholIngredientsTable.setText(1, 1, "-");
			alcoholIngredientsTable.setText(1, 2, "-");
			recipeOverviewPanel.add(alcoholIngredientsTable);
			alcoholIngredientsTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			alcoholIngredientsTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
			alcoholIngredientsTable.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

			
			directionsOverviewPanel = new HorizontalPanel();
			recipeOverviewPanel.add(directionsOverviewPanel);
			
			directionsLabel = new Label("Directions:    ");
			directionsLabel.setStyleName("italicText");
			directionsOverviewPanel.add(directionsLabel);
			
			directionsTable = new FlexTable();
			directionsOverviewPanel.add(directionsTable);
			
			submitRecipeButton = new Button("New button");
			submitRecipeButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if(checkRecipe()) {
						saveRecipe();
					}
				
				}
			});
			submitRecipeButton.setText("Submit Recipe");
			recipeOverviewPanel.add(submitRecipeButton);
			recipeOverviewPanel.setCellHorizontalAlignment(submitRecipeButton, HasHorizontalAlignment.ALIGN_CENTER);
			recipeOverviewPanel.setCellVerticalAlignment(submitRecipeButton, HasVerticalAlignment.ALIGN_MIDDLE);
			
			ingredientPanel = new VerticalPanel();
			ingredientPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			ingredientPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			mainPanel.add(ingredientPanel);
			mainPanel.setCellVerticalAlignment(ingredientPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			mainPanel.setCellHorizontalAlignment(ingredientPanel, HasHorizontalAlignment.ALIGN_CENTER);
			ingredientPanel.setWidth("410px");
			
			productBrowsingPanel = new VerticalPanel();
			productBrowsingPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			productBrowsingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			ingredientPanel.add(productBrowsingPanel);
			ingredientPanel.setCellHorizontalAlignment(productBrowsingPanel, HasHorizontalAlignment.ALIGN_CENTER);
			
			horizontalPanel_1 = new HorizontalPanel();
			horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			productBrowsingPanel.add(horizontalPanel_1);
			horizontalPanel_1.setSize("349px", "68");
			
			RecipeTitleInputLabel = new Label("Recipe Title");
			horizontalPanel_1.add(RecipeTitleInputLabel);
			RecipeTitleInputLabel.setSize("122px", "18px");
			
			RecipeTitleInputBox = new TextBox();
			RecipeTitleInputBox.setFocus(true);
			RecipeTitleInputBox.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent event) {
					if (event.getCharCode() == KeyCodes.KEY_ENTER){
						setTitle();
					}
				}
			});
			horizontalPanel_1.add(RecipeTitleInputBox);
			
			RecipeTitleAddButton = new Button("Add");
			RecipeTitleAddButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					setTitle();
				}
			});
			horizontalPanel_1.add(RecipeTitleAddButton);
			
			LiquorInputPanel = new HorizontalPanel();
			LiquorInputPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			LiquorInputPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			productBrowsingPanel.add(LiquorInputPanel);
			LiquorInputPanel.setWidth("349px");
			
			LiquorSearchLabel = new Label("Search for a Liquor: ");
			LiquorInputPanel.add(LiquorSearchLabel);
			LiquorSearchLabel.setSize("122px", "18px");
			
			LiquorSuggestionBox = new SuggestBox(oracle);
			LiquorSuggestionBox.setLimit(6);
			LiquorSuggestionBox.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent event) {
					if (event.getCharCode() == KeyCodes.KEY_ENTER){
						addLiquor();
					}
				}
			});
			LiquorInputPanel.add(LiquorSuggestionBox);
			
			LiquorAddButton = new Button("Add");
			LiquorAddButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					addLiquor();
				}
			});
			LiquorInputPanel.add(LiquorAddButton);
			
			miscIngredientPanel = new HorizontalPanel();
			miscIngredientPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			miscIngredientPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			ingredientPanel.add(miscIngredientPanel);
			ingredientPanel.setCellHorizontalAlignment(miscIngredientPanel, HasHorizontalAlignment.ALIGN_CENTER);
			miscIngredientPanel.setWidth("349px");
			
			miscInputLabel = new Label("Misc Ingredient: ");
			miscIngredientPanel.add(miscInputLabel);
			miscInputLabel.setSize("122px", "18px");
			miscIngredientPanel.setCellVerticalAlignment(miscInputLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			
			miscIngredientTextBox = new TextBox();
			miscIngredientTextBox.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent event) {
					if (event.getCharCode() == KeyCodes.KEY_ENTER){
						addIngredient();
					}
				}
			});
			miscIngredientPanel.add(miscIngredientTextBox);
			
			addMiscIngredientButton = new Button("Add Item");
			addMiscIngredientButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					addIngredient();
				}
			});
			addMiscIngredientButton.setText("Add");
			miscIngredientPanel.add(addMiscIngredientButton);
			
			directionsPanel = new VerticalPanel();
			mainPanel.add(directionsPanel);
			mainPanel.setCellVerticalAlignment(directionsPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			mainPanel.setCellHorizontalAlignment(directionsPanel, HasHorizontalAlignment.ALIGN_CENTER);
			directionsPanel.setSize("410px", "134px");
			
			directionsInputLabel = new Label("Directions:");
			directionsPanel.add(directionsInputLabel);
			directionsPanel.setCellVerticalAlignment(directionsInputLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			directionsInputLabel.setSize("377px", "20px");
			
			directionsTextBox = new TextArea();
			directionsPanel.add(directionsTextBox);
			directionsPanel.setCellHorizontalAlignment(directionsTextBox, HasHorizontalAlignment.ALIGN_CENTER);
			directionsTextBox.setSize("377px", "71px");
			
			directionsSubmissionButton = new Button("Submit");
			directionsSubmissionButton.setText("Add Directions");
			directionsPanel.add(directionsSubmissionButton);
			directionsSubmissionButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					setDirections();
				}
			});
			directionsPanel.setCellHorizontalAlignment(directionsSubmissionButton, HasHorizontalAlignment.ALIGN_CENTER);
			
			setWidget(mainPanel);
	}
	
	// Save the recipe via Objectify
	private void saveRecipe() {
		
		//Generate recipe object with input data
		
		//Get Alcohol Volumes
		ArrayList<String> alcVolumes = new ArrayList<String>();
		
		if(lastAlcoholEntry != 0) {
			for(int i = 1; i <= lastAlcoholEntry; i++ ) {
				Widget tempWidget = alcoholIngredientsTable.getWidget(i, 1);
				TextBox tempTextBox = (TextBox) tempWidget;
				String liquorQty = tempTextBox.getText();
				
				alcVolumes.add(liquorQty);
			}
		}
		
		//Generate AlcoholIngredient Objects and store in recipe
		for(int i = 0; i < liquorArray.size(); i++) {
			liquorArray.get(i).setVolume(alcVolumes.get(i));
		}
		recipe.setAlcohol(liquorArray);
		
		// Save miscIngredients to recipe
		recipe.setIngredients(ingredientArray);
		
//		Window.alert(recipe.getTitle() + "   " + recipe.getDirections() + "   " + recipe.getAlcohol().get(0).getDisplayName()
//				+ ", " + recipe.getAlcohol().get(0).getSKU() + ", " + recipe.getAlcohol().get(0).getVolume());
		
		//Save recipe object
		
		
		
		try {
			 recipeSaveService.saveRecipe(recipe, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					Window.alert("recipeSaveService failed. Error: "
							+ caught.getMessage());
				}

				public void onSuccess(String result) {
					
					Window.alert("recipe saving was successful with this message: " + result);
					
				}
			});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

	// Check that all the required fields of the recipe are properly filled in
	private boolean checkRecipe() {
		//Check that the recipe has ingredients (has only 1 row)
		if((alcoholIngredientsTable.getRowCount() <= 1) & (isEmpty)) {
			Window.alert("Add Ingredients to your recipe.");
			return false;
		}
		//Check that the recipe has a title
		if(!hasTitle) {
			Window.alert("Add a Title to your Recipe.");
			return false;
		}
		//Check that the recipe has a method
		if(!hasMethod) {
			Window.alert("Add some Directions for your Recipe.");
			return false;
		}
		//Check that the recipe has quantities filled in for all the alcoholic ingredients
		if(lastAlcoholEntry != 0) {
			for(int i = 1; i <= lastAlcoholEntry; i++ ) {		
				Widget tempWidget = alcoholIngredientsTable.getWidget(i, 1);
				TextBox tempTextBox = (TextBox) tempWidget;
				String liquorQty = tempTextBox.getText();
				
				if(!liquorQty.matches("^[0-9]{1,6}$")) {
					Window.alert("Quantity at row " + i + " is invalid. Please enter a number.");
					return false;
				}
			}
		}
		return true;
	}

	// Adds an alcoholic ingredient to the recipe
	private void addLiquor() {
		// Parsing suggestion box input
		final String liquorSearchName = LiquorSuggestionBox.getText().trim();
		LiquorSuggestionBox.setFocus(false);
		String liquorName = getName(liquorSearchName);
		String liquorSKU = getSKU(liquorSearchName);
		final AlcoholIngredient alcIng = new AlcoholIngredient(liquorName, liquorSKU);
		liquorArray.add(alcIng);
		
		// If table is empty remove row of dashes
		if(isEmpty) {
			alcoholIngredientsTable.removeRow(1);
		}
		
		// Add liquor name to the table
		alcoholIngredientsTable.insertRow(1);
		alcoholIngredientsTable.setText(1, 0, liquorName);
		isEmpty = false;
		LiquorSuggestionBox.setText("");
		lastAlcoholEntry++;
		
		// Add quantity input box
		final TextBox liquorQty = new TextBox();
		liquorQty.setWidth("35px");
		liquorQty.setHeight("8px");
		liquorQty.setText("mL");
		liquorQty.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				liquorQty.setText("");
			}
		});
		alcoholIngredientsTable.setWidget(1, 1, liquorQty);
		
		// Add Removal Button
		Button removeLiquor = new Button("x");
		removeLiquor.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {					
		        int removedIndex = liquorArray.indexOf(alcIng);
		        liquorArray.remove(removedIndex);
		        alcoholIngredientsTable.removeRow(lastAlcoholEntry - removedIndex);
		        lastAlcoholEntry--;
		    }
		    });		
		
		alcoholIngredientsTable.setWidget(1, 2, removeLiquor);
		alcoholIngredientsTable.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);
		
	}




	// Adds a non-alcoholic ingredient to the recipe
	protected void addIngredient() {
		final String ingredientName = miscIngredientTextBox.getText().trim();
		miscIngredientTextBox.setFocus(false);
		final Ingredient ing = new Ingredient(ingredientName);
		ingredientArray.add(ing);
		
		// If table is empty remove row of dashes
		if(isEmpty) {
			alcoholIngredientsTable.removeRow(1);
		}
		
		// Add ingredient to the table
		int row = alcoholIngredientsTable.getRowCount();
		alcoholIngredientsTable.setText(row, 0, ingredientName);
		isEmpty = false;
		miscIngredientTextBox.setText("");
		
		// Add quantity input box
		TextBox ingredientQty = new TextBox();
		ingredientQty.setWidth("35px");
		ingredientQty.setHeight("8px");
		alcoholIngredientsTable.setWidget(row, 1, ingredientQty);
		
		// Add Removal Button
		Button removeIngredient = new Button("x");
		removeIngredient.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {					
		        int removedIndex = ingredientArray.indexOf(ing);
		        ingredientArray.remove(removedIndex);
		        alcoholIngredientsTable.removeRow(lastAlcoholEntry + removedIndex + 1);
		    }
		    });
	
		alcoholIngredientsTable.setWidget(row, 2, removeIngredient);
		alcoholIngredientsTable.getCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_CENTER);

	}

	// Sets the directions for the recipe
	private void setDirections() {
		final String directions = directionsTextBox.getText().trim();
		directionsTextBox.setFocus(false);
		recipe.setDirections(directions);
		directionsTable.setText(0, 0, recipe.getDirections());
		hasMethod = true;
	}

	// Sets the title of the recipe being worked on
	private void setTitle() {
		final String title = RecipeTitleInputBox.getText().trim();
		RecipeTitleInputBox.setFocus(false);
		
		if (!title.matches("^[0-9A-Za-z\\.]{1,50}$")) {
		      Window.alert("'" + title + "' is an invalid title");
		      RecipeTitleInputBox.selectAll();
		      return;
		    }
		
		RecipeTitleInputBox.setText("");
		
		recipe.setTitle(title);
		RecipeTitleLabel.setText(recipe.getTitle());
		hasTitle = true;
		
	}
	
	private String getName(String displayName) {
		int endOfName = displayName.indexOf(" -- ");
		return displayName.substring(0, endOfName);
	}

	private String getSKU(String displayName) {
		int startOfSKU = displayName.indexOf(" - (") + 4;
		int endOfSKU = displayName.indexOf(")");
		return displayName.substring(startOfSKU, endOfSKU);
	}
	
}
