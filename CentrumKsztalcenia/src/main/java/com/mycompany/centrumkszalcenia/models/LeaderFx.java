package com.mycompany.centrumkszalcenia.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeaderFx {

    private IntegerProperty idProwadzacy = new SimpleIntegerProperty();
    private StringProperty imie = new SimpleStringProperty();
    private StringProperty nazwisko = new SimpleStringProperty();
    private StringProperty mail = new SimpleStringProperty();

    public int getIdProwadzacy() {
        return idProwadzacy.get();
    }

    public IntegerProperty idProwadzacyProperty() {
        return idProwadzacy;
    }

    public void setIdProwadzacy(int idProwadzacy) {
        this.idProwadzacy.set(idProwadzacy);
    }

    public String getImie() {
        return imie.get();
    }

    public StringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getMail() {
        return mail.get();
    }

    public StringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }
}
