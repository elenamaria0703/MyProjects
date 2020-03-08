package service;

import database.PostgreSQLJDBC;
import database.TemaDBRepository;
import domain.AnUniversitar;
import domain.Tema;
import domain.validators.TemaValidator;
import exceptions.ValidationException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class TemaService {
    private List<PropertyChangeListener> listener=new ArrayList<>();
    public static  final TemaValidator temaValidator=new TemaValidator();
   //private static final String PROPERTY_TO_LOAD_DATA_TEMA="temaXMLFile";
    //public static final TemaXMLRepository temaRepo=new TemaXMLRepository(temaValidator,PROPERTY_TO_LOAD_DATA_TEMA);
    public static final TemaDBRepository temaRepo=new TemaDBRepository(PostgreSQLJDBC.c,temaValidator);
    public TemaService(){}

    public Tema add(Tema tema){
        Tema retTema=(Tema) temaRepo.save(tema);
        notifyListeners(this,"ADD",temaRepo.findAll(),temaRepo.findAll());
        return retTema ;
    }
    public void remove(Integer id){
        temaRepo.delete(id);
    }

    public void update(Tema tema,String descriere,Integer deadline){
        Integer currentWeek= AnUniversitar.INSTANCE.getCurrentWeek();
        if(deadline<currentWeek) throw new ValidationException("Deadline-ul selectat nu este corect!");
        tema.setDescriere(descriere);
        tema.setDeadlineWeek(deadline);
        temaRepo.update(tema);
        notifyListeners(this,"UPDATE",temaRepo.findAll(),temaRepo.findAll());
    }
    public Tema search(Integer id){
        return (Tema)temaRepo.findOne(id);
    }
    public Iterable<Tema> getAll(){
        return temaRepo.findAll();
    }

    public void notifyListeners(Object object,String property,Iterable<Tema> oldValue, Iterable<Tema> newValue){
        listener.forEach(name->name.propertyChange(new PropertyChangeEvent(this,property,oldValue,newValue)));
    }
    public void addChangeListener(PropertyChangeListener newListener){
        listener.add(newListener);
    }
}
