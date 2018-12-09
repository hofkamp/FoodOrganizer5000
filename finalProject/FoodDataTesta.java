package finalProject;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class FoodDataTesta {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodDataTesta() {
        foodItemList = new ArrayList<>();
        indexes = new HashMap<String, BPTree<Double,FoodItem>>();
        BPTree<Double, FoodItem> cals = new BPTree<>(6);
        BPTree<Double, FoodItem> fat = new BPTree<>(6);
        BPTree<Double, FoodItem> carb = new BPTree<>(6);
        BPTree<Double, FoodItem> fib = new BPTree<>(6);
        BPTree<Double, FoodItem> pro = new BPTree<>(6);
        indexes.put("calories", cals);
        indexes.put("fat", fat);
        indexes.put("carbohydrates", carb);
        indexes.put("fiber", fib);
        indexes.put("protein", pro);
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    public void loadFoodItems(String filePath) {
        Path pathToFile = Paths.get(filePath);
        
        //create a buffered reader using try with resources
        try(BufferedReader reader = Files.newBufferedReader(pathToFile)){
        		
        	//read the first line of the file
        		String line = reader.readLine();
        		
        	//read all lines of the file
        		while(line != null) {
        			//split the string in to an array of values using the comma as a delimiter
        			
        			String[] foodArray = line.split(",");
        			FoodItem food = new FoodItem(foodArray[0], foodArray[1]);
        			
        			//add the nutrients to the food object
        			food.addNutrient(foodArray[2], Double.parseDouble(foodArray[3])); //calories
        			food.addNutrient(foodArray[4], Double.parseDouble(foodArray[5])); //fat
        			food.addNutrient(foodArray[6], Double.parseDouble(foodArray[7])); //carb
        			food.addNutrient(foodArray[8], Double.parseDouble(foodArray[9])); //fiber
        			food.addNutrient(foodArray[10], Double.parseDouble(foodArray[11])); //protein 
        			
        			indexes.get("calories").insert(Double.parseDouble(foodArray[3]), food);
        			indexes.get("fat").insert(Double.parseDouble(foodArray[5]), food);
        			indexes.get("carbohydrates").insert(Double.parseDouble(foodArray[7]), food);
        			indexes.get("fiber").insert(Double.parseDouble(foodArray[9]), food);
        			indexes.get("protein").insert(Double.parseDouble(foodArray[11]), food);
        			
        			foodItemList.add(food);
        			line = reader.readLine();
        		}
        	
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    //@Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List<FoodItem> filteredItems = new ArrayList<FoodItem>();
    	//List<FoodItem> finalItems = new ArrayList<FoodItem>();
    	for(int i = 0; i < rules.size(); i++) {
    		String rule = rules.get(i);
    		String[] ruleSplit = rule.split(" ");
    		String nutrient = ruleSplit[0];
    		String comparator = ruleSplit[1];
    		Double value = Double.parseDouble(ruleSplit[2]);
    		filteredItems.addAll(indexes.get(nutrient).rangeSearch(value, comparator));
    	}
         return filteredItems;   
}

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */

    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }




        public static void main (String[] args) {
                FoodDataTesta data = new FoodDataTesta();


                FoodItem g = new FoodItem( "1223","celery");
                g.addNutrient("calories", 7);
                g.addNutrient("fat", 34);
                g.addNutrient("protein", 3);
                g.addNutrient("fiber", 1);
                g.addNutrient("carbohydrates", 4.2);

                FoodItem h = new FoodItem( "1503","peanut");
                h.addNutrient("calories", 12);
                h.addNutrient("fat", 73);
                h.addNutrient("protein", 19);
                h.addNutrient("fiber", 8);
                h.addNutrient("carbohydrates", 41493);

                FoodItem i = new FoodItem( "1453","apple");
                i.addNutrient("calories", 453);
                i.addNutrient("fat", 34);
                i.addNutrient("protein", 76);
                i.addNutrient("fiber", 9);
                i.addNutrient("carbohydrates", 8);

                FoodItem q = new FoodItem( "13","carrot");
                q.addNutrient("calories", 17);
                q.addNutrient("fat", 29);
                q.addNutrient("protein", 12);
                q.addNutrient("fiber", 6);
                q.addNutrient("carbohydrates", 493);

                data.foodItemList.add(g);
                data.foodItemList.add(h);
                data.foodItemList.add(i);
                data.foodItemList.add(q);

                List<String> rules = new ArrayList<String>();
                rules.add("calories <= 20");
                rules.add("protein <= 15");
                rules.add("calories >= 2");
                rules.add("protein <= 5");



                List<FoodItem> items;
                items = data.filterByNutrients(rules);
                for(int l = 0; l<items.size(); l++) {
                        System.out.print(items.get(l).getName() + " ");
                }
        }

}