package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Mappa {

    private LinkedList<City> listaCitta = new LinkedList<>(); //Lista delle città ottenute dall'XML
    private LinkedList<City> percorso = new LinkedList<>();
    
    private LinkedList<City> cittaControllate= new LinkedList<>();
    private Double costoTotale=0.0;
    private City campoBase;
    private City rovinePerdute;
    private int idPesoMinore;
    private int size;
    
    private int idMaggiore=0;
    
    public Mappa() {
    	listaCitta=GestioneXml.getListCity();
    }

    public LinkedList<City> calcolaPercorso() {
    	size=GestioneXml.getListCity().size();
    	while(!listaCitta.isEmpty()) {
    		idPesoMinore=getIdPesoMinore();
    		if(idPesoMinore==size) {
    			City campoBase=listaCitta.getFirst();
    			for(int i=0;i<campoBase.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(campoBase.getIdCollegamenti().get(i));
    				vicino.setDistanza(getPeso(campoBase.getX(),vicino.getX(),campoBase.getY(),vicino.getY()));
    				vicino.setIdPrecedente(campoBase.getId());
    			}
    			cittaControllate.add(campoBase);
    			listaCitta.remove(campoBase);
    		}else {
    			City T=getCitta(idPesoMinore);
    			for(int i=0;i<T.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(T.getIdCollegamenti().get(i));   				
    					double distanza =T.getDistanza()+getPeso(T.getX(),vicino.getX(),T.getY(),vicino.getY());
    					if(distanza<vicino.getDistanza()) {
		    				vicino.setDistanza(distanza);
		    				vicino.setIdPrecedente(T.getId());
    					}
    			}
    			if(idMaggiore<T.getId()) {
    				idMaggiore=cittaControllate.size();
    			}
    			cittaControllate.add(T);
    			removeCitta(T.getId());
    		}
    	}
    	
    	rovinePerdute=cittaControllate.get(idMaggiore);
    	
    	do{
    		percorso.add(rovinePerdute);
    		costoTotale+=rovinePerdute.getDistanza();
    		rovinePerdute=getCitta(rovinePerdute.getIdPrecedente());
    	}while(rovinePerdute!=campoBase);
    	
    	return percorso;
    }
    
    private City getCitta(int id) {
    	City citta=new City();
    	for(City c:GestioneXml.getListCity()) {
    		if(id==c.getId())
    			citta=c;
    	}
		return citta;
    }
    
    private void removeCitta(int id) {
    	for(int i=0;i<listaCitta.size();i++) {
    		if(id==listaCitta.get(i).getId())
    			listaCitta.remove(i);
    	}
    }
    
    private int getIdPesoMinore() {
    	int id=GestioneXml.getListCity().size();
    	double costo=Double.POSITIVE_INFINITY;
    	for(City c:listaCitta) {
    		if(c.getDistanza()<costo)
    			id=c.getId();
    	}
    	return id;
    }
    
    public double getPeso(int x0, int x1, int y0, int y1) {
        double x = Math.pow(x0-x1, 2);
        double y = Math.pow(y0-y1, 2);
        return Math.sqrt(x+y);
    }
    
    public double getCostoTotale() {
    	return costoTotale;
    }

    private void setListaCitta() {
        this.listaCitta = GestioneXml.getListCity();
    }

}
