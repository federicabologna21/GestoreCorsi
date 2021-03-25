package it.polito.tdp.corsi.model;

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
}
