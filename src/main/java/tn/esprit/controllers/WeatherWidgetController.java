package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import org.json.*;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.image.ImageView;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherWidgetController {

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label conditionsLabel;

    @FXML
    private ListView<String> forecastListView;

    @FXML
    private ImageView weatherIcon1;

    @FXML
    private ImageView weatherIcon2;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    // Other existing code...

    // Initialize method and other methods...

    // Add this method for translating the scene based on the selected language
    @FXML
    private void translateScene(ActionEvent event) {
        String targetLanguage = languageChoiceBox.getValue();
        String temperatureText = temperatureLabel.getText();
        String translatedTemperature = translateText(temperatureText, targetLanguage);
        temperatureLabel.setText(translatedTemperature);

        // Similarly, translate other labels as needed
    }

    private static final String API_KEY = "65025a085f2eb3790711bcc62025f0b4";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";

    public void initialize() {
        try {
            // Fetch location information dynamically
            String locationInfo = getLocationInfo();
            JSONObject locationObject = new JSONObject(locationInfo);
            String city = locationObject.getString("city");
            String countryCode = locationObject.getString("country");

            // Fetch weather data based on location
            fetchWeatherData(city, countryCode);
            fetchForecastData(city, countryCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLocationInfo() throws Exception {
        String apiUrl = "https://ipinfo.io/json";
        StringBuilder response = new StringBuilder();

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        connection.disconnect();

        return response.toString();
    }

    private void fetchWeatherData(String city, String countryCode) {
        try {
            String apiUrl = BASE_URL + "/weather?q=" + city + "," + countryCode + "&appid=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response and update UI
            updateWeatherInfo(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchForecastData(String city, String countryCode) {
        try {
            String apiUrl = BASE_URL + "/forecast?q=" + city + "," + countryCode + "&appid=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response and update UI
            updateForecastInfo(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateWeatherInfo(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONObject main = jsonObject.getJSONObject("main");
            double temperatureKelvin = main.getDouble("temp");
            double temperatureCelsius = temperatureKelvin - 273.15;
            double humidity = main.getDouble("humidity");

            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String weatherDescription = weatherObject.getString("description");
            String iconCode = weatherObject.getString("icon");

            String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + ".png";
            Image iconImage = new Image(iconUrl);

            temperatureLabel.setText(String.format("Temperature: %.2f°C", temperatureCelsius));
            humidityLabel.setText("Humidity: " + humidity + "%");
            windSpeedLabel.setText("Wind Speed: " + windSpeed + " m/s");
            conditionsLabel.setText("Conditions: " + weatherDescription);
            weatherIcon1.setImage(iconImage); // You can set multiple images for different weather conditions
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateForecastInfo(String jsonData) {
        try {
            List<String> forecastData = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray list = jsonObject.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject forecast = list.getJSONObject(i);
                JSONObject main = forecast.getJSONObject("main");
                double temperatureKelvin = main.getDouble("temp");
                double temperatureCelsius = temperatureKelvin - 273.15;

                JSONArray weatherArray = forecast.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String weatherDescription = weatherObject.getString("description");

                String forecastInfo = String.format("%.2f°C - %s", temperatureCelsius, weatherDescription);
                forecastData.add(forecastInfo);
            }

            forecastListView.getItems().addAll(forecastData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implement additional methods as needed
    private String translateText(String text, String targetLanguage) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String requestBody = String.format("from=auto&to=%s&json=%s",
                    URLEncoder.encode(targetLanguage, "UTF-8"),
                    URLEncoder.encode("{\"text\":\"" + text + "\"}", "UTF-8"));

            RequestBody body = RequestBody.create(mediaType, requestBody);
            Request request = new Request.Builder()
                    .url("https://google-translate113.p.rapidapi.com/api/v1/translator/json")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("X-RapidAPI-Key", "9d6810e93amsheaf56c60afb8e7cp1e6219jsn04f292e2461e")
                    .addHeader("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String responseBody = response.body().string();
                // Parse the response JSON to get the translated text
                return parseTranslationResponse(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Translation Error";
        }
    }

    private String parseTranslationResponse(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String translation = jsonObject.getString("trans");
            return translation;
        } catch (JSONException e) {
            e.printStackTrace();
            return "Translation Error: Unable to parse response";
        }
    }

}
