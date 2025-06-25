
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    public MessageTest() {
    }

    @Test
    public void testValidateMessageLength_Success() {
        Message user = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", user.validateMessageLength());
    }

    @Test
    public void testValidateMessageLength_Failure() {
        String longText = "x".repeat(260);
        Message user = new Message(1, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10, please reduce size.", user.validateMessageLength());
    }

    @Test
    public void testCheckRecipientCell_Success() {
        Message user = new Message(1, "+27718693002", "Hello");
        assertTrue(user.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        Message user = new Message(1, "08575975889", "Hello");
        assertFalse(user.checkRecipientCell());
    }

    @Test
    public void testCheckMessageID_Valid() {
        Message user = new Message(1, "+27718693002", "Test message");
        assertTrue(user.checkMessageID());
        assertEquals(10, user.getMessageID().length());
    }
    
        @Test
    public void testCreateMessageHash() {
        Message user = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String expectedStart = user.getMessageID().substring(0, 2) + ":0:";
        String expectedEnd = "HITONIGHT";
        assertEquals(expectedStart + expectedEnd, user.getMessageHash());
    }
    
        @Test
    public void testMessageSendOptions() {
        Message send = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        Message discard = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");

        
        
        Message.messageList.clear();
        Message.totalMessagesSent = 0;
        
        
        
        // Simulate sending
        Message.messageList.add(send);
        Message.totalMessagesSent++;
        assertTrue(Message.messageList.contains(send));

        // Simulate discard (nothing added)
        assertFalse(Message.messageList.contains(discard));

        // Simulate store (file created, message not added)
        boolean result = discard.writeMessageToFile("test_message_" + discard.getMessageID() + ".json");
        assertTrue(result);
    }

    @Test
    public void testToJsonAndFromJson() {
        Message original = new Message(2, "+27718693002", "Hello again");
        String json = original.toJson();

        Message copy = Message.fromJson(json);
        assertEquals(original.getMessage(), copy.getMessage());
        assertEquals(original.getRecipient(), copy.getRecipient());
        assertEquals(original.getMessageID(), copy.getMessageID());
        assertEquals(original.getMessageHash(), copy.getMessageHash());
        assertEquals(original.getMessageNumber(), copy.getMessageNumber());
    }
    
    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        Message.messageList.clear();
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message m4 = new Message(1, "0838884567", "It is dinner time !");
        Message.messageList.add(m1);
        Message.messageList.add(m4);

        assertEquals(2, Message.messageList.size());
        assertTrue(Message.messageList.stream().anyMatch(m -> m.getMessage().equals("Did you get the cake?")));
        assertTrue(Message.messageList.stream().anyMatch(m -> m.getMessage().equals("It is dinner time !")));
    }

    @Test
    public void testLongestMessage() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1, "+27834557896", "Did you get the cake?"));
        messages.add(new Message(2, "+27834557867", "Where are you? You are late! I have asked you to be on time."));
        messages.add(new Message(1, "0838884567", "It is dinner time !"));

        Message longest = messages.stream().max((m1, m2) -> Integer.compare(m1.getMessage().length(), m2.getMessage().length())).get();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessage());
    }

    @Test
    public void testSearchByMessageID() {
        Message.messageList.clear();
        Message m4 = new Message(1, "0838884567", "It is dinner time !");
        Message.messageList.add(m4);

        String searchID = m4.getMessageID();
        Message found = Message.messageList.stream()
                .filter(m -> m.getMessageID().equals(searchID))
                .findFirst()
                .orElse(null);

        assertNotNull(found);
        assertEquals("0838884567", found.getRecipient());
    }

    @Test
    public void testSearchMessagesByRecipient() {
        List<Message> allMessages = new ArrayList<>();
        allMessages.add(new Message(2, "+2783454567", "Where are you? You are late! I have asked you to be on time."));
        allMessages.add(new Message(2, "+2783454567", "Ok, I am leaving without you."));

        List<String> foundMessages = new ArrayList<>();
        for (Message m : allMessages) {
            if (m.getRecipient().equals("+2783454567")) {
                foundMessages.add(m.getMessage());
            }
        }

        assertTrue(foundMessages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(foundMessages.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByMessageHash() {
        List<Message> allMessages = new ArrayList<>();
        Message toDelete = new Message(2, "+27834557867", "Where are you? You are late! I have asked you to be on time.");
        allMessages.add(toDelete);

        String targetHash = toDelete.getMessageHash();
        boolean removed = allMessages.removeIf(m -> m.getMessageHash().equals(targetHash));

        assertTrue(removed);
        assertFalse(allMessages.contains(toDelete));
    }

    @Test
    public void testDisplayReportFormat() {
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message m2 = new Message(1, "0838884567", "It is dinner time !");
        Message.messageList.clear();
        Message.messageList.add(m1);
        Message.messageList.add(m2);

        for (Message m : Message.messageList) {
            assertNotNull(m.getMessageHash());
            assertNotNull(m.getRecipient());
            assertNotNull(m.getMessage());
        }
    }
}