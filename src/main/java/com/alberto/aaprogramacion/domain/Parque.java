package com.alberto.aaprogramacion.domain;

public class Parque {
    private String idParque;
    private String idCiudad;
    private String nparque;
    private int extension;


    public Parque() {

    }

    public String getIdParque() {
        return idParque;
    }

    public void setIdParque(String idParque) {
        this.idParque = idParque;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNparque() {
        return nparque;
    }

    public void setNparque(String nparque) {
        this.nparque = nparque;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Parque{" +
                "id_Parque='" + idParque + '\'' +
                ", id_Ciudad='" + idCiudad + '\'' +
                ", n_parque='" + nparque + '\'' +
                ", extension=" + extension +
                '}';
    }
}
