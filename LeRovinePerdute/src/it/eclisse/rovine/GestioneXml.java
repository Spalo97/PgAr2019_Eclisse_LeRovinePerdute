package it.eclisse.rovine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class GestioneXml {

	/**dichiarazione dei file XML*/
	private String map_5="PgAr_Map_5.xml";
	private String map_12="PgAr_Map_12.xml";
	private String map_50="PgAr_Map_50.xml";
	private String map_200="PgAr_Map_200.xml";
	private  String map_2000="PgAr_Map_2000.xml";
	private String map_10000="PgAr_Map_10000.xml";
	
	private String fileSelected; /**attributo del nome del file xml su cui lavorare*/
	private LinkedList<City> listCity=new LinkedList<>(); /**LinkedList delle citta prelevate dal file XML*/

	/*attributi per la gestione dei file XML*/
	private XMLInputFactory xmlif=null;
	private XMLStreamReader xmlr=null;
	private XMLOutputFactory xmlof = null;
	private XMLStreamWriter xmlw = null;
	
	public GestioneXml() {

	}

	/**metodo per l'importazione dei file XML*/
	public void importXml(int n) {
		switch(n) {
			case 0: fileSelected=map_5; break;
			case 1: fileSelected=map_12; break;
			case 2: fileSelected=map_50; break;
			case 3:	fileSelected=map_200; break;
			case 4: fileSelected=map_2000; break;
			case 5: fileSelected=map_10000; break;
		}
		
		openInitialization();
		
		try{
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()){
				case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
					System.out.println("Inizio lettura file: " + fileSelected); break;
				case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
					if(xmlr.getLocalName().equals("city")) {
						City c=new City();
						for (int i = 0; i < xmlr.getAttributeCount(); i++) {
							switch(xmlr.getAttributeLocalName(i)) {
								case "id": c.setId(Integer.parseInt(xmlr.getAttributeValue(i))); break;
								case "name": c.setNome(xmlr.getAttributeValue(i)); break;
								case "x": c.setX(Integer.parseInt(xmlr.getAttributeValue(i))); break;
								case "y": c.setY(Integer.parseInt(xmlr.getAttributeValue(i))); break;
								case "h": c.setH(Integer.parseInt(xmlr.getAttributeValue(i))); break;
							}
						}
						listCity.add(c);
					}else if(xmlr.getLocalName().equals("link")){
						listCity.get(listCity.size()-1).addCollegamento(Integer.parseInt(xmlr.getAttributeValue(0)));
					}
				}
				xmlr.next();
			}
		}catch(Exception e) {
			System.out.println("Errore nell'importazione del file:");
			System.out.println(e.getMessage());
		}
	}

	/**getter della lista delle citta*/
	public LinkedList<City> getListCity() {
		return listCity;
	}

	/**metodo per la scrittura del file XML dati LinkedList dei percorsi e i costi totali per ogni percorso*/
	public void writeFile(LinkedList<City> percorso_metztli,LinkedList<City> percorso_tonatiuh, double costo_metztli,double costo_tonatiuh) {
		writerInitialization();
		
		try {
			xmlw.writeStartElement("routes");
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("Team", "Tonatiuh");
			xmlw.writeAttribute("cost", Double.toString(costo_tonatiuh));
			xmlw.writeAttribute("cities", Integer.toString(percorso_tonatiuh.size()));
			for(int i=0;i<percorso_tonatiuh.size();i++) {
				xmlw.writeStartElement("city");
				xmlw.writeAttribute("id",Integer.toString(percorso_tonatiuh.get(i).getId()));
				xmlw.writeAttribute("name",percorso_tonatiuh.get(i).getNome());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("Team", "Metztli");
			xmlw.writeAttribute("cost", Double.toString(costo_metztli));
			xmlw.writeAttribute("cities", Integer.toString(percorso_metztli.size()));
			for(int i=0;i<percorso_metztli.size();i++) {
				xmlw.writeStartElement("city");
				xmlw.writeAttribute("id",Integer.toString(percorso_metztli.get(i).getId()));
				xmlw.writeAttribute("name",percorso_tonatiuh.get(i).getNome());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.writeEndElement();
			xmlw.writeEndDocument();
			xmlw.flush();
			xmlw.close();
			
		}catch(Exception e){
			System.out.println("Errore nella scrittura del file:");
			System.out.println(e.getMessage());
		}
		
	}

	/**metodo di inizializzazione della lettura del file XML*/
	private void openInitialization() {
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(fileSelected, new FileInputStream(fileSelected));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
	}

	/**metodo di ininzializzazione della scrittura del file XML*/
	private void writerInitialization() {
		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("Routes.xml"), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
		}
	}
	
	/** metodo che permette all'utente di scegliere la mappa*/
	public int sceltamappa() {
		int scelta = 0;
		boolean finito = false;
		Scanner lettore = new Scanner(System.in);
		do {
			System.out.print("SCEGLI UNA MAPPA:\n");
			System.out.print("[0] Map_5 \n");
			System.out.print("[1] Map_12 \n");
			System.out.print("[2] Map_50 \n");
			System.out.print("[3] Map_200 \n");
			System.out.print("[4] Map_2000 \n");
			System.out.print("[5] Map_10000 \n");
			try {
				scelta = lettore.nextInt();
				if (scelta >5) {
					System.out.println("ERRORE: INSERISCI UN VALORE CORRETTO!");
				}else {
					finito = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("ERRORE: INSERISCI UN VALORE CORRETTO!");
				String trashString = lettore.next();
			}
		} while (!finito);
		return scelta;
	}
}
