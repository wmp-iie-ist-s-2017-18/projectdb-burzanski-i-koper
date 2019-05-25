package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Kursy;
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

public class CourseModel {

    private ObjectProperty<CourseFx> courseFxObjectProperty = new SimpleObjectProperty<>(new CourseFx());
    private ObjectProperty<CourseFx> courseFxObjectPropertyEdit = new SimpleObjectProperty<>(new CourseFx());
    private ObservableList<CourseFx> courseFxObservableList = FXCollections.observableArrayList();

    public void initCourseList(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Kursy> query = builder.createQuery(Kursy.class);
        Root<Kursy> root = query.from(Kursy.class);
        query.select(root);
        Query<Kursy> q = session.createQuery(query);
        List<Kursy> kursyList = q.getResultList();
        courseFxObservableList.clear();
        for (Kursy kurs : kursyList){
            CourseFx courseFx = new CourseFx();
            courseFx.setIdKursu(kurs.getIdKursu());
            courseFx.setNazwa(kurs.getNazwa());
            courseFx.setLiczbaGodzin(kurs.getLiczbaGodzin());
            courseFx.setProwadzacy(kurs.getProwadzacy().getNazwisko());
            courseFxObservableList.add(courseFx);
        }

        session.getTransaction().commit();
    }

    public void saveCourse(String nazwa, int liczbaGodzin, LeaderFx leaderFx) throws ApplicationException {
        Prowadzacy prowadzacy = new Prowadzacy();
        prowadzacy.setIdProwadzacego(leaderFx.getIdProwadzacy());

        Kursy nowyKurs = new Kursy();
        nowyKurs.setNazwa(nazwa);
        nowyKurs.setLiczbaGodzin(liczbaGodzin);
        nowyKurs.setProwadzacy(prowadzacy);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            session.save(nowyKurs);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initCourseList();
    }

    public void updateCourse() throws ApplicationException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();

            Kursy updateKurs = (Kursy) session.get(Kursy.class, courseFxObjectPropertyEdit.getValue().getIdKursu());
            updateKurs.setNazwa(courseFxObjectPropertyEdit.getValue().getNazwa());
            updateKurs.setLiczbaGodzin(courseFxObjectPropertyEdit.getValue().getLiczbaGodzin());

            session.update(updateKurs);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initCourseList();
    }

    public void updateLeaderCourse(LeaderFx leaderFx) throws ApplicationException {
        Prowadzacy prowadzacy = new Prowadzacy();
        prowadzacy.setIdProwadzacego(leaderFx.getIdProwadzacy());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();

            Kursy updateKurs = (Kursy) session.get(Kursy.class, courseFxObjectPropertyEdit.getValue().getIdKursu());
            updateKurs.setProwadzacy(prowadzacy);

            session.update(updateKurs);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initCourseList();
    }

    public void deleteCourse() throws ApplicationException {
        Kursy deleteKurs = new Kursy();
        deleteKurs.setIdKursu(courseFxObjectPropertyEdit.getValue().getIdKursu());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(deleteKurs);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initCourseList();
    }

    <E extends Throwable> E getCauseOfClass(RuntimeException e, Class<E> exceptionClass) {
        Throwable t = e;
        do {
            if (exceptionClass.isAssignableFrom(t.getClass())) {
                return (E) t;
            }
        } while ((t = t.getCause()) != null);
        throw e;
    }

    public CourseFx getCourseFxObjectProperty() {
        return courseFxObjectProperty.get();
    }

    public ObjectProperty<CourseFx> courseFxObjectPropertyProperty() {
        return courseFxObjectProperty;
    }

    public void setCourseFxObjectProperty(CourseFx courseFxObjectProperty) {
        this.courseFxObjectProperty.set(courseFxObjectProperty);
    }

    public CourseFx getCourseFxObjectPropertyEdit() {
        return courseFxObjectPropertyEdit.get();
    }

    public ObjectProperty<CourseFx> courseFxObjectPropertyEditProperty() {
        return courseFxObjectPropertyEdit;
    }

    public void setCourseFxObjectPropertyEdit(CourseFx courseFxObjectPropertyEdit) {
        this.courseFxObjectPropertyEdit.set(courseFxObjectPropertyEdit);
    }

    public ObservableList<CourseFx> getCourseFxObservableList() {
        return courseFxObservableList;
    }

    public void setCourseFxObservableList(ObservableList<CourseFx> courseFxObservableList) {
        this.courseFxObservableList = courseFxObservableList;
    }
}
