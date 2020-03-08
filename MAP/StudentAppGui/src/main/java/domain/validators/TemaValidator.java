package domain.validators;

import domain.Tema;
import exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class TemaValidator implements Validator<Tema>{

    @Override
    public void validate(Tema entity) throws ValidationException {
        List<Integer> nrSapt= Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        if(!(nrSapt.contains(entity.getDeadlineWeek()))) throw new ValidationException("DeadlineWeek is not correct");
        if(!(nrSapt.contains(entity.getStartWeek()))) throw new ValidationException("StartWeek is not correct");
        if(entity.getDeadlineWeek()<entity.getStartWeek()) throw new ValidationException("DeadlineWeek is not correct");
    }

}
