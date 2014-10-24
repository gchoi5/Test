public class AutoServe{
	public static void main(String[] args){
		
		KitchenStatus.loadIngredientList();
		MenuTable.loadMenuList();
		MenuTable.updateMenuListAvailability();
		HallStatus.init();

		Customer customer = new Customer();
		customer.sendOrderToWaiter(5, 3);	//-> 1
		customer.sendOrderToWaiter(5, 4);	//-> 2

		KitchenStatus.printSelectedMenuList();
		
		//HallStatus.printTableInfoAll();
		
		HallStatus.getTable(5).printTableInfo();

		Waiter waiter = new Waiter();
		waiter.confirmOrder(5,1);	//->1
		waiter.confirmOrder(5,2);	//->2

		KitchenStatus.printSelectedMenuList();
		KitchenStatus.printOrderList();

		//HallStatus.printTableInfoAll();
		
		HallStatus.getTable(5).printTableInfo();

		customer.cancelOrder(5, 1);
	
		HallStatus.getTable(5).printTableInfo();
		
		customer.cancelOrder(5, 2);

		HallStatus.getTable(5).printTableInfo();

		customer.sendOrderToWaiter(10, 1); //-> 3

		waiter.confirmOrder(10, 3);

		HallStatus.getTable(10).printTableInfo();

		OrderControlObject orderCO = new OrderControlObject();
		orderCO.serveOrder(10, 3);


		HallStatus.getTable(10).printTableInfo();
		/*
		KitchenStatus.loadIngredientList();
		KitchenStatus.printIngredientList();

		KitchenStatus.loadIngredientList();
		KitchenStatus.printIngredientList();
		*/

		/*
		MenuTable.loadMenuList();
		MenuTable.printMenuListWithIngredient();

		KitchenStatus.loadIngredientList();
		MenuTable.updateMenuListAvailability();
		MenuTable.printMenuListWithIngredient();
		*/

		/*
		ComplainList.addComplain(5, "Too slow!");	//1
		ComplainList.addComplain(10, "Dirty...");	//2
		ComplainList.addComplain(1, "Where is out waiter?");	//3
		ComplainList.addComplain(2, "Hair in the food?!");	//4
		ComplainList.addComplain(3, "Bugs in the food...");	//5

		ComplainList.printComplainList();

		ComplainList.removeComplain(3);

		ComplainList.printComplainList();

		ComplainList.removeComplain(10);
		ComplainList.removeComplain(11);

		ComplainList.printComplainList();
		
		ComplainList.removeComplain(1);
		ComplainList.removeComplain(2);
		ComplainList.removeComplain(3);
		ComplainList.removeComplain(4);
		ComplainList.removeComplain(5);
		
		ComplainList.printComplainList();
		*/
	}
}

