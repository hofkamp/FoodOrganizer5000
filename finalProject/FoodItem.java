/**
 * Filename:   FoodItem.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a food item with all its properties.
 * 
 * @authors Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
 */
public class FoodItem {
    // The name of the food item.
    private String name;

    // The id of the food item.
    private String id;

    // Map of nutrients and value.
    private HashMap<String, Double> nutrients;
    
     
    /**
     * Constructor for the FoodItem class
     * @param name name of the food item
     * @param id unique id of the food item 
     */
	public FoodItem (String id, String name) {
		this.name = name;
        this.id = id;
	    nutrients = new HashMap<String, Double>();
	}
     
    
    /**
     * Gets the name of the food item
     * 
     * @return name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique id of the food item
     * 
     * @return id of the food item
     */
    public String getID() {
        return id;
    }
    
    /**
     * Gets the nutrients of the food item
     * 
     * @return nutrients of the food item
     */
    public HashMap<String, Double> getNutrients() {
        return nutrients;
    }

    /**
     * Adds a nutrient and its value to the food item 
     * If nutrient already exists, updates its value.
     * @param String name of the nutrient
     * @param double value of the nutrient
     */
    public void addNutrient(String name, double value) {
        nutrients.put(name, value);
    }

    /**
     * Returns the value of the given nutrient for the food item. 
     * If not present, it returns 0.
     * @param String name of nutrient
     * @return the double value of the nutrient for the food item
     */
    public double getNutrientValue(String name) {
        return nutrients.get(name);
    }
    
    /**
     * toString method
     */
    @Override
    public String toString() {
        return this.name;
    }
}
