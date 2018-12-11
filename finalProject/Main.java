package finalProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.*;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;

enum NUTRIENTS {Cal,Carb,Fat,Pro,Fib};

public class Main extends Application {
	
    private String filePath = "foodItems.csv";
    private FoodData foodData = new FoodData();
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private List<foodTableItem> tableItems = new ArrayList<foodTableItem>();
    private List<MealItem> mealItems = new ArrayList<MealItem>();
    private MealItem chosenMeal = null;
    private boolean queryOn = false;
    private FoodItem temp;
    private ObservableList<MealItem> mealArray =  FXCollections.observableArrayList();
    private File selectedFile;
    private MealItem tempMeal;

    
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Food Organizer 5000");
		BorderPane borderPane = new BorderPane();
		DropShadow shadow = new DropShadow();
		StackPane homeLayout = new StackPane();
		StackPane foodLayout = new StackPane();
		StackPane mealLayout = new StackPane();
		StackPane queryLayout = new StackPane();
		StackPane addLayout = new StackPane();
		StackPane chooseLayout = new StackPane();
		Scene homeScreen = new Scene(homeLayout, 900, 600);
		Scene foodScreen = new Scene(foodLayout, 900, 600);
		Scene mealScreen = new Scene(mealLayout, 900, 600);
		Scene queryScreen = new Scene(queryLayout, 900, 600);
		Scene addScreen = new Scene(addLayout, 900, 600);
		Scene chooseScreen = new Scene(chooseLayout,900,600);
		
		ObservableList<foodTableItem> foodList = FXCollections.observableList(tableItems);
		

		
		
		
		
		//////////////////// MEAL LIST LAYOUT////////////////////
		Label text = new Label("Selected Item");
		text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
 		text.setTranslateX(-130);
 		text.setTranslateY(-140);
		Label mealN = new Label("Name:");
		mealN.setFont(Font.font(25));
		mealN.setTranslateX(-240);
 		mealN.setTranslateY(-100);
		Label mealIn = new Label("Ingredients:");
		mealIn.setFont(Font.font(25));
		mealIn.setTranslateX(-240);
 		mealIn.setTranslateY(-70);
		Label mealCb = new Label("Carbohydrates:");
		mealCb.setFont(Font.font(25));
		mealCb.setTranslateX(-240);
 		mealCb.setTranslateY(10);
		Label mealCa = new Label("Calories:");
		mealCa.setFont(Font.font(25));
		mealCa.setTranslateX(-240);
 		mealCa.setTranslateY(50);
		Label mealPr = new Label("Protein:");
		mealPr.setFont(Font.font(25));
		mealPr.setTranslateX(-240);
 		mealPr.setTranslateY(90);
		Label mealFa = new Label("Fat:");
		mealFa.setFont(Font.font(25));
		mealFa.setTranslateX(-240);
 		mealFa.setTranslateY(130);
		Label mealFi = new Label("Fiber:");
		mealFi.setFont(Font.font(25));
		mealFi.setTranslateX(-240);
 		mealFi.setTranslateY(170);
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
		
		Label tempName = new Label();
		tempName.setFont(Font.font(25));
		tempName.setTranslateX(-135);
 		tempName.setTranslateY(-100);
		ListView<String> ingrList = new ListView<>();
		ingrList.setMaxSize(200, 75);
 		ingrList.setTranslateX(-30);
 		ingrList.setTranslateY(-40);
		Label tempCal = new Label();
		tempCal.setFont(Font.font(25));
 		tempCal.setTranslateX(-135);
 		tempCal.setTranslateY(50);
		Label tempCarb = new Label();
		tempCarb.setFont(Font.font(25));
		tempCarb.setTranslateX(-135);
 		tempCarb.setTranslateY(13);
		Label tempPro = new Label();
		tempPro.setFont(Font.font(25));
		tempPro.setTranslateX(-135);
 		tempPro.setTranslateY(90);
		Label tempFat = new Label();
		tempFat.setFont(Font.font(25));
		tempFat.setTranslateX(-135);
 		tempFat.setTranslateY(130);
		Label tempFib = new Label();
		tempFib.setFont(Font.font(25));
		tempFib.setTranslateX(-135);
 		tempFib.setTranslateY(170);
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
		
