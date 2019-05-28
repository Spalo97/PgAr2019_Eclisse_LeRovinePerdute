package it.eclisse.rovine;

import java.util.Collections;
import java.util.LinkedList;

public class MappaTonatiuh extends Mappa{
	
	public MappaTonatiuh(GestioneXml gestione,int mappaScelta) {
		super(gestione,mappaScelta);
	}
	
    /** Metodo per il calcolo del percorso dei Tonatiuh  */
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
    				vicino.setDistanza(getPeso(campoBase.getX(),vicino.getX(),campoBase.getY(),vicino.getY()));
    				vicino.setIdPrecedente(campoBase.getId());
    			}
//    			cittaControllate.add(campoBase);
    			listaCitta.remove(campoBase);
    		}else {
    			City T=getCitta(idPesoMinore);
    			for(int i=0;i<T.getIdCollegamenti().size();i++) {
    				if(checkId(T.getIdCollegamenti().get(i))) {
	    				City vicino=getCitta(T.getIdCollegamenti().get(i));   				
	    					double distanza =T.getDistanza()+getPeso(T.getX(),vicino.getX(),T.getY(),vicino.getY());
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

	 /** Metodo che dato come parametro le coordinate di due città restituisce la distanza fra le due */
    public double getPeso(int x0, int x1, int y0, int y1) {
        double x = Math.pow(x0-x1, 2);
        double y = Math.pow(y0-y1, 2);
        return Math.sqrt(x+y);
    }
}
