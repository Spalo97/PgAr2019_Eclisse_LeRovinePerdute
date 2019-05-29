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
//    	controlloPrecedente(); bugga perché ci sono città che non hanno precedenti come campoBase
    	while(!listaCitta.isEmpty()) {
    		idPesoMinore=getIdPesoMinore();
    		if(idPesoMinore==size) {
    			campoBase=listaCitta.getFirst();
    			rovinePerdute=listaCitta.getLast();
    			for(int i=0;i<campoBase.getIdCollegamenti().size();i++) {
    				City vicino=getCitta(campoBase.getIdCollegamenti().get(i));
    				vicino.setDistanza(getPeso(campoBase.getH(),vicino.getH()));
    				vicino.setIdPrecedente(campoBase.getId());
    			}
    			removeCitta(campoBase.getId());
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
    			removeCitta(T.getId());
    			
    		}
    	}
    
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
