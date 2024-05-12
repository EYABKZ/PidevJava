package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.io.IOException;

public class ChatBotController {

    private static final String API_KEY = "cRR0IkqVPPvryQIDJtFUU6mDGCcQTHOg";
    private static final String API_URL = "https://api.ai21.com/studio/v1/j2-mid/complete";

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    private void sendMessage(ActionEvent event) {
        String userMessage = inputField.getText().trim();
        if (!userMessage.isEmpty()) {
            chatArea.appendText("You: " + userMessage + "\n");

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            // Construct the JSON request body
            String json = "{\"prompt\": \"" + userMessage + "\"}";
            RequestBody requestBody = RequestBody.create(json, mediaType);

            // Build the request
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    chatArea.appendText("Bot: Sorry, I couldn't process your request.\n");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        String errorBody = response.body().string();
                        throw new IOException("Unexpected code " + response.code() + ": " + errorBody);
                    }
                    String jsonResponse = response.body().string();
                    String botResponse = parseResponse(jsonResponse);
                    chatArea.appendText("Bot: " + botResponse + "\n");
                }
            });

            inputField.clear();
        }
    }

    private String parseResponse(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        if (jsonObject.has("completions")) {
            JsonArray completions = jsonObject.getAsJsonArray("completions");
            if (completions.size() > 0) {
                JsonObject firstCompletion = completions.get(0).getAsJsonObject();
                if (firstCompletion.has("data")) {
                    JsonObject data = firstCompletion.getAsJsonObject("data");
                    StringBuilder textBuilder = new StringBuilder();
                    if (data.has("text")) {
                        textBuilder.append(data.get("text").getAsString());
                    }
                    if (data.has("tokens")) {
                        JsonArray tokensArray = data.getAsJsonArray("tokens");
                        for (JsonElement tokenElement : tokensArray) {
                            JsonObject tokenObject = tokenElement.getAsJsonObject();
                            if (tokenObject.has("token")) {
                                textBuilder.append(tokenObject.get("token").getAsString()).append(" ");
                            }
                        }
                    }
                    return textBuilder.toString().trim();
                }
            }
        }

        return "No response found.";
    }
}
