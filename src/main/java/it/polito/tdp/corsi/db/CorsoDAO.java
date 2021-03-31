package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

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
	
	/**
	 * RICHIESTA 3
	 * ELENCARE TUTTI GLI STUDENTI DI UN DETERMINATO CORSO 
	 */
	
	public List<Studente> getStudentiCorso (Corso corso){
		
		String sql = "SELECT  s.matricola, s.nome, s.cognome, s.cds "
				+ "FROM studente s, iscrizione i "
				+ "WHERE i.matricola = s.matricola AND i.codins = ?";
		List<Studente> studentiCorso = new LinkedList<Studente>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql); 
			st.setString(1, corso.getCodins()); 
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				Studente s = new Studente (rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
				
				
				studentiCorso.add(s);
			}
			rs.close();
			st.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException (e);
		}
		return studentiCorso;
		
	}

	// CREO UN METODO PER VERIFICARE SE IL CORSO ESISTE
	public boolean esisteCorso(Corso corso) {
		
		String sql = " SELECT * FROM corso WHERE codins=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql); 
			st.setString(1, corso.getCodins()); 
			
			ResultSet rs = st.executeQuery();
			
			// --> IL METODO RITORNA VERO SE C'E' UN RISULTATO
			// USIAMO IF PERCHE' AL MASSIMO C'E' SOLO UN RISULTATO
			if (rs.next()) {
				rs.close(); // RICORDA DI CHIUDERE SIA
				st.close(); // IN QUESTA RETURN CHE IN QUELLA DOPO
				conn.close();
				return true;
			} else {
				rs.close();
				st.close();
				conn.close();
				return false; // NON C'E' NESSUN ELEMENTO 
			}
		}catch(SQLException e) {
			throw new RuntimeException (e);
		}
		
	}
	
	// CREO UN METODO CHE RITORNA UNA MAPPA CON 
	// SUDDIVISIONE DI STUDENTI PER CDS DATO UN CORSO
	public Map <String, Integer> getDivisioneStudenti(Corso corso){
		
		// ho bisogno di un contatore --> COUNT(*) AS tot
		// escludo gli studenti con stringa cds vuota --> s,cds<>''
		// metto una GROUP BY per raggruppare per cds 
		String sql ="SELECT s.cds, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.cds <> '' "
				+ "GROUP BY s.CDS";
		
		Map <String, Integer> result = new HashMap<String, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql); 
			st.setString(1, corso.getCodins()); 
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				result.put(rs.getString("cds"), rs.getInt("tot"));
				
			}
			rs.close();
			st.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException (e);
		}
		return result;
	}
	
}

