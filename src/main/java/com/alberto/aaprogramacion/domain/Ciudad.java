package com.alberto.aaprogramacion.domain;

public class Ciudad {
    private String idCiudad;
    private String nCiudad;
    private String ccaa;

    public Ciudad() {
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getnCiudad() {
        return nCiudad;
    }

    public void setnCiudad(String nCiudad) {
        this.nCiudad = nCiudad;
    }

    public String getCcaa() {
        return ccaa;
    }

    public void setCcaa(String ccaa) {
        this.ccaa = ccaa;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id_Ciudad='" + idCiudad + '\'' +
                ", n_Ciudad='" + nCiudad + '\'' +
                ", ccaa='" + ccaa + '\'' +
                '}';
    }
}
