package it.eclisse.rovine;

public class LeRovinePerduteMain {
	private static int xmlScelto = 5;

	public static void main(String[] args) {
		Mappa mappa = new Mappa();
		GestioneXml.importXml(0);

		for (Nodo elemento: mappa.calcolaMappa(xmlScelto)) {
			System.out.println(elemento.getId());
		}
		System.out.println("Fatto!");
	}

}
