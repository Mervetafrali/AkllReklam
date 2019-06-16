package com.example.akllreklam;

public class Firmalar {

    private String firma_key;
    private String firma_adi;
    private String firma_enlem;
    private  String firma_boylam;
    private String firma_kampanya_icerik;
    private String kagegori;
    private String firma_ksure;

    public Firmalar(String firma_key, String firma_adi, String firma_enlem, String firma_boylam, String firma_kampanya_icerik, String kagegori, String firma_ksure) {
        this.firma_key = firma_key;
        this.firma_adi = firma_adi;
        this.firma_enlem = firma_enlem;
        this.firma_boylam = firma_boylam;
        this.firma_kampanya_icerik = firma_kampanya_icerik;
        this.kagegori = kagegori;
        this.firma_ksure = firma_ksure;
    }

    public Firmalar() {
    }

    @Override
    public String toString() {
        return "Firmalar{" +
                "firma_key='" + firma_key + '\'' +
                ", firma_adi='" + firma_adi + '\'' +
                ", firma_enlem='" + firma_enlem + '\'' +
                ", firma_boylam='" + firma_boylam + '\'' +
                ", firma_kampanya_icerik='" + firma_kampanya_icerik + '\'' +
                ", kagegori='" + kagegori + '\'' +
                ", firma_ksure='" + firma_ksure + '\'' +
                '}';
    }

    public String getFirma_key() {
        return firma_key;
    }

    public void setFirma_key(String firma_key) {
        this.firma_key = firma_key;
    }

    public String getFirma_adi() {
        return firma_adi;
    }

    public void setFirma_adi(String firma_adi) {
        this.firma_adi = firma_adi;
    }

    public String getFirma_enlem() {
        return firma_enlem;
    }

    public void setFirma_enlem(String firma_enlem) {
        this.firma_enlem = firma_enlem;
    }

    public String getFirma_boylam() {
        return firma_boylam;
    }

    public void setFirma_boylam(String firma_boylam) {
        this.firma_boylam = firma_boylam;
    }

    public String getFirma_kampanya_icerik() {
        return firma_kampanya_icerik;
    }

    public void setFirma_kampanya_icerik(String firma_kampanya_icerik) {
        this.firma_kampanya_icerik = firma_kampanya_icerik;
    }

    public String getKagegori() {
        return kagegori;
    }

    public void setKagegori(String kagegori) {
        this.kagegori = kagegori;
    }

    public String getFirma_ksure() {
        return firma_ksure;
    }

    public void setFirma_ksure(String firma_ksure) {
        this.firma_ksure = firma_ksure;
    }
}
