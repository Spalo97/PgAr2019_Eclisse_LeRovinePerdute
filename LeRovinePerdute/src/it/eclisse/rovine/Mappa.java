package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Mappa {
	/** lista delle città dell'xml*/
    private LinkedList<City> listaCitta = new LinkedList<>();
    
    /** Array che contiene tutti i collegamenti fra le città */
    private ArrayList<City> grafo= new ArrayList<City>();
    
    /** Array ottenuti dai rispettivi metodi di calcolo del costo*/
    private LinkedList<City> percorsoTonatiuh = new LinkedList<>();
    private LinkedList<City> percorsoMetztli = new LinkedList<>();
    
    /** Array che contiene le città già controllate */
    private LinkedList<City> cittaControllate= new LinkedList<>();

    /** Attributi della mappa*/
    private City campoBaseTonatiuh;
    private City rovinePerduteTonatiuh;
    private City campoBaseMetztli;
    private City rovinePerduteMetztli;
    private int idPesoMinore;
    private int size;
    
    /** id delle rovine perdute*/
    private int idMaggiore=0;
    /** peso dei percorsi delle squadre*/
	private double costoMetztli=0.0;
    private double costoTonatiuh=0.0;
    
    /** data come parametro un'Array di città crea la mappa */
    public Mappa(LinkedList<City> listCity) {
    	listaCitta=listCity;
    }

    /** Metodo per il calcolo del percorso dei Tonatiuh  */
    public LinkedList<City> calcolaPercorsoTonatiuh() {
    	setGrafo();
    	size=grafo.size();
    	while(!listaCitta.isEmpty()) {
    		idPesoMinore=getIdPesoMinore();
    		if(idPesoMinore==size) {
    			campoBaseTonatiuh=listaCitta.getFirst();
    			for(int i=0;i<campoBaseTonatiuh.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(campoBaseTonatiuh.getIdCollegamenti().get(i));
    				vicino.setDistanza(getPeso(campoBaseTonatiuh.getX(),vicino.getX(),campoBaseTonatiuh.getY(),vicino.getY()));
    				vicino.setIdPrecedente(campoBaseTonatiuh.getId());
    			}
    			cittaControllate.add(campoBaseTonatiuh);
    			listaCitta.remove(campoBaseTonatiuh);
    		}else {
    			City T=getCitta(idPesoMinore);
    			for(int i=0;i<T.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(T.getIdCollegamenti().get(i));   				
    					double distanza =T.getDistanza()+getPeso(T.getX(),vicino.getX(),T.getY(),vicino.getY());
    					if(distanza<vicino.getDistanza() && vicino.getId()!=0) {
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
    	
    	rovinePerduteTonatiuh=cittaControllate.get(idMaggiore);
    	costoTonatiuh=rovinePerduteTonatiuh.getDistanza();
    	
    	while(rovinePerduteTonatiuh.getId()!=0){
    		percorsoTonatiuh.add(rovinePerduteTonatiuh);
    		rovinePerduteTonatiuh=getCitta(rovinePerduteTonatiuh.getIdPrecedente());
    	}
    	percorsoTonatiuh.add(rovinePerduteTonatiuh);
    	Collections.reverse(percorsoTonatiuh);
    	return percorsoTonatiuh;
    }
    
    /** Metodo per il calcolo del percorso dei Metztli  */
    public LinkedList<City> calcolaPercorsoMetztli() {
    	reset();
    	
    	size=grafo.size();
    	while(!listaCitta.isEmpty()) {
    		idPesoMinore=getIdPesoMinore();
    		if(idPesoMinore==size) {
    			campoBaseMetztli=listaCitta.getFirst();
    			for(int i=0;i<campoBaseMetztli.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(campoBaseMetztli.getIdCollegamenti().get(i));
    				vicino.setDistanza(getPesoMetztli(campoBaseMetztli.getH(),vicino.getH()));
    				vicino.setIdPrecedente(campoBaseMetztli.getId());
    			}
    			cittaControllate.add(campoBaseMetztli);
    			listaCitta.remove(campoBaseMetztli);
    		}else {
    			City T=getCitta(idPesoMinore);
    			for(int i=0;i<T.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(T.getIdCollegamenti().get(i));   				
    					double distanza =T.getDistanza()+getPesoMetztli(T.getH(),vicino.getH());
    					if(distanza<vicino.getDistanza() && vicino.getId()!=0) {
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
    	
    	rovinePerduteMetztli=cittaControllate.get(idMaggiore);
    	costoMetztli=rovinePerduteMetztli.getDistanza();
    	
    	while(rovinePerduteMetztli.getId()!=0){
    		percorsoMetztli.add(rovinePerduteMetztli);
    		rovinePerduteMetztli=getCitta(rovinePerduteMetztli.getIdPrecedente());
    	}
    	percorsoMetztli.add(rovinePerduteMetztli);
    	Collections.reverse(percorsoMetztli);
    	return percorsoMetztli;
    }
    
    /** Getter del costo totale della squadra Metztli*/
    public double getCostoMetztli() {
    	return costoMetztli;
    }
    
    /** Restituisce un'oggetto di tipo City dato il suo id*/
    private City getCitta(int id) {
    	City citta=new City();
    	for(City c:grafo) {
    		if(id==c.getId())
    			citta=c;
    	}
		return citta;
    }
    
    /** riempie l'array di collegamenti*/
    private void setGrafo() {
    	for(int i=0;i<this.listaCitta.size();i++) {
    		City c=new City();
    		c=this.listaCitta.get(i);
    		grafo.add(c);
    	}
    }
    
    /** riempie listaCitta */
    private void resetListaCitta() {
    	for(int i=0;i<this.grafo.size();i++) {
    		City c=new City();
    		c=this.grafo.get(i);
    		listaCitta.add(c);
    	}
    }

    /** rimuove le città dalla lista*/
    private void removeCitta(int id) {
    	for(int i=0;i<listaCitta.size();i++) {
    		if(id==listaCitta.get(i).getId())
    			listaCitta.remove(i);
    	}
    }
    /** restituisce l'id del nodo con peso  minore   */
    private int getIdPesoMinore() {
    	int id=listaCitta.size();
    	double costo=Double.POSITIVE_INFINITY;
    	for(City c:listaCitta) {
    		if(c.getDistanza()<costo)
    			id=c.getId();
    	}
    	return id;
    }
    
    /** Metodo che dato come parametro le coordinate di due città restituisce la distanza fra le due */
    public double getPeso(int x0, int x1, int y0, int y1) {
        double x = Math.pow(x0-x1, 2);
        double y = Math.pow(y0-y1, 2);
        return Math.sqrt(x+y);
    }
    /** Metodo che dato come parametro la quota di due città restituisce la differenza 
     * fra le due quote */
    private int getPesoMetztli(int h1,int h2) {
    	return Math.abs(h1-h2);
    }
    
    /** Getter costo totale del percorso Tonatiuh */
    public double getCostoTonatiuh() {
    	return costoTonatiuh;
    }
    
    /**imposta la distanza inziale a infinito e imposta gli id dei precedenti a -1 
     * in modo da analizzare in modo corretto la mappa e resettarla
     * dopo il caclcolo del percorso della prima squadra */
    private void reset() {
    	for(int i=0;i<grafo.size();i++) {
    		grafo.get(i).setDistanza(Double.POSITIVE_INFINITY);
    		grafo.get(i).setIdPrecedente(-1);

    	}
    	for(int i=0;i<cittaControllate.size();i++) {
    		cittaControllate.remove();
    	}
		idMaggiore=0;
		idPesoMinore=0;
    	resetListaCitta();
    }
}
