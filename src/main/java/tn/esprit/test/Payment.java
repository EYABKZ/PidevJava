package tn.esprit.test;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OpuJwKvIA0EH3EDkYzwptoDQpQQy0POJNeH8NU0zhsknV3OzCxJaUA1urrYWmuyNEcgDVbjC23x02sJlgq5fHJx001xX60TqK";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

}