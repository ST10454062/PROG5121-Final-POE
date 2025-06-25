package com.mycompany.chatapp;
import javax.swing.JOptionPane;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String registeredPhoneNumber;
    private String loginUsername;
    private String loginPassword;
    private String name;
    private String surname;
    
    // Default constructor
    public Login(){ 
    }
    
    // Overloaded constructor to initialize registration and login data
    public Login(String registeredUsername, String registeredPassword, String registeredPhoneNumber, String loginUsername, 
            String loginPassword, String loginPhoneNumber, String name, String surname){
        this.registeredPassword = registeredPassword;
        this.registeredUsername = registeredUsername;
        this.registeredPhoneNumber = registeredPhoneNumber;
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.name = name;
        this.surname = surname;
    }
    
    
        //Setters
    public void setRegisteredUsername(String registeredUsername) {
        this.registeredUsername = registeredUsername;
    }
    public void setRegisteredPassword(String registeredPassword) {
        this.registeredPassword = registeredPassword;
    }
    public void setRegisteredPhoneNumber(String registeredPhoneNumber) {
        this.registeredPhoneNumber = registeredPhoneNumber;
    }
    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    //getters
    public String getRegisteredUsername() {
        return registeredUsername;
    }
    public String getRegisteredPassword() {
        return registeredPassword;
    }
    public String getRegisteredPhoneNumber() {
        return registeredPhoneNumber;
    }
    public String getLoginUsername() {
        return loginUsername;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    
    // Method to welcome the user and collect their registration details (username and password)
    public void welcomeInput(){
        // Display a welcome message
        JOptionPane.showMessageDialog(null, "Welcome to the Chat APP!");
        JOptionPane.showMessageDialog(null, "Let's get you registered.");

        registeredUsername = JOptionPane.showInputDialog("Enter your username");
        if (registeredUsername == null){  
        // If the user cancels the input, exit the program
            JOptionPane.showMessageDialog(null, "Registration cancelled.");
            System.exit(0);
        }else if (registeredUsername.trim().isEmpty()) {   //(Baeldung, 2024)
        // If the username is empty, show an error message and re-prompt
           JOptionPane.showMessageDialog(null, "username cannot be empty.");
           welcomeInput(); // Recursive call to retry
        }
        
        registeredPassword = JOptionPane.showInputDialog("Enter your password");
        if (registeredPassword == null){  
        // If the user cancels the input, exit the program
            JOptionPane.showMessageDialog(null, "Registration cancelled.");
            System.exit(0);   // (GeeksforGeeks, 2024)
        }else if (registeredPassword.trim().isEmpty()) {
        // If the password is empty, show an error message and re-prompt
           JOptionPane.showMessageDialog(null, "Password cannot be empty.");
           welcomeInput();
        }
    }    
    
    // Method to register the user and ensure the username and password meet the required criteria
    public String registerUser() {
        // Check if the username is correctly formatted (must contain an underscore and be no more than 5 characters)
        if (!checkUserName()) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. \n" +
                    "It must contain an underscore and be no more than 5 characters.");
            registeredUsername = JOptionPane.showInputDialog("Input your username again");

            // If the user cancels or provides an empty username, exit or re-prompt
            if (registeredUsername == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                System.exit(0);
            } else if (registeredUsername.trim().isEmpty()) {
               JOptionPane.showMessageDialog(null, "Username cannot be empty.");
                registerUser(); // Retry
            } else if (!checkUserName()) {
                return registerUser(); // Retry until valid
            }   
        }
        
        // Check if the password meets complexity requirements (at least 8 characters, includes uppercase, number, and special character)
        if (!checkPasswordComplexity()) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted. \n" +
                    "It must be at least 8 characters, contain a capital letter, a number, and a special character.");
            registeredPassword = JOptionPane.showInputDialog("Input your password again");

            // If the user cancels or provides an empty password, exit or re-prompt
            if (registeredPassword == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                System.exit(0);
            } else if (registeredPassword == null || registeredPassword.trim().isEmpty()) {
               JOptionPane.showMessageDialog(null, "Password cannot be empty.");
                registerUser();
            } else if (!checkPasswordComplexity()) {
                return registerUser();
            } 
        }
        // If the registration passes all checks, return success message
        return "User has been successfully registered.";
    }
    
    
    
    // Method to check if the username is correctly formatted (contains an underscore and no more than 5 characters)
    public boolean checkUserName(){
        boolean validate = false;
        if (registeredUsername.contains("_") && registeredUsername.length() <= 5){
            validate = true;
        }
        return validate;
    }       
    
    // Method to check if the password meets complexity requirements (at least 8 characters, uppercase, number, and special character)
    public boolean checkPasswordComplexity(){
        boolean validate = false;
        if (registeredPassword.length() >= 8){
            boolean hasUppercase = registeredPassword.matches(".*[A-Z].*");
            boolean hasNumber = registeredPassword.matches(".*[0-9].*");
            boolean hasSpecial = registeredPassword.matches(".*[^a-zA-Z0-9].*");
            
            if (hasUppercase && hasNumber && hasSpecial){
                validate = true;
            }
        }
        return validate;
    }
    
    // Method to check if the cell phone number is correctly formatted (South African format)
    public boolean checkCellPhoneNumber(){
        boolean validate = false;
        if(registeredPhoneNumber.matches("^\\+27\\d{9}$")){
            validate = true;
        }
        return validate;
    }
    
    // Method to allow the user to add a cell phone number, ensuring the format is correct
    public boolean addCellPhoneNumber(){
        if (registeredPhoneNumber == null) {
           return false;  
        }
        // If the number is incorrectly formatted, prompt the user to re-enter it
        if(!checkCellPhoneNumber()){
           JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code (+27)");
           registeredPhoneNumber = JOptionPane.showInputDialog("Input your Cell phone number again");
           return addCellPhoneNumber(); // Retry until valid
        }else{
           return true;   
        }
    }  
    
    // Method to prompt the user to enter a phone number and validate it
    public String returnCellPhoneNumber(){
        registeredPhoneNumber = JOptionPane.showInputDialog("Add your phone number");
        if(addCellPhoneNumber()){
            return "Cell phone number successfully added";
        }else {
            return "Failed to add your number.";
        }
    }
    
    // Method to handle user login by verifying the username and password entered
    public boolean loginUser(){
        // Prompt the user for their login username
        loginUsername = JOptionPane.showInputDialog("Login with your username");
        if (loginUsername == null || loginUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return loginUser();
        }
        
        // Prompt the user for their login password
        loginPassword = JOptionPane.showInputDialog("Login with your password"); 
        if (loginPassword == null || loginPassword.trim().isEmpty()) {
           JOptionPane.showMessageDialog(null, "Password cannot be empty.");
           return loginUser();
        }
        
        // Check if the entered credentials match the registered details
        else if (loginUsername.equals(registeredUsername) &&
        loginPassword.equals(registeredPassword)){
            return true;// Login successful
        }else {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            return loginUser(); // Retry on failure
        }
    }
    
    // Method to return the login status based on the success or failure of login attempt
    public String returnLoginStatus(){
        JOptionPane.showMessageDialog(null, "Follow the next steps to log-in.");
        name = JOptionPane.showInputDialog("Enter your name.");
        surname = JOptionPane.showInputDialog("Enter your surname.");
        if (loginUser()) {
            return "Welcome " + name + " " + surname + ", it is great to see you again.";
        } else {
            return "Login attempt failed: fields were empty.";
        }
    }
}


/*

References:

Baeldung, 2024. Java: Check if a String is Empty or Blank. [online] Available at: <https://www.baeldung.com/java-blank-empty-strings>.

GeeksforGeeks, 2024. System.exit() in Java with Examples. [online] Available at: <https://www.geeksforgeeks.org/system-exit-in-java/>.

*/