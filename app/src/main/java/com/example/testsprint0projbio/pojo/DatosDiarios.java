package com.example.testsprint0projbio.pojo;

public class DatosDiarios {

    private String fecha;
    private double media;
    private double suma;

    public DatosDiarios(String fecha, double media, double suma) {
        this.fecha = fecha;
        this.media = media;
        this.suma = suma;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "DatosDiarios{" +
                "fecha='" + fecha + '\'' +
                ", media=" + media +
                ", suma=" + suma +
                '}';
    }
}
