package finalProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem {
    // The name of the food item.
    private String name;

    // The id of the food item.
    private String id;

    // Map of nutrients and value.
    private HashMap<String, Double> nutrients;
    
    //other fields
    private int calories;
	private int carbohydrates;
	private int protein;
	private int fat;
	private int fiber;
     
    /**
     * Constructor
     * initializes a meal item with all fields to 0 or empty
     */
	public FoodItem () {
		this.name = "";
        this.id = "";
		this.calories = 0;
		this.carbohydrates= 0;
		this.fat = 0;
		this.protein = 0;
		this.fiber = 0;
	    nutrients = new HashMap<String, Double>();
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
	public FoodItem (String name, String id, int calories, int carbs, int fat, int protein, int fiber) {
		this.id = id;
        this.name = name;
		this.calories = calories;
		this.carbohydrates= carbs;
		this.fat = fat;
		this.protein = protein;
		this.fiber = fiber;
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
     * Sets the name of the food
     */
	public void setName(String name) {
		this.name = name;
	}
    	
	/**
     * Gets the amount of calories in the foodItem
     * @return int calories
     */
	public int getCal() {
		return calories;
	}
	
	/**
     * Sets the number of calories in the foodItem
     */
	public void setCal(int cal) {
		this.calories = cal;
	}
	
	/**
     * Gets the amount of carbohydrates in the foodItem
     * @return int carbohydrates
     */
	public int getCarb() {
		return carbohydrates;
	}
	
	/**
     * Sets the amount of carbohydrates in the foodItem
     */
	public void setCarb(int carb) {
		this.carbohydrates = carb;
	}
	
	/**
     * Gets the amount of fat in the foodItem
     * @return int fat
     */
	public int getFat() {
		return fat;
	}
	
	/**
     * Sets the amount of fat in the foodItem
     */
	public void setFat(int fat) {
		this.fat = fat;
	}
	
	/**
     * Gets the amount of protein in the foodItem
     * @return int protein
     */
	public int getPro() {
		return protein;
	}
	
	/**
     * Sets the amount of protein in the foodItem
     */
	public void setPro(int pro) {
		this.protein = pro;
	}
	
	/**
     * Gets the amount of fiber in the foodItem
     * @return int fiber
     */
	public int getFib() {
		return fiber;
	}
	
	/**
     * Sets the amount of fiber in the foodItem
     */
	public void setFib(int fib) {
		this.fiber = fib;
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
     * Adds a nutrient and its value to this food. 
     * If nutrient already exists, updates its value.
     */
    public void addNutrient(String name, double value) {
        nutrients.put(name, value);
    }

    /**
     * Returns the value of the given nutrient for this food item. 
     * If not present, then returns 0.
     */
    public double getNutrientValue(String name) {
        return nutrients.get(name);
    }
    
}
