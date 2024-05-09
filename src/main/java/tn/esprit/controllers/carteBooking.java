package tn.esprit.controllers;




import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Date;

public class carteBooking {

    @FXML
    private ImageView image;

    @FXML
    private Label IDBLabel;

    @FXML
    private Label DateDebutLabel;

    @FXML
    private Label DateFinLabel;

    @FXML
    private Text text;

    @FXML
    private Label IDALabel;

    @FXML
    private Label IDULabel;


    public void setId(String IdB) {
        IDBLabel.setText(IdB);
    }
    public void setDebut(Date Debut) { DateDebutLabel.setText(String.valueOf(Debut));}
    public void setFin(Date Fin) {DateFinLabel.setText(String.valueOf(Fin)); }
    public void setUserId(String IDU) {IDULabel.setText(IDU); }
    public void setAccomodationId(String IDA) {IDALabel.setText(IDA); }


}
