package com.mycompany.chatapp;

import java.util.Random;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import java.util.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;


public final class Message {
    
    // Static variables to track messages and arrays
    public static int totalMessagesSent = 0;
    public static List<Message> messageList = new ArrayList<>();

    // Part 3 arrays
    public static List<Message> sentMessages = new ArrayList<>();
    public static List<Message> disregardedMessages = new ArrayList<>();
    public static List<Message> storedMessages = new ArrayList<>();
    public static List<String> messageHashes = new ArrayList<>();
    public static List<String> messageIDs = new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String message;
    private final String messageHash;

    // Constructor initializes the message and generates ID and hash
    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID as a String
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<10; i++) {
            sb.append(random.nextInt(10)); // append digit 0-9
        }
        return sb.toString();
    }

    // Checks that the message ID is not null and exactly 10 digits
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // Validates the recipient phone number: must be <=13 characters and start with '+'
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("\\+27\\d{9}");
    }

    // Creates a hash representation of the message using ID, number, and content
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Returns a formatted String with message details
    public String printMessage() {
        return "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + message;
    }

    // Getter methods
    public String getMessage() {
        return message;
    }
    public String getRecipient() {
        return recipient;
    }
    public String getMessageID() {
        return messageID;
    }
    public String getMessageHash() {
        return messageHash;
    }
    public int getMessageNumber() {
        return messageNumber;
    }

    // Message length validation: max 250 chars
    public String validateMessageLength() {
        int length = message.length();
        if (length <= 250) {
            return "Message ready to send.";
        } else {
            int excess = length - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }
    
    // Converts this Message object into a JSON string using Gson
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    // Static method to recreate a Message object from JSON string
    public static Message fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Message.class);
    }
    
    // Stores the current message object to a JSON file
    public boolean writeMessageToFile(String fileName) {
        Gson gson = new Gson(); 
        try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(gson.toJson(this));
                return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    // Wrapper with GUI – use only inside runApp()
    public void storeMessageToFileWithDialog() {
        String fileName = "message_" + messageID + ".json";
        if (writeMessageToFile(fileName)) {
            JOptionPane.showMessageDialog(null, "Message stored to file:\n" + fileName);
        } else {
            JOptionPane.showMessageDialog(null, "Error saving message.");
        }
    }
    
    
        // Reads all stored messages from JSON files in current directory
    public static void loadStoredMessagesFromFiles() {
        storedMessages.clear(); // Clear old list
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.matches("message_\\d{10}\\.json"));

        if (files != null) {
            Gson gson = new Gson();
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    Message m = gson.fromJson(br, Message.class);
                    if (m != null) {
                        storedMessages.add(m);
                    }
                } catch (IOException e) {
                    
                }
            }
        }
    }
    
    
    
    
    // Runs the interactive app with menu options
    public static void runApp() {
        
        // Load stored messages once at startup
        loadStoredMessagesFromFiles();
        
        boolean running = true;
        while (running) {
            String menuOption = JOptionPane.showInputDialog(null,
                    "Choose an option:" +
                            "\n1) Send Messages" +
                            "\n2) Show Recently Sent Messages" +
                            "\n3) Quit",
                    "QuickChat Menu", JOptionPane.QUESTION_MESSAGE);

            if (menuOption == null) { // Cancel pressed
                break;
            }

            switch (menuOption) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    showSentMessagesMenu();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please select 1, 2, or 3.");
                    break;
            }
        }
    }

    // Core logic to collect, validate, and send/store messages
    public static void sendMessages() {
        // Ask how many messages user wants to send
        String input = JOptionPane.showInputDialog("Enter number of messages to send:");
        if (input == null) return;

        int numMessages = 0;
        try {
            numMessages = Integer.parseInt(input);
            if (numMessages <= 0) {
                JOptionPane.showMessageDialog(null, "Number must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number entered.");
            return;
        }

         // Loop through message 
        for (int i = 1; i <= numMessages; i++) {
            
            String recipient = JOptionPane.showInputDialog("Enter recipient " + i + "'s number (include international code, max 13 chars):");
            if (recipient == null) return;
            
            if (!recipient.matches("\\+27\\d{9}")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid cell phone number.\nMust start with +27 and be followed by exactly 9 digits.\nExample: +27831234567");
                i--; // Retry this message
                continue;
            }
           


            String messageText = JOptionPane.showInputDialog("Enter message (max 250 characters):");
            if (messageText == null) return;

            Message message = new Message(i, recipient, messageText);

            // Validate recipient
            if (!message.checkRecipientCell()) {
                JOptionPane.showMessageDialog(null,
                        "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                i--; // allow to re-enter this message
                continue;
            }

            // Validate message length
            String validationMsg = message.validateMessageLength();
            if (!validationMsg.equals("Message ready to send.")) {
                JOptionPane.showMessageDialog(null, validationMsg);
                i--; // re-enter this message
                continue;
            }

            // Ask send options
            String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Choose an action for the message:",
                    "Send Message",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0: // Send message
                    messageList.add(message);
                    sentMessages.add(message);
                    messageHashes.add(message.getMessageHash());
                    messageIDs.add(message.getMessageID());
                    totalMessagesSent++;
                    JOptionPane.showMessageDialog(null, "Message sent.\n\n" + message.printMessage());
                    break;
                case 1: // Disregard
                    disregardedMessages.add(message);
                    JOptionPane.showMessageDialog(null, "Message disregarded and not saved");
                    break;
                case 2: // Store message to send later
                    message.storeMessageToFileWithDialog();
                    JOptionPane.showMessageDialog(null, "Message successfully stored to send later:\n\n"
                            + "Message ID: " + message.getMessageID() + "\n"
                            + "Message Number: " + message.getMessageNumber() + "\n"
                            + "Recipient: " + message.getRecipient() + "\n"
                            + "Message: " + message.getMessage() + "\n"
                            + "Message Hash: " + message.getMessageHash());
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "No valid option selected. Message disregarded.");
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
    }
    
    
    
    
    
        // The menu to show sent messages and other Part 3 features
    public static void showSentMessagesMenu() {
        String menu = "Choose a report option:\n"
                + "1) Display sender and recipient of all sent messages\n"
                + "2) Display the longest sent message\n"
                + "3) Search for a message ID\n"
                + "4) Search messages by recipient\n"
                + "5) Delete a message using message hash\n"
                + "6) Display full sent messages report\n"
                + "7) Return to Main Menu";

        while (true) {
            String option = JOptionPane.showInputDialog(null, menu, "Sent Messages Menu", JOptionPane.QUESTION_MESSAGE);
            if (option == null || option.equals("7")) break;

            switch (option) {
                case "1":
                    displaySenderAndRecipient();
                    break;
                case "2":
                    displayLongestMessage();
                    break;
                case "3":
                    searchMessageID();
                    break;
                case "4":
                    searchMessagesByRecipient();
                    break;
                case "5":
                    deleteMessageByHash();
                    break;
                case "6":
                    displayFullSentMessagesReport();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option, please choose 1-7.");
            }
        }
    }
    
        // 1) Display sender (assumed developer here) and recipient of all sent messages
    public static void displaySenderAndRecipient() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sender and Recipient of Sent Messages:\n");
        for (Message m : sentMessages) {
            // Assuming "developer" as sender for test data; else could add sender field if needed
            sb.append("Sender: Developer\nRecipient: ").append(m.getRecipient()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
        // 2) Display the longest sent message
    public static void displayLongestMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available.");
            return;
        }

        Message longest = sentMessages.get(0);
        for (Message m : sentMessages) {
            if (m.getMessage().length() > longest.getMessage().length()) {
                longest = m;
            }
        }

        JOptionPane.showMessageDialog(null, "Longest sent message:\n" + longest.getMessage());
    }
    
        // 3) Search for a message ID and display recipient and message
    public static void searchMessageID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (searchID == null || searchID.trim().isEmpty()) return;

        for (Message m : sentMessages) {
            if (m.getMessageID().equals(searchID)) {
                JOptionPane.showMessageDialog(null,
                        "Message found:\nRecipient: " + m.getRecipient() +
                                "\nMessage: " + m.getMessage());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }
    
    
       // 4) Search all messages (sent + stored) by recipient and display their messages
    public static void searchMessagesByRecipient() {
        String recipientSearch = JOptionPane.showInputDialog("Enter recipient number to search messages:");
        if (recipientSearch == null || recipientSearch.trim().isEmpty()) return;

        List<String> foundMessages = new ArrayList<>();

        // Search sent messages
        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipientSearch)) {
                foundMessages.add(m.getMessage());
            }
        }
        // Search stored messages
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipientSearch)) {
                foundMessages.add(m.getMessage());
            }
        }

        if (foundMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient: " + recipientSearch);
        } else {
            StringBuilder sb = new StringBuilder("Messages for recipient " + recipientSearch + ":\n");
            for (String msg : foundMessages) {
                sb.append("- ").append(msg).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // 5) Delete a message using the message hash (from sent messages)
    public static void deleteMessageByHash() {
        String hashToDelete = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) return;

        Iterator<Message> iterator = sentMessages.iterator();
        boolean deleted = false;
        while (iterator.hasNext()) {
            Message m = iterator.next();
            if (m.getMessageHash().equals(hashToDelete)) {
                iterator.remove();
                messageHashes.remove(hashToDelete);
                messageIDs.remove(m.getMessageID());
                deleted = true;
                JOptionPane.showMessageDialog(null, "Message deleted successfully.");
                break;
            }
        }
        if (!deleted) {
            JOptionPane.showMessageDialog(null, "Message hash not found.");
        }
    }

    // 6) Display a full report of all sent messages
    public static void displayFullSentMessagesReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to report.");
            return;
        }

        StringBuilder report = new StringBuilder("Full Sent Messages Report:\n\n");
        for (Message m : sentMessages) {
            report.append(m.printMessage()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }
    
    
    
    
}

    
    
