package com.mycompany.centrumkszalcenia.models;

import javafx.beans.property.*;

public class CourseFx {

    private IntegerProperty idKursu = new SimpleIntegerProperty();
    private StringProperty nazwa = new SimpleStringProperty();
    private IntegerProperty liczbaGodzin = new SimpleIntegerProperty();
    private StringProperty prowadzacy = new SimpleStringProperty();

    public int getIdKursu() {
        return idKursu.get();
    }

    public IntegerProperty idKursuProperty() {
        return idKursu;
    }

    public void setIdKursu(int idKursu) {
        this.idKursu.set(idKursu);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public int getLiczbaGodzin() {
        return liczbaGodzin.get();
    }

    public IntegerProperty liczbaGodzinProperty() {
        return liczbaGodzin;
    }

    public void setLiczbaGodzin(int liczbaGodzin) {
        this.liczbaGodzin.set(liczbaGodzin);
    }

    public String getProwadzacy() {
        return prowadzacy.get();
    }

    public StringProperty prowadzacyProperty() {
        return prowadzacy;
    }

    public void setProwadzacy(String prowadzacy) {
        this.prowadzacy.set(prowadzacy);
    }
}
