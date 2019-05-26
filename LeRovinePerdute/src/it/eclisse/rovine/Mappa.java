package it.eclisse.rovine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Mappa {

    private LinkedList<Nodo> listaNodi = new LinkedList<>(); //Elenco dei nodi che inizialmente sarà vuoto
    private ArrayList<City> listaCitta = new ArrayList<>(); //Lista delle città ottenute dall'XML
    private LinkedList<Nodo> listaFinale = new LinkedList<>();

    private Nodo nodoMinore = new Nodo();
    private City cittaDiRiferimento = new City();
    private ArrayList<Nodo> cittaRimaste = new ArrayList<>();

    public LinkedList<Nodo> calcolaMappa(int lunghezza) {
        lunghezza--;
        int idDaCercare = -1;
        setListaCitta();
        algoritmoDijkstra(listaCitta.get(0).getId());
        for (Nodo nodo: listaNodi) {
            if (nodo.getId() == lunghezza) {
                listaFinale.add(nodo);
                idDaCercare = nodo.getIdPrecedente();
            }
            else if (nodo.getId() == idDaCercare) {
                listaFinale.add(nodo);
                idDaCercare = nodo.getIdPrecedente();
            }
            if (nodo.getId() == 0) {
                break;
            }
        }
        Collections.reverse(listaFinale);
        return listaFinale;
    }


    public void algoritmoDijkstra(int idIniziale) {
        cittaDiRiferimento = listaCitta.get(idIniziale);
        while (!listaCitta.isEmpty()) {
            if (cittaDiRiferimento.getId() == 0) {
                nodoMinore.setId(cittaDiRiferimento.getId());
                nodoMinore.setDistanza(0);
                nodoMinore.setIdPrecedente(-1);
            }
            Nodo nodoAggiunto = nodoMinore;
            listaNodi.add(nodoAggiunto);

            double distanzaDaAggiungere = nodoMinore.getDistanza();
            nodoMinore.setDistanza(Double.POSITIVE_INFINITY);

            for (int idCollegato: cittaDiRiferimento.getIdCollegamenti()) {
                boolean ctrl = true;
                Nodo nodoDaAggiungere = new Nodo();
                nodoDaAggiungere.setId(idCollegato);

                int x = calcolaValore(listaCitta, idCollegato, true);
                int y = calcolaValore(listaCitta, idCollegato, false);

                nodoDaAggiungere.setDistanza(getPeso(x, cittaDiRiferimento.getX(), y, cittaDiRiferimento.getY())+distanzaDaAggiungere);
                nodoDaAggiungere.setIdPrecedente(cittaDiRiferimento.getId());
                for (Nodo cityDaControllare: cittaRimaste) {
                    if (cityDaControllare.getId() == nodoDaAggiungere.getId()) {
                        if (nodoDaAggiungere.getDistanza() < cityDaControllare.getDistanza()) {
                            cityDaControllare.setDistanza(nodoDaAggiungere.getDistanza());
                            ctrl = false;
                        }
                    }
                }
                if (ctrl) {
                    if (nodoDaAggiungere.getDistanza() < nodoMinore.getDistanza()) {
                        nodoMinore.setId(nodoDaAggiungere.getId());
                        nodoMinore.setIdPrecedente(nodoDaAggiungere.getIdPrecedente());
                        nodoMinore.setDistanza(nodoDaAggiungere.getDistanza());
                    }
                    else {
                        cittaRimaste.add(nodoDaAggiungere);
                    }
                }
            }
//            for (City citta: listaCitta) {
//                if (citta.getId() == nodoMinore.getId()) {
//                    cittaDiRiferimento = citta;
//                    break;
//                }
//            }
            listaCitta.remove(cittaDiRiferimento);
            for (City citta: listaCitta) {
                if (citta.getId() == nodoMinore.getId()) {
                    cittaDiRiferimento = citta;
                    break;
                }
            }
            if (!listaCitta.isEmpty()) {
                for (Nodo citta: cittaRimaste) {
                    if (citta.getDistanza() < nodoMinore.getDistanza()) {
                        nodoMinore.setId(citta.getId());
                        nodoMinore.setIdPrecedente(citta.getIdPrecedente());
                        nodoMinore.setDistanza(citta.getDistanza());
                    }
                }
                //algoritmoDijkstra(nodoMinore.getId());
            }
            else {
                listaNodi.add(nodoMinore);
                System.out.println("Fatto!");
            }
        }
    }


    public void setListaCitta() {
        this.listaCitta = GestioneXml.getListCity();
    }

    public int calcolaValore(ArrayList<City> lista, int id, boolean isX) {
        int val = 0;
        for (City elemento: lista) {
            if (elemento.getId() == id) {
                if (isX) {
                    val = elemento.getX();
                }
                else {
                    val = elemento.getY();
                }
            }
        }
        return val;
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

    public Nodo minimaDistanza(int idDaAnalizzare) {
        int idMin = 0;
        double pesoMin = Double.POSITIVE_INFINITY;
        for(int i: listaCitta.get(idDaAnalizzare).getIdCollegamenti()) {
            double peso = getPeso(listaCitta.get(i).getX(), listaCitta.get(idDaAnalizzare).getX(), listaCitta.get(i).getY(),listaCitta.get(idDaAnalizzare).getY());
            if (peso < idMin) {
                idMin = i;
                pesoMin = peso;
            }
        }
        Nodo nodoVicino = new Nodo(idMin, pesoMin, idDaAnalizzare);
        return nodoVicino;
    }

//    public void algoritmoDijkstra(int idIniziale) {
//        //per ogni città esistente viene definito una distanza infinita dall'ID precedente e si imposta ID precedente sconosciuto(-1)
//        for (City citta: listaCitta) {
//            City nuovoNodo = new City(citta.getId(), Integer.MAX_VALUE, -1);
//            elencoNodi.add(nuovoNodo); //ogni città definita in questo modo viene aggiunta all'elenco dei nodi
//        }
//
//        //il nodo iniziale (che ha id = idIniziale) ha distanza 0 dal nodo precedente, ovvero è esso stesso il nodo di partenza
//        elencoNodi.get(idIniziale).setDistanza(0);
//
//        while (!elencoNodi.isEmpty()) {
//
//            Nodo minimo = minimaDistanza(elencoNodi.getFirst().getId()); //calola nodo con minima distanza
//            int idMinimo = minimo.getId();
//            elencoNodi.remove(elencoNodi.get(idMinimo));
//            tabellaRiferimento.add(minimo);
//
//            for (int idVicino: listaCitta.get(idMinimo).getIdCollegamenti()) {
//                if (elencoNodi.getI.contains(listaCitta.get(idVicino))) {
//
//                }
//            }
//        }
//    }
}
