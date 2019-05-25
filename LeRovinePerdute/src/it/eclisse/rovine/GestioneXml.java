package it.eclisse.rovine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class GestioneXml {
	
	private final static String map_5="PgAr_Map_5.xml";
	private final static String map_12="PgAr_Map_12.xml";
	private final static String map_50="PgAr_Map_50.xml";
	private final static String map_200="PgAr_Map_200.xml";
	private final static String map_2000="PgAr_Map_2000.xml";
	private final static String map_10000="PgAr_Map_10000.xml";
	
	private static String fileSelected;
	private static ArrayList<City> listCity=new ArrayList<>();
	
	private static XMLInputFactory xmlif=null;
	private static XMLStreamReader xmlr=null;
	private static XMLOutputFactory xmlof = null;
	private static XMLStreamWriter xmlw = null;
	
	public GestioneXml() {
		
	}

	public ArrayList<City> getListaCity() {
		return listCity;
	}

	public static void importXml(int n) {
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
	
	public static ArrayList<City> getListCity() {
		return listCity;
	}

	public static void writeFile(ArrayList<City> percorso_metztli,ArrayList<City> percorso_tonatiuh, int costo_metztli,int costo_tonatiuh) {
		writerInitialization();
		
		try {
			xmlw.writeStartElement("routes");
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("Team", "Tonatiuh");
			xmlw.writeAttribute("cost", Integer.toString(costo_tonatiuh));
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
			xmlw.writeAttribute("cost", Integer.toString(costo_metztli));
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
	
	private static void openInitialization() {
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(fileSelected, new FileInputStream(fileSelected));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
	}
	
	private static void writerInitialization() {
		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("Routes.xml"), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
		}
	}
}
