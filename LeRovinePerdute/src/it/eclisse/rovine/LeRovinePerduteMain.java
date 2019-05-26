package it.eclisse.rovine;

import java.util.LinkedList;

public class LeRovinePerduteMain {
	
	public static void main(String[] args) {
		GestioneXml gestione=new GestioneXml();
		Mappa mappa=new Mappa(gestione.getListCity());
		gestione.importXml(gestione.sceltamappa());
		LinkedList<City> percorsoTonatiuh=mappa.calcolaPercorsoTonatiuh();
		LinkedList<City> percorsoMetztli=mappa.calcolaPercorsoMetztli();
		double costoTonatiuh=mappa.getCostoTonatiuh();
		double costoMetztli=mappa.getCostoMetztli();
		gestione.writeFile(percorsoTonatiuh, percorsoMetztli, costoMetztli, costoTonatiuh);
		System.out.println("Fatto!");
	}
	
}
