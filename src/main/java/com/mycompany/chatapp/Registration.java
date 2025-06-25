package com.mycompany.chatapp;
import javax.swing.JOptionPane;

public class Registration {
    
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        
        // Create a Login object to handle user registration and login
        Login user = new Login();
        
        // Start the registration process by collecting input from the user
        user.welcomeInput();
        
        // Display a message to the user after registration attempt
        JOptionPane.showMessageDialog(null, user.registerUser());
        
        // Request and validate the user's cellphone number
        user.returnCellPhoneNumber();
        
        JOptionPane.showMessageDialog(null, user.returnLoginStatus());
        
        loggedIn = true;

        if (loggedIn) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            Message.runApp();
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Cannot send messages.");
        }
    }
}