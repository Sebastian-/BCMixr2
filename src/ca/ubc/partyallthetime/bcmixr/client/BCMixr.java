// Version 4

package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.widgetideas.client.GlassPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.Cookies;
//import com.google.gwt.user.client.ui.FlowPanel;

public class BCMixr implements EntryPoint {

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final CheckIfRecentlyParsedAsync checkIfRecentlyParsed = GWT.create(CheckIfRecentlyParsed.class);
	private final PriceServiceAsync priceService = (PriceServiceAsync) GWT.create(PriceService.class); 
	private final ParseServiceAsync parseService = (ParseServiceAsync) GWT.create(ParseService.class); 
	private final AddressServiceAsync addressService = (AddressServiceAsync) GWT.create(AddressService.class);
	private final ProductVolumeServiceAsync volumeService = (ProductVolumeServiceAsync) GWT.create(ProductVolumeService.class); 
	private final RecipeRatingServiceAsync recipeRatingService = (RecipeRatingServiceAsync) GWT.create(RecipeRatingService.class);
	private final LoginServiceAsync loginService = (LoginServiceAsync) GWT.create(LoginService.class); 
	private final DisplayNameServiceAsync displayNameService = (DisplayNameServiceAsync) GWT.create(DisplayNameService.class); 
	private final RecipeSaveServiceAsync recipeSaveService = (RecipeSaveServiceAsync) GWT.create(RecipeSaveService.class); 

	
	private FlexTable ingredientsTable;
	private FlexTable currentLocationFlexTable;
	private Button locationButton;
	private Button createNewRecipeButton;
	private ListBox recipesListBox;
	private double totalPricePerDrink;
	
	private String currentLocation;
	
	private AbsolutePanel locationDropDown;
	
	private HorizontalPanel loginOrLogoutPanel;
	private RootPanel rootPanel;

	private HorizontalPanel horizontalPanel;

	// for login box:
	private LoginInfo loginInfo = null;
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private ArrayList<String> locationList;
	
	private MapPopUp mapPopup;
	private MapWidget map;
	
	// Counters for rows for formatting the ingredients table
	
	private int priceDispRow;

	/**
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		setAllServiceEntryPoints();
		Window.alert("You must be 19 years of age or older to use this site");
		/*
		 * This service parses the alcohol data from the DataBC website. Each
		 * time a user accesses the site, this service will check to see if the
		 * data from DataBC has been recently parsed (ie, whether it has been
		 * parsed within the past 12 hours). If the data is out-of-date, it
		 * re-parses it. All parsed data is persisted using Objectify.
		 */
		parseServiceCall();

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		rootPanel = RootPanel.get("nameFieldContainer");
		// rootPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);

		makeLocationDropDown();
		setBackground();

