package it.polito.tdp.corsi.model;

public class Corso {

	//(3)
	// CREO LA CLASSE 
	// GENERO COSTRUTTORE,
	// GETTER E SETTER (per tutti gli attributi)
	// METODI HASHCODE E EQUALS (PER LA CHIAVE PRIMARIA!)
	// METODO TOSTRING (per tutti gli attributi)
	
	// normalmente si usano gli stessi nomi 
	// presenti nel diagramma ER
	
	// utile usare Integer perchè ha gia implementati 
	// i metodi hashCode e equals
	// ATTENZIONE !!  se la chiave è un intero definirla a INTEGER
	private String codins;
	private Integer crediti;
	private String nome;
	private Integer pd;
	
	
	public Corso(String codins, Integer crediti, String nome, Integer pd) {
		super();
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}

	public String getCodins() {
		return codins;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public Integer getCrediti() {
		return crediti;
	}

	public void setCrediti(Integer crediti) {
		this.crediti = crediti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPd() {
		return pd;
	}

	public void setPd(Integer pd) {
		this.pd = pd;
	}

	
	// genero hashCode e equals PER LA CHIAVE PRIMARIA (CODINS)
	// RICORDA!! il metodo equals e hashCode vanno sempre generati insieme
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codins == null) ? 0 : codins.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		if (codins == null) {
			if (other.codins != null)
				return false;
		} else if (!codins.equals(other.codins))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Corso [codins=" + codins + ", crediti=" + crediti + ", nome=" + nome + ", pd=" + pd + "]";
	}
	
}
