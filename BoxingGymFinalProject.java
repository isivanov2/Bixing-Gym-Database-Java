//Student: Ivan Ivanov
//Professor: Rachel Adler
//CS315

import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;
public class BoxingGymFinalProject {

   public static void main(String[]args){
   	
   	Connection conn = null;
   	
   	try {
       	conn =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/cs315_fall2020?serverTimezone=UTC&" +
                                      "user=root&password=root");
   
         Statement stmt = conn.createStatement();
         Scanner kbd = new Scanner(System.in);
         System.out.print("-----------------------------------\n" +
                          "Welcome to the Boxing Gym Database!\n" +
                          "-----------------------------------\n");
         int num = 0;                 
                   
         do
         {
            System.out.println("Hello please select one of the following options." + "\nIn order to do that please enter the number corresponding to the opton you wish to use.\n");
            System.out.println("Enter 1 to display all gym members.");
            System.out.println("Enter 2 to add a new gym member.");
            System.out.println("Enter 3 to update the status of an existing gym member.");
            System.out.println("Enter 4 to delete existing gym member.");
            System.out.println("Enter 5 to display the complete weekly gym schedule.");
            System.out.println("Enter 6 to find out when each of our classes is offered.");
            System.out.println("Enter 0 to Exit.");
            
            System.out.print("Please enter your selection:");
            num = kbd.nextInt();
            System.out.println();
            
            if(num == 1)
            {
               showMembers(stmt);
            }
            else if(num == 2)
            {
               addMembers(stmt);
            }
            else if(num == 3)
            {
               updateMembers(stmt);
            }

            else if(num == 4)
            {
               deleteMembers(stmt);
            }
  
            else if(num == 5)
            {
               showGymSchedule(stmt);
            }
            else if(num == 6)
            {
               showClassSchedule(stmt);
            }
            
         }while(num !=0);                       
         

         System.out.print("----------------------------------------------------------------------\n" +
                          "Thank you for using our Boxing Gym Database feature. Have a great day!\n" +
                          "----------------------------------------------------------------------\n");
            
   	} catch (SQLException ex) {
       // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
   	}
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only numeric values for your input.");
         System.out.println();
      }
   }
   
   //Display gym members
   public static void showMembers(Statement stmt)
   {  
      System.out.println("================================================================================================================================");
      System.out.println();
      try
      {
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------");      
         System.out.format("%-15s%-15s%-15s%-35s%-30s%-10s%-10s%n", "Member ID", "First Name", "Last Name", "Street", "City", "State", "Status");
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------"); 
         
         String sql = "SELECT * FROM gymMembers";
         ResultSet rs = stmt.executeQuery(sql);
            
         while(rs.next())
         {
            int id = rs.getInt("memberId");
            String fname = rs.getString("fName");
            String lname = rs.getString("lName");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String state = rs.getString("state");
            String status = rs.getString("status");
            
            System.out.format("%-15s%-15s%-15s%-35s%-30s%-10s%-10s%n", id, fname, lname, street, city, state, status);
         }
         rs.close();
   
         
         
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
         System.out.println();    
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only numeric values for your input.");
         System.out.println();
      }
      System.out.println("================================================================================================================================");
      
                  
   }
   
   //Add new gym member
   public static void addMembers(Statement stmt)
   {
      System.out.println("================================================================================================================================");
      Scanner kbd = new Scanner(System.in);
      try
      {
         System.out.println("Insert records into the Gym Members table:");

       
         System.out.print("Insert member ID number:");
         int memberId = kbd.nextInt();
         kbd.nextLine();
       
         System.out.print("Insert first name:");
         String fName = kbd.nextLine();
       
         System.out.print("Insert last name:");
         String lName = kbd.nextLine();
       
         System.out.print("Insert address Street:");
         String street = kbd.nextLine();
         
         System.out.print("Insert address City:");
         String city = kbd.nextLine();
         
         System.out.print("Insert adress State:");
         String state = kbd.nextLine();   
         
         System.out.print("Insert membership status(Active/Inactive/On Hold):");
         String memberStatus = kbd.nextLine();
                
         String sql ="INSERT INTO gymMembers VALUES(" +memberId+ ", '" +fName+ "', '" +lName+ "', '" +street+ "', '" +city+ "', '" +state+ "', '" +memberStatus+ "')";
         stmt.executeUpdate(sql);
         System.out.println();
         System.out.print("---------------------------------------------------------------------------\n" +
                          "Member: " + fName + " " + lName + " was successfully registered in our gym.\n" +
                          "----------------------------------------------------------------------------\n");
         System.out.println();
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only the required input format.");
         System.out.println();
      }
      System.out.println("================================================================================================================================");
   }
  
   //Update status of existing gym member
   public static void updateMembers(Statement stmt)
   {
      System.out.println("================================================================================================================================");
      Scanner kbd = new Scanner(System.in);
      try
      {

         System.out.print("Please enter the ID number of the gym member you wish to update the status of: ");
         int memberId = kbd.nextInt();
         kbd.nextLine();
         int num;
         
         do
         {
            System.out.println("Please enter 1 to update the status of a member with ID number: " + memberId + " to Active.");
            System.out.println("Please enter 2 to update the status of a member with ID number: " + memberId + " to Inactive.");
            System.out.println("Please enter 3 to update the status of a member with ID number: " + memberId + " to On Hold.");
            System.out.print("Please enter your selection:");
            num = kbd.nextInt();
            
         }while(num > 3 || num<1);
         
         String memberStatus = "";
         if(num==1)
         {
            memberStatus = "Active";
         }
         else if(num==2)
         {
            memberStatus = "Inactive";
         }
         else if(num==3)
         {
            memberStatus = "On Hold";
         }
                
         String sql ="UPDATE gymMembers SET status = '" +memberStatus+ "' WHERE memberId = " +memberId+ "";
         stmt.executeUpdate(sql);
         System.out.println();
         System.out.print("---------------------------------------------------------------------------------\n" +
                          "The status of a member with ID number: " + memberId + " was successfully updated.\n" +
                          "---------------------------------------------------------------------------------\n");
         System.out.println();
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only the required input format.");
         System.out.println();
      }
      System.out.println("================================================================================================================================");

   }
   
   //Delete existing gym member
   public static void deleteMembers(Statement stmt)
   {
      System.out.println("================================================================================================================================");
      Scanner kbd = new Scanner(System.in);
      try
      {

         System.out.print("Please enter the ID number of the gym member you wish to be removed from our database: ");
         int memberId = kbd.nextInt();
         kbd.nextLine();
         
                
         String sql ="DELETE FROM gymMembers WHERE memberId = " +memberId+ "";
         stmt.executeUpdate(sql);
         System.out.println();
         System.out.print("---------------------------------------------------------------------------------------\n" +
                          "Member with ID number: " + memberId + " was successfully removed from our gym database.\n" +
                          "---------------------------------------------------------------------------------------\n");
         System.out.println();
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only the required input format.");
         System.out.println();
      }
      System.out.println("================================================================================================================================");
   }

   //Display the full weekly gym schedule using view
   public static void showGymSchedule(Statement stmt)
   {  
      System.out.println("================================================================================================================================");
      System.out.println();
      try
      {
         System.out.println("--------------------------------------------------------------------------------");      
         System.out.format("%-10s%-10s%-15s%-25s%-25s%n", "Day", "Time", "Class Title", "Instructor First Name", "Instructor Last Name");
         System.out.println("--------------------------------------------------------------------------------"); 
         
         String sql = "SELECT * FROM FullScheduleView";
         ResultSet rs = stmt.executeQuery(sql);
            
         while(rs.next())
         {
            String cDay = rs.getString("day");
            String cTime = rs.getString("classTime");
            String cTitle = rs.getString("classTitle");
            String fname = rs.getString("fName");
            String lname = rs.getString("lName");
            
            System.out.format("%-10s%-10s%-15s%-25s%-25s%n", cDay, cTime, cTitle, fname, lname);
         }
         rs.close();
   
         
         
         System.out.println("--------------------------------------------------------------------------------");
         System.out.println();    
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      System.out.println("================================================================================================================================");
                  
   }
   
   //Display the days and time of every class the gym offers.
   public static void showClassSchedule(Statement stmt)
   {  
      System.out.println("================================================================================================================================");
      try
      {
         Scanner kbd = new Scanner(System.in);
         System.out.println("Hello please select one of the following options." + "\nIn order to do that please enter the number coresponding to the opton you wish to use.\n");
         System.out.println("Enter 1 to view the day and time for all Boxing classes offered in our gym.");
         System.out.println("Enter 2 to view the day and time for all Kickboxing classes offered in our gym.");
         System.out.println("Enter 3 to view the day and time for all MMA classes offered in our gym.");
         System.out.println("Enter 4 to view the day and time for all CrossFit classes offered in our gym.");
         System.out.println("Enter 5 to view the day and time for all Jiu Jitus classes offered in our gym..");
         System.out.println("Enter 0 to Exit");
         
         
         System.out.print("Please enter your selection:");
         int num_class = kbd.nextInt();
         System.out.println();
         String str_class;
         
         
         if(num_class==1)
         {
            str_class = "Boxing";
         }
         else if(num_class==2)
         {
            str_class = "Kickboxing";
         }
         else if(num_class==3)
         {
            str_class = "MMA";
         }
         else if(num_class==4)
         {
            str_class = "CrossFit";
         }
         else if(num_class==5)
         {
            str_class = "Jiu Jitsu";
         }
         else
         {
            return;
         }


                         
         System.out.println("-------------------------------");      
         System.out.format("%-10s%-10s%-15s%n", "Day", "Time", "Class Title");
         System.out.println("-------------------------------"); 
         
         String sql = "SELECT classTitle, day, classTime FROM gymSchedule s, gymClasses c WHERE s.classId=c.classId AND classTitle = '" +str_class+ "'";
         ResultSet rs = stmt.executeQuery(sql);
            
         while(rs.next())
         {
            String cDay = rs.getString("day");
            String cTime = rs.getString("classTime");
            String cTitle = rs.getString("classTitle");
            
            System.out.format("%-10s%-10s%-15s%n", cDay, cTime, cTitle);
         }
         rs.close();
   
         
         
         System.out.println("-------------------------------");
         System.out.println();    
      }
      catch  (SQLException ex)
      {
         // handle any errors
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(InputMismatchException ex)
      {
         System.out.println();
         System.out.println("Please use only numeric values for your input.");
         System.out.println();
      }
      System.out.println("================================================================================================================================");
                  
   }

}