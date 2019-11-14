


import java.util.InputMismatchException;
import java.util.Scanner;

public class User {

	private DatabaseAccess db;
	private String userId;
	private String address;
	private String userName;
	private String password;
	private String creditCard;
	private String email;
	private boolean logInStatus;
	private String query;
	private String[] userInfo;
	private ShoppingCart cart;
	private Order orders;
	
	
	public User(DatabaseAccess db_)
	{
		db = db_;
		this.logInStatus=false;
		cart = new ShoppingCart(db);
		orders = new Order(db);
	}
	
//////////////////////////////////////////////////////////////////////////////////////	
	//GETTER / SETTER methods
	public String getUserName() {return userName;}

	public String getPassword() {return password;
	}
	public String getCreditCard() {return creditCard;}

	public String getUserId() {return userId; }
	
	public void setUserId(String id) {userId = id;}

	public String getEmail() {return email;}
	
	public boolean getStatus() {return logInStatus;}
	
	public void setAddress(String addr) {this.address=addr;}
	
	public String getAddress() {return address;}
	
	public void setUserName(String userName) {this.userName = userName;}

	public void setPassword(String password) {this.password = password;}

	public void setCreditCard(String creditCard) {this.creditCard = creditCard;}

	public void setEmail(String email) {this.email = email;}
/////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public Order getOrderInstance()
	{
		return orders;
		
	}
	
	//Makes new order for user and adds it to ORDERS database
	public void createOrder(int _userId, int bookToOrder,Scanner scanner) throws Exception
	{
		//Check if order is in cart
		
		
		if(orders.createOrder(_userId, bookToOrder))
			cart.removeFromCart(_userId, bookToOrder);
	}
	
	
	//Completely clears shopping cart
	public ShoppingCart getCart() 
	{
		return cart;
	}
	
	
	public void logIn(Scanner sc) throws Exception {
		
		do
		{
		//Get credentials from user
		System.out.print("Username: ");
		String enteredUserName = sc.nextLine();
		System.out.print("Password: ");
		String enteredPassword = sc.nextLine();
		
		//Get credentials from database
		query = "SELECT * FROM USERS WHERE username ='"+ enteredUserName 
				+"' AND password='" + enteredPassword +"'";
		
		
		db.readDatabase();
		userInfo = db.saveQueryResults(query);
		this.setUserId(userInfo[0]);
		this.setUserName(userInfo[1]);
		this.setPassword(userInfo[2]);
		this.setEmail(userInfo[3]);
		this.setAddress(userInfo[4]);
		db.close();
		if(this.getUserId()!=null) {
			this.logInStatus=true;
		}
		else
			System.out.println("Username or Password incorrent, Try Again!\n");
		
		}
		while(this.logInStatus==false);
		

	}
	
	public void logOff() {
		logInStatus = false;
	}
}
