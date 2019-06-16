package com.example.akllreklam;

public class Kisiler {
    private String kisi_key;
    private String kisi_ad;
    private String kisi_paralo;

    public Kisiler(String kisi_key, String kisi_ad, String kisi_paralo) {
        this.kisi_key = kisi_key;
        this.kisi_ad = kisi_ad;
        this.kisi_paralo = kisi_paralo;
    }

    public Kisiler() {
    }

    public String getKisi_key() {
        return kisi_key;
    }

    public void setKisi_key(String kisi_key) {
        this.kisi_key = kisi_key;
    }

    public String getKisi_ad() {
        return kisi_ad;
    }

    public void setKisi_ad(String kisi_ad) {
        this.kisi_ad = kisi_ad;
    }

    public String getKisi_paralo() {
        return kisi_paralo;
    }

    public void setKisi_paralo(String kisi_paralo) {
        this.kisi_paralo = kisi_paralo;
    }
}
