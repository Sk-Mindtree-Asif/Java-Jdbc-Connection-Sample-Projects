// Create Table in Sql Database and connect through this Java code.

package com.jdbc.student;
import java.util.*;
import java.sql.*;
public class StudentDatabase {
	private static Connection connection=null;
	private static Scanner scanner =new Scanner(System.in);
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StudentDatabase sd=new StudentDatabase();
		
		try
		{
          Class.forName("com.mysql.cj.jdbc.Driver");
          
          String dbURL="jdbc:mysql://localhost:3306/jdbcdb";
          String username="root";
          String password="root";
          
          connection=DriverManager.getConnection(dbURL,username,password);
          
          System.out.println("Enter your Choice: ");
          System.out.println("1. Insert Records:");
          System.out.println("2. Select Records: ");
          System.out.println("3. callable statement: select record ");
          System.out.println("4. Caalable Statement: Select record by roll number ");
          System.out.println("5. Update Record: ");
          System.out.println("6. Delete Record: ");
          
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
        	  sd.selectAllRecords();
        	  break;
          case 4:
        	  sd.selectRecordsByRollNumber();
        	  break;
          case 5:
        	  sd.updateRecord();
        	  break;
          case 6:
        	  sd.deleteRecord();
        	  break;
          default:
        	   break;
          }
          
          
		}catch(Exception e){
			throw new RuntimeException("Something went wrong");
		}
	}
	
	//Insert records .....
	private  void insertRecord() throws SQLException {
		System.out.println("<-------------------------->");
		
		//String sql="insert into student (name,percentage,address) values ('Asif',85.5,'Mumbai')";
		String sql="insert into student (name,percentage,address) values (?,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		/*preparedStatement.setString(1, "Aman");
		preparedStatement.setDouble(2, 80.1);
		preparedStatement.setString(3, "Bihar");*/
		System.out.println("Enter name:");
		preparedStatement.setString(1, scanner.nextLine());
		
		System.out.println("Enter Percentage:");
		preparedStatement.setDouble(2, Double.parseDouble(scanner.nextLine()));
		
		System.out.println("Enter Address:");
		preparedStatement.setString(3, scanner.nextLine());
		
		
		
		int rows=preparedStatement.executeUpdate();
		
		if(rows>0)
		{
			System.out.println("Record insert Successfully");
		}
	}
	//normal fetch record..
	public void selectRecord() throws SQLException
	{
		System.out.println("Enter roll number to find result");
		int number=Integer.parseInt(scanner.nextLine());
		
		Statement statement=connection.createStatement();
		String sql="select * from student where roll_number= "+number;
		
		ResultSet result=statement.executeQuery(sql);
		
		if(result.next())
		{
			int rollNumber=result.getInt("roll_number");
			String name=result.getString("name");
			double percentage=result.getDouble("percentage");
			String address=result.getString("address");
			System.out.println("Roll Number is: "+rollNumber);
			System.out.println("Name is: "+name);
			System.out.println("Percentage is: "+percentage);
			System.out.println("Address is: "+address);
		}else
		{
			System.out.println("Result Not Found......");
		}
	}
	
	//stored procedure without argument.....
	private void selectAllRecords() throws SQLException
	{
		CallableStatement callableStatement=connection.prepareCall("{ call GET_ALL() }");
		ResultSet result=callableStatement.executeQuery();
		
		while(result.next())
		{
			System.out.println("Roll number is: "+result.getInt("roll_number"));
			System.out.println("Name is: "+result.getString("name"));
			System.out.println("Percentage is: "+result.getDouble("percentage"));
			System.out.println("Address is: "+result.getString("address"));
		}
	}
	
	// stored procedure with Argument...
	private void selectRecordsByRollNumber() throws SQLException
	{
		System.out.println("Enter roll number to fetch details: ");
		int roll=Integer.parseInt(scanner.nextLine());
		
		CallableStatement callableStatement=connection.prepareCall("{ call GET_RECORD(?) }");
		callableStatement.setInt(1, roll);
		
		ResultSet result=callableStatement.executeQuery();
		
		while(result.next())
		{
			System.out.println("Roll number is: "+result.getInt("roll_number"));
			System.out.println("Name is: "+result.getString("name"));
			System.out.println("Percentage is: "+result.getDouble("percentage"));
			System.out.println("Address is: "+result.getString("address"));
		}
	}
	//update records...
	public void updateRecord() throws SQLException{
		System.out.println("Enter roll number to update record:");
		int roll=Integer.parseInt(scanner.nextLine());
		
		String sql="select * from movie where movie_id= "+roll;
		
		Statement statement=connection.createStatement();
		ResultSet result=statement.executeQuery(sql);
		if(result.next())
		{
		   int rollNumber=result.getInt("roll_number");
		   String name=result.getString("name");
		   double percentage=result.getDouble("percentage");
		   String address=result.getString("address");
		   
		   System.out.println("Roll number is: "+rollNumber);
		   System.out.println("Name is:"+name);
		   System.out.println("Percentage is: "+percentage);
		   System.out.println("Roll number is: "+address);
		   
		   System.out.println("What do you want to update?");
		   System.out.println("1. Name");
		   System.out.println("2. Percentage");
		   System.out.println("3. Address");
		   
		   int choice=Integer.parseInt(scanner.nextLine());
		   
		   String sqlQuery="update student set ";
		   switch(choice) {
		   case 1:
			   System.out.println("Enter new name: ");
			   String newName=scanner.nextLine();
			   sqlQuery=sqlQuery+ "name= ? where roll_number = "+rollNumber;
			   PreparedStatement preparedStatement= connection.prepareStatement(sqlQuery);
			   preparedStatement.setString(1, newName);
			   
			   int rows=preparedStatement.executeUpdate();
			   if(rows>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("Name not update..");
			   }
	
			   break;
		   case 2:
			   System.out.println("Enter new percentage: ");
			   double newPercentage=Double.parseDouble(scanner.nextLine());
			   
			   sqlQuery=sqlQuery+ "percentage= ? where roll_number = "+rollNumber;
			   
			   PreparedStatement preparedStatement1= connection.prepareStatement(sqlQuery);
			   preparedStatement1.setDouble(1, newPercentage);
			   
			   int rows2=preparedStatement1.executeUpdate();
			   if(rows2>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("percentage not update..");
			   }
			   break;
		   case 3:
			   System.out.println("Enter new address: ");
			   String newAddress=scanner.nextLine();
			   sqlQuery=sqlQuery+ "address= ? where roll_number = "+rollNumber;
			   PreparedStatement preparedStatement2= connection.prepareStatement(sqlQuery);
			   preparedStatement2.setString(1, newAddress);
			   
			   int rows3=preparedStatement2.executeUpdate();
			   if(rows3>0)
			   {
				   System.out.println("Record updated Successfully...");
			   }else
			   {
				   System.out.println("Name not update..");
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
	
	//delete records....
	public void deleteRecord() throws SQLException
	{
		System.out.println("Enter roll number to delete record: ");
		int roll=Integer.parseInt(scanner.nextLine());
		
		Statement statement=connection.createStatement();
		String sql="delete from student where roll_number= "+roll;
		
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
