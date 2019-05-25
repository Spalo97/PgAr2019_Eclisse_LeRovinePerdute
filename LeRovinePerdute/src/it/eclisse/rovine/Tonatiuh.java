package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.LinkedList;

public class Tonatiuh {
    private ArrayList<City> listaCitta = new ArrayList<>();
    private final String NOME_DESTINAZIONE = "Rovine Perdute";
    //private LinkedList<City> percorsoMigliore = new LinkedList<>();
    private int numeroCittaAdiacenti;
    private boolean ctrl = true;
    private int idDestinazione; // valore da assegnare
    private LinkedList<Integer>  idGiaUtilizzati = new LinkedList<>();

    public void setIdDestinazione(int idDestinazione) {
        this.idDestinazione = idDestinazione;
    }

    public void setListaCitta() {
        this.listaCitta = GestioneXml.getListCity();
    }

    public boolean controllaDestinazione(String nome) {
        if(nome.equals(NOME_DESTINAZIONE)) {
            return true;
        }
        else {
            return false;
        }
    }

    public City scegliCittaDaLista(int indice) {
        int ascissaX = listaCitta.get(indice).getX();
        int ordinataY = listaCitta.get(indice).getY();
        int altezzaH = listaCitta.get(indice).getH();
        int nuovoId = listaCitta.get(indice).getId();
        City cittaInizializzata = new City(ascissaX, ordinataY, altezzaH,nuovoId);
        return cittaInizializzata;
    }

    public double getPeso(int x0, int x1, int y0, int y1) {
        double x = Math.pow(x0-x1, 2);
        double y = Math.pow(y0-y1, 2);
        return Math.sqrt(x+y);
    }

    /*public void calcolaPeso(int id) {
        if (ctrl) {
            id = 0;
            City cittaIniziale = scegliCittaDaLista(id);
            percorsoMigliore.add(cittaIniziale);
            idGiaUtilizzati.add(cittaIniziale.getId());
            ctrl = false;
        }

        LinkedList<City> cittaAdiacenti = new LinkedList<>();
        int numeroCittaCollegate = listaCitta.get(id).getIdCollegamenti().size();
        for (int i = 0; i < numeroCittaCollegate; i++){
            int idAdiacente = listaCitta.get(id).getIdCollegamenti().get(i);
            City nuovaCittaAdiacente = scegliCittaDaLista(idAdiacente);
            cittaAdiacenti.add(nuovaCittaAdiacente);
        }

        numeroCittaAdiacenti = cittaAdiacenti.size();
        double[] distanze = new double[numeroCittaAdiacenti];
        for (int i = 0; i < numeroCittaAdiacenti; i++) {
            distanze[i] = calcolaDistanza(listaCitta.get(id).getX(), cittaAdiacenti.get(i).getX(), listaCitta.get(id).getY(), cittaAdiacenti.get(i).getY());
        }

        double distanzaInferiore =distanze[0];
        int indiceCittaPiuVicina = 0;
        for (int i = 0; i < numeroCittaAdiacenti; i++) {
            if (distanzaInferiore<distanze[i]) {
                distanzaInferiore = distanze[i];
                indiceCittaPiuVicina = i;
            }
        }
        City cittaScelta = scegliCittaDaLista(indiceCittaPiuVicina);
        percorsoMigliore.add(cittaScelta);
        if (cittaScelta.getId() < idDestinazione) {
            calcolaPercorso(cittaScelta.getId());
        }
    }*/

}
