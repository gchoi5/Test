import java.sql.*;
import java.util.*;

class Ingredient{
	String ingName;
	double quantity;

	public Ingredient(String ingName, double quantity){
		this.ingName = new String(ingName);
		this.quantity = quantity;
	}

	/**
	 * @return the ingName
	 */
	public String getIngName() {
		return ingName;
	}

	/**
	 * @param ingName the ingName to set
	 */
	public void setIngName(String ingName) {
		this.ingName = ingName;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	//method(s) for testing
	public void printIngredient(){
		System.out.println("IngName: " + this.ingName);
		System.out.println("Quantity: " + this.quantity);
	}
	
}


class Table{
	private int tabNum;
	private String status;		//empty | occupied | served
	private List<OrderedMenuItem> selectedOrderList = new LinkedList<OrderedMenuItem>();
	private List<OrderedMenuItem> servedOrderList = new LinkedList<OrderedMenuItem>();

	public void updateStatus(){
		if(this.selectedOrderList.isEmpty() && this.servedOrderList.isEmpty())
			this.status = "empty";
		else if(!this.selectedOrderList.isEmpty() && this.servedOrderList.isEmpty())
			this.status = "occupied";
		else if(!this.servedOrderList.isEmpty())
			this.status = "served";
	}
	public void addToSelectedOrderList(OrderedMenuItem orderedMenuItem){
		this.selectedOrderList.add(orderedMenuItem);
	}
	public void addToServedOrderList(OrderedMenuItem orderedMenuItem){
		this.servedOrderList.add(orderedMenuItem);
	}
	public void removeFromSelectedMenuList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> tempSelectedOrderListIterator = this.selectedOrderList.iterator();
		while(tempSelectedOrderListIterator.hasNext())
			if(tempSelectedOrderListIterator.next().getIdx() == orderedMenuItemIdx)
				tempSelectedOrderListIterator.remove();
	}
	public void removeFromServedOrderList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> tempOrderListIterator = this.servedOrderList.iterator();
		while(tempOrderListIterator.hasNext())
			if(tempOrderListIterator.next().getIdx() == orderedMenuItemIdx)
				tempOrderListIterator.remove();
	}

	//constructor
	public Table(int tabNum){
		this.tabNum = tabNum;
		this.status = new String("empty");
	}

	/**
	 * @return the tabNum
	 */
	public int getTabNum() {
		return tabNum;
	}

	/**
	 * @param tabNum the tabNum to set
	 */
	public void setTabNum(int tabNum) {
		this.tabNum = tabNum;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the selectedOrderList
	 */
	public List<OrderedMenuItem> getSelectedOrderList() {
		return this.selectedOrderList;
	}

	/**
	 * @param selectedOrderList the selectedOrderList to set
	 */
	public void setSelectedOrderList(List<OrderedMenuItem> selectedOrderList) {
		this.selectedOrderList = selectedOrderList;
	}

	/**
	 * @return the servedOrderList
	 */
	public List<OrderedMenuItem> getServedOrderList() {
		return this.servedOrderList;
	}
	/**
	 * @param servedOrderList the servedOrderList to set
	 */
	public void setServedOrderList(List<OrderedMenuItem> servedOrderList) {
		this.servedOrderList = servedOrderList;
	}

	//method(s) for testing
	public void printTableInfo(){
		System.out.println("----------Table " + this.tabNum + " Information----------");
		System.out.println("Status: " + this.status);
		System.out.println("SelectedOrderList: ");
		Iterator<OrderedMenuItem> tempSelectedOrderListIterator = this.selectedOrderList.iterator();
		while(tempSelectedOrderListIterator.hasNext()){
			System.out.println("\t" + tempSelectedOrderListIterator.next().getTitle());
		}
		System.out.println("ServedOrderList: ");
		Iterator<OrderedMenuItem> tempServedOrderListIterator = this.servedOrderList.iterator();
		while(tempServedOrderListIterator.hasNext()){
			System.out.println("\t" + tempServedOrderListIterator.next().getTitle());
		}
	}
}

class HallStatus{
	static Table[] table = new Table[20];

	//constructor
	public static void init(){
		for(int i = 0; i < 20; i++){
			table[i] = new Table(i + 1);
		}
	}
	//getter
	public static Table getTable(int tabNum){
		return table[tabNum - 1];
	}
	public static void printTableInfoAll(){
		for(int i = 0; i < 20; i++){
			table[i].printTableInfo();
		}
	
	}
}



class KitchenStatus{
	static int nextSelectedMenuListIndex = 1;
	static int nextOrderListIndex = 1;
	static List<OrderedMenuItem> selectedMenuList = new LinkedList<OrderedMenuItem>();
	static List<OrderedMenuItem> orderList = new LinkedList<OrderedMenuItem>();
	static List<Ingredient> ingredientList = new LinkedList<Ingredient>();

