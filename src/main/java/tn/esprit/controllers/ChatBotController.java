package tn.esprit.controllers;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField msgField;

    private void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    private String getChatBotResponse(String user_msg) {
        // Convert the user message to lowercase for easier comparison
        String message = user_msg.toLowerCase();

        // Example response logic
        if (message.contains("what is your name")) {
            return "My name is ChatBot.";
        } else if (message.contains("how old are you") || message.contains("what's your age")) {
            return "I'm just a computer program, so I don't have an age!";
        } else if (message.contains("what day is it today")) {
            LocalDate currentDate = LocalDate.now();
            return "Today is " + currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        } else if (message.contains("what time is it now")) {
            LocalTime currentTime = LocalTime.now();
            return "It's currently " + currentTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } else if (message.contains("how are you")) {
            return "I'm okay, thank you!";
        } else if (message.contains("hello") || message.contains("hi")) {
            return "Hello there!";
        } else {
            // Default response for unrecognized messages
            return "I'm not sure how to respond to that.";
        }
    }

    @FXML
    private void sendMessage() {
        String message = msgField.getText().trim();
        if (!message.isEmpty()) {
            // Add user message to chat area
            appendMessage("You: " + message);
            // Implement your chatbot logic here, and get the response
            String response = getChatBotResponse(message);
            // Add chatbot response to chat area
            appendMessage("ChatBot: " + response);
            appendMessage("--------------------------\n");
            // Clear input field
            msgField.clear();

        }
    }



}
