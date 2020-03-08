package service;

import database.PostgreSQLJDBC;
import database.PredareDBRepository;
import domain.*;
import domain.validators.NotaValidator;
import domain.validators.PredareValidator;
import exceptions.ValidationException;
import repository.StudentPersnalFile;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static service.StudentService.studentRepo;


public class NotaService {
    private List<PropertyChangeListener> listeners=new ArrayList<>();
    public static final NotaValidator notaValidator=new NotaValidator();
    public static final PredareValidator predareValidator=new PredareValidator();
//    private static final String PROPERTY_LOAD_DATA_NOTE="noteJSONFile";
//    public static final NotaJSONRepository notaRepo=new NotaJSONRepository(predareValidator,PROPERTY_LOAD_DATA_NOTE);
    public static final PredareDBRepository notaRepo=new PredareDBRepository(PostgreSQLJDBC.c,predareValidator);
    public NotaService(){}

    public void add(Nota nota,String feedback,Boolean motivare){
        Integer saptPredare=getSaptPredarii(nota.getData());
        Integer idTema=nota.getId().getIdTema();
        Integer idStudent=nota.getId().getIdStudent();
        Tema tema = (Tema)TemaService.temaRepo.findOne(idTema);
        notaValidator.validate(nota);
        Integer notaFinala=validareNotaTema(saptPredare,tema,nota);
        if(notaFinala==-1) throw new ValidationException("Termenul de predare a expirat!");
        else {
            if(notaFinala<10) notaFinala=notaFinala+validareAdaugareTema(nota);
            if(motivare==false)
                nota.setValoare(notaFinala);
            Predare predare=new Predare(idStudent,tema.getId(),nota.getValoare(),saptPredare,tema.getDeadlineWeek(),feedback,nota.getProfesor());
            predare.setId(nota.getId());
            notaRepo.save(predare);
            StudentNota studentNota=new StudentNota(tema.getId(),nota.getValoare(),saptPredare,tema.getDeadlineWeek(),feedback);
            saveStudentNotatoFile(idStudent,studentNota);

        }
    }

    private void saveStudentNotatoFile(Integer idStud,StudentNota studentNota){
        Student student= (Student) studentRepo.findOne(idStud);
        String studentName=student.getNume()+student.getPrenume();
        StudentPersnalFile.setFilename(studentName);
        StudentPersnalFile.setStudentNota(studentNota);
        StudentPersnalFile.saveNota();
        notifyListeners(this,"UPDATE",getNoteStudent(student),getNoteStudent(student));

        String message=student.getNume()+" "+student.getPrenume()+" "+student.getEmail()+"\nLaboratorul:"+studentNota.getTema().toString()+
                "\n Nota:"+studentNota.getNota().toString()+"\n Feedback:"+studentNota.getFeedback();
        ExecutorService executor= Executors.newFixedThreadPool(1);
        Callable<Void> callable=()->{
            MailSender.sendMail("Note",message);
            return null;
        };
        executor.submit(callable);
        executor.shutdown();

    }
    public List<StudentNota> getNoteStudent(Student student){
        String studentName=student.getNume()+student.getPrenume();
        StudentPersnalFile.setFilename(studentName);
        return StudentPersnalFile.getAllGrades();
    }
    private Integer validareNotaTema(Integer saptPredare,Tema tema,Nota nota){
        if(saptPredare-tema.getDeadlineWeek()==0)
            return nota.getValoare();
        if(saptPredare-tema.getDeadlineWeek()==1)
            return nota.getValoare()-1;
        if(saptPredare-tema.getDeadlineWeek()==2)
            return nota.getValoare()-2;
        return -1;
    }

    private Integer validareAdaugareTema(Nota nota){
//        LocalDate currentDate= LocalDate.now();
//        WeekFields weekFields = WeekFields.of(Locale.getDefault());
//        if(currentDate.get(weekFields.weekOfWeekBasedYear())-nota.getData().get(weekFields.weekOfWeekBasedYear())>=2){
//            return 1;
//        }
//        return 0;
        int saptPredarii=getSaptPredarii(nota.getData());
        int saptAdaugarii=getSaptPredarii(LocalDate.now());
        if(saptAdaugarii-saptPredarii>=2){
            return 1;
        }
        return 0;
    }

    private Integer getSaptPredarii(LocalDate date){
       return AnUniversitar.INSTANCE.detSaptPredarii(date);
    }


    private void notifyListeners(Object object, String property,List<StudentNota> oldValue,List<StudentNota> newValue){
        listeners.forEach(name->name.propertyChange(new PropertyChangeEvent(this,property,oldValue,newValue)));
    }
    public void addChangeListener(PropertyChangeListener newListener){
        listeners.add(newListener);
    }

    public Iterable<Predare> getAll(){
        return notaRepo.findAll();
    }

    public ArrayList<Student> filterByGroup(Integer grupa){
        List<Student> students= new ArrayList<Student>();
        studentRepo.findAll().forEach(s->students.add((Student) s));
        List<Student> result =students.stream()
                .filter(student -> student.getGrupa().equals(grupa))
                .collect(Collectors.toList());
        return (ArrayList<Student>) result;
    }
    public List<Student> filterByTema(Integer temaId) {
        List<Predare> resultPredari=getPredariFiltrateTema(temaId);
        return getStudentsFilter(resultPredari);
    }

    public List<Student> filterByTemaProf(Integer idTema, String prof) {
        List<Predare> predari=getPredariFiltrateTema(idTema);
        List<Predare> predareList=predari.stream()
                .filter(p->p.getProfesor().equals(prof))
                .collect(Collectors.toList());
        return getStudentsFilter(predareList);
    }

    public List<Integer> filterByNote(Integer temaid, Integer saptamana) {
        List<Predare> predari= getPredariFiltrateTema(temaid);
        List<Predare> resultFilter=predari.stream()
                .filter(p1->p1.getSaptPredare().equals(saptamana))
                .collect(Collectors.toList());
        List<Integer> result=new ArrayList<>();
        resultFilter.forEach(r->result.add(r.getNrNota()));
        return result;
    }
    private List<Predare> getPredariFiltrateTema(Integer temaId)
    {
        List<Predare> predari=new ArrayList<>();
        notaRepo.findAll().forEach(n->predari.add((Predare) n));
        List<Predare> resultPredari=predari.stream()
                .filter(p->p.getNrTema().equals(temaId))
                .collect(Collectors.toList());
        return resultPredari;
    }

    private List<Student> getStudentsFilter(List<Predare> predari){
        List<Student> result=new ArrayList<>();
        predari.forEach(p->{
            Student student= (Student) studentRepo.findOne(p.getNrStudent());
            result.add(student);
        });
        return result;
    }

}
