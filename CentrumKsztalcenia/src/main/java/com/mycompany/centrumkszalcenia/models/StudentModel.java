package com.mycompany.centrumkszalcenia.models;

import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import com.mycompany.centrumkszalcenia.database.Kursanci;
import com.mycompany.centrumkszalcenia.database.Kursy;
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

public class StudentModel {

    private ObjectProperty<StudentFx> studentFxObjectProperty = new SimpleObjectProperty<>(new StudentFx());
    private ObjectProperty<StudentFx> studentFxObjectPropertyEdit = new SimpleObjectProperty<>(new StudentFx());
    private ObservableList<StudentFx> studentFxObservableList = FXCollections.observableArrayList();


    public void initStudentList() {
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
    }

    public int saveStudent(CourseFx courseFx) throws ApplicationException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int answer = -1;

        try {
            session.beginTransaction();

            String query = "SELECT dodaj('" + studentFxObjectProperty.getValue().getImie() + "'," +
                    " '" + studentFxObjectProperty.getValue().getNazwisko() + "'," +
                    " '" + studentFxObjectProperty.getValue().getPesel() + "'," +
                    " '" + studentFxObjectProperty.getValue().getMiejscowosc() + "'," +
                    " '" + studentFxObjectProperty.getValue().getUlica() + "'," +
                    " " + studentFxObjectProperty.getValue().getNrDomu() + "," +
                    " " + studentFxObjectProperty.getValue().getKodPocztowy() + "," +
                    " " + studentFxObjectProperty.getValue().getNrTelefonu() + "," +
                    " '" + studentFxObjectProperty.getValue().getMail() + "'," +
                    " " + courseFx.getIdKursu() + ")";

            answer = (int) session.createNativeQuery(query).getSingleResult();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        return answer;
    }

    public void updateStudent() throws ApplicationException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Kursanci kursant = (Kursanci) session.get(Kursanci.class, studentFxObjectPropertyEdit.getValue().getIdKursanta());
            kursant.setImie(studentFxObjectPropertyEdit.getValue().getImie());
            kursant.setNazwisko(studentFxObjectPropertyEdit.getValue().getNazwisko());
            kursant.setPesel(studentFxObjectPropertyEdit.getValue().getPesel());
            kursant.setMiejscowosc(studentFxObjectPropertyEdit.getValue().getMiejscowosc());
            kursant.setUlica(studentFxObjectPropertyEdit.getValue().getUlica());
            kursant.setNrDomu(studentFxObjectPropertyEdit.getValue().getNrDomu());
            kursant.setKodPocztowy(studentFxObjectPropertyEdit.getValue().getKodPocztowy());
            kursant.setNrTelefonu(studentFxObjectPropertyEdit.getValue().getNrTelefonu());
            kursant.setMail(studentFxObjectPropertyEdit.getValue().getMail());

            session.update(kursant);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initStudentList();
    }

    public void updateCourseStudent(CourseFx courseFx) throws ApplicationException {
        Kursy kurs = new Kursy();
        kurs.setIdKursu(courseFx.getIdKursu());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();

            Kursanci updateKursant = (Kursanci) session.get(Kursanci.class, studentFxObjectPropertyEdit.getValue().getIdKursanta());
            updateKursant.setKurs(kurs);

            session.update(updateKursant);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initStudentList();
    }

    public void deleteStudent() throws ApplicationException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Kursanci deleteKursant = new Kursanci();
        deleteKursant.setIdKursanta(studentFxObjectPropertyEdit.getValue().getIdKursanta());

        try {
            session.beginTransaction();
            session.delete(deleteKursant);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            PSQLException psqle = getCauseOfClass(e, PSQLException.class);
            throw new ApplicationException(psqle.getMessage());
        }

        initStudentList();
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
