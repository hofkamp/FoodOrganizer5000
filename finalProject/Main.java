/**
 * Filename:   Main.java
 * Project:    Final Project
 * Authors:    Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   12/12/18
 * Version:    Milestone 3
 * 
 * Bugs:       No known bugs exist
 */
package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.binding.Bindings;

enum NUTRIENTS {Cal,Carb,Fat,Pro,Fib};

 /**
  * This class creates the  javaFx screen, creates the mealList, foodList, query, addFood, and chooseMeal scenes
  * entities are created and added to the scenes, and the  scenes are all linked. There are popups for errors. This
  * displays the screen and lets users add food items, remove food items, create meals, analyze meals, and search for 
  * food by nutrients and by name. 
  * 
  * @authors  Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
  */
public class Main extends Application {
	
	//fields
    private String filePath = "foodItems.csv";
    private FoodData foodData = new FoodData();
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private List<foodTableItem> tableItems = new ArrayList<foodTableItem>();
    private List<MealItem> mealItems = new ArrayList<MealItem>();
    private MealItem chosenMeal = null;
    private boolean queryOn = false;
    private FoodItem temp;
    private File selectedFile;    
    private ObservableList<MealItem> mealArray =  FXCollections.observableArrayList();
    private MealItem tempMeal;
    
    //creates the fadeTransition 
  	private FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
    
  	 /**
     * This method launches the application
     * @param String[] args
     */
	public static void main(String[] args) {
		launch(args);
	}
	
	
	 /**
     * This method creates the stages and GUI layout for the Main
     * It loads all layouts and creates the functionality for the buttons and lists
     * @param Stage primaryStage
     */
	public void start(Stage primaryStage) throws Exception {
		//title of the home page
		primaryStage.setTitle("Food Organizer 5000");
		
		//instantiates the Layouts
		BorderPane borderPane = new BorderPane();
		DropShadow shadow = new DropShadow();
		StackPane homeLayout = new StackPane();
		StackPane foodLayout = new StackPane();
		StackPane mealLayout = new StackPane();
		StackPane queryLayout = new StackPane();
		StackPane addLayout = new StackPane();
		StackPane chooseLayout = new StackPane();
		
		//instantiates the home screens
		Scene homeScreen = new Scene(homeLayout, 900, 600);
		Scene foodScreen = new Scene(foodLayout, 900, 600);
		Scene mealScreen = new Scene(mealLayout, 900, 600);
		Scene queryScreen = new Scene(queryLayout, 900, 600);
		Scene addScreen = new Scene(addLayout, 900, 600);
		Scene chooseScreen = new Scene(chooseLayout,900,600);
		
		ObservableList<foodTableItem> foodList = FXCollections.observableList(tableItems);
		
		
		
		/////////////////////////////////////////////////////////
		//////////////////// MEAL LIST LAYOUT////////////////////
		/////////////////////////////////////////////////////////
		
		//creates a table for the meals
				TableView table = new TableView();
				table.setEditable(true);
				TableColumn mealColumn = new TableColumn("Meals");
				mealColumn.setMinWidth(300);
				mealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
				table.getColumns().add(mealColumn);
				table.setTranslateX(75);
				table.setTranslateY(150);
				table.setMaxSize(300, 400);
				
		//label for the selected Item
		Label text = new Label("Selected Item");
		text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
 		text.setTranslateX(-130);
 		text.setTranslateY(-140);
 		
 		//label for the name
		Label mealN = new Label("Name:");
		mealN.setFont(Font.font(25));
		mealN.setTranslateX(-240);
 		mealN.setTranslateY(-100);
 		
 		//label for the ingredients
		Label mealIn = new Label("Ingredients:");
		mealIn.setFont(Font.font(25));
		mealIn.setTranslateX(-240);
 		mealIn.setTranslateY(-70);
 		
 		//label for the carbohydrates
		Label mealCb = new Label("Carbohydrates:");
		mealCb.setFont(Font.font(25));
		mealCb.setTranslateX(-240);
 		mealCb.setTranslateY(10);
 		
 		//label for the calories
		Label mealCa = new Label("Calories:");
		mealCa.setFont(Font.font(25));
		mealCa.setTranslateX(-240);
 		mealCa.setTranslateY(50);
 		
 		//label for the protein
		Label mealPr = new Label("Protein:");
		mealPr.setFont(Font.font(25));
		mealPr.setTranslateX(-240);
 		mealPr.setTranslateY(90);
 		
 		//label for the fat
		Label mealFa = new Label("Fat:");
		mealFa.setFont(Font.font(25));
		mealFa.setTranslateX(-240);
 		mealFa.setTranslateY(130);
 		
 		//label for the fiber
		Label mealFi = new Label("Fiber:");
		mealFi.setFont(Font.font(25));
		mealFi.setTranslateX(-240);
 		mealFi.setTranslateY(170);
 		
 		//adds the labels to the mealLayout and positions them
		mealLayout.getChildren().addAll(mealN, mealIn, mealCb,
				mealCa, mealPr, mealFa, mealFi, text);
		mealLayout.setAlignment(mealN, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealIn, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealCb, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealCa, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealPr, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealFa, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(mealFi, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(text, Pos.CENTER_RIGHT);
		
		//creates  a fade out label if the meal was successfully added
        Label addedToMealWarning = new Label("Successfully added to meal");
        addedToMealWarning.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addedToMealWarning.setTranslateX(0);
        addedToMealWarning.setTranslateY(-180);
        mealLayout.getChildren().add(addedToMealWarning);
        mealLayout.setAlignment(addedToMealWarning, Pos.CENTER);
        fadeOut.setNode(addedToMealWarning);
        addedToMealWarning.setVisible(false);
		
        //creates the instance for the name of the selected item
		Label tempName = new Label();
		tempName.setFont(Font.font(25));
		tempName.setTranslateX(-135);
 		tempName.setTranslateY(-100);
 		
 		//creates the instance for the ingredients of the selected item
		ListView<String> ingrList = new ListView<>();
		ingrList.setMaxSize(200, 75);
 		ingrList.setTranslateX(-30);
 		ingrList.setTranslateY(-40);
 		
 		//creates the instance for the calories of the selected item
		Label tempCal = new Label();
		tempCal.setFont(Font.font(25));
 		tempCal.setTranslateX(-135);
 		tempCal.setTranslateY(50);
 		
 		//creates the instance for the carbohydrates of the selected item
		Label tempCarb = new Label();
		tempCarb.setFont(Font.font(25));
		tempCarb.setTranslateX(-135);
 		tempCarb.setTranslateY(13);
 		
 		//creates the instance for the protein of the selected item
		Label tempPro = new Label();
		tempPro.setFont(Font.font(25));
		tempPro.setTranslateX(-135);
 		tempPro.setTranslateY(90);
 		
 		//creates the instance for the fat of the selected item
		Label tempFat = new Label();
		tempFat.setFont(Font.font(25));
		tempFat.setTranslateX(-135);
 		tempFat.setTranslateY(130);
 		
 		//creates the instance for the fiber of the selected item
		Label tempFib = new Label();
		tempFib.setFont(Font.font(25));
		tempFib.setTranslateX(-135);
 		tempFib.setTranslateY(170);
 		
 		//adds the instances and positions them in mealLayout
		mealLayout.getChildren().addAll(tempName, tempCal, tempCarb, tempPro, tempFat, tempFib, ingrList);
		mealLayout.setAlignment(Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempCal, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempCarb, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempPro, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempFat, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempFib, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(ingrList, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempName, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempCal, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempCarb, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempPro, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempFat, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(tempFib, Pos.CENTER_RIGHT);
		mealLayout.setAlignment(ingrList, Pos.CENTER_RIGHT);
		
		
		//sets the items of the table to the meals
		table.setItems(mealArray);
		
		//creates an event handler if an item in the meal is clicked
		table.setOnMouseClicked((new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(mealArray.size()!=0) {
					if(table.getSelectionModel().getSelectedItem() != null) {
						tempMeal = (MealItem)table.getSelectionModel().getSelectedItem();
	 	 		
	 	 				
				 //sets the instance labels to the values from the selected meal
	 	 				tempName.setText(tempMeal.getName());
	 	 				tempCal.setText(tempMeal.getCal() +"");
	 	 				tempCarb.setText(tempMeal.getCarb() +"");
	 	 				tempPro.setText(tempMeal.getPro() +"");
	 	 				tempFat.setText(tempMeal.getFat() +"");
	 	 				tempFib.setText(tempMeal.getFib() +"");
	 	 				ObservableList tempList = FXCollections.observableArrayList(tempMeal.getIngredients());
	 	 				ingrList.setItems(tempList);
	 	 			
					}
				}
			}
		}));
		
		//add the table to the mealLayout and position it
		mealLayout.getChildren().add(table);
		mealLayout.setAlignment(table, Pos.TOP_LEFT);
		
		//add an instructional label 
		Label instr = new Label("Click on Meal to Analyze");
 		instr.setFont(Font.font("Arial", FontWeight.BOLD, 20));
 		instr.setTranslateX(75);
 		instr.setTranslateY(120);
 		mealLayout.getChildren().add(instr);
 		mealLayout.setAlignment(instr, Pos.TOP_LEFT);
		
 		
		// Food List Button
		Button foodBtn1 = new Button("Food List");
		foodBtn1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, the Food List screen will load
				primaryStage.setScene(foodScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		foodBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			foodBtn1.setEffect(shadow);
		});
		foodBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			foodBtn1.setEffect(null);
		});
		
