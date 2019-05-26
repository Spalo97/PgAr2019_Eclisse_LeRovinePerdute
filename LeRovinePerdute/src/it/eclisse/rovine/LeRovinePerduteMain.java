package it.eclisse.rovine;

import java.util.LinkedList;

public class LeRovinePerduteMain {
	
	public static void main(String[] args) {
		Mappa mappa = new Mappa();
		GestioneXml.importXml(0);
		LinkedList<City> percorso=mappa.calcolaPercorso();
		double costo=mappa.getCostoTotale();
		GestioneXml.writeFile(percorso, percorso, costo, costo);
		System.out.println("Fatto!");
	}

}
