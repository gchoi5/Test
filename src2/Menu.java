import java.util.*;
import java.sql.*;

class MenuItem{
	private int idx;
	private String title;
	private Double price;
	private String imgRef;
	private String status; //available | unavailable
	private List<Ingredient> ingredientList = new LinkedList<Ingredient>();

	public void addIngredient(Ingredient ingredient){
		this.ingredientList.add(ingredient);
	}

	/**
	 *
	 */
	public MenuItem(int idx, String title, double price, String imgRef){
		this.idx = idx;
		this.title = new String(title);
		this.price = new Double(price);
		this.imgRef = new String(imgRef);
		this.status = new String("available");
	}
	public MenuItem(MenuItem menuItem){
		this.idx = menuItem.idx;
		this.title = menuItem.getTitle();
		this.price = menuItem.getPrice();
		this.imgRef = menuItem.getImgRef();
		this.status = new String("available");
	}

	/**
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx the idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the imgRef
	 */
	public String getImgRef() {
		return imgRef;
	}

	/**
	 * @param imgRef the imgRef to set
	 */
	public void setImgRef(String imgRef) {
		this.imgRef = imgRef;
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
	 * @return the ingredientList
	 */
	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	/**
	 * @param ingredientList the ingredientList to set
	 */
	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	
	//print menu item info
	public void printMenuItem(){
		System.out.println(this.idx);
		System.out.println(this.title);
		System.out.println(this.price.toString());
		System.out.println(this.imgRef);
		System.out.println(this.status);
	}
	
	public void printMenuItemWithIngredient(){
		System.out.println(this.idx);
		System.out.println(this.title);
		System.out.println(this.price.toString());
		System.out.println(this.imgRef);
		System.out.println(this.status);
	
		System.out.println("Required ingredient");
		Iterator<Ingredient> tempIngredientListIterator = this.ingredientList.iterator();
		while(tempIngredientListIterator.hasNext()){
			Ingredient tempIngredient = tempIngredientListIterator.next();
			System.out.print("\t");
			System.out.print(tempIngredient.getIngName() + ": ");
			System.out.println(tempIngredient.getQuantity());
		}
	}
}

class OrderedMenuItem extends MenuItem{
	private int idx;	//override
	private int tabNum;
	private String status = new String();	//override	
											//being waited | confirmed | being cooked | served

	public void printOrderedMenuItem(){
		System.out.println("-----Order " + this.idx + "-----");
		System.out.println("Menu title: " + this.getTitle());

		System.out.println("Table: " + this.tabNum);
		System.out.println("Status: " + this.status);
	}
	/**
	 * constructor
	 */
	public OrderedMenuItem(int idx, MenuItem menuItem, int tabNum){
		super(menuItem);

		this.idx = idx;
		this.status = new String("being waited");
		this.tabNum = tabNum;
		this.status = new String();
	}
	/*
	public OrderedMenuItem(int idx, MenuItem menuItem, Table table){
		super(menuItem);

		this.idx = idx;
		this.status = new String("being waited");
		this.tabNum = table.getTabNum();
	}
	*/

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
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx the idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}

	/**
	 * @return the tabNum
	 */
	public Integer getTabNum() {
		return tabNum;
	}

	/**
	 * @param tabNum the tabNum to set
	 */
	public void setTabNum(Integer tabNum) {
		this.tabNum = tabNum;
	}
}

class MenuTable{
	static int nextMenuItemIndex = 1;
	static int menuNum;
	static List<MenuItem> menuList = new LinkedList<MenuItem>();

	public static MenuItem getMenuItem(int menuItemIdx){
		Iterator<MenuItem> tempMenuListIterator = menuList.iterator();
		while(tempMenuListIterator.hasNext()){
			MenuItem tempMenuItem = tempMenuListIterator.next();
			if(tempMenuItem.getIdx() == menuItemIdx)
				return tempMenuItem;
		}
		return null;
	}
	/*
	public static void selectMenuItem(int tabNum, int menuIdx){
		OrderControlObject orderCO = new OrderControlObject();
		orderCO.sendOrderToWaiter(tabNum, menuList.get(menuIdx));
	}
	*/
	public static boolean checkMenuAvailability(MenuItem menuItem){
		Iterator<Ingredient> tempMenuIngredientIterator = menuItem.getIngredientList().iterator();

		while(tempMenuIngredientIterator.hasNext()){
			Ingredient tempMenuIngredient = tempMenuIngredientIterator.next();

			String tempIngName = tempMenuIngredient.getIngName();

			Iterator<Ingredient> tempKitchenIngredientIterator = KitchenStatus.getIngredientList().iterator();

			while(tempKitchenIngredientIterator.hasNext()){
				Ingredient tempKitchenIngredient = tempKitchenIngredientIterator.next();
				if(tempKitchenIngredient.getIngName().equals(tempIngName))
					if(tempMenuIngredient.getQuantity() > tempKitchenIngredient.getQuantity())
						return false;
			}
		}

		return true;
	}
	public static void updateMenuListAvailability(){
		//KitchenStatus.loadIngredientList();

		Iterator<MenuItem> tempMenuListIterator = menuList.iterator();

		while(tempMenuListIterator.hasNext()){
			MenuItem tempMenuItem = tempMenuListIterator.next();
			if(checkMenuAvailability(tempMenuItem)){
				tempMenuItem.setStatus("available");	
			}
			else{
				tempMenuItem.setStatus("unavailable");	
			}
		}
	}
	
	
	public static void loadMenuList(){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();
	
		try{
			dbCO.setStatement(dbCO.getConnection().createStatement());
			dbCO.setResultSet(dbCO.getStatement().executeQuery("select * from menu"));

			menuNum = 0;
			while(dbCO.getResultSet().next()){
				menuList.add(new MenuItem(	nextMenuItemIndex,
											dbCO.getResultSet().getString(1),
											dbCO.getResultSet().getDouble(2),
											dbCO.getResultSet().getString(3)));
				menuNum++;
				nextMenuItemIndex++;
			}

			Iterator<MenuItem> tempMenuItemIterator = menuList.iterator();
			String query = "select * from ingredientForMenu where menuName = ?";
			while(tempMenuItemIterator.hasNext()){
				MenuItem tempMenuItem = tempMenuItemIterator.next();
				dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));	
					dbCO.getPreparedStatement().setString(1, tempMenuItem.getTitle());
				dbCO.setResultSet(dbCO.getPreparedStatement().executeQuery());

				while(dbCO.getResultSet().next()){
					tempMenuItem.addIngredient(new Ingredient( 	dbCO.getResultSet().getString(2),
																dbCO.getResultSet().getDouble(3)));
				}
				dbCO.getPreparedStatement().close();
			}
		}catch(SQLException sqle){
			System.err.println("SQL Exception while loading menu items from database.");
			System.exit(1);
		}

		dbCO.closeConnection();	
	}

	//methods for testing
	public static void printMenuList(){
		ListIterator<MenuItem> tempListIterator = menuList.listIterator();

		System.out.println("----------Menu list----------");

		while(tempListIterator.hasNext()){
			tempListIterator.next().printMenuItem();
			System.out.println();
		}
	}
	public static void printMenuListWithIngredient(){
		ListIterator<MenuItem> tempListIterator = menuList.listIterator();

		System.out.println("----------Menu list----------");

		while(tempListIterator.hasNext()){
			tempListIterator.next().printMenuItemWithIngredient();
			System.out.println();
		}
	}
}
