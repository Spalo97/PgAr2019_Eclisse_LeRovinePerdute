package it.eclisse.rovine;

import java.util.ArrayList;

public class Città {
	
	private String x;
	private String y;
	private String h;
	private String id;
	private String nome;
	private ArrayList<String> idCollegamenti=new ArrayList();
	
	public Città() {
	}
	
	public boolean addCollegamento(String id) {
		return idCollegamenti.add(id);
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<String> getIdCollegamenti() {
		return idCollegamenti;
	}

	
}
