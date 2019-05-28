package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Mappa {
	/** lista delle città dell'xml*/
	protected LinkedList<City> listaCitta = new LinkedList<>();
    
    /** Array che contiene tutti i collegamenti fra le città */
    protected ArrayList<City> grafo= new ArrayList<City>();
    
    /** Array ottenuti dai rispettivi metodi di calcolo del costo*/
    protected LinkedList<City> percorso = new LinkedList<>();
    
   /** Array che contiene le città già controllate */
    protected LinkedList<City> cittaControllate= new LinkedList<>();

    /** Attributi della mappa*/
    protected City campoBase;
    protected City rovinePerdute;
    protected int idPesoMinore;
    protected int size;
    
    /** id delle rovine perdute*/
    protected int idMaggiore=0;
    /** peso dei percorsi delle squadre*/
	protected double costoTotale=0.0;
    
	protected GestioneXml gestione;
	protected int mappaScelta;
	
    /** data come parametro un'Array di città crea la mappa */
    public Mappa(GestioneXml gestione,int mappaScelta) {
    	this.gestione=gestione;
    	this.mappaScelta=mappaScelta;
    }
    
    public void importListaCitta() {
    	this.gestione.importXml(this.mappaScelta);
    	listaCitta=this.gestione.getListCity();
    }

    /** Getter del costo totale del percorso*/
    public double getCostoTotale() {
    	return costoTotale;
    }
    
    public boolean checkId(int id) {
    	boolean check=false;
    	for(City c:grafo) {
    		if(id==c.getId())
    			check=true;
    	}
    	return check;
    }
    
    /** Restituisce un'oggetto di tipo City dato il suo id*/
    public City getCitta(int id) {
    	for(City c:grafo) {
    		if(id==c.getId())
    			return c;
    	}
    	return null;
    }
    
    /** riempie l'array di collegamenti*/
    public void setGrafo() {
    	for(int i=0;i<this.listaCitta.size();i++) {
    		City c=new City();
    		c=this.listaCitta.get(i);
    		grafo.add(c);
    	}
    }
    
    /** riempie listaCitta */
    public void resetListaCitta() {
    	for(int i=0;i<this.grafo.size();i++) {
    		City c=new City();
    		c=this.grafo.get(i);
    		listaCitta.add(c);
    	}
    }

    /** rimuove le città dalla lista*/
    public void removeCitta(int id) {
    	for(int i=0;i<listaCitta.size();i++) {
    		if(id==listaCitta.get(i).getId())
    			listaCitta.remove(i);
    	}
    }
    /** restituisce l'id del nodo con peso  minore   */
    public int getIdPesoMinore() {
    	int id=listaCitta.size();
    	double costo=Double.POSITIVE_INFINITY;
    	for(City c:listaCitta) {
    		if(c.getDistanza()<costo)
    			id=c.getId();
    	}
    	return id;
    }
    
    /**imposta la distanza inziale a infinito e imposta gli id dei precedenti a -1 
     * in modo da analizzare in modo corretto la mappa e resettarla
     * dopo il caclcolo del percorso della prima squadra */
    public void reset() {
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
