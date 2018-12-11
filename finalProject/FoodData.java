/**
 * Filename:   FoodData.java
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
package finalProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @authors sapan (sapan@cs.wisc.edu), Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor for the FoodData class
     */
    public FoodData() {
    	//initializes all the fields
        foodItemList = new ArrayList<>();
        indexes = new HashMap<String, BPTree<Double,FoodItem>>();
        
    // creates instances of the BPTree for the nutrients
        BPTree<Double, FoodItem> cals = new BPTree<>(6);
        BPTree<Double, FoodItem> fat = new BPTree<>(6);
        BPTree<Double, FoodItem> carb = new BPTree<>(6);
        BPTree<Double, FoodItem> fib = new BPTree<>(6);
        BPTree<Double, FoodItem> pro = new BPTree<>(6);
        
    //adds the nutrients to the HashMap
        indexes.put("calories", cals);
        indexes.put("fat", fat);
        indexes.put("carbohydrate", carb);
        indexes.put("fiber", fib);
        indexes.put("protein", pro);
    }
    
    
    /**
     * This method loads the data in from a .csv file
     * @param String filePath
     */
    @Override
    public void loadFoodItems(String filePath) {
        Path pathToFile = Paths.get(filePath);
        //create a buffered reader using try with resources
        try{
        	BufferedReader reader = Files.newBufferedReader(pathToFile);	
        	//read the first line of the file
        		String line = reader.readLine();
        		
        	//read all lines of the file
        		while(line != null) {
        			//split the string in to an array of values using the comma as a delimiter
        			String[] foodArray = line.split(",", -1);
        			
        			//breaks if the ID is empty
        			if(foodArray[0].compareTo("") == 0)
        				break;
        			if (foodArray.length != 12)
        				continue;
        			
        			//creates a food item with the passed in name and ID
        			FoodItem food = new FoodItem(foodArray[0], foodArray[1]);
        			
        			//add the nutrients to the food object and checks to make sure the line is valid
        			if (!foodArray[2].equals("calories"))
        				continue;
        			food.addNutrient(foodArray[2], Double.parseDouble(foodArray[3])); //calories
        			if (!foodArray[4].equals("fat"))
        				continue;
        			food.addNutrient(foodArray[4], Double.parseDouble(foodArray[5])); //fat
        			if (!foodArray[6].equals("carbohydrate"))
        				continue;
        			food.addNutrient(foodArray[6], Double.parseDouble(foodArray[7])); //carb
        			if (!foodArray[8].equals("fiber"))
        				continue;
        			food.addNutrient(foodArray[8], Double.parseDouble(foodArray[9])); //fiber
        			if (!foodArray[10].equals("protein"))
        				continue;
        			food.addNutrient(foodArray[10], Double.parseDouble(foodArray[11])); //protein  
        			
        			//adds the nutrients to the HashMap and updates foodItemList
        			indexes.get("calories").insert(Double.parseDouble(foodArray[3]), food);
        			indexes.get("fat").insert(Double.parseDouble(foodArray[5]), food);
        			indexes.get("carbohydrate").insert(Double.parseDouble(foodArray[7]), food);
        			indexes.get("fiber").insert(Double.parseDouble(foodArray[9]), food);
        			indexes.get("protein").insert(Double.parseDouble(foodArray[11]), food);
        			
        			foodItemList.add(food);
        			line = reader.readLine();      			
        		}
        	
        } catch (Exception e) {
			e.printStackTrace();
		}
    }


    /**
     *  Gets all the food items that have name containing the substring.
     * @param String substring to filter by
     * @return List<FoodItem> filteredItems
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
    		//create a list object to store the filtered items
        List<FoodItem> filteredItems = new ArrayList<FoodItem>();
        
        //for loop to parse through all the food objects 
        for(int i = 0; i<foodItemList.size(); i++ ) {
        		FoodItem food = foodItemList.get(i);
        		//if the food's name contains the substring, it is added to the filtered list
        		if(food.getName().toLowerCase().contains(substring.toLowerCase())) {
        			filteredItems.add(food);
        		}
        }
        return filteredItems;
       
    }

    /**
     * Gets all the food items that fulfill ALL the provided rules
     * @param List<String> rules for filtering
     * @return List<FoodItem> finalItems
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	//fields
    	List<FoodItem> filteredItems = new ArrayList<FoodItem>();
    	List<FoodItem> finalItems = new ArrayList<FoodItem>();
    	
    	//for loop to parse through every rule and split them in to variables to make it comparable
    	for(int i = 0; i < rules.size(); i++) {
    		String rule = rules.get(i);
    		String[] ruleSplit = rule.split(" ");
    		String nutrient = ruleSplit[0];
    		String comparator = ruleSplit[1];
    		Double value = Double.parseDouble(ruleSplit[2]);
    		
    		filteredItems.addAll(indexes.get(nutrient).rangeSearch(value, comparator));
    	}
    	
    	//if there is only one rule, return the filteredItems we recieved from the rangeSearch
    		if(rules.size() == 1) {
			return filteredItems;
		}
	
    //parse through filteredItems list
		for(int f = 0; f<filteredItems.size(); f++) {
			int count = 0;
    			for(int y = f+1; y<filteredItems.size(); y++) {
    				//every time a foodItems name appears in the filtered list it's count is increased
    				if(filteredItems.get(f).getID().equals(filteredItems.get(y).getID())){
					count ++;				
    				}
    			}
    	 //if the count for a food matches the number of rules, indicating every rule was passed, it is added to the final output list.
    			if(count == rules.size()-1) {
    				finalItems.add(filteredItems.get(f));
    			}
			
		}
         return finalItems;   
}

    /**
     * Adds foodItem to the loaded data
     * @param FoodItem foodItem
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
    	   foodItemList.add(foodItem);
    	   
    	   //add the nutrients to the HashMaps
        indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
   		indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
   		indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
   		indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
   		indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    
    /**
     * Gets the list of all food items.
     * @return list of FoodItem
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


    /**
     * Save the list of food items in ascending order by name
     * @param filename name of the file where the data needs to be saved 
     */
	@Override
	public void saveFoodItems(String filename) {
		//fields
		List<String[]> temp = new ArrayList<>(); 
		List<String[]> output = new ArrayList<>();
		List<String> sorter = new ArrayList<>();
		
		//loop through foodItemList
		for(int i = 0; i<foodItemList.size();i++) {
			//create a String array to store the food name, ID and nutrients
			String[] object = new String[3];
			String name = foodItemList.get(i).getName();
			String ID = foodItemList.get(i).getID();
			object[0] = ID;
			object[1] = name;
			object[2] = foodItemList.get(i).getNutrients().toString();

			temp.add(object);
			sorter.add(name.toLowerCase());
		}
		
		//sort the names of the food items
		Collections.sort(sorter);
		
		for(int i = 0; i<foodItemList.size(); i++) {
			String name = sorter.get(i);
			for(int j = 0; j<temp.size();j++) {
				//matchup the sorted names with their ID and nutrients and add it to an output
				if(name.toLowerCase().equals(temp.get(j)[1].toLowerCase())) {
					String[] object = new String[3];
					object[0] = temp.get(j)[0];
					object[1] = name;
					object[2] = temp.get(j)[2];
					output.add(object);
					//remove the object once added to handle duplicates
					temp.remove(j);
					break;
				}			
			}
		}

		try {
			//write the foodItems to a file
			FileWriter writer = new FileWriter(filename);
			for(String[] food: output) {
				writer.write(food[0] + "," + food[1] + "," + food[2]);
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
