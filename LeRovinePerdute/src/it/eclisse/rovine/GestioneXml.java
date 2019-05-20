package it.eclisse.rovine;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class GestioneXml {
	
	private final static String mappa_5="PgAr_Map_5.xml";
	private final static String mappa_12="PgAr_Map_12.xml";
	private final static String mappa_50="PgAr_Map_50.xml";
	private final static String mappa_200="PgAr_Map_200.xml";
	private final static String mappa_2000="PgAr_Map_2000.xml";
	private final static String mappa_10000="PgAr_Map_10000.xml";
	
	private static String fileSelezionato;
	private static ArrayList<Città> listaCittà=new ArrayList();
	private static String dimensioni;
	
	private static XMLInputFactory xmlif=null;
	private static XMLStreamReader xmlr=null;
	
	public GestioneXml() {
		
	}
	
	public static void importXml(int n) {
		switch(n) {
			case 0: fileSelezionato=mappa_5; break;
			case 1: fileSelezionato=mappa_12; break;
			case 2: fileSelezionato=mappa_50; break;
			case 3:	fileSelezionato=mappa_200; break;
			case 4: fileSelezionato=mappa_2000; break;
			case 5: fileSelezionato=mappa_10000; break;
		}
		
		aperturaFile();
		
		try{
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()){
				case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
					System.out.println("Inizio lettura file: " + fileSelezionato); break;
				case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
					if(xmlr.getLocalName().equals("city")) {
						Città c=new Città();
						for (int i = 0; i < xmlr.getAttributeCount(); i++) {
							switch(xmlr.getAttributeLocalName(i)) {
								case "id": c.setId(xmlr.getAttributeValue(i)); break;
								case "name": c.setNome(xmlr.getAttributeValue(i)); break;
								case "x": c.setX(xmlr.getAttributeValue(i)); break;
								case "y": c.setY(xmlr.getAttributeValue(i)); break;
								case "h": c.setH(xmlr.getAttributeValue(i)); break;
							}
						}
						listaCittà.add(c);
					}else if(xmlr.getLocalName().equals("link")){
						listaCittà.get(listaCittà.size()-1).addCollegamento(xmlr.getAttributeValue(0));
					}else {
						dimensioni= xmlr.getAttributeValue(0);
					}
					break;
				}
				xmlr.next();
			}
		}catch(Exception e) {
			System.out.println("Errore nell'importazione del file:");
			System.out.println(e.getMessage());
		}
	}
	
	private static void aperturaFile() {
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(fileSelezionato, new FileInputStream(fileSelezionato));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
	}
}
