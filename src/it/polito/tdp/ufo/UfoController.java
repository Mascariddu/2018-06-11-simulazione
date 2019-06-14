/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebParam.Mode;
import javax.management.modelmbean.ModelMBean;
import javax.tools.Diagnostic;

import it.polito.tdp.ufo.db.SightingsDAO;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<String> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {

    	txtResult.appendText("Predecessori: \n");
    	for(String s: model.getPredecessori(this.boxStato.getValue()))
    		txtResult.appendText(s+"\n");
    	
    	txtResult.appendText("Successori: \n");
    	for(String string : model.getSuccessori(this.boxStato.getValue()))
    		txtResult.appendText(string+"\n");
    	
    	txtResult.appendText("Stati raggiungibili: \n");
    	for(String s: model.getRaggiungibili(this.boxStato.getValue()))
    		txtResult.appendText(s+"\n");
    	
    	txtResult.appendText("Numero di stati raggiungibili: "+model.ottieniCammino(this.boxStato.getValue()));
    	
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	this.boxStato.getItems().clear();
    	String c[] = this.boxAnno.getValue().split(" ");

    	model.creaGrafo(Integer.parseInt(c[0]));
    	this.boxStato.getItems().addAll(model.getVertex());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	txtResult.clear();
    	String c[] = this.boxAnno.getValue().split(" ");
    	
    	for(String s: model.trovaCammino(Integer.parseInt(c[0]),this.boxStato.getValue())) {
    		
    		txtResult.appendText(s+"\n");
    		
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model2) {
		// TODO Auto-generated method stub
		this.model = model2;
		this.boxAnno.getItems().clear();
		this.boxAnno.getItems().addAll(model2.getAnni());
	}
}
