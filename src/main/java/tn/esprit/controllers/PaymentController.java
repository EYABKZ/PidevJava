package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.YearMonth;
import java.util.regex.Pattern;

public class PaymentController {

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvcField;

    @FXML
    private TextField nameOnCardField;

    // Stripe API key
    private static final String STRIPE_API_KEY = "sk_test_51OpuJwKvIA0EH3EDkYzwptoDQpQQy0POJNeH8NU0zhsknV3OzCxJaUA1urrYWmuyNEcgDVbjC23x02sJlgq5fHJx001xX60TqK";

    // Event handler for the Pay Now button
    @FXML
    private void handlePayButtonClicked() {
        // Get payment details from input fields
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvc = cvcField.getText();
        String nameOnCard = nameOnCardField.getText();

        // Validate input fields
        if (validateInput(cardNumber, expiryDate, cvc, nameOnCard)) {
            // Process payment
            processPayment(cardNumber, expiryDate, cvc, nameOnCard);
        }
    }

    // Method to validate input fields
    private boolean validateInput(String cardNumber, String expiryDate, String cvc, String nameOnCard) {
        // Card number validation
        if (!Pattern.matches("^\\d{16}$", cardNumber)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Card Number", "Please enter a valid 16-digit card number.");
            return false;
        }

        // Expiry date validation
        if (!Pattern.matches("^\\d{2}/\\d{4}$", expiryDate)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expiry Date", "Please enter the expiry date in MM/YYYY format.");
            return false;
        }

        // Check if expiry date is in the future
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        YearMonth currentMonth = YearMonth.now();
        YearMonth inputMonth = YearMonth.of(year, month);
        if (inputMonth.isBefore(currentMonth)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expiry Date", "Expiry date must be in the future.");
            return false;
        }

        // CVC validation
        if (!Pattern.matches("^\\d{3,4}$", cvc)) {
            showAlert(Alert.AlertType.ERROR, "Invalid CVC", "Please enter a valid 3 or 4-digit CVC number.");
            return false;
        }

        // Name on card validation
        if (nameOnCard.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Name on Card", "Please enter the name on the card.");
            return false;
        }

        // All validations passed
        return true;
    }

    // Method to process payment using Stripe API
    private void processPayment(String cardNumber, String expiryDate, String cvc, String nameOnCard) {
        try {
            // Initialize Stripe with your API key
            Stripe.apiKey = STRIPE_API_KEY;

            // Create parameters for the charge
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(150000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd") // Currency
                    .setSource("tok_visa") // Use a test token for demo purposes
                    .setDescription("Payment for accommodation") // Description
                    .build();

            // Create the charge
            Charge charge = Charge.create(params);

            // Handle successful charge
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "Your payment was processed successfully. Thank you!");
        } catch (StripeException e) {
            // Handle Stripe API exception
            showAlert(Alert.AlertType.ERROR, "Payment Error", "Error processing payment: " + e.getMessage());
        }
    }

    // Method to display an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
