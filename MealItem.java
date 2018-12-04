package finalProject;

import java.util.ArrayList;

public class MealItem <T> {
	//fields
	private String name;
	private int calories;
	private int carbohydrates;
	private int fat;
	private int protein;
	private int fiber;
	private ArrayList ingredients = new ArrayList();
	
	
	  /**
     * Constructor
     * initializes a meal item with all fields to 0 or empty
     */
	public MealItem () {
		this.name = "";
		this.calories = 0;
		this.carbohydrates= 0;
		this.fat = 0;
		this.protein = 0;
		this.fiber = 0;
		this.ingredients = ingredients;
	}
	
	 /**
     * Constructor
     * initializes the parameters to the values passed in
     * @param String name
     * @param int calories
     * @param int carbs
     * @param int fat
     * @param int protein
     * @param int fiber
     * @param ArrayList ingr
     */
	public MealItem (String name, int calories, int carbs, int fat, int protein, int fiber, ArrayList ingredients) {
		this.name = name;
		this.calories = calories;
		this.carbohydrates= carbs;
		this.fat = fat;
		this.protein = protein;
		this.fiber = fiber;
		this.ingredients = ingredients;
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
     */
	public void setCal(int cal) {
		this.calories = cal;
	}
	
	/**
     * Gets the amount of carbohydrates in the meal
     * @return int carbohydrates
     */
	public int getCarb() {
		return carbohydrates;
	}
	
	/**
     * Sets the amount of carbohydrates in the meal
     */
	public void setCarb(int carb) {
		this.carbohydrates = carb;
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
     */
	public void setFib(int fib) {
		this.fiber = fib;
	}
	
	
}
