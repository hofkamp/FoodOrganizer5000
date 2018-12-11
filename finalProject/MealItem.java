/**
 * Filename:   MealItem.java
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

/**
 * This class represents a meal item with all its properties.
 * 
 * @authors Thomas Antonacci, Sally Gerich, Kelsey Hickok, William Hofkamp, Apostolos Velliotis
 */
public class MealItem <T> {
	//fields
	private String name;
	private int calories;
	private int carbohydrate;
	private int fat;
	private int protein;
	private int fiber;
	private ArrayList<FoodItem> ingredients = new ArrayList();
	
	
	 /**
     * Constructor
     * initializes a meal item with all fields to 0 or empty
     */
	public MealItem (String name, ArrayList ingredients) {
		this.name = name;
		this.ingredients = ingredients;
		calcNutrients();
		this.calories = calories;
		this.carbohydrate= carbohydrate;
		this.fat = fat;
		this.protein = protein;
		this.fiber = fiber;
		
	}
	
	/**
     * Calculates the total nutrient value for a meal
     */
	 public void calcNutrients() {
		 for(int i = 0; i < ingredients.size(); i++) {
			 calories += ingredients.get(i).getNutrientValue("calories");
			 carbohydrate += ingredients.get(i).getNutrientValue("carbohydrate"); 
			 fat += ingredients.get(i).getNutrientValue("fat");
			 protein += ingredients.get(i).getNutrientValue("protein");
			 fiber += ingredients.get(i).getNutrientValue("fiber");
		 }
	 }
	
	/**
     * Gets the list of ingredients for the meal
     * 
     * @return ArrayList ingredients
     */
	public ArrayList getIngredients() {
		return ingredients;
	}

	/**
     * Sets the value of the arrayList for the ingredients
     * @param ArrayList ingr
     */
	public void setIngr(ArrayList ingr) {
		this.ingredients = ingredients;
	}

	/**
     * Gets the name of the meal
     * @return String name
     */
	public String getName() {
		return name;
	}
	
	/**
     * Sets the name of the meal
     * @param String name 
     */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Gets the amount of calories in the meal
     * @return int calories
     */
	public int getCal() {
		return calories;
	}
	
	/**
     * Sets the number of calories in the meal
     * @param int cal
     */
	public void setCal(int cal) {
		this.calories = cal;
	}
	
	/**
     * Gets the amount of carbohydrates in the meal
     * @return int carbohydrates
     */
	public int getCarb() {
		return carbohydrate;
	}
	
	/**
     * Sets the amount of carbohydrates in the meal
     * @param carb
     */
	public void setCarb(int carb) {
		this.carbohydrate = carb;
	}
	
	/**
     * Gets the amount of fat in the meal
     * @return int fat
     */
	public int getFat() {
		return fat;
	}
	
	/**
     * Sets the amount of fat in the meal
     * @param int fat
     */
	public void setFat(int fat) {
		this.fat = fat;
	}
	
	/**
     * Gets the amount of protein in the meal
     * @return int protein
     */
	public int getPro() {
		return protein;
	}
	
	/**
     * Sets the amount of protein in the meal
     * @param int pro
     */
	public void setPro(int pro) {
		this.protein = pro;
	}
	
	/**
     * Gets the amount of fiber in the meal
     * @return int fiber
     */
	public int getFib() {
		return fiber;
	}
	
	/**
     * Sets the amount of fiber in the meal
     * @param int fib
     */
	public void setFib(int fib) {
		this.fiber = fib;
	}
	
}
