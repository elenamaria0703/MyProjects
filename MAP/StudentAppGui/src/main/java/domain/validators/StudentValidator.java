package domain.validators;

import domain.Student;
import exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student entity) throws ValidationException {
        List<Integer> grupe= Arrays.asList(221,222,223,224,225,226,227,921,922,923,924,925,926,927);
        if(entity.getId()!=null && entity.getId()<0) throw new ValidationException("Id trebuie sa fie pozitiv!\n");
        if(entity.getNume()==null || entity.getNume()=="") throw new ValidationException("Studentul nu are nume!\n");
        if(entity.getPrenume()==null || entity.getPrenume()=="") throw new ValidationException("Studentul nu are prenume!\n");
        if(!(grupe.contains(entity.getGrupa()))) throw new ValidationException("Grupa este incorecta!\n");
        if(!(entity.getEmail().matches("[a-z][a-z][a-z][a-z][1-9][0-9][0-9][0-9]@scs.ubbcluj.ro"))) throw new ValidationException("Email nu este valid!\n");
    }
}
