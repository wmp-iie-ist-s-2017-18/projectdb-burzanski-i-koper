package com.mycompany.centrumkszalcenia.models;

import javafx.beans.property.*;

public class StudentFx {
    private IntegerProperty idKursanta = new SimpleIntegerProperty();
    private StringProperty imie = new SimpleStringProperty();
    private StringProperty nazwisko = new SimpleStringProperty();
    private StringProperty pesel = new SimpleStringProperty();
    private StringProperty miejscowosc = new SimpleStringProperty();
    private StringProperty ulica = new SimpleStringProperty();
    private IntegerProperty nrDomu = new SimpleIntegerProperty();
    private IntegerProperty kodPocztowy = new SimpleIntegerProperty();
    private IntegerProperty nrTelefonu = new SimpleIntegerProperty();
    private StringProperty mail = new SimpleStringProperty();
    private StringProperty kurs = new SimpleStringProperty();

    public int getIdKursanta() {
        return idKursanta.get();
    }

    public IntegerProperty idKursantaProperty() {
        return idKursanta;
    }

    public void setIdKursanta(int idKursanta) {
        this.idKursanta.set(idKursanta);
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

    public String getPesel() {
        return pesel.get();
    }

    public StringProperty peselProperty() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel.set(pesel);
    }

    public String getMiejscowosc() {
        return miejscowosc.get();
    }

    public StringProperty miejscowoscProperty() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc.set(miejscowosc);
    }

    public String getUlica() {
        return ulica.get();
    }

    public StringProperty ulicaProperty() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica.set(ulica);
    }

    public int getNrDomu() {
        return nrDomu.get();
    }

    public IntegerProperty nrDomuProperty() {
        return nrDomu;
    }

    public void setNrDomu(int nrDomu) {
        this.nrDomu.set(nrDomu);
    }

    public int getKodPocztowy() {
        return kodPocztowy.get();
    }

    public IntegerProperty kodPocztowyProperty() {
        return kodPocztowy;
    }

    public void setKodPocztowy(int kodPocztowy) {
        this.kodPocztowy.set(kodPocztowy);
    }

    public int getNrTelefonu() {
        return nrTelefonu.get();
    }

    public IntegerProperty nrTelefonuProperty() {
        return nrTelefonu;
    }

    public void setNrTelefonu(int nrTelefonu) {
        this.nrTelefonu.set(nrTelefonu);
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

    public String getKurs() {
        return kurs.get();
    }

    public StringProperty kursProperty() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs.set(kurs);
    }

}
