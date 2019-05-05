package com.mycompany.centrumkszalcenia.database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "Prowadzacy")
public class Prowadzacy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prowadzacego")
    private int idProwadzacego;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "mail")
    private String mail;

    public Prowadzacy() {
    }

    public int getIdProwadzacego() {
        return idProwadzacego;
    }

    public void setIdProwadzacego(int idProwadzacego) {
        this.idProwadzacego = idProwadzacego;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
