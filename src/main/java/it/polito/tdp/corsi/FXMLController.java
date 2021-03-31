/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	
    	//(6) 
    	
    	
    	// CONTROLLI DA FARE SEMPRE
    	// all'inizio ripulisco il risultato per sicurezza
    	txtRisultato.clear(); 
    	
    	// RECUPERIAMO INPUT utente --> periodo didattico
    	String periodoStringa = txtPeriodo.getText();	
    	
    	// CONTROLLO che sia un PERIODO VALIDO --> IMPORTANTE, FARLO SEMPRE!! 
    	// C'E' SEMPRE QUANDO IL PERIODO E' NUMERICO!!! 
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch (NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return; // importante mettere il return perchè non posso piuù andare avanti
    	}catch (NullPointerException npe) { 
    		// CONTROLLO che la STRINGA NON SIA NULLA
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	// CONTROLLO che il PERIODO RISPETTI I VINCOLI 
    	if (periodo <1 || periodo >2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	// richiamo passando da model il metodo del DAO
    	List<Corso> corsi = this.model.getCorsiByPeriodo(periodo);
    	
    	// SCORRO LA LISTA E RIEMPIO IL RISULTATO
    	/*for (Corso c: corsi) {
    		txtRisultato.appendText(c.toString() + "\n");
    	}*/
    	
    	
    	
    	// PER METTERE GLI ELEMENTI IN COLONNA ORDINATA
    	// RICORDA NEL SET MODEL DI AGGIUNGERE!!
    	StringBuilder sb = new StringBuilder();
    	for (Corso c: corsi) {
    		
    		// mettere sempre il &
    		// il meno intende l'allineamento a sinistra
    		// il numero indica la dimensione massima del campo 
    		// ( si mette il numero massimo di caratteri che può 
    		// avere l'attributo)
    		// l'ultima lettere indica che cosa vado a inserire
    		// (s --> stringa, d --> intero)
    		sb.append(String.format("%-8s", c.getCodins()));
    		sb.append(String.format("%-4d", c.getCrediti()));
    		sb.append(String.format("%-50s", c.getNome()));
    		sb.append(String.format("%-4d\n", c.getPd()));
    		
    	}
    	txtRisultato.appendText(sb.toString());
    	
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	
    	//(9)
    	
    	txtRisultato.clear(); 
    	
    	// CONTROLLI DA FARE SEMPRE
    	// all'inizio ripulisco il risultato per sicurezza
    	txtRisultato.clear(); 
    	
    	// RECUPERIAMO INPUT utente --> periodo didattico
    	String periodoStringa = txtPeriodo.getText();
    	
    	// CONTROLLO che sia un PERIODO VALIDO --> IMPORTANTE, FARLO SEMPRE!! 
    	// C'E' SEMPRE QUANDO IL PERIODO E' NUMERICO!!! 
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch (NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return; // importante mettere il return perchè non posso piuù andare avanti
    	}catch (NullPointerException npe) { 
    		// CONTROLLO che la STRINGA NON SIA NULLA
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	// CONTROLLO che il PERIODO RISPETTI I VINCOLI 
    	if (periodo <1 || periodo >2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	Map <Corso, Integer> corsiIscrizioni = this.model.getIscrittiByPeriodo(periodo);
    	
    	for (Corso c: corsiIscrizioni.keySet()) {
    		txtRisultato.appendText(c.toString());
    		Integer n = corsiIscrizioni.get(c);
    		txtRisultato.appendText("\t" + n + "\n"); // t da una tabulazione
    	}
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	if (!model.esisteCorso(codice)) {
    		txtRisultato.setText("Il corso non esiste");
    		return;
    	}

    	Map <String, Integer> divisione = model.getDivisioneCDS(codice);
    	
    	for(String cds: divisione.keySet()) {
    		txtRisultato.appendText(cds+" "+ divisione.get(cds)+"\n");
    	}
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	// NON ABBIAMO NESSUN CONTROLLO DA FARE
    	
    	
    	List<Studente> studenti = this.model.getStudentiCorso(codice);
    	
    	// se l'utente scrive un CODICE CHE NON ESISTE 
    	// la lista sara vuota perche il metodo nel while
    	// non riempie niente
    	
    	// SE LA LISTA E' VUOTA PU' ESSERE PERCHE' IL 
    	// CORSO NON ESISTE O SE E' PERCHE' NESSUNO STUDENTE 
    	// E' ISCRITTO
    	
    	
    	// CONTROLLO SE IL CORSO ESISTE
    	// COSTRUENDO UN METODO 
    	if (!model.esisteCorso(codice)) {
    		txtRisultato.setText("Il corso non esiste");
    		return;
    	}
    	
    	if (studenti.size()==0) {
    		txtRisultato.setText("Il corso non ha iscritti");
    		return;
    	}
    	
    	for (Studente s: studenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	// SPECIFICARE LO STILE DEL CARATTERE --> sempre questo!
    	txtRisultato.setStyle("-fx-font-family: monospace");
    }
    
    
}