package domain.validators;

import domain.Nota;
import domain.Student;
import domain.Tema;
import exceptions.ValidationException;
import service.StudentService;
import service.TemaService;

public class NotaValidator implements Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
        if(entity.getValoare()<1 || entity.getValoare()>10) throw new ValidationException("Valoarea nu este valida");
        Integer idStd=entity.getId().getIdStudent();
        Integer idTema=entity.getId().getIdTema();
        Tema tema = (Tema) TemaService.temaRepo.findOne(idTema);
        Student student=(Student) StudentService.studentRepo.findOne(idStd);
        if(tema == null) throw new ValidationException("Nu exista tema introdusa");
        if(student == null) throw new ValidationException("Nu exista studentul introdus");
    }

}
