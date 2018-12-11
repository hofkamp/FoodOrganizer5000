/**
 * Filename:   foodTableItem.java
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

import finalProject.FoodItem;

/**
 * This class represents a food item in the table with all its properties.
 * 
 * @authors Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
 */
public class foodTableItem {
	
	//fields
    private FoodItem food;
	private String name;
	private String id;
	private String calories;
	private String carbohydrate;
	private String fat;
	private String fiber;
	private String protein; 
	
	
	/**
     * Constructor for the foodTableItem class
     * initializes all variables to start with
     * @param FoodItem food
     */
	public foodTableItem(FoodItem food) {
	    this.food = food;
		this.name = food.getName();
		this.id = food.getID();
		this.calories = food.getNutrientValue("calories") + "";
		this.carbohydrate = food.getNutrientValue("carbohydrate") + "";
		this.fat = food.getNutrientValue("fat") + "";
		this.fiber = food.getNutrientValue("fiber") + "";
		this.protein = food.getNutrientValue("protein") + "";
		
	}
	
	public FoodItem getFood() {
	    return food;
	}

	/**
     * Gets the name of the food in the table
     * @return String name
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the food in the table
     * @param String name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the ID of the food in the table
     * @return String id
     */
	public String getId() {
		return id;
	}

	/**
     * Sets the ID of the food in the table
     * @param String id
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Gets the calories of the food in the table  as a string
     * @return String calories
     */
	public String getCalories() {
		return calories;
	}

	/**
     * Sets the calories of the food in the table as a string
     * @param String calories
     */
	public void setCalories(String calories) {
		this.calories = calories;
	}

	/**
     * Gets the carbohydrates of the food in the table as a string
     * @return String carbohydrate
     */
	public String getCarbohydrate() {
		return carbohydrate;
	}

	/**
     * Sets the carbohydrates of the food in the table as a string
     * @param String carbohydrate
     */
	public void setCarbohydrate(String carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	/**
     * Gets the fat of the food in the table as a string
     * @return String fat
     */
	public String getFat() {
		return fat;
	}

	/**
     * Sets the fat of the food in the table as a string
     * @param String fat
     */
	public void setFat(String fat) {
		this.fat = fat;
	}

	/**
     * Gets the fiber of the food in the table as a string
     * @return String fiber
     */
	public String getFiber() {
		return fiber;
	}

	/**
     * Sets the fiber of the food in the table as a string
     * @param String fiber
     */
	public void setFiber(String fiber) {
		this.fiber = fiber;
	}

	/**
     * Gets the protein of the food in the table as a string
     * @return String protein
     */
	public String getProtein() {
		return protein;
	}

	/**
     * Sets the protein of the food in the table as a string
     * @param String protein
     */
	public void setProtein(String protein) {
		this.protein = protein;
	}
}
