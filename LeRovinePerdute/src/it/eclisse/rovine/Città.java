package it.eclisse.rovine;

import java.util.ArrayList;

public class Città {
	
	private int x;
	private int y;
	private int h;
	private int id;
	private String nome;
	private ArrayList<String> idCollegamenti=new ArrayList();
	
	public Città() {
	}
	
	public boolean addCollegamento(String id) {
		return idCollegamenti.add(id);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
