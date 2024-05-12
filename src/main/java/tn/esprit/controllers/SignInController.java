package tn.esprit.controllers;

import javafx.embed.swing.SwingFXUtils;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.controlsfx.control.Notifications;

import tn.esprit.utils.SessionManager;
import tn.esprit.utils.MyConnection;
//import utils.SessionManager;

public class SignInController {

    public SignInController() {
        // Initialize your connection here
        cnx = MyConnection.getInstance().getCnx();
    }

    ServicePersonne sv = new ServicePersonne();

    @FXML
    private TextField email_signin;

    @FXML
    private TextField password_signin;

    @FXML
    private ImageView captchImg;
    Captcha captcha= new Captcha.Builder(250, 150).build();
    boolean captchaIsCorrect;

    @FXML
    private TextField captchaInput;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void initialize() {
        generateCaptcha();
        captchaIsCorrect=false;
    }

    public void setEmail_signin(String email_signin) {
        this.email_signin.setText(email_signin);
    }

    public void setPassword_signin(String password_signin) {
        this.password_signin.setText(password_signin);
    }

    public void goToSignUp(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

        try {
            Parent root = loader.load();

            AjouterPersonneController ajouterPersonneController = loader.getController();

                 ajouterPersonneController.setTxtLastname("");
                 ajouterPersonneController.setTxtEmail("");
                 ajouterPersonneController.setTxtPassword("");


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully sign up.");

                 email_signin.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }


    private void notiff_login() {
        String imagePath = "file:///C:/Users/yassi/OneDrive/Bureau/ProjetPidev/src/main/Img/tick.jpg";
        Image image = new Image(imagePath);


        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("you logged in successfully \n Welcome to Travel_with_me!");
        notifications.title("valid Log in");
        notifications.hideAfter(Duration.seconds(4));

        notifications.show();
    }

    private void notiff_captcha() {
        String imagePath = "file:///C:/Users/yassi/OneDrive/Bureau/ProjetPidev/src/main/Img/captcha.png";
        Image image = new Image(imagePath);


        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("you need to valid your captcha");
        notifications.title("invalid captcha");
        notifications.hideAfter(Duration.seconds(4));

        notifications.show();
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException, SQLException {

        System.out.println(captchaInput.getText()+" c le input");
        System.out.println("captchaInput.getText().isEmpty()"+captchaInput.getText().isEmpty());
        if (captchaInput.getText().isEmpty()) {
            notiff_captcha();
            return;
        }
        System.out.println("isValidCaptcha()"+isValidCaptcha());
        if (!isValidCaptcha()) {
            notiff_captcha();
            return;
        }

        if(email_signin.getText().equals("admin") && password_signin.getText().equals("admin") )
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();

            notiff_login();

            Parent root = FXMLLoader.load(getClass().getResource("/Back.fxml"));
            Scene scene;

            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        }else {

            Personne user = SessionManager.getInstance().auth(email_signin.getText(),password_signin.getText());
            System.out.println("user_id that just logged in is ==>  " + SessionManager.getInstance().getId());

        String email = email_signin.getText();
        String pass = password_signin.getText();
        sv.login(email,pass);
        notiff_login();
    }


    }

    void sendPassword() throws SQLException {
        PreparedStatement smt=sv.sendPass();
        String email1="empty";
        try {
            //PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            if(rs.next()){
                email1=rs.getString("email");
                System.out.println(email1);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
            sendMail(email1);
    }
    public void sendMail(String recepient){
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        //properties.put("mail.smtp.host","smtp.gmail.com");
        //properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port","2525");
        String myAccountEmail = "ec36e3ee17ac2f";
        String passwordd = "d6d851b44b0ce8";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,passwordd);
            }
        });
        Message message =preparedMessage(session,myAccountEmail,recepient);
        try{
            Transport.send(message);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("TravelMe :: Boite Mail");
            alert.setHeaderText(null);
            alert.setContentText("consulter votre boite mail !!");
            alert.showAndWait();

        }catch(Exception ex){
            ex.printStackTrace();

        }

    }
    private Message preparedMessage(Session session, String myAccountEmail, String recepient){
        String query2="select * from user where email=?";
        String userEmail="null" ;
        String pass="empty";
        try {
            //cnx = MyConnection.getInstance().getCnx();
            PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            System.out.println(rs);
            if(rs.next()){
                pass=rs.getString("password");
                userEmail=rs.getString("email");                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print("c est en cours");
        String text="Votre mot de pass est :"+pass+"";
        String object ="Recupération de mot de passe";
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(object);
            String htmlcode ="<h1> "+text+" </h1> <h2> <b> </b2> </h2> ";
            message.setContent(htmlcode, "text/html");
            System.out.println("Message envoyeer");

            System.out.println(pass);

            return message;

        }catch(MessagingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @FXML
    void sendPaswword_btn(ActionEvent event) throws SQLException {
        //sendMail(email_signin.getText());
        sendPassword();
    }


    public Captcha generateCaptcha() {

        Captcha.Builder builder = new Captcha.Builder(250, 150)
                .addText()
                .addBackground(new FlatColorBackgroundProducer(Color.PINK))
                .addNoise()
                .gimp(new FishEyeGimpyRenderer())
                .addBorder();

        Captcha captcha = builder.build();
        System.out.println(captcha.getAnswer());
        captchImg.setImage(SwingFXUtils.toFXImage(captcha.getImage(), null));
        return captcha;
    }

    boolean isValidCaptcha(){

        if (captcha != null) {
            System.out.println(captcha.getAnswer());

            if (captcha.getAnswer().equals(captchaInput.getText())) {

                System.out.println("isValidCaptcha if");
                captchaIsCorrect = true;
                return true;
            } else {
                System.out.println("isValidCaptcha else");
                captcha = generateCaptcha();
                return false;
            }
        }
        System.err.println("Captcha is not initialized");
        return false;
    }


}