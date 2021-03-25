package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	
	// (2)
	//CREO UNA CLASSE DAO PER OGNI 
	//TABELLA DA CUI VADO AD ESTRARRE
	
	// per ogni tabella che vogliamo modellare creiamo una 
	// classe apposita che contiene informazioni su questa tabella
	// quindi tutte le tabelle hanno la corrispettiva classe 
	// in java (tranne quelle con relazioni molti a molti)
	// --> quindi creo la classe Corso nel package Model (3)
	
	
	// mi aspetto che ritorni una lista di corsi 
	public List<Corso> getCorsiByPeriodo(Integer periodo){
		
		//(4)
		// DOPO AVER CREATO LA CLASSE CORSO COMPILO IL METODO
		
		// faccio copia e incolla da quello che scrivo in Heidi
		// attenzione che quando incollo devo togliere i \r e \n
		// e devo aggiungere lo spazio prima delle virgolette di chiusura
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE pd = ?"; // modifico 1 con ?
		
		// scrivo la lista che dovrò ritornare
		List <Corso> result = new ArrayList<Corso>();
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql); // creo uno statement della query
			st.setInt(1, periodo); // che mi permette di impostare il primo parametro (quello con ?)
			// setInt perchè il periodo è un numero 
			// 1 vuol dire che è il primo parametro che c'è nella query 
			// (in questo caso è anche l'unico)
			
			ResultSet rs = st.executeQuery();
			
			// scorro il ResultSet
			while (rs.next()) {
				
				// per ogni riga mi creo un nuovo corso
				Corso c = new Corso (rs.getString("codins"), rs.getInt("crediti"), 
						rs.getString("nome"), rs.getInt("pd"));
				
				result.add(c); // lo aggiungo alla lista 
			}
			
			rs.close();// le prime due 
			st.close();// non sono fondamentali
			conn.close(); // IMPORTANTE, FARLO SEMPRE!!!
			
		}catch (SQLException e) {
			throw new RuntimeException (e);
		}
		
		return result;
		
	// CONCLUSO IL METODO LO RICHIAMO NEL MODEL (5)

	}
	
	/**
	 * SECONDA RICHIESTA: NUMERO DI ISCRITTI PER OGNI CORSO 
	 * DEL PERIODO DIDATTICO PASSATO
	 * @param periodo
	 * @return
	 */
	
	// (7)
	// PREVEDIAMO UNA MAPPA DOVE LA CHIAVE E' IL CORSO
	// E IL VALORE E' IL NUMERO DI ISCRITTI
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer periodo){
		
		// faccio copia e incolla da quello che scrivo in Heidi
		// attenzione che quando incollo devo togliere i \r e \n
		// e devo aggiungere lo spazio prima delle virgolette di chiusura
		
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd = ? "
				+ "GROUP BY c.codins, c.nome, c.crediti, c.pd";
		
		// definisco la mappa che dovrò ritornare
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql); // creo uno statement della query
			st.setInt(1, periodo); // che mi permette di impostare il primo parametro (quello con ?)
			// setInt perchè il periodo è un numero 
			// 1 vuol dire che è il primo parametro che c'è nella query 
			// (in questo caso è anche l'unico)
			
			ResultSet rs = st.executeQuery();
						
			// scorro il ResultSet
			while (rs.next()) {
				// creo il corso per ogni riga
				Corso c = new Corso (rs.getString("codins"), rs.getInt("crediti"), 
						rs.getString("nome"), rs.getInt("pd"));
				
				// recupero anche il totale (che è un intero)
				// tot è il nome usato per ridefinire il COUNT *
				Integer n = rs.getInt("tot");
				
				// aggiunnngo alla mappa
				result.put(c, n);
			}
			
			rs.close();//le prime due non sono fondamentali
			st.close();// la connection si
			conn.close(); // RICORDARSI DI FARLO SEMPRE!!!
			
		}catch (SQLException e) {
			throw new RuntimeException (e);
		}
		
		return result;

	// COLLEGO IL METODO AL MODEL (8)
	}
	
}

