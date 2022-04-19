
// Create Table in Sql Database and connect through this Java code.

package movie_management;

import java.sql.*;
import java.util.*;

public class MovieManagement {

	private static Connection connection=null;
	private static Scanner scanner =new Scanner(System.in);
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieManagement sd=new MovieManagement();
		
		try
		{
          Class.forName("com.mysql.cj.jdbc.Driver");
          
          String dbURL="jdbc:mysql://localhost:3306/moviemanagementdb";
          String username="root";
          String password="root";
          
          connection=DriverManager.getConnection(dbURL,username,password);
          
          System.out.println("Enter your Choice: ");
          System.out.println("1. Insert Records:");
          System.out.println("2. Select Records: ");
          System.out.println("3. Update Record: ");
          System.out.println("4. Delete Record: ");
 
          
          int choice=Integer.parseInt(scanner.nextLine());
          switch(choice)
          {
          case 1:
        	  sd.insertRecord();
        	  break;
          case 2:
        	  sd.selectRecord();
        	  break;
          case 3:
        	  sd.updateRecord();
        	  break;
          case 4:
        	  sd.deleteRecord();
        	  break;	  
          default:
        	   break;
          }
          
          
		}catch(Exception e){
			throw new RuntimeException("Something went wrong");
		}
	}
	
	private  void insertRecord() throws SQLException {
		System.out.println("<-------------------------->");
		
		//String sql="insert into student (name,percentage,address) values ('Asif',85.5,'Mumbai')";
		String sql="insert into movie (movie_name,movie_budget,lead_actor_malename,lead_actor_male_fee) values (?,?,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);

		System.out.println("Enter Movie name:");
		preparedStatement.setString(1, scanner.nextLine());
		
		System.out.println("Enter Movie_Budget:");
		preparedStatement.setInt(2, Integer.parseInt(scanner.nextLine()));
		
		System.out.println("Enter Lead_Actor_Malename:");
		preparedStatement.setString(3, scanner.nextLine());
		
		System.out.println("Enter Lead_Actor_MaleFees:");
		preparedStatement.setInt(4, Integer.parseInt(scanner.nextLine()));

		
		
		
		int rows=preparedStatement.executeUpdate();
		
		if(rows>0)
		{
			System.out.println("Record insert Successfully");
		}
	}
	
	public void selectRecord() throws SQLException
	{
		System.out.println("Enter movie id number to find result");
		int number=Integer.parseInt(scanner.nextLine());
		
		Statement statement=connection.createStatement();
		String sql="select * from movie where movie_id = "+number;
		
		ResultSet result=statement.executeQuery(sql);
		
		if(result.next())
		{
			int movie_id=result.getInt("movie_id");
			String movie_name=result.getString("movie_name");
			int movie_budget=result.getInt("movie_budget");
			String lead_actor_malename=result.getString("lead_actor_malename");
			int lead_actor_male_fee=result.getInt("lead_actor_male_fee");
			
			System.out.println("Movie Id Number is: "+movie_id);
			System.out.println("Movie Name is: "+movie_name);
			System.out.println("Movie Budget is:"+movie_budget);
			System.out.println("Lead Actor Male name is: "+lead_actor_malename);
			System.out.println("Movie Actor male fee is: "+lead_actor_male_fee);
		}else
		{
			System.out.println("Result Not Found......");
		}
	}
	
	public void updateRecord() throws SQLException{
		System.out.println("Enter movie id number to update record:");
		int number=Integer.parseInt(scanner.nextLine());
		
		String sql="select * from movie where movie_id= "+number;
		
		Statement statement=connection.createStatement();
		ResultSet result=statement.executeQuery(sql);
		if(result.next())
		{
			int movie_id=result.getInt("movie_id");
			String movie_name=result.getString("movie_name");
			int movie_budget=result.getInt("movie_budget");
			String lead_actor_malename=result.getString("lead_actor_malename");
			int lead_actor_male_fee=result.getInt("lead_actor_male_fee");
		   
			System.out.println("Movie Id Number is: "+movie_id);
			System.out.println("Movie Name is: "+movie_name);
			System.out.println("Movie Budget is:"+movie_budget);
			System.out.println("Lead Actor Male name is: "+lead_actor_malename);
			System.out.println("Movie Actor male fee is: "+lead_actor_male_fee);
		   
		   System.out.println("What do you want to update?");
		   System.out.println("1. Movie_Name:");
		   System.out.println("2. Movie Budget:");
		   System.out.println("3. Lead actor male name:");
		   System.out.println("4. Lead actor male fee:");
		   
		   
		   int choice=Integer.parseInt(scanner.nextLine());
		   
		   String sqlQuery="update movie set ";
		   switch(choice) {
		   case 1:
			   System.out.println("Enter new Movie name: ");
			   String newName=scanner.nextLine();
			   sqlQuery=sqlQuery+ "movie_name= ? where movie_id = "+movie_id;
			   PreparedStatement preparedStatement= connection.prepareStatement(sqlQuery);
			   preparedStatement.setString(1, newName);
			   
			   int rows=preparedStatement.executeUpdate();
			   if(rows>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("Movie Name not update..");
			   }
	
			   break;
		   case 2:
			   System.out.println("Enter new Movie Budget: ");
			   int  budget=Integer.parseInt(scanner.nextLine());
			   
			   sqlQuery=sqlQuery+ "movie_budget= ? where movie_id = "+movie_id;
			   
			   PreparedStatement preparedStatement1= connection.prepareStatement(sqlQuery);
			   preparedStatement1.setInt(1, budget);
			   
			   int rows2=preparedStatement1.executeUpdate();
			   if(rows2>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("Budget not update..");
			   }
			   break;
		   case 3:
			   System.out.println("Enter new Actor male name: ");
			   String newname=scanner.nextLine();
			   sqlQuery=sqlQuery+ "lead_actor_malename= ? where movie_id = "+movie_id;
			   PreparedStatement preparedStatement2= connection.prepareStatement(sqlQuery);
			   preparedStatement2.setString(1, newname);
			   
			   int rows3=preparedStatement2.executeUpdate();
			   if(rows3>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("Name not update..");
			   }
			   break;
		   case 4:
			   System.out.println("Enter new lead Actor Male Budget: ");
			   int  lead_budget=Integer.parseInt(scanner.nextLine());
			   
			   sqlQuery=sqlQuery+ "lead_actor_male_fee= ? where movie_id = "+movie_id;
			   
			   PreparedStatement preparedStatement3= connection.prepareStatement(sqlQuery);
			   preparedStatement3.setInt(1, lead_budget);
			   
			   int rows4=preparedStatement3.executeUpdate();
			   if(rows4>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("lead Budget not update..");
			   }
			   break;
			   
		   default:
			   System.out.println("invalid Choice ");
			   break;
				   
		   }
		   
		}else {
			System.out.println("Records Not Found.....");
		}
	}
	
	public void deleteRecord() throws SQLException
	{
		System.out.println("Enter movie id number to delete record: ");
		int roll=Integer.parseInt(scanner.nextLine());
		
		Statement statement=connection.createStatement();
		String sql="delete from movie where movie_id= "+roll;
		
		int rows=statement.executeUpdate(sql);
		if(rows>0)
		{
			System.out.println("Record Delete Successfully....");
		}else
		{
			System.out.println("Record not deleted...");
		}
	}

}