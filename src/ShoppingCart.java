

public class ShoppingCart {
	
	private DatabaseAccess db;
	String query;
	
	
	public ShoppingCart(DatabaseAccess database)
	{
		db = database;
	}
	
	//Show all items in cart
	public void getCartContents(int userId) throws Exception
	{
		query = "SELECT book_ID, Title,price FROM SHOPPINGCART as s, BOOKS as b "
				+ " WHERE cart_user ='"+ userId+"' "
				+ "AND s.cart_book = b.book_ID";
		db.readDatabase();
		System.out.println("||CART CONTENTS||");
		db.writeResultSet(query);
		db.close();
	}
	
	
	//Add book from bookstore to user's cart	
	public void addToCart(int userId,int bookId) throws Exception {

		//Checks if already in ShoppingCart or if book exists
		
		db.readDatabase();
		
		if(!db.checkInCart(userId,bookId)){
		
		
		//Adds to shopping cart
		query = "INSERT INTO SHOPPINGCART(cart_user, cart_book) "
				+ "VALUES("+userId+","+ bookId + ");";
		db.readDatabase();
		db.updateData(query);
		System.out.println("Book " + bookId +" added to your cart!\n");
	}
		else
			System.out.println("Already in your shopping cart!\n");
			
		db.close();
	}
	
	
	public void removeFromCart(int userId,int bookToRemove) throws Exception{
		
		query = "DELETE FROM SHOPPINGCART WHERE cart_book = '" + bookToRemove+"'";
		db.readDatabase();
		db.updateData(query);
		System.out.println("Book " + bookToRemove + " removed from your cart!\n\n");}
		
		
	
	
	public void clearCart() throws Exception {
		query = "DELETE FROM SHOPPINGCART";
		db.readDatabase();
		db.updateData(query);
		System.out.println("Cart cleared!\n\n");
		db.close();
	}
	

}
