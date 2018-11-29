import java.util.ArrayList;

public class MealItem <T> {
	private String name;
	private int cal;
	private int carb;
	private int fat;
	private int pro;
	private int fib;
	private ArrayList ingr = new ArrayList();
	
	public MealItem () {
		this.name = "";
		this.cal = 0;
		this.carb= 0;
		this.fat = 0;
		this.pro = 0;
		this.fib = 0;
		this.ingr = ingr;
	}
	
	public ArrayList getIngr() {
		return ingr;
	}

	public void setIngr(ArrayList ingr) {
		this.ingr = ingr;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCal() {
		return cal;
	}
	public void setCal(int cal) {
		this.cal = cal;
	}
	public int getCarb() {
		return carb;
	}
	public void setCarb(int carb) {
		this.carb = carb;
	}
	public int getFat() {
		return fat;
	}
	public void setFat(int fat) {
		this.fat = fat;
	}
	public int getPro() {
		return pro;
	}
	public void setPro(int pro) {
		this.pro = pro;
	}
	public int getFib() {
		return fib;
	}
	public void setFib(int fib) {
		this.fib = fib;
	}
	
	
}
