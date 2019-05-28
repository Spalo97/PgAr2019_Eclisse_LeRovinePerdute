package it.eclisse.rovine;

import java.util.LinkedList;

public class LeRovinePerduteMain {
	
	public static void main(String[] args) {
		GestioneXml gestione=new GestioneXml();
		int mappaScelta=gestione.sceltamappa();
		MappaTonatiuh mappaTonatiuh=new MappaTonatiuh(gestione,mappaScelta);
		MappaMetztli mappaMetztli=new MappaMetztli(gestione,mappaScelta);
		LinkedList<City> percorsoTonatiuh=mappaTonatiuh.calcolaPercorso();
		LinkedList<City> percorsoMetztli=mappaMetztli.calcolaPercorso();
		double costoTonatiuh=mappaTonatiuh.getCostoTotale();
		double costoMetztli=mappaMetztli.getCostoTotale();
		gestione.writeFile(percorsoTonatiuh, percorsoMetztli, costoMetztli, costoTonatiuh);
		System.out.println("Fatto!");
	}
	
}
