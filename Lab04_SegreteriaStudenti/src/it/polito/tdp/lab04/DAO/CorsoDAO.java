package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi()
	{

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			conn.close();
			return corsi;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}



	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) 
	{
		String nomeCorso = corso.getNome();
		final String sql = "SELECT *" + 
				"FROM studente, iscrizione, corso" + 
				" WHERE studente.matricola = iscrizione.matricola" + 
				" AND iscrizione.codins = corso.codins" + 
				" AND corso.nome = ?";
			try
			{
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, nomeCorso);
				ResultSet rs = st.executeQuery();
				List<Studente>studentiIscritti = new LinkedList<Studente>();
				while (rs.next()) 
				{
					int matr = rs.getInt("matricola");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String cds = rs.getString("CDS");
					// Crea un nuovo JAVA Bean Corso
					// Aggiungi il nuovo oggetto Corso alla lista corsi
					Studente studente = new Studente(matr, nome, cognome, cds);
					studentiIscritti.add(studente);
				}
				conn.close();
				return studentiIscritti;
			}
			catch (SQLException e) 
			{
				 e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) 
	{
		final String sql = "INSERT INTO iscrizione (matricola, codins)" + 
							"VALUES (?, ?)";
		try
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
			boolean result = st.execute();
			conn.close();
			return result;
		}
		catch (SQLException e) 
		{
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String corsoSelezionato) 
	{
		final String sql = "SELECT * FROM Corso WHERE nome = ? ";
		try
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corsoSelezionato);
			ResultSet rs = st.executeQuery();
			Corso corso = null;
			if (rs.next())
			{
				String codIns = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("corso.nome");
				int pd = rs.getInt("pd");
				corso = new Corso(codIns, crediti, nome, pd);
			}
			conn.close();
			return corso;
		}
		catch (SQLException e) 
		{
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


}
