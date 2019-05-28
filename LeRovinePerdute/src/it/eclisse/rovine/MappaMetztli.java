package it.eclisse.rovine;

import java.util.Collections;
import java.util.LinkedList;

public class MappaMetztli extends Mappa{
	
	public MappaMetztli(GestioneXml gestione,int mappaScelta) {
		super(gestione,mappaScelta);
	}

	/** Metodo per il calcolo del percorso dei Metztli  */
    public LinkedList<City> calcolaPercorso() {
    	importListaCitta();
    	setGrafo();
    	size=grafo.size();
    	while(!listaCitta.isEmpty()) {
    		idPesoMinore=getIdPesoMinore();
    		if(idPesoMinore==size) {
    			campoBase=listaCitta.getFirst();
    			for(int i=0;i<campoBase.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(campoBase.getIdCollegamenti().get(i));
    				vicino.setDistanza(getPeso(campoBase.getH(),vicino.getH()));
    				vicino.setIdPrecedente(campoBase.getId());
    			}
//    			cittaControllate.add(campoBase);
    			listaCitta.remove(campoBase);
    		}else {
    			City T=getCitta(idPesoMinore);
    			for(int i=0;i<T.getIdCollegamenti().size();i++) {
    				if(checkId(T.getIdCollegamenti().get(i))) {
	    				City vicino=getCitta(T.getIdCollegamenti().get(i));   				
	    					double distanza =T.getDistanza()+getPeso(T.getH(),vicino.getH());
	    					if(distanza<vicino.getDistanza() && vicino.getId()!=0) {
			    				vicino.setDistanza(distanza);
			    				vicino.setIdPrecedente(T.getId());
	    					}
    				}
    			}
//    			if(idMaggiore<T.getId()) {
//    				idMaggiore=cittaControllate.size();
//    			}
//    			cittaControllate.add(T);
    			listaCitta.remove(T);
    			
    		}
    	}
    	
    	rovinePerdute=grafo.get(size-1);
    	costoTotale=rovinePerdute.getDistanza();
    	
    	while(rovinePerdute.getId()!=0){
    		percorso.add(rovinePerdute);
    		rovinePerdute=getCitta(rovinePerdute.getIdPrecedente());
    	}
    	percorso.add(rovinePerdute);
    	Collections.reverse(percorso);
    	return percorso;
    }
    
    /** Metodo che dato come parametro la quota di due città restituisce la differenza 
     * fra le due quote */
    public int getPeso(int h1,int h2) {
    	return Math.abs(h1-h2);
    }
}
