package it.eclisse.rovine;

import java.util.ArrayList;

public class City{
	
	private int x;
	private int y;
	private int h;
	private int id;
	private String name;
	private ArrayList<Integer> idCollegamenti=new ArrayList<Integer>();
	
	public City() {
	}
	
	public boolean addCollegamento(int id) {
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
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	public ArrayList<Integer> getIdCollegamenti() {
		return idCollegamenti;
	}
}