		//formats the Food List button and adds it to the mealLayout
		foodBtn1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		foodBtn1.setTranslateX(-10);
		foodBtn1.setTranslateY(10);
		foodBtn1.setMinSize(150, 150 / 2);
		foodBtn1.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(foodBtn1);
		mealLayout.setAlignment(foodBtn1, Pos.TOP_RIGHT);

		
		// Query Button
		Button queryBtn2 = new Button("Query");
		queryBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked the query screen will load
				primaryStage.setScene(queryScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		queryBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn2.setEffect(shadow);
		});
		queryBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn2.setEffect(null);
		});
		
		//formats the query button and adds it to the mealLayout
		queryBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		queryBtn2.setTranslateX(-165);
		queryBtn2.setTranslateY(10);
		queryBtn2.setMinSize(150, 150 / 2);
		queryBtn2.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(queryBtn2);
		mealLayout.setAlignment(queryBtn2, Pos.TOP_RIGHT);

		
		// Add Food Button
		Button addBtn2 = new Button("Add Food");
		addBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, the Add Food screen will load
				primaryStage.setScene(addScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		addBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn2.setEffect(shadow);
		});
		addBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn2.setEffect(null);
		});
		
		//formats the Add Food Button and adds it to the mealLayout
		addBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		addBtn2.setTranslateX(-320);
		addBtn2.setTranslateY(10);
		addBtn2.setMinSize(150, 150 / 2);
		addBtn2.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(addBtn2);
		mealLayout.setAlignment(addBtn2, Pos.TOP_RIGHT);

		
		// Home Button
		Button homeBtn1 = new Button("Go Back");
		homeBtn1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, the Home Screen will load
				primaryStage.setScene(homeScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		homeBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			homeBtn1.setEffect(shadow);
		});
		homeBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			homeBtn1.setEffect(null);
		});
		
		//adds the Home Button to the mealLayout and formats it
		homeBtn1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		homeBtn1.setTranslateX(-475);
		homeBtn1.setTranslateY(10);
		homeBtn1.setMinSize(150, 150 / 2);
		homeBtn1.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(homeBtn1);
		mealLayout.setAlignment(homeBtn1, Pos.TOP_RIGHT);

		
		// Title Label
		Label title2 = new Label("Meal List");
		title2.setFont(Font.font("Cambria", 50));
		title2.setTranslateX(50);
		title2.setTranslateY(20);
		mealLayout.getChildren().add(title2);
		mealLayout.setAlignment(title2, Pos.TOP_LEFT);
		
		//Create Meal Button
		Button chooseMeal1 = new Button("Create a Meal");
		chooseMeal1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
		chooseMeal1.setMinSize(150, 50);
		chooseMeal1.setTranslateX(-45);
        chooseMeal1.setTranslateY(-50); 
        mealLayout.getChildren().add(chooseMeal1);       
        mealLayout.setAlignment(chooseMeal1, Pos.BOTTOM_RIGHT);
        chooseMeal1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
            	//if the button is clicked, the choose screen is loaded to create a meal
                primaryStage.setScene(chooseScreen);
            }
        });
        //creates an back shadow effect if the mouse is over the button
        chooseMeal1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            chooseMeal1.setEffect(shadow);
        });
        chooseMeal1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            chooseMeal1.setEffect(null);
        });

        //creates a tool tip to give instructions to the user when the mouse is over the button
        Tooltip t = new Tooltip("Create a new meal");
        bindTooltip(chooseMeal1, t);
        
        //Clear Meal Button
        Button clearMeal = new Button("Clear Meal");
 		clearMeal.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
 		clearMeal.setMinSize(150, 50);
 		clearMeal.setTranslateX(-300);
        clearMeal.setTranslateY(-50);  
        mealLayout.getChildren().add(clearMeal);       
        mealLayout.setAlignment(clearMeal, Pos.BOTTOM_RIGHT);
        clearMeal.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent event) {
            	 	//if the button is clicked, remove the selected meal from the Meal List            	
             	for(int i = 0; i < mealArray.size(); i++) {
             		if(tempMeal.getName().compareTo(mealArray.get(i).getName()) == 0)
             			mealArray.remove(i);
             	}
             	mealArray.remove(tempMeal.getName());
             	primaryStage.setScene(mealScreen);
             	tempName.setText("");
 	 			tempCal.setText("");
 	 			tempCarb.setText("");
 	 			tempPro.setText("");
 	 			tempFat.setText("");
 	 			tempFib.setText("");
             	ingrList.setItems(null);
             }
         });
         //creates an back shadow effect if the mouse is over the button
         clearMeal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
             clearMeal.setEffect(shadow);
         });
         clearMeal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
             clearMeal.setEffect(null);
         });
         
         //creates a tool tip to inform the user what the button does
         Tooltip x = new Tooltip("Remove the selected meal from the meal list");
         bindTooltip(clearMeal, t);
         
         
         
        ////////////////////////////////////////////////////////
		////////////////////FOOD LIST LAYOUT////////////////////
		////////////////////////////////////////////////////////
      
         
        //Food Item Table
		TableView table2 = new TableView();
		table2.setEditable(true);
		//allows selection of multiple items
 		table2.getSelectionModel().setSelectionMode(
 			    SelectionMode.MULTIPLE
 		);	
 		//creates the columns
		TableColumn foodCol = new TableColumn("Food");
		foodCol.setMinWidth(200);
		foodCol.setMaxWidth(200);
		foodCol.setSortType(TableColumn.SortType.ASCENDING);
		TableColumn calCol = new TableColumn("Calories (cal)");
		TableColumn carbCol = new TableColumn ("Carbohydrate (g)");
		TableColumn proCol = new TableColumn ("Protein (g)");
		TableColumn fatCol = new TableColumn ("Fat (g)");
		TableColumn fibCol = new TableColumn ("Fiber (g)");
		fibCol.setMinWidth(100);
		fibCol.setMaxWidth(100);
		table2.getColumns().addAll(foodCol, calCol, carbCol, proCol, fatCol, fibCol);
		table2.setTranslateX(50);
		table2.setTranslateY(150);
		table2.setMaxSize(635, 400);
		
		//adds the table and the columns to the Layout
		foodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		calCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
		carbCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
		fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
		fibCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));
		proCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
		table2.setItems(foodList);
		table2.getSortOrder().add(foodCol);
		foodLayout.getChildren().add(table2);
		foodLayout.setAlignment(table2, Pos.TOP_LEFT);
		
		//File Name Label
		Label fileL = new Label("File Name:");
 		fileL.setFont(Font.font("Arial", FontWeight.BOLD,20));
 		fileL.setTranslateX(25);
 		fileL.setTranslateY(-20);
 		foodLayout.getChildren().add(fileL); 
 		foodLayout.setAlignment(fileL, Pos.BOTTOM_LEFT);

 		//Text Field for the save option
 		TextField fileField = new TextField();
 		fileField.setTranslateX(135);
 		fileField.setTranslateY(-15);
 		fileField.setMinSize(550, 30);
 		fileField.setMaxSize(550, 30);
 		foodLayout.getChildren().add(fileField);
 		foodLayout.setAlignment(fileField, Pos.BOTTOM_LEFT);

 		//Save To Current File button
 		Button save2 = new Button("Save to current file");
 		save2.setOnAction(new EventHandler<ActionEvent>() {
 			public void handle(ActionEvent event) {
 				//saves the file to the current file
 				foodData.saveFoodItems(filePath);
 			}
 		});
 		 //creates an back shadow effect if the mouse is over the button
 		save2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
 			save2.setEffect(shadow);
 		});
 		save2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
 			save2.setEffect(null);
 		});
 		
 		//formats the button at adds it to the layout
 		save2.setFont(Font.font("Arial", FontWeight.BOLD,15));
 		save2.setTranslateX(-25);
 		save2.setTranslateY(185);
 		save2.setMinSize(160, 60);
 		save2.setMaxSize(160, 60);
 		foodLayout.getChildren().add(save2); 
 		foodLayout.setAlignment(save2, Pos.CENTER_RIGHT);



		// Save button 
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//save the file to newFile.csv if no file is inserted
 				if(fileField.getText().isEmpty())
 					foodData.saveFoodItems("newFile.csv");
 				else {
 				//save the file to the file provided in the text field
 					if(fileField.getText().contains(".csv"))
 						foodData.saveFoodItems(fileField.getText());
 					else
 						foodData.saveFoodItems(fileField.getText() + ".csv");
 				}
 			}
 		});
		 //creates an back shadow effect if the mouse is over the button
		save.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		        save.setEffect(shadow);
		    });
		save.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		        save.setEffect(null);
		    });
		
		//adds the save button to the layout
		save.setFont(Font.font("Arial", FontWeight.BOLD,20));
		save.setTranslateX(-25);
		save.setTranslateY(260);
		save.setMinSize(160, 50);
		foodLayout.getChildren().add(save); 
		foodLayout.setAlignment(save, Pos.CENTER_RIGHT);
		
		//creates a tool tip for instructional purposes
		Tooltip l = new Tooltip("save the food list to a file");
        bindTooltip(save, l);
		
        
		// Add To Meal Button
		Button enter2 = new Button("Add to Meal");
		enter2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
            	//Adds the selected food item to the meal
            ObservableList<Object> chosenFoods = null;
            		if(table2.getSelectionModel().getSelectedItems() != null) {
            			chosenFoods = (ObservableList<Object>) table2.getSelectionModel().getSelectedItems();
            		}
            		if(chosenMeal !=null && chosenFoods!=null) {
            			for(Object chosenFood : chosenFoods) {
            				chosenMeal.getIngredients().add(((foodTableItem)chosenFood).getFood());
            			}
            			//calculates nutrients and adds the food to the meal and loads the meal screen
            				chosenMeal.calcNutrients();
                  		 addedToMealWarning.setVisible(true);
                  		 mealItems.add(chosenMeal);   
                  		 fadeOut.playFromStart();
                  		 table.getSortOrder().add(foodCol);
                  		 primaryStage.setScene(mealScreen);
                  		 tempName.setText("");
                 		 tempCal.setText("");
                 		 tempCarb.setText("");
                 		 tempPro.setText("");
                 		 tempFat.setText("");
                 		 tempFib.setText("");		 
            		}
            
            }
        });
		 //creates an back shadow effect if the mouse is over the button
		enter2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		        enter2.setEffect(shadow);
		    });
		enter2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		        enter2.setEffect(null);
		    });
		
		//adds the Add To Meal Button to the layout
		enter2.setFont(Font.font("Arial", FontWeight.BOLD,20));
		enter2.setTranslateX(-25);
		enter2.setTranslateY(-125);
		enter2.setMinSize(160, 50);
		foodLayout.getChildren().add(enter2); 
		foodLayout.setAlignment(enter2, Pos.CENTER_RIGHT);
		
		//creates an instructional tool tip over the Add To Meal Button
		Tooltip g = new Tooltip("Add the selected food to the selected meal");
        bindTooltip(enter2, g);

        // filter status
        Label filterStatus = new Label();
        filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
                String s = " ";
                if (queryOn)
                        s = "Filter Status: ON ";
                else
                        s = "Filter Status: OFF";
                return s;
        }));
        //adds the label to the layout
        filterStatus.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        filterStatus.setTranslateX(-25);
        filterStatus.setTranslateY(-5);
        foodLayout.getChildren().add(filterStatus);
        foodLayout.setAlignment(filterStatus, Pos.CENTER_RIGHT);

        // Unfilter button
        Button unfilter = new Button("Unfilter");
        unfilter.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                		//if clicked, will unfilter the button from what was filtered on the query screen
                        queryOn = false;

                        filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
                                String s = " ";
                                if (queryOn)
                                        s = "Filter Status: ON ";
                                else
                                        s = "Filter Status: OFF";
                                return s;
                        }));

                       table2.setItems(foodList);
                       
                }
        });
        //creates an back shadow effect if the mouse is over the button
        unfilter.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                unfilter.setEffect(shadow);
        });
        unfilter.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                unfilter.setEffect(null);
        });
        
        //adds the unfilter button to the layout and formats it
        unfilter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        unfilter.setTranslateX(-25);
        unfilter.setTranslateY(-50);
        unfilter.setMinSize(160, 50);
        foodLayout.getChildren().add(unfilter);
        foodLayout.setAlignment(unfilter, Pos.CENTER_RIGHT);
        
		//Adds the filter status to the layout and formats it
		Label filterBox = new Label("Filter Status:");
		filterBox.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		filterBox.setTranslateX(-25);
		filterBox.setTranslateY(-15);

		//creates an instructional tool tip for the unfilter button
		Tooltip u = new Tooltip("unfilters the list");
        bindTooltip(unfilter, u);
		
        //creates the fade out
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        
		//Current Meal Label
		 Label currentMealText = new Label("Adding to This Meal:");
	        currentMealText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	        currentMealText.setTranslateX(-15);
	        currentMealText.setTranslateY(-180);
	        foodLayout.getChildren().add(currentMealText);
	        foodLayout.setAlignment(currentMealText, Pos.BOTTOM_RIGHT);        
	    Label currentMeal = new Label();
	    	currentMeal.setText("none");
	        currentMeal.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	        currentMeal.setTranslateX(-25);
	        currentMeal.setTranslateY(-155);
	        currentMeal.setMaxWidth(160);
	        foodLayout.getChildren().add(currentMeal);
	        foodLayout.setAlignment(currentMeal, Pos.BOTTOM_RIGHT);
        
        // chooseMeal button
        Button chooseMeal = new Button("Select a Meal to Add to");
        chooseMeal.setFont(Font.font("Arial", FontWeight.BOLD,12));
        chooseMeal.setTranslateX(-25);
        chooseMeal.setTranslateY(-220);
        chooseMeal.setMinSize(50, 40);
        chooseMeal.setMaxSize(160, 50);     
        foodLayout.getChildren().add(chooseMeal);       
        foodLayout.setAlignment(chooseMeal, Pos.BOTTOM_RIGHT);
        chooseMeal.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
            	//if the button is clicked, the chooseScreen is loaded
                primaryStage.setScene(chooseScreen);
            }
        });
        //creates an back shadow effect if the mouse is over the button
        chooseMeal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            chooseMeal.setEffect(shadow);
        });
        chooseMeal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            chooseMeal.setEffect(null);
        });
        
        //creates an instructional tool tip for the add a meal button
        Tooltip w = new Tooltip("Select the meal you want to add to");
        bindTooltip(chooseMeal, w);
        
		//Meal List Button
		Button mealBtn1 = new Button("Meal List");
		mealBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if clicked, calls the Meal List Screen
				primaryStage.setScene(mealScreen);
			}
		});
		  //creates an back shadow effect if the mouse is over the button
		mealBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			mealBtn1.setEffect(shadow);
		});
		mealBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			mealBtn1.setEffect(null);
		});
		//adds the Meal Button to the Layout
		mealBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		mealBtn1.setTranslateX(-10);
		mealBtn1.setTranslateY(10);
		mealBtn1.setMinSize(150, 150/2);
		mealBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(mealBtn1);
		foodLayout.setAlignment(mealBtn1, Pos.TOP_RIGHT);
		
		
		//Query Button
		Button queryBtn1 = new Button("Query");
		queryBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if clicked, the Query Screen is loaded
				primaryStage.setScene(queryScreen);
			}
		});
		  //creates an back shadow effect if the mouse is over the button
		queryBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn1.setEffect(shadow);
		});
		queryBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn1.setEffect(null);
		});
		//adds the query button to the layout
		queryBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		queryBtn1.setTranslateX(-165);
		queryBtn1.setTranslateY(10);
		queryBtn1.setMinSize(150, 150/2);
		queryBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(queryBtn1);
		foodLayout.setAlignment(queryBtn1, Pos.TOP_RIGHT);
		
		//Add Food button
		Button addBtn1 = new Button("Add Food");
		addBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if clicked, load the addScreen
				primaryStage.setScene(addScreen);
			}
		});
		 //creates an back shadow effect if the mouse is over the button
		addBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn1.setEffect(shadow);
		});
		addBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn1.setEffect(null);
		});
		//adds the Add Button to the Layout
		addBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		addBtn1.setTranslateX(-320);
		addBtn1.setTranslateY(10);
		addBtn1.setMinSize(150, 150/2);
		addBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(addBtn1);
		foodLayout.setAlignment(addBtn1, Pos.TOP_RIGHT);
		
		//Home Button
		Button homeBtn = new Button("Go Back");
		homeBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if clicked, load the homeScreen
				primaryStage.setScene(homeScreen);
			}
		});
		 //creates an back shadow effect if the mouse is over the button
		homeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			homeBtn.setEffect(shadow);
		});
		homeBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			homeBtn.setEffect(null);
		});
		//adds the home button to the layout
		homeBtn.setFont(Font.font("Arial", FontWeight.BOLD,20));
		homeBtn.setTranslateX(-475);
		homeBtn.setTranslateY(10);
		homeBtn.setMinSize(150, 150/2);
		homeBtn.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(homeBtn);
		foodLayout.setAlignment(homeBtn, Pos.TOP_RIGHT);

		//title
		Label title1 = new Label("Food List");
		title1.setFont(Font.font("Cambria", 50));
		title1.setTranslateX(50);
		title1.setTranslateY(20);
		foodLayout.getChildren().add(title1);
		foodLayout.setAlignment(title1, Pos.TOP_LEFT);
		
		
		
		////////////////////////////////////////////////////////
		////////////////////ADD FOOD LAYOUT/////////////////////
		////////////////////////////////////////////////////////
		
		//File Button
				Button fileButton = new Button("Choose a file...");
				fileButton.setTranslateY(150);
				fileButton.setMinSize(300, 75);
				//adds the file button to the addLayout
				addLayout.getChildren().addAll(fileButton);
				fileButton.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
				addLayout.setAlignment(fileButton, Pos.TOP_CENTER);
				
				//creates an back shadow effect if the mouse is over the button
				fileButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					fileButton.setEffect(shadow);
				});
				fileButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					fileButton.setEffect(null);
				});
				
				//creates a tool tip to inform the user what to do when the mouse is over the button
				Tooltip a = new Tooltip("select a .csv file to import");
			    bindTooltip(fileButton, a);    
				
				//OR label
				Label ORText = new Label("OR");
				ORText.setFont(Font.font("Arial", 40));
				ORText.setTranslateY(-40);
				addLayout.getChildren().add(ORText);
				addLayout.setAlignment(ORText, Pos.CENTER);
				
				
				//Food Name Text Field and Label
				Label foodName = new Label("Food Name:");
				TextField foodF = new TextField();
				foodF.setMaxSize(250, 40);
				foodF.setTranslateX(225);
				foodF.setTranslateY(10);
				foodName.setTranslateY(10);
				foodName.setTranslateX(10);
				foodName.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(foodF);
				addLayout.setAlignment(foodF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(foodName);
				addLayout.setAlignment(foodName, Pos.CENTER_LEFT);	
				
				//Carbohydrates Text Field and Label
				Label carbNum = new Label("Carbohydrate(g):");
				TextField carbF = new TextField();
				carbF.setMaxSize(250, 40);
				carbF.setTranslateX(225);
				carbF.setTranslateY(70);
				carbNum.setTranslateY(70);
				carbNum.setTranslateX(10);
				carbNum.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(carbF);
				addLayout.setAlignment(carbF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(carbNum);
				addLayout.setAlignment(carbNum, Pos.CENTER_LEFT);
				
				//Calorie Text Field and Label
				Label calNum = new Label("Calories:");
				TextField calF = new TextField();
				calF.setMaxSize(250, 40);
				calF.setTranslateX(225);
				calF.setTranslateY(130);
				calNum.setTranslateY(130);
				calNum.setTranslateX(10);
				calNum.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(calF);
				addLayout.setAlignment(calF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(calNum);
				addLayout.setAlignment(calNum, Pos.CENTER_LEFT);
				
				//Fat Text Field and Label
				Label fatNum = new Label("Fat(g):");
				TextField fatF = new TextField();
				fatF.setMaxSize(250, 40);
				fatF.setTranslateX(620);
				fatF.setTranslateY(10);
				fatNum.setTranslateY(10);
				fatNum.setTranslateX(500);
				fatNum.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(fatF);
				addLayout.setAlignment(fatF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(fatNum);
				addLayout.setAlignment(fatNum, Pos.CENTER_LEFT);
				
				//Protein Text Field and Label
				Label proNum = new Label("Protein(g):");
				TextField proF = new TextField();
				proF.setMaxSize(250, 40);
				proF.setTranslateX(620);
				proF.setTranslateY(70);
				proNum.setTranslateY(70);
				proNum.setTranslateX(500);
				proNum.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(proF);
				addLayout.setAlignment(proF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(proNum);
				addLayout.setAlignment(proNum, Pos.CENTER_LEFT);
				
				//Fiber Text Field and Label
				Label fibNum = new Label("Fiber(g):");
				TextField fibF = new TextField();
				fibF.setMaxSize(250, 40);
				fibF.setTranslateX(620);
				fibF.setTranslateY(130);
				fibNum.setTranslateY(130);
				fibNum.setTranslateX(500);
				fibNum.setFont(Font.font("Arial", 25));
				addLayout.getChildren().add(fibF);
				addLayout.setAlignment(fibF, Pos.CENTER_LEFT);
				addLayout.getChildren().add(fibNum);
				addLayout.setAlignment(fibNum, Pos.CENTER_LEFT);
				
				
				
				fileButton.setOnAction(
			            new EventHandler<ActionEvent>() {
			                public void handle(final ActionEvent e) {
			                	FoodData data = new FoodData();
			                	//if the file Button is selected, load a file chooser and pass the selected file to a variable
			                	FileChooser fileChooser = new FileChooser();
			                	//ensure that onlu .csv files are being selected
			                	fileChooser.getExtensionFilters().addAll(
			                		new FileChooser.ExtensionFilter("CSV Files", "*.csv")
			                	);
			            		File selectedFile = fileChooser.showOpenDialog(null);
			            		if (selectedFile != null) {
			            			//load the food items in to the lists and table
			 	            		List<FoodItem> loadedFoods = new ArrayList<FoodItem>();
			 					data.loadFoodItems(selectedFile.getAbsolutePath());
			 					loadedFoods = data.getAllFoodItems();
			 					
			 					for(int i = 0; i<loadedFoods.size(); i++) {
			 					foodTableItem tableFood = new foodTableItem(loadedFoods.get(i));
			 					foodItems.add(loadedFoods.get(i));
			 					foodData.addFoodItem(loadedFoods.get(i));
			 					tableItems.add(tableFood);
			 					}
			 					
			 				
			 	        		
							
			            		}
			            		//sort the table, clear all testFields and load the foodScreen
			            		table2.getSortOrder().add(foodCol);
								carbF.clear();  
								calF.clear(); 
								fatF.clear(); 
								proF.clear(); 
								fibF.clear(); 
								foodF.clear(); 
								primaryStage.setScene(foodScreen);	            
						
			            }});
				
				//Enter Button
				Button entrBtn = new Button("ENTER");
		 		entrBtn.setOnAction(new EventHandler<ActionEvent>(){
		 			public void handle(ActionEvent e) {
						//adds a pop up if any of the input text is empty 
						if (foodF.getText().isEmpty() || carbF.getText().isEmpty() || calF.getText().isEmpty() || fatF.getText().isEmpty() || proF.getText().isEmpty() || fibF.getText().isEmpty())
	 	                {
	 						Alert alert = new Alert(AlertType.INFORMATION);
	 						alert.setTitle("Error");
	 						alert.setHeaderText("Please enter a food name or nutrient values" + "\n" + "before adding a food item.");
	 						alert.showAndWait();

	 	       			    	
	 	                }
	 				
	 				//adds a pop up if any of the values for any nutrient is not numeric
	 				  else if ((!((carbF.getText()).matches("[0-9]+")) && (!(String.valueOf(carbF.getText()).equals(""))))
	                           || (!((calF.getText()).matches("[0-9]+")) && (!(String.valueOf(calF.getText()).equals(""))))
	                           || (!((fatF.getText()).matches("[0-9]+")) && (!(String.valueOf(fatF.getText()).equals(""))))
	                           || (!((proF.getText()).matches("[0-9]+")) && (!(String.valueOf(proF.getText()).equals(""))))
	                           || (!((fibF.getText()).matches("[0-9]+")) && (!(String.valueOf(fibF.getText()).equals(""))))) {

	 					  			Alert alert = new Alert(AlertType.INFORMATION);
	 					  			alert.setTitle("Error");
	 					  			alert.setHeaderText("Please enter valid, numerical values for the nutrients.");
	 					  			alert.showAndWait();
	 				  }
	 				
	 				  else {
	 					//load the input from the text field to variables
	 					String nameT = foodF.getText();
	 					Double carbsT = Double.parseDouble(carbF.getText());
	 					Double caloriesT = Double.parseDouble(calF.getText());
	 					Double fatT = Double.parseDouble(fatF.getText());
	 					Double proteinT = Double.parseDouble(proF.getText());
	 					Double fiberT = Double.parseDouble(fibF.getText());
	 						
								
							//add the inputs to a foodItem and load it to the table
							FoodItem food = new FoodItem(nameT.hashCode()+ "", nameT);
							food.addNutrient("carbohydrate", carbsT);
							food.addNutrient("calories", caloriesT);
							food.addNutrient("fat", fatT);
							food.addNutrient("protein", proteinT);
							food.addNutrient("fiber", fiberT);	
							foodData.addFoodItem(food);
							foodItems.add(food);
							foodTableItem tableFood = new foodTableItem(food);
							tableItems.add(tableFood);
							table2.setItems(foodList);
							
							//sort the table, clear the text fields and load the foodScreen
							table2.getSortOrder().add(foodCol);
							carbF.clear();  
							calF.clear(); 
							fatF.clear(); 
							proF.clear(); 
							fibF.clear(); 
							foodF.clear(); 
							primaryStage.setScene(foodScreen);
						}
						
					}
				});
				//creates an back shadow effect if the mouse is over the button
				entrBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) ->{
					entrBtn.setEffect(shadow);
				});
				
				entrBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) ->{
					entrBtn.setEffect(null);
				});
				
				//Formats the Enter Button and adds it to the layout
				entrBtn.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
				entrBtn.setMinSize(300, 75);
				entrBtn.setTranslateY(-25);
				addLayout.getChildren().add(entrBtn);
				addLayout.setAlignment(entrBtn, Pos.BOTTOM_CENTER);
				
				//Food List button
				Button foodBtn2 = new Button("Food List");
				foodBtn2.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//if clicked, load the foodScreen
						primaryStage.setScene(foodScreen);
					}	
				});
				
				//creates an back shadow effect if the mouse is over the button
				foodBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					foodBtn2.setEffect(shadow);
				});
				foodBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					foodBtn2.setEffect(null);
				});
				//adds the Food List button to the addLayout and formats it
				foodBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				foodBtn2.setTranslateX(-10);
				foodBtn2.setTranslateY(10);
				foodBtn2.setMinSize(150, 150 / 2);
				foodBtn2.setMaxSize(150, 150 / 2);
				addLayout.getChildren().add(foodBtn2);
				addLayout.setAlignment(foodBtn2, Pos.TOP_RIGHT);
				
				// Query Button
				Button queryBtn3 = new Button("Query");
				queryBtn3.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//if clicked, load the queryScreen
						primaryStage.setScene(queryScreen);
					}
				});
				
				//creates an back shadow effect if the mouse is over the button
				queryBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					queryBtn3.setEffect(shadow);
				});
				queryBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					queryBtn3.setEffect(null);
				});
				//adds the Query button to the addLayout and formats it
				queryBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				queryBtn3.setTranslateX(-165);
				queryBtn3.setTranslateY(10);
				queryBtn3.setMinSize(150, 150 / 2);
				queryBtn3.setMaxSize(150, 150 / 2);
				addLayout.getChildren().add(queryBtn3);
				addLayout.setAlignment(queryBtn3, Pos.TOP_RIGHT);
				
				// Meal List Button
				Button mealBtn3 = new Button("Meal List");
				mealBtn3.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//if clicked, load the mealScreen
						primaryStage.setScene(mealScreen);
					}
				});
				
				//creates an back shadow effect if the mouse is over the button
				mealBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					mealBtn3.setEffect(shadow);
				});
				mealBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					mealBtn3.setEffect(null);
				});
				//adds the Meal List button to the addLayout and formats it
				mealBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				mealBtn3.setTranslateX(-320);
				mealBtn3.setTranslateY(10);
				mealBtn3.setMinSize(150, 150 / 2);
				mealBtn3.setMaxSize(150, 150 / 2);
				addLayout.getChildren().add(mealBtn3);
				addLayout.setAlignment(mealBtn3, Pos.TOP_RIGHT);
				
				// Home Button
				Button homeBtn2 = new Button("Go Back");
				homeBtn2.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//if clicked, load the homeScreen
						primaryStage.setScene(homeScreen);
					}
				});
				
				//creates an back shadow effect if the mouse is over the button
				homeBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					homeBtn2.setEffect(shadow);
				});
				homeBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					homeBtn2.setEffect(null);
				});
				//adds the Home button to the addLayout and formats it
				homeBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				homeBtn2.setTranslateX(-475);
				homeBtn2.setTranslateY(10);
				homeBtn2.setMinSize(150, 150 / 2);
				homeBtn2.setMaxSize(150, 150 / 2);
				addLayout.getChildren().add(homeBtn2);
				addLayout.setAlignment(homeBtn2, Pos.TOP_RIGHT);
				
				// Title Label
				Label title3 = new Label("Add Food");
				title3.setFont(Font.font("Cambria", 50));
				title3.setTranslateX(50);
				title3.setTranslateY(20);
				//adds the title label to the addLayout and formats it
				addLayout.getChildren().add(title3);
				addLayout.setAlignment(title3, Pos.TOP_LEFT);	

				
				
				
		////////////////////////////////////////////////////////
		////////////////////CHOOSE MEAL/////////////////////////
		////////////////////////////////////////////////////////
		
		//Meal Table
		TableView chooseTable = new TableView();
		chooseTable.setEditable(true);
		
		//formats the meal table
		TableColumn chooseMealColumn = new TableColumn("Meals");
		chooseMealColumn.setMinWidth(300);
		chooseMealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		chooseTable.getColumns().add(chooseMealColumn);
		chooseTable.setTranslateX(-190);
		chooseTable.setTranslateY(-10);
		chooseTable.setMaxSize(300, 400);
		chooseTable.setOnMouseClicked((new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//if a meal is double clicked, keep the selected meal and load the food screen to select food to add to that meal
				if (event.getClickCount() == 2) {
					chosenMeal = (MealItem)chooseTable.getSelectionModel().getSelectedItem();
					if (chosenMeal != null) {
						currentMeal.setText(chosenMeal.getName());
						primaryStage.setScene(foodScreen);
					}
				}
			}
		}));

		//Add the table to the chooseLayout
		chooseTable.setItems(mealArray);
		chooseLayout.getChildren().add(chooseTable);

		//Continue Button
		Button continueButton = new Button("Continue with meal");
		continueButton.setFont(Font.font("Arial", FontWeight.BOLD,20));
		continueButton.setTranslateX(-190);
		continueButton.setTranslateY(225);
		continueButton.setMaxSize(230, 50);
		continueButton.setMinSize(180, 30);
		continueButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if the button is clicked, keep the selected meal and load the food screen to select food to add to that meal
				chosenMeal = (MealItem)chooseTable.getSelectionModel().getSelectedItem();
				if (chosenMeal != null) {
					currentMeal.setText(chosenMeal.getName());
					primaryStage.setScene(foodScreen);
				}
			}
		});
		//creates an back shadow effect if the mouse is over the button
		continueButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			continueButton.setEffect(shadow);
		});
		continueButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			continueButton.setEffect(null);
		});

		//creates a tool tip to inform the user what to do when the mouse is over the button
		Tooltip c = new Tooltip("Select a meal to add to");
		bindTooltip(continueButton, c);
		chooseLayout.getChildren().add(continueButton);

		//New Meal Label
		Label addNewMeal = new Label("Add a new meal");
		addNewMeal.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		addNewMeal.setTranslateX(210);
		addNewMeal.setTranslateY(-80);
		chooseLayout.getChildren().add(addNewMeal);

		//Creates a Text Field for a new Meal
		TextField newMealF = new TextField();
		newMealF.setTranslateX(210);
		newMealF.setTranslateY(-35);
		newMealF.setMinSize(200, 30);
		newMealF.setMaxSize(200, 30);
		chooseLayout.getChildren().add(newMealF);

		//Add Meal Button
		Button addMealButton = new Button("Add new meal");
		addMealButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		addMealButton.setTranslateX(210);
		addMealButton.setTranslateY(20);
		addMealButton.setMaxSize(180, 50);
		addMealButton.setMinSize(180, 30);
		//adds the Add Meal Button to the chooseLayout
		chooseLayout.getChildren().add(addMealButton);
		addMealButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the text is empty and the add Meal button is pressed, display a pop up warning
				if (newMealF.getText().isEmpty())
				 {
				    	Alert alert = new Alert(AlertType.INFORMATION);
				    	alert.setTitle("Error");
				    	alert.setHeaderText("Please enter text for a meal before adding it.");
				    	alert.showAndWait();
				 }
				
				// otherwise, add the meal to the meal List and display it on the table
				 else {
					String newMealName = newMealF.getText();
					boolean contains = false;
					for (Object meal : mealArray)
						if (((MealItem)meal).getName().equals(newMealName))
							contains = true;
					if (!contains)
						mealArray.add(new MealItem(newMealF.getText(),new ArrayList<FoodItem>()));
					
					//clear the text from the text field
					newMealF.clear();
				 }
			}
		});
		//creates an back shadow effect if the mouse is over the button
		addMealButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addMealButton.setEffect(shadow);
		});
		addMealButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addMealButton.setEffect(null);
		});

		//Cancel Button
		Button cancelButton = new Button("Cancel");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		cancelButton.setTranslateX(390);
		cancelButton.setTranslateY(-265);
		cancelButton.setMaxSize(100, 50);
		cancelButton.setMinSize(100, 30);
		//adds the cancel button to the chooseLayout
		chooseLayout.getChildren().add(cancelButton);
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			//if the button is pressed, load the foodScreen
			public void handle(ActionEvent event) {
				primaryStage.setScene(foodScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(shadow);
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
		});

		// Instruction Label
		Label instruct = new Label("Select A Meal From The Table Or Create a New Meal");
		instruct.setFont(Font.font("Cambria", 30));
		instruct.setTranslateX(40);
		instruct.setTranslateY(20);
		//adds the title label to the addLayout and formats it
		chooseLayout.getChildren().add(instruct);
		chooseLayout.setAlignment(instruct, Pos.TOP_LEFT);	

		
		
		/////////////////////////////////////////////////////////
		//////////////////// QUERY LAYOUT////////////////////////
		/////////////////////////////////////////////////////////

		//Formats and adds the Labels to the layout
		Label leftText = new Label("Search by nutritional facts:");
		Label rightText = new Label("Search for a food item:");
		leftText.setFont(Font.font("Arial", 30));
		rightText.setFont(Font.font("Arial", 30));
		leftText.setTranslateY(-150);
		leftText.setTranslateX(40);
		rightText.setTranslateX(-40);
		rightText.setTranslateY(-150);
		queryLayout.getChildren().add(leftText);
		queryLayout.getChildren().add(rightText);
		queryLayout.setAlignment(leftText, Pos.CENTER_LEFT);
		queryLayout.setAlignment(rightText, Pos.CENTER_RIGHT);

		//Text Field
		TextField foodField = new TextField();
		foodField.setMaxSize(300, 30);
		foodField.setTranslateY(-90);
		foodField.setTranslateX(-40);
		queryLayout.getChildren().add(foodField);
		queryLayout.setAlignment(foodField, Pos.CENTER_RIGHT);

		// Calorie Label
		Label calQ = new Label("Calories (cal):");
		TextField calorieF = new TextField();
		calorieF.setMaxSize(75, 30);
		calorieF.setTranslateX(290);
		calorieF.setTranslateY(-90);
		calQ.setFont(Font.font("Arial", 20));
		calQ.setTranslateY(-95);
		calQ.setTranslateX(40);
		ToggleGroup calGroup = new ToggleGroup();
		RadioButton calB = new RadioButton("Below");
		calB.setToggleGroup(calGroup);
		calB.setSelected(true);
		calB.setTranslateX(190);
		calB.setTranslateY(-95);
		calB.setStyle("-fx-text-fill: black");
		RadioButton calA = new RadioButton("Above");
		calA.setToggleGroup(calGroup);
		calA.setTranslateX(190);
		calA.setTranslateY(-75);
		calA.setStyle("-fx-text-fill: black");
		RadioButton calE = new RadioButton("Equal");
		calE.setToggleGroup(calGroup);
		calE.setTranslateX(190);
		calE.setTranslateY(-55);
		calE.setStyle("-fx-text-fill: black");

		//add the calorie label and the toggle to the layout
		queryLayout.getChildren().add(calE);
		queryLayout.getChildren().add(calQ);
		queryLayout.getChildren().add(calB);
		queryLayout.getChildren().add(calA);
		queryLayout.getChildren().add(calorieF);
		queryLayout.setAlignment(calQ, Pos.CENTER_LEFT);
		queryLayout.setAlignment(calB, Pos.CENTER_LEFT);
		queryLayout.setAlignment(calA, Pos.CENTER_LEFT);
		queryLayout.setAlignment(calorieF, Pos.CENTER_LEFT);
		queryLayout.setAlignment(calE, Pos.CENTER_LEFT);

		// Protein Label
		Label proQ = new Label("Protein (g):");
		TextField proteinF = new TextField();
		proteinF.setMaxSize(75, 30);
		proteinF.setTranslateX(290);
		proteinF.setTranslateY(-15);
		proQ.setFont(Font.font("Arial", 20));
		proQ.setTranslateX(40);
		proQ.setTranslateY(-20);
		ToggleGroup proGroup = new ToggleGroup();
		RadioButton proB = new RadioButton("Below");
		proB.setToggleGroup(proGroup);
		proB.setSelected(true);
		proB.setTranslateX(190);
		proB.setTranslateY(-20);
		proB.setStyle("-fx-text-fill: black");
		RadioButton proA = new RadioButton("Above");
		proA.setToggleGroup(proGroup);
		proA.setTranslateX(190);
		proA.setTranslateY(0);
		proA.setStyle("-fx-text-fill: black");
		RadioButton proE = new RadioButton("Equal");
		proE.setToggleGroup(proGroup);
		proE.setTranslateX(190);
		proE.setTranslateY(20);
		proE.setStyle("-fx-text-fill: black");

		//add the protein label and the toggle to the layout
		queryLayout.getChildren().add(proE);
		queryLayout.getChildren().add(proQ);
		queryLayout.getChildren().add(proB);
		queryLayout.getChildren().add(proA);
		queryLayout.getChildren().add(proteinF);
		queryLayout.setAlignment(proQ, Pos.CENTER_LEFT);
		queryLayout.setAlignment(proB, Pos.CENTER_LEFT);
		queryLayout.setAlignment(proA, Pos.CENTER_LEFT);
		queryLayout.setAlignment(proteinF, Pos.CENTER_LEFT);
		queryLayout.setAlignment(proE, Pos.CENTER_LEFT);

		// Carb Label
		Label carQ = new Label("Carbs (g):");
		TextField carF = new TextField();
		carF.setMaxSize(75, 30);
		carF.setTranslateX(290);
		carF.setTranslateY(60);
		carQ.setFont(Font.font("Arial", 20));
		carQ.setTranslateX(40);
		carQ.setTranslateY(55);
		ToggleGroup carGroup = new ToggleGroup();
		RadioButton carB = new RadioButton("Below");
		carB.setToggleGroup(carGroup);
		carB.setSelected(true);
		carB.setTranslateX(190);
		carB.setTranslateY(55);
		carB.setStyle("-fx-text-fill: black");
		RadioButton carA = new RadioButton("Above");
		carA.setToggleGroup(carGroup);
		carA.setTranslateX(190);
		carA.setTranslateY(75);
		carA.setStyle("-fx-text-fill: black");
		RadioButton carE = new RadioButton("Equal");
		carE.setToggleGroup(carGroup);
		carE.setTranslateX(190);
		carE.setTranslateY(95);
		carE.setStyle("-fx-text-fill: black");

		//add the carb label and toggle to the layout
		queryLayout.getChildren().add(carE);
		queryLayout.getChildren().add(carQ);
		queryLayout.getChildren().add(carB);
		queryLayout.getChildren().add(carA);
		queryLayout.getChildren().add(carF);
		queryLayout.setAlignment(carQ, Pos.CENTER_LEFT);
		queryLayout.setAlignment(carB, Pos.CENTER_LEFT);
		queryLayout.setAlignment(carA, Pos.CENTER_LEFT);
		queryLayout.setAlignment(carF, Pos.CENTER_LEFT);
		queryLayout.setAlignment(carE, Pos.CENTER_LEFT);

		// Fat Label
		Label fatQ = new Label("Fat (g):");
		TextField fatF1 = new TextField();
		fatF1.setMaxSize(75, 30);
		fatF1.setTranslateX(290);
		fatF1.setTranslateY(135);
		fatQ.setFont(Font.font("Arial", 20));
		fatQ.setTranslateX(40);
		fatQ.setTranslateY(130);
		ToggleGroup fatGroup = new ToggleGroup();
		RadioButton fatB = new RadioButton("Below");
		fatB.setToggleGroup(fatGroup);
		fatB.setSelected(true);
		fatB.setTranslateX(190);
		fatB.setTranslateY(130);
		fatB.setStyle("-fx-text-fill: black");
		RadioButton fatA = new RadioButton("Above");
		fatA.setToggleGroup(fatGroup);
		fatA.setTranslateX(190);
		fatA.setTranslateY(150);
		fatA.setStyle("-fx-text-fill: black");
		RadioButton fatE = new RadioButton("Equal");
		fatE.setToggleGroup(fatGroup);
		fatE.setTranslateX(190);
		fatE.setTranslateY(170);
		fatE.setStyle("-fx-text-fill: black");

		//add the fat label and it's toggle to the layout
		queryLayout.getChildren().add(fatE);
		queryLayout.getChildren().add(fatQ);
		queryLayout.getChildren().add(fatB);
		queryLayout.getChildren().add(fatA);
		queryLayout.getChildren().add(fatF1);
		queryLayout.setAlignment(fatQ, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fatB, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fatA, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fatF1, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fatE, Pos.CENTER_LEFT);

		// Fiber Label
		Label fibQ = new Label("Fiber (g):");
		TextField fibF1 = new TextField();
		fibF1.setMaxSize(75, 30);
		fibF1.setTranslateX(290);
		fibF1.setTranslateY(210);
		fibQ.setFont(Font.font("Arial", 20));
		fibQ.setTranslateX(40);
		fibQ.setTranslateY(205);
		ToggleGroup fibGroup = new ToggleGroup();
		RadioButton fibB = new RadioButton("Below");
		fibB.setToggleGroup(fibGroup);
		fibB.setSelected(true);
		fibB.setTranslateX(190);
		fibB.setTranslateY(205);
		fibB.setStyle("-fx-text-fill: black");
		RadioButton fibA = new RadioButton("Above");
		fibA.setToggleGroup(fibGroup);
		fibA.setTranslateX(190);
		fibA.setTranslateY(225);
		fibA.setStyle("-fx-text-fill: black");
		RadioButton fibE = new RadioButton("Equal");
		fibE.setToggleGroup(fibGroup);
		fibE.setTranslateX(190);
		fibE.setTranslateY(245);
		fibE.setStyle("-fx-text-fill: black");

		//add the fiber label and it's toggle to the layout
		queryLayout.getChildren().add(fibE);
		queryLayout.getChildren().add(fibQ);
		queryLayout.getChildren().add(fibB);
		queryLayout.getChildren().add(fibA);
		queryLayout.getChildren().add(fibF1);
		queryLayout.setAlignment(fibQ, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fibB, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fibA, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fibF1, Pos.CENTER_LEFT);
		queryLayout.setAlignment(fibE, Pos.CENTER_LEFT);

		// enter button
				Button enter = new Button("Enter");
				enter.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {

						// if the fields are empty then it will display an error message
						if (foodField.getText().isEmpty() && (fibF1.getText().isEmpty() && fatF1.getText().isEmpty()
								&& carF.getText().isEmpty() && proteinF.getText().isEmpty() && calorieF.getText().isEmpty())) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error");
							alert.setHeaderText(
									"Please enter a food name or nutrient values" + "\n" + "before searching for a food item.");
							alert.showAndWait();
							primaryStage.setScene(queryScreen);

						}
						
						//if the values entered in the text field are negative or alphabetical, then it will display an error message
						else if ((!((calorieF.getText()).matches("[0-9]+"))
								&& (!(String.valueOf(calorieF.getText()).equals(""))))
								|| (!((proteinF.getText()).matches("[0-9]+"))
										&& (!(String.valueOf(proteinF.getText()).equals(""))))
								|| (!((carF.getText()).matches("[0-9]+")) && (!(String.valueOf(carF.getText()).equals(""))))
								|| (!((fatF1.getText()).matches("[0-9]+")) && (!(String.valueOf(fatF1.getText()).equals(""))))
								|| (!((fibF1.getText()).matches("[0-9]+"))
										&& (!(String.valueOf(fibF1.getText()).equals(""))))) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error");
							alert.setHeaderText("Please enter valid, numerical values for the nutrients.");
							alert.showAndWait();
							primaryStage.setScene(queryScreen);

						} else {//for all correct values, it will do they following

							//if the search by name field is empty but there are values in the nutritional spots, then it will execute the following
							if (String.valueOf(foodField.getText()).equals("")
									&& (!(String.valueOf(calorieF.getText()).equals(""))
											|| !(String.valueOf(proteinF.getText()).equals(""))
											|| !(String.valueOf(carF.getText()).equals(""))
											|| !(String.valueOf(fatF1.getText()).equals(""))
											|| !(String.valueOf(fibF1.getText()).equals("")))) {

								List<String> finalInputList = new ArrayList<String>();
								
								//checks if the calorie textfield is not empty
								if (!(String.valueOf(calorieF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (calB.isSelected()) {
										String input = "calories <= " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									} else if (calA.isSelected()) {
										String input = "calories >= " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									} else if (calE.isSelected()) {
										String input = "calories == " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									}
								}

								//checks if the protein textfield is not empty
								if (!(String.valueOf(proteinF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (proB.isSelected()) {
										String input = "protein <= " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									} else if (proA.isSelected()) {
										String input = "protein >= " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									} else if (proE.isSelected()) {
										String input = "protein == " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									}
								}
								
								//checks if the carbs textfield is not empty
								if (!(String.valueOf(carF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (carB.isSelected()) {
										String input = "carbohydrate <= " + String.valueOf(carF.getText());
										finalInputList.add(input);
									} else if (carA.isSelected()) {
										String input = "carbohydrate >= " + String.valueOf(carF.getText());
										finalInputList.add(input);
									} else if (carE.isSelected()) {
										String input = "carbohydrate == " + String.valueOf(carF.getText());
										finalInputList.add(input);
									}
								}

								//checks if the fat textfield is not empty
								if (!(String.valueOf(fatF1.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (fatB.isSelected()) {
										String input = "fat <= " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									} else if (fatA.isSelected()) {
										String input = "fat >= " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									} else if (fatE.isSelected()) {
										String input = "fat == " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									}
								}
								
								//checks if the fiber textfield is not empty
								if (!(String.valueOf(fibF1.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (fibB.isSelected()) {
										String input = "fiber <= " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									} else if (fibA.isSelected()) {
										String input = "fiber >= " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									} else if (fibE.isSelected()) {
										String input = "fiber == " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									}
								}

								List<foodTableItem> tempFoodTableItems = new ArrayList<foodTableItem>();
								List<FoodItem> tempFoodList = foodData.filterByNutrients(finalInputList);

								//for each loop that adds the filtered nutrients into a list
								for (FoodItem food : tempFoodList) {
									foodTableItem temp = new foodTableItem(food);
									tempFoodTableItems.add(temp);
								}
								
								//list is turned into an observable list so that it can be properly displayed on the table
								ObservableList<foodTableItem> temp = FXCollections.observableList(tempFoodTableItems);

								//sets the list to the table
								table2.setItems(temp);

								//changes the filter status to on
								filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
									return "Filter Status: ON";
								}));

								//sets the screen as the food list screen
								primaryStage.setScene(foodScreen);

							} 
							// if the search for food based on name textfield is the only part filled out, then the following code will execute
							else if (!(foodField.getText().equals("")) && ((String.valueOf(calorieF.getText()).equals(""))
									&& (String.valueOf(proteinF.getText()).equals(""))
									&& (String.valueOf(carF.getText()).equals(""))
									&& (String.valueOf(fatF1.getText()).equals(""))
									&& (String.valueOf(fibF1.getText()).equals("")))) {

								//set textFieldInput equal to the value of the user input
								String textFieldInput = String.valueOf(foodField.getText());

								//create a list of foodTableItems and a list of foodItems that calls the foodData method fitler by Name
								List<foodTableItem> tempFoodTableItems = new ArrayList<foodTableItem>();
								List<FoodItem> tempFoodList = foodData.filterByName(textFieldInput);
								
								//puts every FoodItem object into the foodTableItem
								for (FoodItem food : tempFoodList) {
									foodTableItem temp = new foodTableItem(food);
									tempFoodTableItems.add(temp);
								}

								//puts all of the foodTableItems into an observable list so that it can be displayed into a table
								ObservableList<foodTableItem> temp = FXCollections.observableList(tempFoodTableItems);

								//sets the observable list into the table
								table2.setItems(temp);

								//changes the filter status to on
								filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
									return "Filter Status: ON";
								}));

								//sets the current screen to the food list
								primaryStage.setScene(foodScreen);

							}
							// if the search for food field is not empty and there is at least one nutrition filter filled out, the following will execute
							else if (!(foodField.getText().equals("")) && (!(calorieF.getText().isEmpty())
									|| !(proteinF.getText().isEmpty()) || !(carF.getText().isEmpty())
									|| !(fatF1.getText().isEmpty()) || !(fibF1.getText().isEmpty()))) {

								//creates a list of Strings for the rules to be put into
								List<String> finalInputList = new ArrayList<String>();
								//creates a string for the input received from the search for food
								String textFieldInput = String.valueOf(foodField.getText());

								//checks if the caloire text field is not empty
								if (!(String.valueOf(calorieF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (calB.isSelected()) {
										String input = "calories <= " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									} else if (calA.isSelected()) {
										String input = "calories >= " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									} else if (calE.isSelected()) {
										String input = "calories == " + String.valueOf(calorieF.getText());
										finalInputList.add(input);
									}
								}

								//checks if the protein text field is not empty
								if (!(String.valueOf(proteinF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (proB.isSelected()) {
										String input = "protein <= " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									} else if (proA.isSelected()) {
										String input = "protein >= " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									} else if (proE.isSelected()) {
										String input = "protein == " + String.valueOf(proteinF.getText());
										finalInputList.add(input);
									}
								}

								//checks if the carb textfield is not empty
								if (!(String.valueOf(carF.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (carB.isSelected()) {
										String input = "carbohydrate <= " + String.valueOf(carF.getText());
										finalInputList.add(input);
									} else if (carA.isSelected()) {
										String input = "carbohydrate >= " + String.valueOf(carF.getText());
										finalInputList.add(input);
									} else if (carE.isSelected()) {
										String input = "carbohydrate == " + String.valueOf(carF.getText());
										finalInputList.add(input);
									}
								}

								//checks if the fat text field is empty
								if (!(String.valueOf(fatF1.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (fatB.isSelected()) {
										String input = "fat <= " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									} else if (fatA.isSelected()) {
										String input = "fat >= " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									} else if (fatE.isSelected()) {
										String input = "fat == " + String.valueOf(fatF1.getText());
										finalInputList.add(input);
									}
								}

								//checks if the fiber textfield is empty
								if (!(String.valueOf(fibF1.getText()).equals(""))) {
									//checks which button is selected and then adds the textfield into the List
									if (fibB.isSelected()) {
										String input = "fiber <= " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									} else if (fibA.isSelected()) {
										String input = "fiber >= " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									} else if (fibE.isSelected()) {
										String input = "fiber == " + String.valueOf(fibF1.getText());
										finalInputList.add(input);
									}
								}

								//creates a list of foodTableItem's that will be used for the display of the table
								List<foodTableItem> tempFoodTableItems = new ArrayList<foodTableItem>();
								//creates a list of FoodItems that will call the foodData method filterByNutrients
								List<FoodItem> tempFoodList = foodData.filterByNutrients(finalInputList);
								//creates a list of FoodItems that will be used for filter the tempFoodList based on the search for food text field
								List<FoodItem> secondFoodList = new ArrayList<FoodItem>();

								//checks if any names of the tempFoodList match with the search for food textfield and then puts
								// the FoodItem into the secondFoodList
								for (int i = 0; i < tempFoodList.size(); i++) {
									if (tempFoodList.get(i).getName().toLowerCase().contains(textFieldInput.toLowerCase())) {
										secondFoodList.add(tempFoodList.get(i));
									}
								}

								//puts the items from the secondFoodList into the tempFoodTableItems list
								for (FoodItem food : secondFoodList) {
									foodTableItem temp = new foodTableItem(food);
									tempFoodTableItems.add(temp);
								}
								
								//creates an ObservableList of foodTableItem's so that the table can properly display the items
								ObservableList<foodTableItem> temp = FXCollections.observableList(tempFoodTableItems);

								//sets the table to the observable list
								table2.setItems(temp);

								//turns the filter status to ON
								filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
									return "Filter Status: ON";
								}));

								//sets the screen as the foodlist
								primaryStage.setScene(foodScreen);

							}

							else {
								//puts the original foodList as the table and sets the screen as the foodlist
								table2.setItems(foodList);
								primaryStage.setScene(foodScreen);
							}

						}
					}
				});
        
        //adds the enter button to the layout
        enter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        enter.setTranslateX(-40);
        enter.setTranslateY(-30);
        enter.setMinSize(50, 20);
        enter.setMaxSize(100, 50);
        queryLayout.getChildren().add(enter);
        queryLayout.setAlignment(enter, Pos.CENTER_RIGHT);

        // food list button
        Button foodBtn3 = new Button("Food List");
        foodBtn3.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	//if clicked, loads the foodScreen
                        primaryStage.setScene(foodScreen);
                }
        });
        //creates an back shadow effect if the mouse is over the button
        foodBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                foodBtn3.setEffect(shadow);
        });
        foodBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                foodBtn3.setEffect(null);
        });
        
        //adds the food list button to the layout and formats it
        foodBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        foodBtn3.setTranslateX(-10);
        foodBtn3.setTranslateY(10);
        foodBtn3.setMinSize(150, 150 / 2);
        foodBtn3.setMaxSize(150, 150 / 2);
        queryLayout.getChildren().add(foodBtn3);
        queryLayout.setAlignment(foodBtn3, Pos.TOP_RIGHT);

        // add food button
        Button addBtn3 = new Button("Add Food");
        addBtn3.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                 	//if clicked, loads the addScreen
                        primaryStage.setScene(addScreen);
                }
        });
        //creates an back shadow effect if the mouse is over the button
        addBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                addBtn3.setEffect(shadow);
        });
        addBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                addBtn3.setEffect(null);
        });
        
        //adds the food add food button to the layout and formats it
        addBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addBtn3.setTranslateX(-165);
        addBtn3.setTranslateY(10);
        addBtn3.setMinSize(150, 150 / 2);
        addBtn3.setMaxSize(150, 150 / 2);
        queryLayout.getChildren().add(addBtn3);
        queryLayout.setAlignment(addBtn3, Pos.TOP_RIGHT);

        // meal list button
        Button mealBtn4 = new Button("Meal List");
        mealBtn4.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                 	//if clicked, loads the mealScreen
                        primaryStage.setScene(mealScreen);
                }
        });
        //creates an back shadow effect if the mouse is over the button
        mealBtn4.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                mealBtn4.setEffect(shadow);
        });
        mealBtn4.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                mealBtn4.setEffect(null);
        });
        
        //adds the meal list button to the layout and formats it
        mealBtn4.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        mealBtn4.setTranslateX(-320);
        mealBtn4.setTranslateY(10);
        mealBtn4.setMinSize(150, 150 / 2);
        mealBtn4.setMaxSize(150, 150 / 2);
        queryLayout.getChildren().add(mealBtn4);
        queryLayout.setAlignment(mealBtn4, Pos.TOP_RIGHT);

        // home button
        Button homeBtn3 = new Button("Go Back");
        homeBtn3.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                 	//if clicked, loads the homeScreen
                        primaryStage.setScene(homeScreen);
                }
        });
        //creates an back shadow effect if the mouse is over the button
        homeBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                homeBtn3.setEffect(shadow);
        });
        homeBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                homeBtn3.setEffect(null);
        });
        
        //adds the home button to the layout and formats it
        homeBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        homeBtn3.setTranslateX(-475);
        homeBtn3.setTranslateY(10);
        homeBtn3.setMinSize(150, 150 / 2);
        homeBtn3.setMaxSize(150, 150 / 2);
        queryLayout.getChildren().add(homeBtn3);
        queryLayout.setAlignment(homeBtn3, Pos.TOP_RIGHT);

        // Title label
        Label title4 = new Label("Query");
        title4.setFont(Font.font("Cambria", 50));
        title4.setTranslateX(50);
        title4.setTranslateY(20);
        queryLayout.getChildren().add(title4);
        queryLayout.setAlignment(title4, Pos.TOP_LEFT);
		
        
        
        ////////////////////////////////////////////////////////
		//////////////////// HOME LAYOUT ///////////////////////
        ////////////////////////////////////////////////////////
        
		//Quit Button
		Button exitBtn = new Button("Quit");
		exitBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				//if the button is clicked, quit the program
				System.exit(0);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		exitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			exitBtn.setEffect(shadow);
		});
		exitBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			exitBtn.setEffect(null);
		});
		
		//adds the quit button to the homeLayout and formats it
		exitBtn.setFont(Font.font("Arial", FontWeight.BOLD,20));
		exitBtn.setTranslateX(-10);
		exitBtn.setTranslateY(10);
		homeLayout.getChildren().add(exitBtn);
		homeLayout.setAlignment(exitBtn, Pos.TOP_RIGHT);
		
		
		//Title Label
		Label title = new Label("Food Organizer 5000");
		title.setFont(Font.font("Cambria", 70));
		title.setTranslateY(50);
		homeLayout.getChildren().add(title);
		homeLayout.setAlignment(title, Pos.TOP_CENTER);
		
		
		//Meal List Button
		Button mealBtn = new Button("View Meal List");
		mealBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, load the mealScreen
				primaryStage.setScene(mealScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		mealBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			mealBtn.setEffect(shadow);
		});
		mealBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			mealBtn.setEffect(null);
		});
		
		//adds the Meal List Button to the homeLayout and formats it
		mealBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		mealBtn.setTranslateX(100);
 		mealBtn.setTranslateY(-50);
 		mealBtn.setMinSize(300, 150);
 		mealBtn.setMaxSize(300,150);
		homeLayout.getChildren().add(mealBtn);
		homeLayout.setAlignment(mealBtn, Pos.CENTER_LEFT);
		
		
		//Food List Button
		Button foodBtn = new Button("View Food List");
		foodBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, load the foodScreen
				primaryStage.setScene(foodScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		foodBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			foodBtn.setEffect(shadow);
		});
		foodBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			foodBtn.setEffect(null);
		});
		
		//add the Food List Button to the homeLayout and formats it
		foodBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		foodBtn.setTranslateX(-100);
 		foodBtn.setTranslateY(-50);
 		foodBtn.setMinSize(300, 150);
 		foodBtn.setMaxSize(300, 150);
		homeLayout.getChildren().add(foodBtn);
		homeLayout.setAlignment(foodBtn, Pos.CENTER_RIGHT);
		
		
		//Add Food Button
		Button addBtn = new Button("Add Food Item(s)");
		addBtn.wrapTextProperty().setValue(true);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, load the addScreen
				primaryStage.setScene(addScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		addBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn.setEffect(shadow);
		});
		addBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn.setEffect(null);
		});
		
		//adds the Add Food Button to the homeLayout and formats it
		addBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		addBtn.setTranslateX(-100);
 		addBtn.setTranslateY(-100);
 		addBtn.setMinSize(300, 150);
 		addBtn.setMaxSize(300, 150);
		homeLayout.getChildren().add(addBtn);
		homeLayout.setAlignment(addBtn, Pos.BOTTOM_RIGHT);
		
		
		//Query Button
		Button queryBtn = new Button("Search For Food");
		queryBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//if the button is clicked, load the queryScreen
				primaryStage.setScene(queryScreen);
			}
		});
		//creates an back shadow effect if the mouse is over the button
		queryBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn.setEffect(shadow);
		});
		queryBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn.setEffect(null);
		});
		
		//adds the Query Button to the homeLayout and formats it
		queryBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		queryBtn.setTranslateY(-100);
		queryBtn.setTranslateX(100);
 		queryBtn.setMinSize(300, 150);
 		queryBtn.setMaxSize(300, 150);
		homeLayout.getChildren().add(queryBtn);
		homeLayout.setAlignment(queryBtn, Pos.BOTTOM_LEFT);
		primaryStage.setScene(homeScreen);
		
		
		
		//////////////////////////////////////////
		////////////////// CSS ///////////////////
		//////////////////////////////////////////
		
		//adds the screens to the style sheet
		homeScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		foodScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		queryScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		addScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		mealScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		chooseScreen.getStylesheets().add(getClass().getResource("styleFile.css").toExternalForm());

		
		
		
		///////////////////////////////////////////////
		/////////////////// END ///////////////////////
		///////////////////////////////////////////////
		
		primaryStage.show();
		
	}
	
	
	/**
     * formats and makes the tool tip
     * 
     * @param final Node node
     * @param final Tooltip tooltip
     */
	public static void bindTooltip(final Node node, final Tooltip tooltip){
		  
		//if the mouse is over the button, the tool tip appeares
			node.setOnMouseMoved(new EventHandler<MouseEvent>(){
		      @Override  
		      public void handle(MouseEvent event) {
		         // +15 moves the tooltip 15 pixels below the mouse cursor;
		         tooltip.show(node, event.getScreenX(), event.getScreenY() + 15);
		      }
		   });  
			
	    //when the mouse moves outside the button, hide the tool tip
		   node.setOnMouseExited(new EventHandler<MouseEvent>(){
		      @Override
		      public void handle(MouseEvent event){
		         tooltip.hide();
		      }
		   });
		}
}
