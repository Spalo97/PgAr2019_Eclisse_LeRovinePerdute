package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.LinkedList;

public class City{
	/**attributi della citta necessari per il calcolo dei pesi della città*/
	private int x;
	private int y;
	private int h;
	private int id;
	private String nome;
	private ArrayList<Integer> idCollegamenti=new ArrayList<Integer>();
	
	private double distanza=Double.POSITIVE_INFINITY;
	private int idPrecedente=-1;

	/**costruttore per la creazione di una nuova città vuota*/
	public City() {
	}

	/**costruttore per la creazione di una nuova città*/
	public City(int x, int y, int h, int id) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.id = id;
	}

	/**getter e setter posizione X*/
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}


	/**getter e setter posizione Y*/
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	/**getter e setter posizione H*/
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}


	/*getter e setter ID della città*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	/*getter e setter nome della città*/
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	/*metodi per la gestione degli ID delle città collegate alla città in questione*/
	public boolean addCollegamento(int id) {
		return idCollegamenti.add(id);
	}

	public ArrayList<Integer> getIdCollegamenti() {
		return idCollegamenti;
	}

	/*getter e setter della distanza*/
	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	/*getter e setter dell'ID precedente*/
	public int getIdPrecedente() {
		return idPrecedente;
	}

	public void setIdPrecedente(int idPrecedente) {
		this.idPrecedente = idPrecedente;
	}
}
