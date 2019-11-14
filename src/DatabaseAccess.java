
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseAccess {

	private static Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	

	
	//Open database and create connection to it
	public void readDatabase() throws Exception
	{
		
		try{ Class.forName("org.sqlite.JDBC"); }
		catch(ClassNotFoundException cnfex) {
			System.out.println("Problem in loading JDBC");
			cnfex.printStackTrace();
		}
		
		//Opening Connection
		try{
			String dbURL = "jdbc:sqlite://D:/Workspace/Bookstore/src/Project Database.db";
			
			
			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();
		}
		catch(Exception e) {
			throw e;}
		}
		
	
	
	//Get largest orderId number for making a new order
	public int largestOrderNumber() throws Exception
	{
		int largestOrderId=0;
		this.readDatabase();
		String query = "SELECT MAX(order_ID) FROM ORDERS";
	
		try
		{
		resultSet=statement.executeQuery(query);
		if(resultSet.next()){
			largestOrderId = resultSet.getInt(1);
		}
		}
		
		catch(Exception e) {
			System.out.println(e);
			System.out.println("Problem with finding the biggest order_ID!");
		}
		return largestOrderId;
	}
	
	
	//Check is book exists in our BOOK database
	public boolean checkInBooks(int userId, int bookId) throws Exception
	{
		boolean bookExists = false;
		int bookCount = 0;
		String query="select count(1) from BOOKS where book_ID = " + bookId;
		resultSet=statement.executeQuery(query);
		
		if(resultSet.next()){
			bookCount = resultSet.getInt(1);
			if(bookCount==1)
				bookExists=true;
		}
		return bookExists;
	}
	
	//To check if already in cart
	public boolean checkInCart(int userId, int bookId) throws Exception
	{
		readDatabase();
		boolean dataFound = false;
		
		//Check if book exists
		if(this.checkInBooks(userId, bookId)){
		
		//Check if already in table
		String query = "SELECT * FROM SHOPPINGCART WHERE cart_user ='" + userId + "' AND cart_book ='" + bookId+"'";
		resultSet=statement.executeQuery(query);
		if (resultSet.isBeforeFirst() ) {    
		    dataFound = true; 
		} 
		
		}
		close();
		return dataFound;
		
	}
		
	//Save query results in array (used to get userInfo)
	public String[] saveQueryResults(String query) throws SQLException
	{
		String userInfo[]; 
		try
		{
		resultSet=statement.executeQuery(query);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		userInfo = new String[columnsNumber];
		while(resultSet.next()) {

		{
		for(int i = 1 ; i<=columnsNumber ; i++)
		{
			String columnValue = resultSet.getString(i);
			userInfo[i-1] = columnValue ;
			}
		}
		}
}
		catch(Exception e) {throw e;}
		return userInfo;
		
}
	
	//Update Tables with query
	public void updateData(String query) throws SQLException
	{
		try {
		statement.executeUpdate(query);
		
		}
	
		catch(Exception e) {
			throw e;
		}
		
		
	}
	
	
	//Print out query results in table
	public boolean writeResultSet(String query) throws SQLException
	{
		boolean nodata = false;
		try {
		resultSet=statement.executeQuery(query);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		
		if (!resultSet.isBeforeFirst() ) {    
		    nodata = true; 
		} 
		
		while(resultSet.next()) {	
			for(int i = 1; i<= columnsNumber; i++){
				
				if(i>1) System.out.print(",  ");
				String columnValue = resultSet.getString(i);
				System.out.print( rsmd.getColumnName(i)+ ":  " +columnValue);
			}
			
			System.out.println("");
		}
		
		return nodata;
		}
		catch(Exception e) {
			throw e;
			
		}
		
	
	}
	
	//Print out query result without column titles
	public void writeNoColumnTitle(String query) throws SQLException
	{
		int j=1;
		try {
		resultSet=statement.executeQuery(query);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		
		while(resultSet.next()) {
			for(int i = 1; i<= columnsNumber; i++){
				
				if(i>1) System.out.print(",  ");
				String columnValue = resultSet.getString(i);
				System.out.print(j + ". "+ columnValue);
			}
			System.out.println("");
			j++;
		}
		}
		catch(Exception e) {
			throw e;
		}
		finally{
			close();
		}
	}
	
	
	//Close resultSet and database connection
	public void close()
	{
		try{
			if(resultSet != null){resultSet.close();}
			if(statement!=null) {statement.close();}
			if(connection!=null){ connection.close();}
		}
		catch(Exception e){}	
	}
}
	
	
	

