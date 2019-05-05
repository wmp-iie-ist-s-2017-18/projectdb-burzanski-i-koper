package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Kursanci;
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

public class StudentModel {

    private ObjectProperty<StudentFx> studentFxObjectProperty = new SimpleObjectProperty<>(new StudentFx());
    private ObjectProperty<StudentFx> studentFxObjectPropertyEdit = new SimpleObjectProperty<>(new StudentFx());
    private ObservableList<StudentFx> studentFxObservableList = FXCollections.observableArrayList();


    public void initStudentList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Kursanci> query = builder.createQuery(Kursanci.class);
        Root<Kursanci> root = query.from(Kursanci.class);
        query.select(root);
        Query<Kursanci> q = session.createQuery(query);
        List<Kursanci> kursanciList = q.getResultList();
        studentFxObservableList.clear();
        for (Kursanci kursant : kursanciList){
            StudentFx studentFx = new StudentFx();
            studentFx.setIdKursanta(kursant.getIdKursanta());
            studentFx.setImie(kursant.getImie());
            studentFx.setNazwisko(kursant.getNazwisko());
            studentFx.setPesel(kursant.getPesel());
            studentFx.setMiejscowosc(kursant.getMiejscowosc());
            studentFx.setUlica(kursant.getUlica());
            studentFx.setNrDomu(kursant.getNrDomu());
            studentFx.setKodPocztowy(kursant.getKodPocztowy());
            studentFx.setNrTelefonu(kursant.getNrTelefonu());
            studentFx.setMail(kursant.getMail());
            studentFx.setKurs(kursant.getKurs().getNazwa());
            studentFxObservableList.add(studentFx);
        }

        session.getTransaction().commit();
        session.close();
    }

    public StudentFx getStudentFxObjectProperty() {
        return studentFxObjectProperty.get();
    }

    public ObjectProperty<StudentFx> studentFxObjectPropertyProperty() {
        return studentFxObjectProperty;
    }

    public void setStudentFxObjectProperty(StudentFx studentFxObjectProperty) {
        this.studentFxObjectProperty.set(studentFxObjectProperty);
    }

    public StudentFx getStudentFxObjectPropertyEdit() {
        return studentFxObjectPropertyEdit.get();
    }

    public ObjectProperty<StudentFx> studentFxObjectPropertyEditProperty() {
        return studentFxObjectPropertyEdit;
    }

    public void setStudentFxObjectPropertyEdit(StudentFx studentFxObjectPropertyEdit) {
        this.studentFxObjectPropertyEdit.set(studentFxObjectPropertyEdit);
    }

    public ObservableList<StudentFx> getStudentFxObservableList() {
        return studentFxObservableList;
    }

    public void setStudentFxObservableList(ObservableList<StudentFx> studentFxObservableList) {
        this.studentFxObservableList = studentFxObservableList;
    }
}
