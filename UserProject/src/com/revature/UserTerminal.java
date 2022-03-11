package com.revature;

import java.util.*;

public class UserTerminal {

public static void main (String[] args) {
	
	
	
	boolean Continue = false;
    while (Continue == false) {
	
	System.out.println("Welcome"); 
	

	
	System.out.println("Please Select an Option"); 
	System.out.println(" <3 <3 <3 <3 <3 <3 <3 <3 <3 <3 ");
	System.out.println("1.Display Existing Users"); 
	System.out.println("2.New User");
	System.out.println("3.Edit Existing User");
	System.out.println("4.Delete User"); 
	System.out.println("5.Exit"); 
	System.out.println(" <3 <3 <3 <3 <3 <3 <3 <3 <3 <3 ");
	
	//will print user list 
	
	//new user 
	
	//will edit copy existing user 
	
	//deletes user 
	
	// exit program 
	
	List<String> userList = new ArrayList(); 
	
	Scanner grabber = new Scanner (System.in); 
	
	while (Continue= true) {
		int choice = grabber.nextInt();
		
		String userInput = new String (grabber.nextLine());
		
	int e = userList.size();
	
	switch(choice) {
	 case 1:

	 System.out.println("The Current Users Include:");

	 for(int i = 0; i < e;i++)

	 System.out.println(userList.get(i));

	 break;
	 
		
	 case 2:

	 System.out.println("Enter New User Here:");

	 String Unext =grabber.next();

	 
	 if( userList.contains(Unext)){

	 System.out.println("User Name Not Available");
	 }

	 else
	 userList.add(Unext);


	 break;

	 case 3:

	 System.out.println("Please enter the existing user you wish to change");
	 String Unext2 =grabber.next();
	 if( userList.contains(Unext2)){

	 int u = userList.indexOf(Unext2);

	 System.out.println("New User Name ");

	 String Unext3 =grabber.next();

	 if( userList.contains(Unext3)){

	 System.out.println("User currently exists");
	 }
	 else
	 userList.set(u,Unext3);

	 }
	 else
	 System.out.println("You cannot change a user that doesn't exist");





	 break;

	 case 4:
	 System.out.println("Please enter which user you would like to delete");
	 String Unext4 =grabber.next();
	 if( userList.contains(Unext4)){
	 userList.remove(Unext4);
	 System.out.println("User Removed");}
	 
	 else
	 System.out.println("You cannot remove a user that doesn't exist");

	 break;

	 case 5:
         System.out.println("Thank you!");
         Continue = true; 
       
	 break;

	 default :
	 System.out.println("Failed Entry");

	 break;
	 
	}
	 
	}
 
	 grabber.close();

	 }
	 }


	

	
}
