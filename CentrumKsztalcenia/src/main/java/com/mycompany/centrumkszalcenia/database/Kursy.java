package com.mycompany.centrumkszalcenia.database;

import javax.persistence.*;

@Entity
@Table(name = "Kursy")
public class Kursy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kursu")
    private int idKursu;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "liczba_godzin")
    private int liczbaGodzin;

    @ManyToOne
    @JoinColumn(name = "id_prowadzacego", foreignKey = @ForeignKey(name = "id_prowadzacego_fk"))
    private Prowadzacy prowadzacy;

    public Kursy() {
    }

    public int getIdKursu() {
        return idKursu;
    }

    public void setIdKursu(int idKursu) {
        this.idKursu = idKursu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getLiczbaGodzin() {
        return liczbaGodzin;
    }

    public void setLiczbaGodzin(int liczbaGodzin) {
        this.liczbaGodzin = liczbaGodzin;
    }

    public Prowadzacy getProwadzacy() {
        return prowadzacy;
    }

    public void setProwadzacy(Prowadzacy prowadzacy) {
        this.prowadzacy = prowadzacy;
    }
}
