package finalProject;



import java.util.ArrayList;

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
	
	 private void calcNutrients() {
		 for(int i = 0; i < ingredients.size(); i++) {
			 calories += ingredients.get(i).getNutrientValue("calories");
			 carbohydrate += ingredients.get(i).getNutrientValue("carbohydrates"); 
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
		return carbohydrate;
	}
	
	/**
     * Sets the amount of carbohydrates in the meal
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
