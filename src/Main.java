
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import java.util.Scanner;






public class Main {

	
	
	
	
	//MAIN
	public static void main(String[] args) throws Exception{

		BookStore bs = new BookStore();
		
		int searchChoice;
		String searchByTitle, searchByIsbn;
		boolean keepSearching = true;//Keep the search going until user exits
		
		Scanner scanner = new Scanner(System.in);
		
		//Current user's info
		bs.getUser().logIn(scanner);

		if(bs.getUser().getStatus()==true)
		{
			System.out.println(bs.getUser().getUserName()+" Has Logged in Properly!\n");
			
			//Get userId
			bs.setUserId(Integer.parseInt(bs.getUser().getUserId()));
	
		//USER MENU
		do {
			System.out.println("1 --> Search by Title \n2 --> Search by ISBN\n"
					+ "3 --> Show Cart \n" + "4 --> Exit\n\n");
			
			
			searchChoice = bs.getUserInput(scanner);
		
			//User Menu
			switch(searchChoice)
			{	
			
			
				case 1:
				{
					System.out.print("Search following title: ");
					searchByTitle = scanner.nextLine();
					if(bs.searchByTitle(searchByTitle)){
						System.out.println("No books found!\n");
						break;
					}
						bs.getBookToAdd(scanner);
						break;
					
				}
				
				//Search by ISBN
				case 2:
				{
					System.out.print("Search following ISBN: ");
					searchByIsbn = scanner.nextLine();
					if(bs.searchByISBN(searchByIsbn)){
						System.out.println("No books found!\n");
						break;
					}
						
					bs.getBookToAdd(scanner);
					break;
				}
				
				//SHOW CART ITEMS + CART MENU
				case 3:
				{
					bs.showCartMenu(scanner);
					break;
				}
				
				case 4:
				{
					System.out.print("Logged out of your account!");
					keepSearching = false;
					break;
				}
				
				
			}
			
		}
		while(keepSearching);
		
}
	
		
bs.getUser().logOff();

	}

}
