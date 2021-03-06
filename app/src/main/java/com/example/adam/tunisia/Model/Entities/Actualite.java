package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actualite {

    @SerializedName("auto_actualite")
    @Expose
    private int ROW_ID;
    @SerializedName("text")
    @Expose
    private String TEXTE;
    @SerializedName("titre")
    @Expose
    private String titre;
    @SerializedName("date_actualite")
    @Expose
    private String DATE;
    @SerializedName("societe")
    @Expose
    private Societe SOC;

    public Actualite() {
    }

    public Actualite(String TEXTE, String TITRE, String DATE) {
        this.TEXTE = TEXTE;
        this.titre = TITRE;
        this.DATE = DATE;
    }

    public Actualite(int ROW_ID, String TEXTE, String TITRE, String DATE, Societe SOC) {
        this.ROW_ID = ROW_ID;
        this.TEXTE = TEXTE;
        this.titre = TITRE;
        this.DATE = DATE;
        this.SOC = SOC;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "ROW_ID=" + ROW_ID +
                ", TEXTE='" + TEXTE + '\'' +
                ", titre='" + titre + '\'' +
                ", DATE='" + DATE + '\'' +
                ", SOC=" + SOC +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String getTEXTE() {
        return TEXTE;
    }

    public void setTEXTE(String TEXTE) {
        this.TEXTE = TEXTE;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public Societe getSOC() {
        return SOC;
    }

    public void setSOC(Societe SOC) {
        this.SOC = SOC;
    }

}