		TableView table = new TableView();
		table.setEditable(true);
		TableColumn mealColumn = new TableColumn("Meals");
		mealColumn.setMinWidth(300);
		mealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		table.getColumns().add(mealColumn);
		table.setTranslateX(75);
		table.setTranslateY(150);
		table.setMaxSize(300, 400);
		table.setItems(mealArray);
		table.setOnMouseClicked((new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 tempMeal = (MealItem)table.getSelectionModel().getSelectedItem();
	 			tempName.setText(tempMeal.getName());
	 			tempCal.setText(tempMeal.getCal() +"");
	 			tempCarb.setText(tempMeal.getCarb() +"");
	 			tempPro.setText(tempMeal.getPro() +"");
	 			tempFat.setText(tempMeal.getFat() +"");
	 			tempFib.setText(tempMeal.getFib() +"");
	 			ObservableList tempList = FXCollections.observableArrayList(tempMeal.getIngredients());
	 			ingrList.setItems(tempList);
	 			//here we are going to have a global variable that will be a temp variable
	 			//that will be changed when it is clicked on. 
	 			//that will be used to call data from MealItem object or some shit
			}
		}));
		mealLayout.getChildren().add(table);
		mealLayout.setAlignment(table, Pos.TOP_LEFT);
		
		
		// meal Button
		Button foodBtn1 = new Button("Food List");
		foodBtn1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(foodScreen);
			}
		});
		foodBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			foodBtn1.setEffect(shadow);
		});
		foodBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			foodBtn1.setEffect(null);
		});
		foodBtn1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		foodBtn1.setTranslateX(-10);
		foodBtn1.setTranslateY(10);
		foodBtn1.setMinSize(150, 150 / 2);
		foodBtn1.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(foodBtn1);
		mealLayout.setAlignment(foodBtn1, Pos.TOP_RIGHT);

		// query button
		Button queryBtn2 = new Button("Query");
		queryBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(queryScreen);
			}
		});
		queryBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn2.setEffect(shadow);
		});
		queryBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn2.setEffect(null);
		});
		queryBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		queryBtn2.setTranslateX(-165);
		queryBtn2.setTranslateY(10);
		queryBtn2.setMinSize(150, 150 / 2);
		queryBtn2.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(queryBtn2);
		mealLayout.setAlignment(queryBtn2, Pos.TOP_RIGHT);

		// add food button
		Button addBtn2 = new Button("Add Food");
		addBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(addScreen);
			}
		});
		addBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn2.setEffect(shadow);
		});
		addBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn2.setEffect(null);
		});
		addBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		addBtn2.setTranslateX(-320);
		addBtn2.setTranslateY(10);
		addBtn2.setMinSize(150, 150 / 2);
		addBtn2.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(addBtn2);
		mealLayout.setAlignment(addBtn2, Pos.TOP_RIGHT);

		// home button
		Button homeBtn1 = new Button("Go Back");
		homeBtn1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(homeScreen);
			}
		});
		homeBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			homeBtn1.setEffect(shadow);
		});
		homeBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			homeBtn1.setEffect(null);
		});
		homeBtn1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		homeBtn1.setTranslateX(-475);
		homeBtn1.setTranslateY(10);
		homeBtn1.setMinSize(150, 150 / 2);
		homeBtn1.setMaxSize(150, 150 / 2);
		mealLayout.getChildren().add(homeBtn1);
		mealLayout.setAlignment(homeBtn1, Pos.TOP_RIGHT);

		// title
		Label title2 = new Label("Meal List");
		title2.setFont(Font.font("Cambria", 50));
		title2.setTranslateX(50);
		title2.setTranslateY(20);
		mealLayout.getChildren().add(title2);
		mealLayout.setAlignment(title2, Pos.TOP_LEFT);
		
		Button chooseMeal1 = new Button("Create Meal");
		chooseMeal1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
		chooseMeal1.setMinSize(150, 50);
		chooseMeal1.setTranslateX(-45);
        chooseMeal1.setTranslateY(-50);  
        mealLayout.getChildren().add(chooseMeal1);       
        mealLayout.setAlignment(chooseMeal1, Pos.BOTTOM_RIGHT);
        chooseMeal1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                primaryStage.setScene(chooseScreen);
            }
        });
        chooseMeal1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            chooseMeal1.setEffect(shadow);
        });
        chooseMeal1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            chooseMeal1.setEffect(null);
        });
		
        Button clearMeal = new Button("Clear Meal");
		clearMeal.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
		clearMeal.setMinSize(150, 50);
		clearMeal.setTranslateX(-300);
        clearMeal.setTranslateY(-50);  
        mealLayout.getChildren().add(clearMeal);       
        mealLayout.setAlignment(clearMeal, Pos.BOTTOM_RIGHT);
        clearMeal.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
            	System.out.println(mealArray.toString());
            	for(int i = 0; i < mealArray.size(); i++) {
            		if(tempMeal.getName().compareTo(mealArray.get(i).getName()) == 0)
            			mealArray.remove(i);
            	}
            	mealArray.remove(tempMeal.getName());
            	System.out.println(mealArray.toString());
            	primaryStage.setScene(mealScreen);
            }
        });
        clearMeal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            clearMeal.setEffect(shadow);
        });
        clearMeal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            clearMeal.setEffect(null);
        });
	
		////////////////////FOOD LIST LAYOUT////////////////////
		
		TableView table2 = new TableView();
		table2.setEditable(true);

 		table2.getSelectionModel().setSelectionMode(
 			    SelectionMode.MULTIPLE
 			);
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
		
		foodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		//foodCol.setCellFactory(new Callback<Table>);
		calCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
		//calCol.setCellFactory(new Callback<TableColumn<BPTree,  Double>, TableCell<BPTree, Double>>());
		//TableCell<BPTree, Double> testCell = new TableCell<BPTree, Double>();
		carbCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
		fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
		fibCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));
		proCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
		table2.setItems(foodList);
		table2.getSortOrder().add(foodCol);
		
		//table2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		foodLayout.getChildren().add(table2);
		foodLayout.setAlignment(table2, Pos.TOP_LEFT);
		
		// Save button 
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
               foodData.saveFoodItems("savedFile.txt");
            }
        });
		save.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		        save.setEffect(shadow);
		    });
		save.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		        save.setEffect(null);
		    });
		save.setFont(Font.font("Arial", FontWeight.BOLD,20));
		save.setTranslateX(-25);
		save.setTranslateY(-125);
		save.setMinSize(160, 50);
		//save.setMaxSize(100, 50); 
		foodLayout.getChildren().add(save); 
		foodLayout.setAlignment(save, Pos.CENTER_RIGHT);
		
        Label addedToMealWarning = new Label("Successfully added to meal");
        addedToMealWarning.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addedToMealWarning.setTranslateX(0);
        addedToMealWarning.setTranslateY(-180);
        foodLayout.getChildren().add(addedToMealWarning);
        foodLayout.setAlignment(addedToMealWarning, Pos.CENTER);
        fadeOut.setNode(addedToMealWarning);
        addedToMealWarning.setVisible(false);
		
		// Enter button
		Button enter2 = new Button("Add to Meal");
		enter2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
            	FoodItem chosenFood = null; //foodList.((foodTableItem)table2.getSelectionModel().getSelectedItem()).getId();
                if (table2.getSelectionModel().getSelectedItem() != null)
                    chosenFood = (FoodItem)((foodTableItem)table2.getSelectionModel().getSelectedItem()).getFood();
                if(chosenMeal != null && chosenFood != null) {
                    	 chosenMeal.getIngredients().add(chosenFood);
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
		enter2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		        enter2.setEffect(shadow);
		    });
		enter2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		        enter2.setEffect(null);
		    });
		enter2.setFont(Font.font("Arial", FontWeight.BOLD,20));
		enter2.setTranslateX(-25);
		enter2.setTranslateY(200);
		enter2.setMinSize(160, 50);
		//enter2.setMaxSize(100, 50); 
		foodLayout.getChildren().add(enter2); 
		foodLayout.setAlignment(enter2, Pos.CENTER_RIGHT);
		
		//filter status
				Label filterStatus = new Label();
				filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
				    String s = " ";
				    if(queryOn)
				        s = "Filter Status: ON ";
				    else
				        s = "Filter Status: OFF";
				    return s;
				}));
				filterStatus.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				filterStatus.setTranslateX(-25);
				filterStatus.setTranslateY(-5);
				foodLayout.getChildren().add(filterStatus);
				foodLayout.setAlignment(filterStatus, Pos.CENTER_RIGHT);
		
		// Unfilter button
		Button unfilter = new Button ("Unfilter");
		unfilter.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
              
            	queryOn = false;
               
                filterStatus.textProperty().bind(Bindings.createStringBinding(() -> {
				    String s = " ";
				    if(queryOn)
				        s = "Filter Status: ON ";
				    else
				        s = "Filter Status: OFF";
				    return s;
				}));
            }
        });
		unfilter.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    unfilter.setEffect(shadow);
		});
		unfilter.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    unfilter.setEffect(null);
		});
		unfilter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		unfilter.setTranslateX(-25);
		unfilter.setTranslateY(-50);
		unfilter.setMinSize(160, 50);
		//unfilter.setMaxSize(100, 50);
		foodLayout.getChildren().add(unfilter);
		foodLayout.setAlignment(unfilter, Pos.CENTER_RIGHT);
		//filter status label
		Label filterBox = new Label("Filter Status:");
		filterBox.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		filterBox.setTranslateX(-25);
		filterBox.setTranslateY(-15);
		//foodLayout.getChildren().add(filterBox);
		//foodLayout.setAlignment(filterBox, Pos.CENTER_RIGHT);
			
		
