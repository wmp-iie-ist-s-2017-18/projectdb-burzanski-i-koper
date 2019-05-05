package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Prowadzacy;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class LeaderModel {

    private ObjectProperty<LeaderFx> leaderFxObjectProperty = new SimpleObjectProperty<>(new LeaderFx());
    private ObjectProperty<LeaderFx> leaderFxObjectPropertyEdit = new SimpleObjectProperty<>(new LeaderFx());
    private ObservableList<LeaderFx> leaderFxObservableList = FXCollections.observableArrayList();

    public void initLeaderList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Prowadzacy> query = builder.createQuery(Prowadzacy.class);
        Root<Prowadzacy> root = query.from(Prowadzacy.class);
        query.select(root);
        Query<Prowadzacy> q = session.createQuery(query);
        List<Prowadzacy> prowadzacyList = q.getResultList();
        leaderFxObservableList.clear();
        for (Prowadzacy prowadzacy : prowadzacyList){
            LeaderFx leaderFx = new LeaderFx();
            leaderFx.setIdProwadzacy(prowadzacy.getIdProwadzacego());
            leaderFx.setImie(prowadzacy.getImie());
            leaderFx.setNazwisko(prowadzacy.getNazwisko());
            leaderFx.setMail(prowadzacy.getMail());
            leaderFxObservableList.add(leaderFx);
        }

        session.getTransaction().commit();
        session.close();
    }

    public void saveLeader(String imie, String nazwisko, String mail){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Prowadzacy nowyProwadzacy = new Prowadzacy();
        nowyProwadzacy.setImie(imie);
        nowyProwadzacy.setNazwisko(nazwisko);
        nowyProwadzacy.setMail(mail);
        session.save(nowyProwadzacy);

        session.getTransaction().commit();
        session.close();
        initLeaderList();
    }

    public void updateLeader(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Prowadzacy updateProwadzacy = new Prowadzacy();
        updateProwadzacy.setIdProwadzacego(leaderFxObjectPropertyEdit.getValue().getIdProwadzacy());
        updateProwadzacy.setImie(leaderFxObjectPropertyEdit.getValue().getImie());
        updateProwadzacy.setNazwisko(leaderFxObjectPropertyEdit.getValue().getNazwisko());
        updateProwadzacy.setMail(leaderFxObjectPropertyEdit.getValue().getMail());
        session.update(updateProwadzacy);

        session.getTransaction().commit();
        session.close();
        initLeaderList();
    }

    public void deleteLeader() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Prowadzacy deleteProwadzacy = new Prowadzacy();
        deleteProwadzacy.setIdProwadzacego(leaderFxObjectPropertyEdit.getValue().getIdProwadzacy());
        session.delete(deleteProwadzacy);

        session.getTransaction().commit();
        session.close();
        initLeaderList();
    }

    public LeaderFx getLeaderFxObjectProperty() {
        return leaderFxObjectProperty.get();
    }

    public ObjectProperty<LeaderFx> leaderFxObjectPropertyProperty() {
        return leaderFxObjectProperty;
    }

    public void setLeaderFxObjectProperty(LeaderFx leaderFxObjectProperty) {
        this.leaderFxObjectProperty.set(leaderFxObjectProperty);
    }

    public ObservableList<LeaderFx> getLeaderFxObservableList() {
        return leaderFxObservableList;
    }

    public void setLeaderFxObservableList(ObservableList<LeaderFx> leaderFxObservableList) {
        this.leaderFxObservableList = leaderFxObservableList;
    }

    public LeaderFx getLeaderFxObjectPropertyEdit() {
        return leaderFxObjectPropertyEdit.get();
    }

    public ObjectProperty<LeaderFx> leaderFxObjectPropertyEditProperty() {
        return leaderFxObjectPropertyEdit;
    }

    public void setLeaderFxObjectPropertyEdit(LeaderFx leaderFxObjectPropertyEdit) {
        this.leaderFxObjectPropertyEdit.set(leaderFxObjectPropertyEdit);
    }
}
