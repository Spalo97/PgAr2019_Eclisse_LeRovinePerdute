package it.eclisse.rovine;

public class Nodo {
    private int id;
    private double distanza;
    private int idPrecedente;

    public Nodo(int id, double distanza, int idPrecedente) {
        this.id = id;
        this.distanza = distanza;
        this.idPrecedente = idPrecedente;
    }

    public Nodo() {
    }

    public int getId() {
        return id;
    }

    public double getDistanza() {
        return distanza;
    }

    public int getIdPrecedente() {
        return idPrecedente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDistanza(double distanza) {
        this.distanza = distanza;
    }

    public void setIdPrecedente(int idPrecedente) {
        this.idPrecedente = idPrecedente;
    }
}