	public static void addToSelectedMenuList(OrderedMenuItem orderedMenuItem){
	
		selectedMenuList.add(orderedMenuItem);
	}
	public static void addToOrderList(OrderedMenuItem orderedMenuItem){
		orderList.add(orderedMenuItem);
	}
	public static OrderedMenuItem getFromSelectMenuList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> tempSelectedMenuListIterator = selectedMenuList.iterator();
		while(tempSelectedMenuListIterator.hasNext()){
			OrderedMenuItem tempOrderedMenuItem = tempSelectedMenuListIterator.next();
			if(tempOrderedMenuItem.getIdx() == orderedMenuItemIdx)
				return tempOrderedMenuItem;
		}
		return null;
	}
	public static OrderedMenuItem getFromOrderList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> tempOrderListIterator = orderList.iterator();
		while(tempOrderListIterator.hasNext()){
			OrderedMenuItem tempOrderedMenuItem = tempOrderListIterator.next();
			if(tempOrderedMenuItem.getIdx() == orderedMenuItemIdx)
				return tempOrderedMenuItem;
		}
		return null;	
	}
	public static void removeFromSelectMenuList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> selectedMenuListIterator = selectedMenuList.iterator();
		while(selectedMenuListIterator.hasNext()){
			if(selectedMenuListIterator.next().getIdx() == orderedMenuItemIdx){
				selectedMenuListIterator.remove();
			}
		}
	
	}
	public static void removeFromOrderList(int orderedMenuItemIdx){
		Iterator<OrderedMenuItem> orderListIterator = orderList.iterator();
		while(orderListIterator.hasNext()){
			if(orderListIterator.next().getIdx() == orderedMenuItemIdx){
				orderListIterator.remove();
			}	
		}
	
	}
	/*
	public static void removeFromIngredientList(Ingredient ingredient){
		Iterator<Ingredient> tempIngredient = ingredientList.iterator();
		int tempIdx = 0;
		while(tempIngredient.hasNext()){
			if(tempIngredient.next().getIngName().equals(ingredient.getIngName())){
				ingredientList.remove(tempIdx);
			}	
			tempIdx++;
		}
	
	}
	*/
	//loadIngredient
	public static boolean loadIngredientList(){
		ingredientList.clear();

		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();
		try{
			dbCO.setStatement(dbCO.getConnection().createStatement());
			dbCO.setResultSet(dbCO.getStatement().executeQuery("select * from ingredient"));

			while(dbCO.getResultSet().next()){
				ingredientList.add(new Ingredient(	dbCO.getResultSet().getString(1),
													dbCO.getResultSet().getInt(2)));
			}
		}catch(SQLException sqle){
			System.err.println("SQL Exception while loading ingredient information: " + sqle.toString());

			return false;
		}
		dbCO.closeConnection();
		
		return true;
	}

	/*
	public static boolean updateIngredientList(){
		ingredientList.clear();

		if(!loadIngredientList())
			return false;

		return true;
	}
	*/


	/**
	 * @return the orderList
	 */
	public static List<OrderedMenuItem> getOrderList() {
		return orderList;
	}

	/**
	 * @param orderList the orderList to set
	 */
	public static void setOrderList(List<OrderedMenuItem> newOrderList) {
		orderList = newOrderList;
	}

	/**
	 * @return the ingredientList
	 */
	public static List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	/**
	 * @param ingredientList the ingredientList to set
	 */
	public static void setIngredientList(List<Ingredient> ingredientList) {
		KitchenStatus.ingredientList = ingredientList;
	}
	
	
	//print
	public static void printSelectedMenuList(){
		System.out.println("----------Selected menu list----------");

		Iterator<OrderedMenuItem> tempSelectedMenuList = selectedMenuList.iterator();
		
		while(tempSelectedMenuList.hasNext()){
			tempSelectedMenuList.next().printOrderedMenuItem();;
		}
	}
	public static void printOrderList(){
		System.out.println("----------Order list----------");

		Iterator<OrderedMenuItem> tempOrderListIterator = orderList.iterator();

		while(tempOrderListIterator.hasNext()){
			tempOrderListIterator.next().printOrderedMenuItem();
		}
	}
	public static void printIngredientList(){
		System.out.println("----------Ingredient list----------");

		Iterator<Ingredient> tempIngredientListIterator = ingredientList.iterator();

		while(tempIngredientListIterator.hasNext()){
			tempIngredientListIterator.next().printIngredient();
		}
	}
	
}
