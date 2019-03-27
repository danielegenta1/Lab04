package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public Studente cercaStudente(int matricola) 
	{
		final String sql = "SELECT * FROM studente WHERE matricola = ? ";
		try
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			Studente studente = null;

			//risultato di una sola riga
			if (rs.next()) 
			{
				int matr = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				studente = new Studente(matr, nome, cognome, cds);
			}
			conn.close();
			return studente;
		}
		catch (SQLException e) 
		{
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Corso> getCorsiStudente(Studente studente)
	{
		int matricola = studente.getMatricola();
		final String sql = "SELECT *" + 
							"FROM studente, iscrizione, corso" + 
							" WHERE studente.matricola = iscrizione.matricola" + 
							" AND iscrizione.codins = corso.codins" + 
							" AND studente.matricola = ?";
		try
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			List<Corso>corsiStudente = new LinkedList<Corso>();
			while (rs.next()) 
			{
				String codIns = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("corso.nome");
				int pd = rs.getInt("pd");
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso corso = new Corso(codIns, crediti, nome, pd);
				corsiStudente.add(corso);
			}
			conn.close();
			return corsiStudente;
		}
		catch (SQLException e) 
		{
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public boolean getStudenteIscrittoCorso(int matricola, String corsoSelezionato) 
	{
		final String sql = "SELECT *" + 
							"FROM studente, iscrizione, corso" + 
							" WHERE studente.matricola = iscrizione.matricola" + 
							" AND iscrizione.codins = corso.codins" + 
							" AND studente.matricola = ?" +
							" AND  corso.nome = ?";
		try
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, corsoSelezionato);
			ResultSet rs = st.executeQuery();
			boolean iscritto = false;
			if (rs.next())
				iscritto = true;
			conn.close();
			return iscritto;
		}
		catch (SQLException e) 
		{
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


}
