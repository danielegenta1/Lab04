package it.polito.tdp.lab04.controller;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	private Model model;
	
    @FXML
    private ComboBox<String> cmbCorso;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCercaStudente;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;
    
    @FXML 
    private Button btnCerca;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    


    @FXML
    void doCercaCorsi(ActionEvent event) 
    {
    	Studente studente = studenteValido();
		if (studente != null)
		{
			List<Corso> corsiStudente = model.corsiStudente(studente.getMatricola());
			//TODO controllo sui corsi
			for (Corso c : corsiStudente)
			{
				txtResult.appendText(c.toString() + "\n");
			}
		}
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event)
    {
    	if (corsoValido() != null)
    	{
    		List<Studente> studentiIscritti = model.studentiIscrittiACorso(corsoValido());
    		if (studentiIscritti.size() > 0)
    		{
	    		for (Studente s : studentiIscritti)
	    		{
	    			txtResult.appendText(s.toString() + "\n");
	    		}
    		}
    		else
    			txtResult.appendText("Nessuno studente iscritto al corso selezionato\n");
    	}    	
    }
    
    @FXML 
    void doCerca(ActionEvent event)
    {
    	String corsoSelezionato = corsoValido();
    	if (corsoSelezionato != null)
    	{
    		Studente studente = studenteValido();
    		if (studente != null)
    		{
	    		boolean iscritto = model.studenteIscrittoCorso(studente.getMatricola(), corsoSelezionato);
	    		if (iscritto)
	    			txtResult.appendText("Studente iscritto al corso!\n");
	    		else
	    			txtResult.appendText("Studente non iscritto al corso!\n");
    		}
    	}

    }

    @FXML
    void doCercaStudente(ActionEvent event) 
    {
    	Studente studente = studenteValido();
    	if (studente != null)
    	{
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    	}
    }
    
    Studente studenteValido()
    {
    	if (txtMatricola.getText().compareTo("") != 0)
    	{
	    	int matricola;
	    	try
	    	{
	    		matricola = Integer.parseInt(txtMatricola.getText());
	    	}
	    	catch (NumberFormatException e)
	    	{
	    		txtResult.appendText("La matricola inserita non è valida, riprova\n");
	    		return null;
	    	}
	    	Studente studente = model.esisteStudente(matricola);
	    	return studente;
    	}
    	else
    	{
    		txtResult.appendText("Devi inserire una matricola valida\n");
    		return null;
    	}
    }
    
    String corsoValido()
    {
    	if (cmbCorso.getSelectionModel().getSelectedItem() != null)
    		return cmbCorso.getSelectionModel().getSelectedItem();

    	else
    	{
    		txtResult.appendText("Devi selezionare un  corso dal menù e uno studente valido");
    		return null;
    	}
    }
    

    @FXML
    void doIscrivi(ActionEvent event) 
    {
    	//matricola selezionata e valida, corso selezionato
    	Studente studente = studenteValido();
    	String corsoSelezionato = corsoValido();
    	if (studente != null && corsoSelezionato != null)
    	{
    		//studente non ancora iscritto
    		if (!model.studenteIscrittoCorso(studente.getMatricola(), corsoSelezionato))
    		{
    			//iscrizione
    			model.IscriviStudenteCorso(studente.getMatricola(), corsoSelezionato);
    			
    			//feedback 1
    			if (model.studenteIscrittoCorso(studente.getMatricola(), corsoSelezionato))
    				txtResult.appendText("Studente iscritto correttamente al corso selezionato\n");
    			
    		}
    		else
    		{
    			//feedback 2
    			txtResult.appendText("Studente già iscritto al corso selezionato\n");
    		}
    		
    	}
    	
    	
    }

    @FXML
    void doReset(ActionEvent event)
    {
    	cmbCorso.getSelectionModel().select(-1);
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtResult.clear();
    }
    
    @FXML
    void initialize()
    {
    	btnCercaStudente.setStyle("-fx-background-color: #00ff00");
    }
    
	public void setModel(Model model)
	{
		this.model = model;
	}
	
	
    void inizializzaCmb()
    {
    	//popola cmb corsi
    	List<Corso> corsi = model.loadCorsi();
    	List<String> nomiCorsi = new ArrayList<String>();
    	int i = 0;
    	for (Corso c : corsi)
    	{
    		nomiCorsi.add(corsi.get(i).getNome());
    		i++;
    	}
    	ObservableList<String> listCorsi = FXCollections.observableArrayList(nomiCorsi);
     	cmbCorso.setItems(listCorsi);
    }

}
