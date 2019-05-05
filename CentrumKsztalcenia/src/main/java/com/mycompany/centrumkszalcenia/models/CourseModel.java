package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Kursy;
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

public class CourseModel {

    private ObjectProperty<CourseFx> courseFxObjectProperty = new SimpleObjectProperty<>(new CourseFx());
    private ObjectProperty<CourseFx> courseFxObjectPropertyEdit = new SimpleObjectProperty<>(new CourseFx());
    private ObservableList<CourseFx> courseFxObservableList = FXCollections.observableArrayList();

    public void initCourseList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
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
            courseFx.setProwadzacy(kurs.getProwadzacy().getNazwisko() + " " + kurs.getProwadzacy().getImie());
            courseFxObservableList.add(courseFx);
        }

        session.getTransaction().commit();
        session.close();
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
