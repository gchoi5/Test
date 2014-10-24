import java.util.*;
import java.sql.*;

class Customer{
	//Customer does not log in yet.
	//But they may log in later for VIP advantage
	/*
	private String name;
	private int age;
	private String id;
	private String pass;
	*/
	public Customer(){
	}

	public void sendOrderToWaiter(int tabNum, int menuItemIdx){
		OrderControlObject orderCO = new OrderControlObject();
		orderCO.sendOrderToWaiter(tabNum, menuItemIdx);	
	}
	public void cancelOrder(int tabNum, int menuItemIdx){
		OrderControlObject orderCO = new OrderControlObject();
		orderCO.cancelOrder(tabNum, menuItemIdx);
	}
}
class Employee{
	private String name;
	private Integer age;

	private String id;
	private String pass;
	
	/**
	 * 
	 */
	public Employee() {
		this.name = new String();
		this.age = new Integer(0);
		this.id = new String();
		this.pass = new String();
	}
	public Employee(String name, Integer age, String id, String pass){
		this.name = new String(name);
		this.age = new Integer(age);
		this.id = new String(id);
		this.pass = new String(pass);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the userId
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userPass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param userPass the userPass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/*
	//print info
	public void printEmployee(){
		System.out.println("Name: " + this.name);
		System.out.println("Age: " + this.age);
		System.out.println("Id: " + this.id);
		System.out.println("Pass: " + this.pass);
	}
	*/
}

class Waiter extends Employee{
	//constructor
	public Waiter(){
		super();
	}
	public Waiter(String name, Integer age, String id, String pass){
		super(name, age, id, pass);
	}
	//method
	public void confirmOrder(int tabNum, int selectedMenuItemIdx){
		OrderControlObject orderCO = new OrderControlObject();
		orderCO.confirmOrder(tabNum, selectedMenuItemIdx);
	}
	//check kitchen (order) status
	public List<OrderedMenuItem> getKitchenSelectedMenuList(){
		return KitchenStatus.selectedMenuList;
	}
	public List<OrderedMenuItem> getKitchenOrderList(){
		return KitchenStatus.orderList;
	}
	public void billTable(Table table){
		//after Customer pays
		//Table status will be set empty
		//Table order list will be cleaned
		table.setStatus("empty");
		table.getServedOrderList().clear();	
	}
}

class KitchenAssistant extends Employee{
	//constructor
	public KitchenAssistant(){
		super();
	}
	public KitchenAssistant(String name, Integer age, String id, String pass){
		super(name, age, id, pass);
	}
	//method
	public List<OrderedMenuItem> getKitchenOrderList(){
		return KitchenStatus.orderList;
	}
	public List<Ingredient> getKitchenIngredientList(){
		return KitchenStatus.getIngredientList();
	}
}

//Manage may do everything both Waiter and Kitchen Assistant do
//But for now, both Manager and Kitchen Assistant can check kitchen status
class Manager extends KitchenAssistant{
	//constructor
	public Manager(){
		super();
	}
	public Manager(String name, Integer age, String id, String pass){
		super(name, age, id, pass);
	}
	//method
	public boolean insertEmployee(String name, int age, String id, String pass, String type){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "insert into employee values(?, ?, ?, ?, ?)";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, name);
				dbCO.getPreparedStatement().setInt(2, age);
				dbCO.getPreparedStatement().setString(3, id);
				dbCO.getPreparedStatement().setString(4, pass);
				dbCO.getPreparedStatement().setString(5, type);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while inserting employee");
			return false;
		}

		dbCO.closeConnection();
		return true;
	}
	public boolean deleteEmployee(String userId){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "delete from employee where id = ?";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, userId);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while deleting employee");
			return false;
		}

		dbCO.closeConnection();
		return true;
	}
	public boolean insertMenu(String menuName, double price, String imgRef){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "insert into menu values (?,?, ?)";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, menuName);
				dbCO.getPreparedStatement().setDouble(2, price);
				dbCO.getPreparedStatement().setString(3, imgRef);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while inserting menu"+ sqle.toString());
			return false;
		}

		dbCO.closeConnection();
		return true;
	}
	public boolean deleteMenu(String menuName){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "delete from menu where menuName = ?";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, menuName);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while deleting employee");
			return false;
		}
		
		dbCO.closeConnection();
		return true;
	}
	public boolean insertTrendMenu(String menuName, int rating){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "insert into trendMenu values (?,?)";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, menuName);
				dbCO.getPreparedStatement().setDouble(2, rating);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while inserting trend menu"+ sqle.toString());
			return false;
		}

		dbCO.closeConnection();
		return true;
	}
	public boolean deleteTrendMenu(String menuName){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "delete from trendMenu where menuName = ?";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, menuName);
			dbCO.getPreparedStatement().executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL Exception while deleting employee");
			return false;
		}
		
		dbCO.closeConnection();

		return true;
	}
}
