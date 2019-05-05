package com.mycompany.centrumkszalcenia.database;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "Kursanci")
public class Kursanci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kursanta")
    private int idKursanta;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "pesel", unique = true)
    private Long pesel;

    @Column(name = "miejscowosc")
    private String miejscowosc;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "nr_domu")
    private int nrDomu;

    @Column(name = "kod_pocztowy")
    private int kodPocztowy;

    @Column(name = "nr_telefonu")
    private int nrTelefonu;

    @Column(name = "mail")
    private String mail;

    @ManyToOne
    @JoinColumn(name = "id_kursu", foreignKey = @ForeignKey(name = "id_kursu_fk"))
    private Kursy kurs;

    public Kursanci() {
    }

    public int getIdKursanta() {
        return idKursanta;
    }

    public void setIdKursanta(int idKursanta) {
        this.idKursanta = idKursanta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(int nrDomu) {
        this.nrDomu = nrDomu;
    }

    public int getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(int kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public int getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(int nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Kursy getKurs() {
        return kurs;
    }

    public void setKurs(Kursy kurs) {
        this.kurs = kurs;
    }
}