		recipesListBox = new ListBox();
		recipesListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				displayRecipe();
			}
		});

		// setting up the login/logout panel:
		loginOrLogoutPanel = new HorizontalPanel();
		loginOrLogoutPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		locationDropDown.add(loginOrLogoutPanel, 701, 10);
		loginOrLogoutPanel.setSize("139px", "28px");

		// setting up the rating box panel:
		makeRatingbox();

		// setting up the 'recipe directions' table:
		makeDirectionsTable();

		// add styles
		addStylesToDirectionTables();
		
		
		recipesListBox.setVisibleItemCount(5);
		locationDropDown.add(recipesListBox, 10, 142);
		recipesListBox.setSize("241px", "364px");

		// TODO: delete/modify this after, this is solely for sprint purposes
		recipesListBox.addItem("Singapore Sling");
		recipesListBox.addItem("Chinatown Sour");

		// setting up the ingredients flextable.
		makeIngredientsTable();

		// setting up the example location combobox:
		locationButton = new Button("Select A Location");
		
		locationDropDown.add(locationButton, 721, 80);
		
		// add styles
		addStylesToIngredientTable();

		locationList = new ArrayList<String>();

		makeMap();
		

		addressServiceCall();


		

		/*
		 * 'Create a new recipe' button
		 */

		makeCreateRecipeButton();

		/*
		 * Login/Logout panel
		 */

		// Check login status using login service.
		loginServiceCall(loginOrLogoutPanel);
		
		Image image_1 = new Image("images/bcmixrlogo.png");
		locationDropDown.add(image_1, 10, 10);
		image_1.setSize("220px", "124px");
		
		// Creates the current location flex table
		currentLocationFlexTable = new FlexTable();
		locationDropDown.add(currentLocationFlexTable, 541, 112);
		currentLocationFlexTable.setSize("299px", "24px");
		currentLocationFlexTable.setText(0,0, "Current Location: Make a selection above!");
		currentLocationFlexTable.setStyleName("currentLocation");

	}

	private void makeCreateRecipeButton() {
		createNewRecipeButton = new Button("Create a new recipe");
		createNewRecipeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CheckLoginServiceCall();
			}

			private void CheckLoginServiceCall() {
				LoginServiceAsync loginService = GWT.create(LoginService.class);
				loginService.login(GWT.getHostPageBaseURL(),
						new AsyncCallback<LoginInfo>() {
							public void onFailure(Throwable error) {
							}

							public void onSuccess(LoginInfo result) {
								loginInfo = result;
								if (loginInfo.isLoggedIn()) {

									new RecipeCreationPopUp().center();
									@SuppressWarnings("unused")
									GlassPanel glassPanel = new GlassPanel(true);
									rootPanel.add(new GlassPanel(true), 0, 0);

								} else {
									// if not logged in, display a message
									// asking the user to log in.
									new LoginPopupPanel().center();
									// grey out the background behind the pop-up
									// message using a gwt 'glass panel'.
									@SuppressWarnings("unused")
									GlassPanel glassPanel = new GlassPanel(true);
									rootPanel.add(new GlassPanel(true), 0, 0);
								}
							}
						});
			}
		});

		locationDropDown.add(createNewRecipeButton, 709, 44);
	}

	private void addStylesToIngredientTable() {
		ingredientsTable.getRowFormatter().addStyleName(0,
				"ingredientsListHeader");
		ingredientsTable.addStyleName("ingredientsList");
	}

	private void addStylesToDirectionTables() {
	}
	

	private void makeIngredientsTable() {
		ingredientsTable = new FlexTable();
		locationDropDown.add(ingredientsTable, 257, 142);
		ingredientsTable.setSize("583px", "24px");
		ingredientsTable.setText(0, 0, "Ingredient");
		ingredientsTable.setText(0, 1, "Amount");
		ingredientsTable.setText(0, 2, "Price per Bottle");
		ingredientsTable.setText(0, 3, "Price per Drink");
		ingredientsTable.setText(0, 4, "Availability");
	}

	private void makeDirectionsTable() {
	}

	private void makeRatingbox() {
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		locationDropDown.add(horizontalPanel, 257, 108);
		horizontalPanel.setSize("224px", "28px");
	}

	private void makeLocationDropDown() {
		locationDropDown = new AbsolutePanel();
		rootPanel.add(locationDropDown, 10, 10);
		locationDropDown.setSize("869px", "950px");
		locationDropDown.setStyleName("center");
	}

	private void setBackground() {
		Image cocktail_background_image = new Image("images/cocktail1.jpg");
		locationDropDown.add(cocktail_background_image, 508, 134);
		cocktail_background_image.setSize("332px", "500px");
		cocktail_background_image.addStyleName("opaque");
	}

	private void loginServiceCall(final HorizontalPanel loginOrLogoutPanel) {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) { // if the user is logged in, display option to sign out.
							signOutLink.setHref(loginInfo.getLogoutUrl());
							loginOrLogoutPanel.clear(); // get rid of whatever might be in the loginOrLogout panel
							loginOrLogoutPanel.add(signOutLink);

						} else { // if the user is not logged in, display option to sign in.
							signInLink.setHref(loginInfo.getLoginUrl());
							loginOrLogoutPanel.clear(); // get rid of whatever might be in the loginOrLogout panel
							loginOrLogoutPanel.add(signInLink);

						}
					}
				});
	}

	
	//TODO: make sure that this is works. 
	
		private void parseServiceCall() {
			
			try {
				checkIfRecentlyParsed.check(new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						Window.alert("checkIfRecentlyParsed failed. Error: "
								+ caught.getMessage());
					}

					public void onSuccess(String result) {
						Window.alert("checkIfRecentlyParsed ran successfully. Message from server: "
								+ result);
						if (result.equals("not recently parsed")){
							doParse();
						}
					}
				});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		private void doParse() {
			
			try {
				parseService.getParse(new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						Window.alert("the parseService call failed. error: "
								+ caught.getMessage());
					}

					public void onSuccess(String result) {
						Window.alert("Parsing completed with this message: "
								+ result);

					}
				});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	
	

	private void displayRecipe() {

		Button priceDisplay = new Button("Click Here To See Price of Serving:");
		priceDisplay.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				double price_for_this_serving_rounded = totalPricePerDrink * 100;
				price_for_this_serving_rounded = Math
						.round(price_for_this_serving_rounded);
				totalPricePerDrink = price_for_this_serving_rounded / 100;
				String totalPricePerDrinkString = Double
						.toString(totalPricePerDrink);
				ingredientsTable.setText(priceDispRow, 1,
						"$" + totalPricePerDrinkString);
			}
		});

		totalPricePerDrink = 0;

		// gets the name of the selected recipe.
		final String selectedRecipe = recipesListBox.getValue(recipesListBox
				.getSelectedIndex());

		// deletes all the other values in the Flextable if previously populated
		while (ingredientsTable.getRowCount() != 1) {
			ingredientsTable.removeRow(1);
		}

		// for demo purposes, add the recipe by hand when selecting a box
		if (selectedRecipe.equals("Chinatown Sour")) {
			
			// First, must add all things provided in the recipe class
			String directions = "Combine all ingredients in a shaker. Shake vigorously and double strain into a fancy cocktail glass.";
			addIngredient("198184", "Wild Turkey Bourbon", "30 ml");
			addIngredient("674119", "Fernet Branca Bitters", "22 ml");
			addIngredient("Lemon Juice", "30 ml");
			addIngredient("Astragalus Syrup", "22 ml");
			
			// Then we set specific parameters for additional things such as calculations

			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "________");
			priceDispRow = ingredientsTable.getRowCount() + 1;
			ingredientsTable.setWidget(priceDispRow, 0, priceDisplay);
			ingredientsTable.getFlexCellFormatter().setColSpan(ingredientsTable.getRowCount() - 1, 0, 3);
			
			// Sets Directions at the bottom of the ingredients table
			
			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "________");
			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "Directions:");
			ingredientsTable.setText(ingredientsTable.getRowCount() - 1, 1, directions);
			ingredientsTable.getFlexCellFormatter().setColSpan(ingredientsTable.getRowCount() - 1, 1, 5);
			
			// Displays the rating for this particular recipe
			
			displayRatingBox("Chinatown Sour");

		}

		if (selectedRecipe.equals("Singapore Sling")) {
			String directions = "Combine all ingredients over ice and stir. Finely strain into a sling or a hurricane glass filled with crushed ice. Garnish with orange zest, cherry, and a flower.";
			addIngredient("637058", "Bombay Sapphire Gin", "45 ml");
			addIngredient("126565", "Okanagan Spirits Cherry Liqueur", "30 ml");
			addIngredient("10322", "Cointreau", "15 ml");
			addIngredient("20024", "Benedictine", "15 ml");
			addIngredient("Pineapple Juice", "60 ml");
			addIngredient("Fresh Lime Juice", "30 ml");
			addIngredient("Orange Zest", "2 dashes");

			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "________");
			priceDispRow = ingredientsTable.getRowCount() + 1;
			ingredientsTable.setWidget(priceDispRow, 0, priceDisplay);
			ingredientsTable.getFlexCellFormatter().setColSpan(ingredientsTable.getRowCount() - 1, 0, 3);
			
			// Sets Directions at the bottom of the ingredients table
			
			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "________");
			ingredientsTable.setText(ingredientsTable.getRowCount(), 0, "Directions:");
			ingredientsTable.setText(ingredientsTable.getRowCount() - 1, 1, directions);
			ingredientsTable.getFlexCellFormatter().setColSpan(ingredientsTable.getRowCount() - 1, 1, 5);
			
			displayRatingBox("Singapore Sling");

		}

	}

	/*
	 * this function is wholly responsible for the recipe rating box. it handles
	 * its display and behavior.
	 */

	private void displayRatingBox(final String recipeTitle) {

		horizontalPanel.clear(); // clears any existing stuff from the rating box.

		// place the text boxes and thumbs up/ thumbs down images into the
		// recipe rating box.

		final Label lblRecipeRating = new Label ("Rate this recipe!");

		if ((Cookies.isCookieEnabled() == true) && (Cookies.getCookie(recipeTitle) != null)){
			lblRecipeRating.setText("Thanks for voting!");
		}
		
		lblRecipeRating.setStyleName("rateThisRecipe");		
		horizontalPanel.add(lblRecipeRating);

		Image thumbs_up_img = new Image("images/thumbsup.png");
		horizontalPanel.add(thumbs_up_img);
		horizontalPanel.setCellVerticalAlignment(thumbs_up_img,
				HasVerticalAlignment.ALIGN_MIDDLE);
		thumbs_up_img.setSize("20px", "20px");

		final Label thumbs_up_text = new Label("--");
		horizontalPanel.add(thumbs_up_text);
		thumbs_up_text.setStyleName("thumbsUpText");
		thumbs_up_text.setSize("30px", "");

		Image thumbs_down_img = new Image("images/thumbsdown.png");
		horizontalPanel.add(thumbs_down_img);
		horizontalPanel.setCellVerticalAlignment(thumbs_down_img,
				HasVerticalAlignment.ALIGN_MIDDLE);
		thumbs_down_img.setSize("20px", "20px");

		final Label thumbs_down_text = new Label("--");
		horizontalPanel.add(thumbs_down_text);
		thumbs_down_text.setSize("30px", "");
		thumbs_down_text.setStyleName("thumbsDownText");

		// load initial thumbs up/ thumbs down values. these values get put into
		// the text boxes created above.

		recipeRatingService.getRating(recipeTitle, "get up votes",
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						thumbs_up_text.setText("fail" + caught.getMessage());
					}

					public void onSuccess(String result) {

						String thumbsUp = result;
						thumbs_up_text.setText(thumbsUp);
					}
				});

		recipeRatingService.getRating(recipeTitle, "get down votes",
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						thumbs_up_text.setText("fail" + caught.getMessage());
					}

					public void onSuccess(String result) {

						String thumbsDown = result;
						thumbs_down_text.setText(thumbsDown);
					}
				});

		// click handler function for the thumbs up icon:

		thumbs_up_img.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Cookies.isCookieEnabled() == true) {
					lblRecipeRating.setText("Thanks for voting!"); } else {
				lblRecipeRating.setText("Enable cookies to vote");
					}
				
				if ((Cookies.isCookieEnabled() == true) && (Cookies.getCookie(recipeTitle) == null)){
					// remote procedure call to the server.
					recipeRatingService.getRating(recipeTitle, "up",
							new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							thumbs_up_text.setText("fail" + caught.getMessage());
							}

						public void onSuccess(String result) {
							String thumbsUp = result;
							thumbs_up_text.setText(thumbsUp);
							setRateCookie(recipeTitle);
							}
						});
					}
				}
			});

		// click handler function for the thumbs down icon.

		thumbs_down_img.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (Cookies.isCookieEnabled() == true){
				lblRecipeRating.setText("Thanks for voting!");} else {
				lblRecipeRating.setText("Enable cookies to vote");
				}
				
				if ((Cookies.isCookieEnabled() == true) && (Cookies.getCookie(recipeTitle) == null)){

				// remote procedure call to the server.
				recipeRatingService.getRating(recipeTitle, "down",
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								thumbs_up_text.setText("fail" + caught.getMessage());
							}

							public void onSuccess(String result) {
								String thumbsDown = result;
								thumbs_down_text.setText(thumbsDown);
								setRateCookie(recipeTitle);
								
							}
						});
				}

			}
		});

		// end of code for recipe rating box.

	}
	
	// creates a cookie after the user has rated a recipe.
	private void setRateCookie(String recipeTitle){
		Cookies.setCookie(recipeTitle, recipeTitle);
	}


	/*
	 * simple method to add an ingredient to the table for the recipe but this
	 * is NOT complete (aka this is only for demo purposes)
	 */

	private void addIngredient(String SKU, String name, String amount) {

		final String ingredientAmount = amount;

		final String location = currentLocation;

		final int row = ingredientsTable.getRowCount();
		ingredientsTable.setText(row, 0, name);
		ingredientsTable.setText(row, 1, amount);

		// get the price with a call to the server
		// using an edited version of greetingservice.
		priceService.getPrice(SKU, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				ingredientsTable.setText(row, 2,
						"totally failed :(" + caught.getMessage());
			}

			public void onSuccess(String result) {
				ingredientsTable.setText(row, 2, "$" + result);
			}
		});

		// get the total product volume with a call to the server
		// using an edited version of greetingservice.
		volumeService.getVolume(SKU, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				ingredientsTable.setText(
						row,
						3,
						"Failed to get product volume. Error: "
								+ caught.getMessage());
			}

			public void onSuccess(String result) {

				double price_per_ml = Double.parseDouble(result);

				String serving_amount_in_ml_string = ingredientAmount
						.replaceAll("\\D+", "");
				double serving_amount_in_ml = Double
						.parseDouble(serving_amount_in_ml_string);

				double price_for_this_serving = price_per_ml
						* serving_amount_in_ml;

				double price_for_this_serving_rounded = price_for_this_serving * 100;
				price_for_this_serving_rounded = Math
						.round(price_for_this_serving_rounded);
				price_for_this_serving = price_for_this_serving_rounded / 100;

				totalPricePerDrink += price_for_this_serving;
				String price_for_this_serving_text = Double
						.toString(price_for_this_serving);

				ingredientsTable.setText(row, 3, "$"
						+ price_for_this_serving_text);

			}
		});

		String locat = validateLocation(location);
		
		

		// get the availability with a call to the server
		// using an edited version of greetingservice.
		greetingService.greetServer(SKU, locat, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("the greetingService call failed.");
			}

			public void onSuccess(String result) {
				ingredientsTable.setText(row, 4, result);
			}
		});

	}

	private void addIngredient(String name, String amount) {
		int row = ingredientsTable.getRowCount();
		ingredientsTable.setText(row, 0, name);
		ingredientsTable.setText(row, 1, amount);
		ingredientsTable.setText(row, 2, "Not Alcohol");
		ingredientsTable.setText(row, 4, "Depends on Local Vendor");
	}
	
	

	

	

	private void addressServiceCall() {
		try {
			addressService.getAddress(new AsyncCallback<String[]>() {
				public void onFailure(Throwable caught) {
					Window.alert("the addressService call failed. error: "
							+ caught.getMessage());
				}

				public void onSuccess(String[] result) {
					Window.alert("note: address parsing successful");
					
					for (String tlnglat : result) {
						int index = tlnglat.indexOf("/") + 1;
						int nameIndex = tlnglat.indexOf("|") + 1;
						int lnglatSplit = tlnglat.indexOf("%");
						int end = tlnglat.length();
						//System.out.println("lnglat: " + tlnglat);
						if (tlnglat.contains("/") && tlnglat.contains("|") && tlnglat.contains(",") && 
								index > 0 && nameIndex > 0 && lnglatSplit > 0 && end > 0) {
							CharSequence charTLat = tlnglat.subSequence(
									index + 0, lnglatSplit - 1);
							CharSequence charTLng = tlnglat.subSequence(
									lnglatSplit + 1 , end);
							CharSequence charAddr = tlnglat.subSequence(nameIndex,
									index - 1);
							
							CharSequence charName = tlnglat.subSequence(0, nameIndex - 1);
							
							final String storeName = charName.toString();
							
							final String overlayAddress = charAddr.toString();
							String tlat = charTLat.toString();
							String tlng = charTLng.toString();
							String name = charName.toString();
							//System.out.println("add: " + charAddr.toString());
							//System.out.println("name: " + name);
							
							
							Float lat = Float.parseFloat(tlat);
							Float lng = Float.parseFloat(tlng);

							locationList.add(name);
							
							//System.out.println("lat: " + lng);
							//System.out.println("lng: " + lat);
							final LatLng locat = LatLng.newInstance(lng, lat);

							final Marker Overlay = new Marker(locat);

							//System.out.println("adding...");
							
							final String fname = name;
							
							
							
							Overlay.addMarkerClickHandler(new MarkerClickHandler() {
								public void onClick(MarkerClickEvent event) {
									
									ClickHandler setLocationButtonHandler = new ClickHandler(){
										public void onClick (ClickEvent event){
											
											for (int i = 0; i < locationList.size(); i++){
												if (storeName.equalsIgnoreCase(locationList.get(i))){
													currentLocation = locationList.get(i);
													currentLocationFlexTable.setText(0,0, "Current Location: " + currentLocation);
													mapPopup.hide();
												}
											}
										
										displayRecipe();
										}
									};
									
									Button setLocationButton = new Button("set location");	
									
									setLocationButton.addClickHandler(setLocationButtonHandler);

								    VerticalPanel vp = new VerticalPanel();
								    
								    FlexTable infoTable = new FlexTable();
								    
									infoTable.setText(0, 0, "Name: ");
									infoTable.setText(1, 0, fname);
									infoTable.setText(2, 0, "Address");
									infoTable.setText(3, 0, overlayAddress);
									vp.add(infoTable);

								    vp.add(setLocationButton);
									InfoWindowContent window = new InfoWindowContent(vp);
									
									
									map.getInfoWindow().open(
											Overlay, window);
													
								}


							});
							
							

							map.addOverlay(Overlay);
							
						}
					}
					locationButton.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							mapPopup = new MapPopUp(map);
							mapPopup.center();
							
							
						}

						
					});
					

				}
			});
		} catch (IllegalArgumentException e) {
		}
		
	}
	
	


	private String validateLocation(final String location) {
		int locationLength = location.length();
		CharSequence lastChar = location.substring(locationLength-1, locationLength);
		
		String locat;
		if (lastChar.equals(" ")){
		    locat = location.substring(0, locationLength-1);
		}else{
			locat = location;
		}
		return locat;
	}

	private void setAllServiceEntryPoints(){

		((ServiceDefTarget) checkIfRecentlyParsed).setServiceEntryPoint(GWT.getModuleBaseURL() + "checkIfRecentlyParsed");
		((ServiceDefTarget) priceService).setServiceEntryPoint(GWT.getModuleBaseURL() + "price");
		((ServiceDefTarget) parseService).setServiceEntryPoint(GWT.getModuleBaseURL() + "parse");
		((ServiceDefTarget) addressService).setServiceEntryPoint(GWT.getModuleBaseURL() + "address");
		((ServiceDefTarget) volumeService).setServiceEntryPoint(GWT.getModuleBaseURL() + "volume");
		((ServiceDefTarget) recipeRatingService).setServiceEntryPoint(GWT.getModuleBaseURL() + "rating");
		((ServiceDefTarget) loginService).setServiceEntryPoint(GWT.getModuleBaseURL() + "login");
		((ServiceDefTarget) displayNameService).setServiceEntryPoint(GWT.getModuleBaseURL() + "getDisplayNames");
		((ServiceDefTarget) recipeSaveService).setServiceEntryPoint(GWT.getModuleBaseURL() + "saveRecipe");

	}
	private void makeMap() {
		Maps.loadMapsApi("", "2", false, new Runnable() {
			

			public void run() {
				LatLng zoomzoom = LatLng.newInstance(49.230353, -122.966309);
				map = new MapWidget(zoomzoom, 2);
				map.setSize("100%", "100%");
				// Add some controls for the zoom level
				map.setZoomLevel(11);
				map.addControl(new LargeMapControl());

				// Add a marker


			}
		});
	}
}
