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
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<>();
        indexes = new HashMap<String, BPTree<Double,FoodItem>>();
        BPTree<Double, FoodItem> cals = new BPTree<>(6);
        BPTree<Double, FoodItem> fat = new BPTree<>(6);
        BPTree<Double, FoodItem> carb = new BPTree<>(6);
        BPTree<Double, FoodItem> fib = new BPTree<>(6);
        BPTree<Double, FoodItem> pro = new BPTree<>(6);
        indexes.put("calories", cals);
        indexes.put("fat", fat);
        indexes.put("carbohydrate", carb);
        indexes.put("fiber", fib);
        indexes.put("protein", pro);
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
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
        			if(foodArray[0].compareTo("") == 0)
        				break;
        			if (foodArray.length != 12)
        				continue;
        			
        			FoodItem food = new FoodItem(foodArray[0], foodArray[1]);
        			
        			//add the nutrients to the food object
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
        			
        			indexes.get("calories").insert(Double.parseDouble(foodArray[3]), food);
        			indexes.get("fat").insert(Double.parseDouble(foodArray[5]), food);
        			indexes.get("carbohydrate").insert(Double.parseDouble(foodArray[7]), food);
        			indexes.get("fiber").insert(Double.parseDouble(foodArray[9]), food);
        			indexes.get("protein").insert(Double.parseDouble(foodArray[11]), food);
        			
        			foodItemList.add(food);
 
        			//if(reader.readLine() == null)
        			//break;
        			line = reader.readLine();
        			
        		}
        	
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List<FoodItem> filteredItems = new ArrayList<FoodItem>();
    	List<FoodItem> finalItems = new ArrayList<FoodItem>();
    	for(int i = 0; i < rules.size(); i++) {
    		String rule = rules.get(i);
    		String[] ruleSplit = rule.split(" ");
    		String nutrient = ruleSplit[0];
    		String comparator = ruleSplit[1];
    		Double value = Double.parseDouble(ruleSplit[2]);
    		filteredItems.addAll(indexes.get(nutrient).rangeSearch(value, comparator));
    	}
    	if(rules.size() == 1) {
			return filteredItems;
		}
		
		for(int f = 0; f<filteredItems.size(); f++) {
			int count = 0;
    			for(int y = f+1; y<filteredItems.size(); y++) {
    				if(filteredItems.get(f).getID().equals(filteredItems.get(y).getID())){
					count ++;				
    				}
    			}
    			if(count == rules.size()-1) {
    				finalItems.add(filteredItems.get(f));
    			}
			
		}
         return finalItems;   
}

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
    	   foodItemList.add(foodItem);
        indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
   		indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
   		indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
   		indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
   		indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


	@Override
	public void saveFoodItems(String filename) {
		List<String[]> al = new ArrayList<>(); 
		List<String[]> output = new ArrayList<>();
		List<String> sorter = new ArrayList<>();
		for(int i = 0; i<foodItemList.size();i++) {
			String[] object = new String[3];
			String name = foodItemList.get(i).getName();
			String ID = foodItemList.get(i).getID();
			object[0] = ID;
			object[1] = name;
			object[2] = foodItemList.get(i).getNutrients().toString();
			al.add(object);
			sorter.add(name.toLowerCase());
		}
		
		Collections.sort(sorter);
		
		for(int i = 0; i<foodItemList.size(); i++) {
			String name = sorter.get(i);
			for(int j = 0; j<al.size();j++) {
				if(name.toLowerCase().equals(al.get(j)[1].toLowerCase())) {
					String[] object = new String[3];
					object[0] = al.get(j)[0];
					object[1] = name;
					object[2] = al.get(j)[2];
					output.add(object);
					al.remove(j);
					break;
				}			
			}
		}

		try {
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