//		// addToMeal button
//        Button addToMeal = new Button("Add to meal");
//        addToMeal.setFont(Font.font("Arial", FontWeight.BOLD,20));
//        addToMeal.setTranslateX(-600);
//        addToMeal.setTranslateY(-15);
//        addToMeal.setMinSize(50, 20);
//        addToMeal.setMaxSize(160, 50);     
//        foodLayout.getChildren().add(addToMeal);       
//        foodLayout.setAlignment(addToMeal, Pos.BOTTOM_RIGHT);
//        addToMeal.setOnAction(new EventHandler<ActionEvent>(){
//            public void handle(ActionEvent event) {
//                FoodItem foodForMeal = (FoodItem)table2.getSelectionModel().getSelectedItem();
//                if (foodForMeal != null) {
//                    System.out.println(foodForMeal.getName());
//                    //chosenMeal.getIngredients().add(foodForMeal);
//                }
//            }
//        });
		
		 Label currentMealText = new Label("Adding to This Meal:");
	        currentMealText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	        currentMealText.setTranslateX(-15);
	        currentMealText.setTranslateY(-180);
	        foodLayout.getChildren().add(currentMealText);
	        foodLayout.setAlignment(currentMealText, Pos.BOTTOM_RIGHT);

            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setCycleCount(1);
            fadeOut.setAutoReverse(false);
	        
	    Label currentMeal = new Label();
	    //if(chosenMeal == null) 
	    	currentMeal.setText("none");
	    //else currentMeal.setText(chosenMeal.getName());
	        currentMeal.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	        currentMeal.setTranslateX(-25);
	        currentMeal.setTranslateY(-155);
	        currentMeal.setMaxWidth(160);
	        foodLayout.getChildren().add(currentMeal);
	        foodLayout.setAlignment(currentMeal, Pos.BOTTOM_RIGHT);
        
        // chooseMeal button
        Button chooseMeal = new Button("Select/Add a Meal");
        chooseMeal.setFont(Font.font("Arial", FontWeight.BOLD,15));
        chooseMeal.setTranslateX(-25);
        chooseMeal.setTranslateY(-220);
        chooseMeal.setMinSize(50, 20);
        chooseMeal.setMaxSize(160, 50);     
        foodLayout.getChildren().add(chooseMeal);       
        foodLayout.setAlignment(chooseMeal, Pos.BOTTOM_RIGHT);
        chooseMeal.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                primaryStage.setScene(chooseScreen);
            }
        });
        chooseMeal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            chooseMeal.setEffect(shadow);
        });
        chooseMeal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            chooseMeal.setEffect(null);
        });

		//meal Button
		Button mealBtn1 = new Button("Meal List");
		mealBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				primaryStage.setScene(mealScreen);
			}
		});
		mealBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			mealBtn1.setEffect(shadow);
		});
		mealBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			mealBtn1.setEffect(null);
		});
		mealBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		mealBtn1.setTranslateX(-10);
		mealBtn1.setTranslateY(10);
		mealBtn1.setMinSize(150, 150/2);
		mealBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(mealBtn1);
		foodLayout.setAlignment(mealBtn1, Pos.TOP_RIGHT);
		
		
		//query button
		Button queryBtn1 = new Button("Query");
		queryBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				primaryStage.setScene(queryScreen);
			}
		});
		queryBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn1.setEffect(shadow);
		});
		queryBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn1.setEffect(null);
		});
		queryBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		queryBtn1.setTranslateX(-165);
		queryBtn1.setTranslateY(10);
		queryBtn1.setMinSize(150, 150/2);
		queryBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(queryBtn1);
		foodLayout.setAlignment(queryBtn1, Pos.TOP_RIGHT);
		
		//add food button
		Button addBtn1 = new Button("Add Food");
		addBtn1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				primaryStage.setScene(addScreen);
			}
		});
		addBtn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn1.setEffect(shadow);
		});
		addBtn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn1.setEffect(null);
		});
		addBtn1.setFont(Font.font("Arial", FontWeight.BOLD,20));
		addBtn1.setTranslateX(-320);
		addBtn1.setTranslateY(10);
		addBtn1.setMinSize(150, 150/2);
		addBtn1.setMaxSize(150, 150/2);
		foodLayout.getChildren().add(addBtn1);
		foodLayout.setAlignment(addBtn1, Pos.TOP_RIGHT);
		
		//home button
		Button homeBtn = new Button("Go Back");
		homeBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				primaryStage.setScene(homeScreen);
			}
		});
		homeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			homeBtn.setEffect(shadow);
		});
		homeBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			homeBtn.setEffect(null);
		});
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
		//foodScreen = new Scene(foodLayout, 900, 600);

		////////////////////ADD FOOD LAYOUT/////////////////////
		//file name input
		Button fileButton = new Button("Choose a file...");
		fileButton.setTranslateY(150);
		fileButton.setMinSize(300, 75);
		addLayout.getChildren().addAll(fileButton);
		fileButton.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
		fileButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			fileButton.setEffect(shadow);
		});
		fileButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			fileButton.setEffect(null);
		});

		addLayout.setAlignment(fileButton, Pos.TOP_CENTER);
		//random OR text box for aesthetic
		Label ORText = new Label("OR");
		ORText.setFont(Font.font("Arial", 40));
		ORText.setTranslateY(-40);
		addLayout.getChildren().add(ORText);
		addLayout.setAlignment(ORText, Pos.CENTER);
		//food name input
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
		//carb number input
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
		//calorie num input
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
		//fat num input
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
		//protein num input
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
		//fiber num input
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
	                	FileChooser fileChooser = new FileChooser();
	            		File selectedFile = fileChooser.showOpenDialog(null);
	            		
	            		if (selectedFile != null) {
	            		List<FoodItem> loadedFoods = new ArrayList<FoodItem>();
					data.loadFoodItems(selectedFile.getAbsolutePath());
					loadedFoods = data.getAllFoodItems();

					for(int i = 0; i<loadedFoods.size(); i++) {
					foodTableItem tableFood = new foodTableItem(loadedFoods.get(i));
					foodItems.add(loadedFoods.get(i));
					tableItems.add(tableFood);
					}
	            		}
					table2.getSortOrder().add(foodCol);
					carbF.clear();  
					calF.clear(); 
					fatF.clear(); 
					proF.clear(); 
					fibF.clear(); 
					foodF.clear(); 
					primaryStage.setScene(foodScreen);
	            }});
		//enter button
		Button entrBtn = new Button("ENTER");
		entrBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				String nameT = foodF.getText();
				Double carbsT = Double.parseDouble(carbF.getText());
				Double caloriesT = Double.parseDouble(calF.getText());
				Double fatT = Double.parseDouble(fatF.getText());
				Double proteinT = Double.parseDouble(proF.getText());
				Double fiberT = Double.parseDouble(fibF.getText());
				FoodData data = new FoodData();

				if(!nameT.isEmpty()) {
					FoodItem food = new FoodItem(nameT.hashCode()+ "", nameT);
					food.addNutrient("carbohydrate", carbsT);
					food.addNutrient("calories", caloriesT);
					food.addNutrient("fat", fatT);
					food.addNutrient("protein", proteinT);
					food.addNutrient("fiber", fiberT);	
					data.addFoodItem(food);
					foodItems.add(food);
					foodTableItem tableFood = new foodTableItem(food);
					tableItems.add(tableFood);
				}
								
				table2.getSortOrder().add(foodCol);
				carbF.clear();  
				calF.clear(); 
				fatF.clear(); 
				proF.clear(); 
				fibF.clear(); 
				foodF.clear(); 
				primaryStage.setScene(foodScreen);
			}
		});
		entrBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) ->{
			entrBtn.setEffect(shadow);
		});
		entrBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) ->{
			entrBtn.setEffect(null);
		});
		entrBtn.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
		entrBtn.setMinSize(300, 75);
		entrBtn.setTranslateY(-25);
		addLayout.getChildren().add(entrBtn);
		addLayout.setAlignment(entrBtn, Pos.BOTTOM_CENTER);
		//food list button
		Button foodBtn2 = new Button("Food List");
		foodBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(foodScreen);
			}	
		});
		foodBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			foodBtn2.setEffect(shadow);
		});
		foodBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			foodBtn2.setEffect(null);
		});
		foodBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		foodBtn2.setTranslateX(-10);
		foodBtn2.setTranslateY(10);
		foodBtn2.setMinSize(150, 150 / 2);
		foodBtn2.setMaxSize(150, 150 / 2);
		addLayout.getChildren().add(foodBtn2);
		addLayout.setAlignment(foodBtn2, Pos.TOP_RIGHT);
		// query button
		Button queryBtn3 = new Button("Query");
		queryBtn3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(queryScreen);
			}
		});
		queryBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn3.setEffect(shadow);
		});
		queryBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn3.setEffect(null);
		});
		queryBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		queryBtn3.setTranslateX(-165);
		queryBtn3.setTranslateY(10);
		queryBtn3.setMinSize(150, 150 / 2);
		queryBtn3.setMaxSize(150, 150 / 2);
		addLayout.getChildren().add(queryBtn3);
		addLayout.setAlignment(queryBtn3, Pos.TOP_RIGHT);
		// meal list button
		Button mealBtn3 = new Button("Meal List");
		mealBtn3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(mealScreen);
			}
		});
		mealBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			mealBtn3.setEffect(shadow);
		});
		mealBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			mealBtn3.setEffect(null);
		});
		mealBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		mealBtn3.setTranslateX(-320);
		mealBtn3.setTranslateY(10);
		mealBtn3.setMinSize(150, 150 / 2);
		mealBtn3.setMaxSize(150, 150 / 2);
		addLayout.getChildren().add(mealBtn3);
		addLayout.setAlignment(mealBtn3, Pos.TOP_RIGHT);
		// home button
		Button homeBtn2 = new Button("Go Back");
		homeBtn2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(homeScreen);
			}
		});
		homeBtn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			homeBtn2.setEffect(shadow);
		});
		homeBtn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			homeBtn2.setEffect(null);
		});
		homeBtn2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		homeBtn2.setTranslateX(-475);
		homeBtn2.setTranslateY(10);
		homeBtn2.setMinSize(150, 150 / 2);
		homeBtn2.setMaxSize(150, 150 / 2);
		addLayout.getChildren().add(homeBtn2);
		addLayout.setAlignment(homeBtn2, Pos.TOP_RIGHT);
		// title
		Label title3 = new Label("Add Food");
		title3.setFont(Font.font("Cambria", 50));
		title3.setTranslateX(50);
		title3.setTranslateY(20);
		addLayout.getChildren().add(title3);
		addLayout.setAlignment(title3, Pos.TOP_LEFT);	

		//////////////////// QUERY LAYOUT////////////////////////

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

		TextField foodField = new TextField();
		foodField.setMaxSize(300, 30);
		foodField.setTranslateY(-90);
		foodField.setTranslateX(-40);
		queryLayout.getChildren().add(foodField);
		queryLayout.setAlignment(foodField, Pos.CENTER_RIGHT);

		// Calorie Label//
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

		// Protein Label//
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

		// Carb Label//
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

		// Fat Label//
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

		// Fiber Label//
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

		// Enter button
		Button enter = new Button("Enter");
		enter.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		if (foodField.getText().equals("")) {
			queryOn = true;
		List<String> finalInputList = new ArrayList<String>();

		if (!String.valueOf(calorieF.getText()).equals("")) {
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

		if (!String.valueOf(proteinF.getText()).equals("")) {
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

		if (!String.valueOf(carF.getText()).equals("")) {
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

		if (!String.valueOf(fatF1.getText()).equals("")) {
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

		if (!String.valueOf(fibF1.getText()).equals("")) {
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
		for (FoodItem food : tempFoodList) {
		foodTableItem temp = new foodTableItem(food);
		tempFoodTableItems.add(temp);
		}

		ObservableList<foodTableItem> temp = FXCollections.observableList(tempFoodTableItems);

		table2.setItems(temp);

		} else if (!foodField.getText().equals("")) {
			queryOn = true;
		// figure out how to display the updated list
		String textFieldInput = String.valueOf(foodField.getText());

		List<foodTableItem> tempFoodTableItems = new ArrayList<foodTableItem>();
		List<FoodItem> tempFoodList = foodData.filterByName(textFieldInput);
		for (FoodItem food : tempFoodList) {
		foodTableItem temp = new foodTableItem(food);
		tempFoodTableItems.add(temp);
		}

		ObservableList<foodTableItem> temp = FXCollections.observableList(tempFoodTableItems);
		

		table2.setItems(temp);
		}


		primaryStage.setScene(foodScreen);
		

		}
		});
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
		primaryStage.setScene(foodScreen);
		}
		});
		foodBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		foodBtn3.setEffect(shadow);
		});
		foodBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		foodBtn3.setEffect(null);
		});
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
		primaryStage.setScene(addScreen);
		}
		});
		addBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		addBtn3.setEffect(shadow);
		});
		addBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		addBtn3.setEffect(null);
		});
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
		primaryStage.setScene(mealScreen);
		}
		});
		mealBtn4.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		mealBtn4.setEffect(shadow);
		});
		mealBtn4.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		mealBtn4.setEffect(null);
		});
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
		primaryStage.setScene(homeScreen);
		}
		});
		homeBtn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		homeBtn3.setEffect(shadow);
		});
		homeBtn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		homeBtn3.setEffect(null);
		});
		homeBtn3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		homeBtn3.setTranslateX(-475);
		homeBtn3.setTranslateY(10);
		homeBtn3.setMinSize(150, 150 / 2);
		homeBtn3.setMaxSize(150, 150 / 2);
		queryLayout.getChildren().add(homeBtn3);
		queryLayout.setAlignment(homeBtn3, Pos.TOP_RIGHT);

		// title
		Label title4 = new Label("Query");
		title4.setFont(Font.font("Cambria", 50));
		title4.setTranslateX(50);
		title4.setTranslateY(20);

		queryLayout.getChildren().add(title4);
		queryLayout.setAlignment(title4, Pos.TOP_LEFT);
		
		////////////////////CHOOSE MEAL/////////////////////////
		TableView chooseTable = new TableView();
		chooseTable.setEditable(true);
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
			    if (event.getClickCount() == 2) {
			        chosenMeal = (MealItem)chooseTable.getSelectionModel().getSelectedItem();
			        System.out.println(chosenMeal.getName());
			        if (chosenMeal != null) {
			            currentMeal.setText(chosenMeal.getName());
			            primaryStage.setScene(foodScreen);
			        }
			    }
			}
		}));
		
		chooseTable.setItems(mealArray);
		chooseLayout.getChildren().add(chooseTable);
		
		Button continueButton = new Button("Continue with meal");
		continueButton.setFont(Font.font("Arial", FontWeight.BOLD,20));
		continueButton.setTranslateX(-190);
		continueButton.setTranslateY(225);
		continueButton.setMaxSize(230, 50);
		continueButton.setMinSize(180, 30);
		continueButton.setOnAction(new EventHandler<ActionEvent>(){
            	public void handle(ActionEvent event) {
                	chosenMeal = (MealItem)chooseTable.getSelectionModel().getSelectedItem();
                if (chosenMeal != null) {
                	currentMeal.setText(chosenMeal.getName());
                    primaryStage.setScene(foodScreen);
                }
            }
        });
		continueButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    continueButton.setEffect(shadow);
        });
		continueButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    continueButton.setEffect(null);
        });
		chooseLayout.getChildren().add(continueButton);
		
		Label addNewMeal = new Label("Add a new meal");
		addNewMeal.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		addNewMeal.setTranslateX(210);
		addNewMeal.setTranslateY(-80);
		chooseLayout.getChildren().add(addNewMeal);
		
		TextField newMealF = new TextField();
		newMealF.setTranslateX(210);
		newMealF.setTranslateY(-35);
		newMealF.setMinSize(200, 30);
		newMealF.setMaxSize(200, 30);
		chooseLayout.getChildren().add(newMealF);
		
		Button addMealButton = new Button("Add new meal");
		addMealButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		addMealButton.setTranslateX(210);
		addMealButton.setTranslateY(20);
        	addMealButton.setMaxSize(180, 50);
        	addMealButton.setMinSize(180, 30);
		chooseLayout.getChildren().add(addMealButton);
		addMealButton.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        String newMealName = newMealF.getText();
		        boolean contains = false;
		        for (Object meal : mealArray)
		            if (((MealItem)meal).getName().equals(newMealName))
		                contains = true;
		        if (!contains)
		            mealArray.add(new MealItem(newMealF.getText(),new ArrayList<FoodItem>()));
		    }
		});
		addMealButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    addMealButton.setEffect(shadow);
        });
		addMealButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    addMealButton.setEffect(null);
        });
		
		Button cancelButton = new Button("Cancel");
        	cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        	cancelButton.setTranslateX(390);
       		cancelButton.setTranslateY(-265);
        	cancelButton.setMaxSize(100, 50);
        	cancelButton.setMinSize(100, 30);
        	chooseLayout.getChildren().add(cancelButton);
        	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
                	primaryStage.setScene(foodScreen);
            	}
        });
        	cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            		cancelButton.setEffect(shadow);
        });
        	cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            		cancelButton.setEffect(null);
        });
		
		////////////////////HOME LAYOUT/////////////////////////
		//exit Button
		Button exitBtn = new Button("Quit");
		exitBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		exitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			exitBtn.setEffect(shadow);
		});
		exitBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			exitBtn.setEffect(null);
		});
		exitBtn.setFont(Font.font("Arial", FontWeight.BOLD,20));
		exitBtn.setTranslateX(-10);
		exitBtn.setTranslateY(10);
		homeLayout.getChildren().add(exitBtn);
		homeLayout.setAlignment(exitBtn, Pos.TOP_RIGHT);
		
		//title Label
		Label title = new Label("Food Organizer 5000");
		title.setFont(Font.font("Cambria", 70));
		//title.setTranslateX(50);
		title.setTranslateY(50);
		homeLayout.getChildren().add(title);
		homeLayout.setAlignment(title, Pos.TOP_CENTER);
		
		//meal List Button
		Button mealBtn = new Button("View Meal List");
		mealBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(mealScreen);
			}
		});
		mealBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			mealBtn.setEffect(shadow);
		});
		mealBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			mealBtn.setEffect(null);
		});
		mealBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		mealBtn.setTranslateX(30);
		mealBtn.setMinSize(250, 250);
		homeLayout.getChildren().add(mealBtn);
		homeLayout.setAlignment(mealBtn, Pos.CENTER_LEFT);
		
		//food List Button
		Button foodBtn = new Button("View Food List");
		foodBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(foodScreen);
			}
		});
		foodBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			foodBtn.setEffect(shadow);
		});
		foodBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			foodBtn.setEffect(null);
		});
		foodBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		//foodBtn.setTranslateX(30);
		foodBtn.setMinSize(250, 250);
		homeLayout.getChildren().add(foodBtn);
		homeLayout.setAlignment(foodBtn, Pos.CENTER);
		
		//add Food Button
		Button addBtn = new Button("Add Food Item(s)");
		addBtn.wrapTextProperty().setValue(true);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(addScreen);
			}
		});
		addBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBtn.setEffect(shadow);
		});
		addBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBtn.setEffect(null);
		});
		addBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		addBtn.setTranslateX(-30);
		addBtn.setMinSize(250, 250);
		addBtn.setMaxSize(250, 250);
		homeLayout.getChildren().add(addBtn);
		homeLayout.setAlignment(addBtn, Pos.CENTER_RIGHT);
		
		//query Button
		Button queryBtn = new Button("Search For Food");
		queryBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(queryScreen);
			}
		});
		queryBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			queryBtn.setEffect(shadow);
		});
		queryBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			queryBtn.setEffect(null);
		});
		queryBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		queryBtn.setTranslateY(-100);
		queryBtn.setMinSize(850, 50);
		homeLayout.getChildren().add(queryBtn);
		homeLayout.setAlignment(queryBtn, Pos.BOTTOM_CENTER);
		//Scene homeScreen = new Scene(homeLayout, 900, 600);
		primaryStage.setScene(homeScreen);
		
		////////////////// CSS STUFF ///////////////////
		homeScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		foodScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		queryScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		addScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		mealScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		chooseScreen.getStylesheets().add(getClass().getResource("../styleFile.css").toExternalForm());
		
		
		/////////////////END ALL THIS STUFF////////////
		primaryStage.show();
		
	}
	
	private FadeTransition fadeOut = new FadeTransition(
	        Duration.millis(2000)
	    );
}
