package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Prowadzacy;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.postgresql.util.PSQLException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class LeaderModel {

    private ObjectProperty<LeaderFx> leaderFxObjectProperty = new SimpleObjectProperty<>(new LeaderFx());
    private ObjectProperty<LeaderFx> leaderFxObjectPropertyEdit = new SimpleObjectProperty<>(new LeaderFx());
    private ObservableList<LeaderFx> leaderFxObservableList = FXCollections.observableArrayList();

    public void initLeaderList(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
    }

    public void saveLeader(String imie, String nazwisko, String mail) throws ApplicationException {
        Prowadzacy nowyProwadzacy = new Prowadzacy();
        nowyProwadzacy.setImie(imie);
        nowyProwadzacy.setNazwisko(nazwisko);
        nowyProwadzacy.setMail(mail);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            session.save(nowyProwadzacy);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initLeaderList();
    }

    public void updateLeader() throws ApplicationException {
        Prowadzacy updateProwadzacy = new Prowadzacy();
        updateProwadzacy.setIdProwadzacego(leaderFxObjectPropertyEdit.getValue().getIdProwadzacy());
        updateProwadzacy.setImie(leaderFxObjectPropertyEdit.getValue().getImie());
        updateProwadzacy.setNazwisko(leaderFxObjectPropertyEdit.getValue().getNazwisko());
        updateProwadzacy.setMail(leaderFxObjectPropertyEdit.getValue().getMail());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            session.update(updateProwadzacy);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initLeaderList();
    }

    public void deleteLeader() throws ApplicationException {
        Prowadzacy deleteProwadzacy = new Prowadzacy();
        deleteProwadzacy.setIdProwadzacego(leaderFxObjectPropertyEdit.getValue().getIdProwadzacy());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(deleteProwadzacy);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initLeaderList();
    }

    <E extends Throwable> E getCauseOfClass(RuntimeException e, Class<E> exceptionClass) {
        Throwable t = e;
        do {
            if (exceptionClass.isAssignableFrom(t.getClass())) {
                return (E) t;
            }
        } while ((t = t.getCause()) != null);  // go deeper in cause chain
        throw e;                               // if not found, re-throw
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
