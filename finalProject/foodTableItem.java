package finalProject;

import finalProject.FoodItem;

//used for displaying food items in table cuz you can't display
//a BPTree
public class foodTableItem {
    private FoodItem food;
	private String name;
	private String id;
	private String calories;
	private String carbohydrates;
	private String fat;
	private String fiber;
	private String protein; 
	
	public foodTableItem(FoodItem food) {
	    this.food = food;
		this.name = food.getName();
		this.id = food.getID();
		this.calories = food.getNutrientValue("calories") + "";
		this.carbohydrates = food.getNutrientValue("carbohydrate") + "";
		this.fat = food.getNutrientValue("fat") + "";
		this.fiber = food.getNutrientValue("fiber") + "";
		this.protein = food.getNutrientValue("protein") + "";
		
	}
	
	public FoodItem getFood() {
	    return food;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(String carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public String getFat() {
		return fat;
	}

	public void setFat(String fat) {
		this.fat = fat;
	}

	public String getFiber() {
		return fiber;
	}

	public void setFiber(String fiber) {
		this.fiber = fiber;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(String protein) {
		this.protein = protein;
	}
}
