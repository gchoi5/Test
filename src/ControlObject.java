import java.sql.*;
import java.util.*;

class OrderControlObject{
	public OrderControlObject(){
	}
	//(Customer) sends order(s) 
	public void sendOrderToWaiter(int tabNum, int menuItemIdx){
		KitchenStatus.addToSelectedMenuList(
			new OrderedMenuItem(	KitchenStatus.nextSelectedMenuListIndex,
									MenuTable.getMenuItem(menuItemIdx),	
									tabNum)		//being waited

		);
		KitchenStatus.nextSelectedMenuListIndex++;
	}
	//from selectedMenuItemList to orderList
	public void confirmOrder(int tabNum, int selectedMenuItemIdx){
		OrderedMenuItem tempOrderedMenuItem = 
			new OrderedMenuItem(	KitchenStatus.nextOrderListIndex, 
									KitchenStatus.getFromSelectMenuList(selectedMenuItemIdx), 
									tabNum);

		KitchenStatus.removeFromSelectMenuList(selectedMenuItemIdx);
		KitchenStatus.orderList.add(tempOrderedMenuItem);

		tempOrderedMenuItem.setStatus("confirmed");
		
		HallStatus.getTable(tabNum).updateStatus();
		HallStatus.getTable(tabNum).addToSelectedOrderList(tempOrderedMenuItem);

		KitchenStatus.nextOrderListIndex++;
	}
	//cancel
	public void cancelOrder(int tabNum, int orderListIdx){
		if(KitchenStatus.getFromOrderList(orderListIdx).getStatus().equals("confirmed")){
			HallStatus.getTable(tabNum).removeFromSelectedMenuList(orderListIdx);
			HallStatus.getTable(tabNum).updateStatus();
		}
		KitchenStatus.removeFromOrderList(orderListIdx);
	}
	//serve
	public void serveOrder(int tabNum, int orderedMenuItemIdx){
		KitchenStatus.getFromOrderList(orderedMenuItemIdx).setStatus("served");

		HallStatus.getTable(tabNum).addToServedOrderList(KitchenStatus.getFromOrderList(orderedMenuItemIdx));
		HallStatus.getTable(tabNum).removeFromSelectedMenuList(orderedMenuItemIdx);
		HallStatus.getTable(tabNum).updateStatus();

		KitchenStatus.removeFromOrderList(orderedMenuItemIdx);
	}
	//getters & setters of KitchenStatus
	public List<OrderedMenuItem> getSelectedMenuList(){
		return KitchenStatus.selectedMenuList;
	}
	public List<OrderedMenuItem> getOrderList(){
		return KitchenStatus.orderList;
	}
}
class LogInControlObject{
	//log in method
	//varify id & pass
	public static Employee logIn(String userId, String userPass){
		DatabaseControlObject dbCO = new DatabaseControlObject();
		dbCO.openConnection();

		String query = "select name, age, id, pass, type from employee where id = ? and pass = ?";
		try{
			dbCO.setPreparedStatement(dbCO.getConnection().prepareStatement(query));
				dbCO.getPreparedStatement().setString(1, userId);
				dbCO.getPreparedStatement().setString(2, userPass);
			dbCO.setResultSet(dbCO.getPreparedStatement().executeQuery());

			//valid id & pass
			if(dbCO.getResultSet().next()){
				String tempUserType = new String(dbCO.getResultSet().getString(5));
				if(tempUserType.equals("M")){
					return new Manager(	dbCO.getResultSet().getString(1),
										dbCO.getResultSet().getInt(2),
										dbCO.getResultSet().getString(3),
										dbCO.getResultSet().getString(4));
				}
				else if(tempUserType.equals("W")){
					return new Waiter(	dbCO.getResultSet().getString(1),
										dbCO.getResultSet().getInt(2),
										dbCO.getResultSet().getString(3),
										dbCO.getResultSet().getString(4));
				}
				else if(tempUserType.equals("KA")){
					return new KitchenAssistant(	dbCO.getResultSet().getString(1),
													dbCO.getResultSet().getInt(2),
													dbCO.getResultSet().getString(3),
													dbCO.getResultSet().getString(4));
				}
			}
		}catch(SQLException sqle){
			System.err.println("SQL Exception during the log in process");
			dbCO.closeConnection();

			return null;
		}
		dbCO.closeConnection();

		return null;
	}
}

class DatabaseControlObject{
	private	static final String OS = System.getProperty("os.name").toLowerCase();	

	private	String dbUrl;
	private	String dbId;
	private	String dbPass;

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet result;

	//constructor
	public DatabaseControlObject(){
		this.dbUrl = "jdbc:mysql://127.0.0.1:3306/AutoServe";
		this.dbId = "root";
		this.dbPass = "1234";

		this.conn = null;
		this.stmt = null;
		this.pstmt = null;
		this.result = null;
	}
	//getter
	public Connection getConnection(){
		return this.conn;
	}
	public Statement getStatement(){
		return this.stmt;
	}
	public PreparedStatement getPreparedStatement(){
		return this.pstmt;
	}
	public ResultSet getResultSet(){
		return this.result;
	}
	//setter
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	public void setStatement(Statement stmt){
		this.stmt = stmt;
	}
	public void setPreparedStatement(PreparedStatement pstmt){
		this.pstmt = pstmt;
	}
	public void setResultSet(ResultSet result){
		this.result = result;
	}
	//open & close database connection
	public boolean openConnection(){
		try{
			if(OS.startsWith("win"))	{	//windows
				System.out.println("windows");
				//Class.forName("org.gjt.mm.mysql.Driver");
				Class.forName("com.mysql.jdbc.Driver");
			}
			//mac | linux
			else if(OS.startsWith("mac") || OS.startsWith("lin")){
					Class.forName("com.mysql.jdbc.Driver");
			}
		}catch(ClassNotFoundException cnfe){
			System.err.println("Class Not Found Exception");

			return false;
		}

		try{
			this.conn = DriverManager.getConnection(this.dbUrl, this.dbId, this.dbPass);
		}catch(SQLException sqle){
			System.err.println("SQL Exception");

			return false;
		}

		return true;
	}
	public boolean closeConnection(){
		try{
			if(this.conn != null)
				this.conn.close();

			if(this.stmt != null)
				this.stmt.close();

			if(this.pstmt != null)
				this.pstmt.close();

			if(this.result != null)
				this.result.close();
		}catch(SQLException sqle){
			System.err.println("SQL Exception while disconnecting");

			return false;
		}
		
		return true;
	}
}
