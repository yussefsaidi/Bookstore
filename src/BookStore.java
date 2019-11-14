
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookStore {

	private static int userId;
	private DatabaseAccess db;
	private String query;
	private User user;
	private ShoppingCart cart;
	
	public BookStore()
	{
		db = new DatabaseAccess();
		user = new User(db);
		cart=user.getCart();
	}

	
	public DatabaseAccess getDb()
	{
		return db;
	}
	
	//Get list of all books available in the Bookstore
	public void getAllBooks() throws Exception
	{
		query = "SELECT * FROM BOOKS";
		db.readDatabase();
		db.writeResultSet(query);
	}		
	
	
	//Get user input, check if it is a valid number
	public int getUserInput(Scanner scanner)
	{
	String line = scanner.nextLine();
	int searchChoice = 0;
	while(true)
	{
	if(line.length()==1 ) {
		try {
			searchChoice = Integer.parseInt(line);
			if(searchChoice<=5) {
			break;
			}
		}
	catch(NumberFormatException e) {
		System.out.println("Cannot take letters");
		break;
	}
		
	}
	
	System.out.println("Please enter a number from 1-5: ");
	line = scanner.nextLine();
	
	}
		return searchChoice;
	}
		
	//Get Specific book with title
	public boolean searchByTitle(String toSearch) throws Exception
	{
		boolean noResults;
		query = "SELECT * FROM BOOKS WHERE Title LIKE '%" + toSearch + "%'";
		db.readDatabase();
		noResults = db.writeResultSet(query);
		return noResults;
	}
	
	public boolean searchByISBN(String toSearch) throws Exception
	{
		boolean noResults;
		query = "SELECT * FROM BOOKS WHERE ISBN LIKE '" + toSearch + "%'";
		db.readDatabase();
		noResults = db.writeResultSet(query);
		return noResults;
	}
	
	public void removeFromCart(int userId,Scanner scanner) throws Exception
	{
		boolean keepRemoving = true;
		int bookToRemove;
		while(keepRemoving){
			
		System.out.print("Enter book ID to remove (0 to exit): ");
		try{bookToRemove = scanner.nextInt();
		scanner.nextLine();
		if(db.checkInCart(userId,bookToRemove)){
			cart.removeFromCart(userId,bookToRemove);
		}
		else {
			if(bookToRemove==0)
				keepRemoving=false;
			else{
			System.out.println("Book " + bookToRemove + " is not in your cart!\n\n");
			}
		}}
		catch(InputMismatchException e)
		{
			System.out.println("Please enter a number for bookId!");
			scanner.nextLine();
		}
		
		}
		db.close();	
	}
	
	public void showCartMenu(Scanner scanner) throws Exception
	{
		boolean stayInCart = true; // Keep the cart menu open
		String fullInput;
		int cartMenu;
		int bookToRemove;
		
		
		do{
			cart.getCartContents(userId);
			System.out.println("\n||CART MENU||\n" 
					+ "1 --> Remove book from cart  (Type '1 bookNumber')\n"
					+ "2 --> Clear cart (Type '2')\n"
					+ "3 --> Initiate Order (Type '3')\n"
					+ "4 --> Display Orders (Type '4')\n"
					+ "5 --> Back to Main Menu  (Type '5')\n");
					
			cartMenu = getUserInput(scanner);
				
			switch(cartMenu){
			
			
			//Remove item from cart
			case 1:
			{
				
				removeFromCart(userId,scanner);
				break;}
		
			//Clear cart
			case 2:
			{
				user.getCart().clearCart();
				break;}
			
			//Initiate Order
			case 3:{
				int orderBooks;
				System.out.print("Enter book ID: ");
				orderBooks = scanner.nextInt();
				scanner.nextLine();
				user.createOrder(userId,orderBooks,scanner);
				break;}
			
			//Check orders
			case 4:{
				user.getOrderInstance().getOrders(userId);
				break;
			}
			//Exit cart menu
			case 5:{
				stayInCart=false;
				break;}
			}
		}
		while(stayInCart);
	}

	public void setUserId(int set)
	{
		userId = set;
	}
	public User getUser()
	{
		return user;
	}
	
	public void getBookToAdd(Scanner scanner) throws Exception
	{
		boolean continueAdding = true;
		
		
		do{
		System.out.print("Add book to your cart, Enter the book_ID(0 to exit): ");
		try{int bookToCart = scanner.nextInt();
		scanner.nextLine();
		if(bookToCart==0){
			continueAdding=false;
		}
		else
			cart.addToCart(userId,bookToCart);}
		catch(InputMismatchException e)
		{
			System.out.println("Please enter a number for bookId");
			scanner.nextLine();
		}
	
		}
		while(continueAdding);
		}
	

	
	
	
}