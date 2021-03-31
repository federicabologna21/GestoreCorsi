package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	//(5)
	// RICHIAMO LA CLASSE E
	// LA DEFINISCO NEL COSTR.
	private CorsoDAO corsoDao; 
	
	
	public Model() {
		corsoDao = new CorsoDAO();
	}
	
	// (5)
	// RICHIAMO IL METODO PRESENTE NEL DAO
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return corsoDao.getCorsiByPeriodo(pd);
		
	// IL METODO VERRA' RICHIAMATO DAL CONTROLLER (6)
	}
	
	// (8)
	// RICHIAMO IL METODO PRESENTE NEL DAO
	public Map <Corso, Integer> getIscrittiByPeriodo(Integer pd){
		return corsoDao.getIscrittiByPeriodo(pd);
		
	// IL METODO VERRA' RICHIAMATO DAL CONTROLLER (9)
	}

	
	// ATTENZIONE!!!
	// IL METODO IN CORSODAO RICEVE COME PARAMETRO 
	// UN OGGETTO DI TIPO 'CORSO'
	// MA NOI SUPPONIAMO CHE L'UTENTE NON CONOSCA 
	// LA LOGICA APPLICATIVA E QUINDI IL CODICE 
	// LO CONSIDERA COME STRING
	// DATO CHE IL METODO E' IMPLEMENTATO CON 
	// 'CORSO' COME PARAMETRO LO CREIAMO 
	// IMPONENDO IL CODICE E GLI ALTRI PARAMETRI A NULL
	public List<Studente> getStudentiCorso(String codice){
		return corsoDao.getStudentiCorso(new Corso (codice, null, null, null));
	}

	public boolean esisteCorso(String codice) {
		return corsoDao.esisteCorso(new Corso(codice, null, null,null));
	}
	
	
	// SOLUZIONE 1 
	// sfruttando il metodo getStudentiCorso, dato un codice
	// ho già tutti gli studenti iscritti in cui so il loro 
	// corso di studio, quindi NON creo il metodo nel Dao 
	// e sfrutto getStudentiCorso
	// ALTRIMENTI 
	// POSSO SFRUTTARE IL DATABASE (-->MEGLIO PERCHE' SCRIVIAMO 
	// MENO RIGHE DI CODICE)
	
	
	public Map <String, Integer >getDivisioneCDS (String codice) {
		/*Map <String, Integer> divisione = new HashMap<String, Integer>();
		List <Studente> studenti = this.getStudentiCorso(codice);
		
		for (Studente s: studenti) {
			
			// effettuo un controllo in più 
			// perchè stampando alcuni corsi ci si accorge 
			// che alcuni studenti non hanno dei campi
			// completati quindi prima verifichiamo che 
			// non siano a null o stringhe vuote
			if(s.getCds() != null && !s.getCds().equals("")) {
				
				// se il cds non c'è già lo vado a creare
				if (divisione.get(s.getCds())==null) {
					divisione.put(s.getCds(), 1);
				}
				
				// se il cds c'è già incremento il valore
				else {
					divisione.put(s.getCds(),  divisione.get(s.getCds())+1);
					
				}
			}
		}
		return divisione;
		*/
		return corsoDao.getDivisioneStudenti(new Corso (codice, null, null, null)); 
	}
}
