

public class Order {

	private DatabaseAccess db;
	private String query;
	
	
	public Order(DatabaseAccess database)
	{
		db = database;
	}

	
	//All orders are displayed
	public void getOrders(int userId) throws Exception
	{
		query = "SELECT * FROM ORDERS";
		System.out.println("\n||ORDERS||\n");
		db.readDatabase();
		db.writeResultSet(query);
		System.out.print("\n");
		db.close();
	}
	
	public boolean createOrder(int _userId, int bookToOrder) throws Exception
	{
		boolean orderCreated = true;
		System.out.println("\n\n");
		db.readDatabase();
		
		//Check if book found in your cart
		if(db.checkInCart(_userId, bookToOrder))
		{
		
		//Get largest order_ID value
		int largestOrderNumber = db.largestOrderNumber();
		int newOrder = largestOrderNumber+1;
		
		query = "INSERT INTO ORDERS(order_ID,order_user,order_book) "
				+ "VALUES(" + newOrder +"," + _userId + "," + bookToOrder + ")" ;
		
		db.updateData(query);
		System.out.println("You ordered book: "+ bookToOrder);
		this.getOrders(_userId);
		System.out.println("");
		
		}
		else
		{
			System.out.println("Book cannot be ordered, it is not in your cart!");
			orderCreated = false;
		}
		
		db.close();
		return orderCreated;
		}
		
	
	}
	
	
	

