package tn.esprit.entities;

import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Pdf {

    public void GeneratePdf(String filename, Personne p, int id) throws IOException, SQLException {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>User Details</title>\n" +
                "</head>\n" +
                "<body style=\"text-align: center;\">\n" + // Center align all content
                "<h2>Date: <span style=\"color: gray;\">" + LocalDateTime.now() + "</span></h2>\n" + // Gray date
                "<p><h1 style=\"color: red;\">" + p.getLastname() + "</h1></p>\n" + // Red lastname
                "<p><h3 style=\"color: black;\">" + p.getEmail() + "</h3></p>\n" + // Black email
                "<p><h4 style=\"color: black;\">" + p.getId() + "</h4></p>\n" + // Black id
                "<p>The Developers confirmed this Pdf version of User details!</p>\n" +
                "</body>\n" +
                "</html>\n";



        // Write HTML content to a temporary file
        String tempHtmlFilePath = filename + ".html";
        try (PrintWriter writer = new PrintWriter(tempHtmlFilePath)) {
            writer.println(htmlContent);
        }

        // Convert HTML to PDF using wkhtmltopdf
        Process pro = Runtime.getRuntime().exec("wkhtmltopdf " + tempHtmlFilePath + " " + filename + ".pdf");

        // Wait for the process to finish
        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Delete the temporary HTML file
        File tempHtmlFile = new File(tempHtmlFilePath);
        if (tempHtmlFile.exists()) {
            tempHtmlFile.delete();
        }

        // Open PDF after saving
        pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }



}
