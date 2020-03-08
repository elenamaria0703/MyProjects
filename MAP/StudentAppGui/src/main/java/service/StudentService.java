package service;

import database.PostgreSQLJDBC;
import database.StudentDBRepository;
import domain.Student;
import domain.validators.StudentValidator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class StudentService  {

    private List<PropertyChangeListener> listener=new ArrayList<PropertyChangeListener>();
    public static final StudentValidator studentValidator=new StudentValidator();
    //private static final String PROPERTY_LOAD_DATA_STUDENT="studentDataFile";
    private static final String PROPERTY_LOAD_DATA_ARCHIVE="studentArchiveFile";
    //public static final StudentFileRepository studentRepo=new StudentFileRepository(studentValidator,PROPERTY_LOAD_DATA_STUDENT,PROPERTY_LOAD_DATA_ARCHIVE);
    public static final StudentDBRepository studentRepo=new StudentDBRepository(PostgreSQLJDBC.c,studentValidator,PROPERTY_LOAD_DATA_ARCHIVE);
    public StudentService() {

    }

    public Student add(Student student){
        Iterable<Student> oldValue=studentRepo.findAll();
        Student stud = (Student) studentRepo.save(student);
        notifyListeners(this,"SAVE",oldValue,studentRepo.findAll());
        return stud;
    }
    public Student remove(Student student){
        Iterable<Student> oldValue=studentRepo.findAll();
        Student stud=studentRepo.deleteStudent(student.getId());
        notifyListeners(this,"DELETE",oldValue,studentRepo.findAll());
        return stud;
    }
    public void update(Student student){
        Iterable<Student> oldValue=studentRepo.findAll();
        Student oldStudent= (Student) studentRepo.update(student);
        notifyListeners(this,"UPDATE",oldValue,studentRepo.findAll());
    }
    public Student search(Integer id){
        return (Student)studentRepo.findOne(id);
    }
    public Iterable<Student> getAll(){
        return studentRepo.findAll();
    }

    private void notifyListeners(Object object, String property,Iterable<Student> oldValue,Iterable<Student> newValue){
        listener.forEach(name->name.propertyChange(new PropertyChangeEvent(this,property,oldValue,newValue)));
    }
    public void addChangeListener(PropertyChangeListener newListener){
        listener.add(newListener);
    }
    public List<String> getUsername(){
        List<String> userNames = new ArrayList<>();
        getAll().forEach(st->{
            userNames.add(st.getEmail().substring(0,4));
        });
        return userNames;
    }
}
