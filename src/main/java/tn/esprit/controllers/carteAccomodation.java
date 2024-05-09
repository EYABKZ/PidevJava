package tn.esprit.controllers;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Date;

public class carteAccomodation {

    @FXML
    private ImageView image;

    @FXML
    private Label LieuLabel;

    @FXML
    private Text text;

    @FXML
    private Label IDALabel;

    @FXML
    private Label DescLabel;

    public void setIdACC(String IDA) {
        IDALabel.setText(IDA);
    }
    public void setAccomodationLieu(String Lieu) {LieuLabel.setText(Lieu); }
    public void setAccomodationdesc(String desc) {DescLabel.setText(desc); }



}
