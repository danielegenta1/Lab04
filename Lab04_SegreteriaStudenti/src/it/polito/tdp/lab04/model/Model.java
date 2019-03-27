package it.polito.tdp.lab04.model;

import java.util.List;
import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model 
{

	public List<Corso> loadCorsi() 
	{
		CorsoDAO dao = new CorsoDAO();
		List<Corso>corsi = dao.getTuttiICorsi();
		return corsi;
	}

	public Studente esisteStudente(int matricola) 
	{
		StudenteDAO dao = new StudenteDAO();
		Studente studente = dao.cercaStudente(matricola);
		return studente;
	}

	public List<Studente> studentiIscrittiACorso(String nomeCorso) 
	{
		CorsoDAO dao = new CorsoDAO();
		List<Studente> listaStudentiIscritti = dao.getStudentiIscrittiAlCorso(new Corso(null, 0, nomeCorso, 0));
		return listaStudentiIscritti;
	}

	public List<Corso> corsiStudente(int matricola) 
	{
		StudenteDAO dao = new StudenteDAO();
		List<Corso> listaCorsiStudente = dao.getCorsiStudente(new Studente(matricola, null, null, null));
		return listaCorsiStudente;
	}

	public boolean studenteIscrittoCorso(int matricola, String corsoSelezionato) 
	{
		StudenteDAO dao = new StudenteDAO();
		boolean iscritto = dao.getStudenteIscrittoCorso(matricola, corsoSelezionato);
		return iscritto;
	}

	public boolean IscriviStudenteCorso(int matricola, String corsoSelezionato)
	{
		CorsoDAO dao = new CorsoDAO();
		Corso corso = dao.getCorso(corsoSelezionato);
		
		return dao.inscriviStudenteACorso(new Studente(matricola, null, null, null), corso);
		
	}

}
