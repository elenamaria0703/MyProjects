package domain;

import config.IOHandler;
import exceptions.ValidationException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class AnUniversitar {
    private Semestru semestru1;
    private Semestru semestru2;
    public static final AnUniversitar INSTANCE=new AnUniversitar();

    public AnUniversitar() {
        semestru1=new Semestru(getDateSem("sem1Start"), getDateSem("sem1End"));
        semestru2=new Semestru(getDateSem("sem2Start"),getDateSem("sem2End"));
    }
    private LocalDate getDateSem(String nameSem){
        Integer yearStart = null,monthStart=null,dayStart=null;
        try {
            String start=IOHandler.getProperties().getProperty(nameSem);
            yearStart= Integer.parseInt(start.split("/")[0]);
            monthStart=Integer.parseInt(start.split("/")[1]);
            dayStart=Integer.parseInt(start.split("/")[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalDate.of(yearStart,monthStart,dayStart);
    }

    /**
     *
     * @throws ValidationException daca data curenta nu este in cadrul anului universitar
     */
    private void validateDate(LocalDate currentDate) throws ValidationException{
        if(currentDate.isBefore(semestru1.getStartDate()) ||
                (currentDate.isAfter(semestru1.getEndDate())&& currentDate.isBefore(semestru2.getStartDate())) ||
                currentDate.isAfter(semestru2.getEndDate()))
            throw new ValidationException("Current date is not valid!");
    }

    /**
     *
     * @return semestrul corespunzator datii curente
     */
    public Semestru getSemestru() {
        LocalDate currentDate = LocalDate.now();
        validateDate(currentDate);
        LocalDate dateStartSem2 = semestru2.getStartDate();
        if (currentDate.isAfter(dateStartSem2))
            return semestru2;
        return semestru1;
    }

    public Integer detSaptPredarii(LocalDate date){
        Semestru semestru=getSemestru();
        if(semestru==getSemestru1())
            return detSaptPredariiSem1(date);
        else
            return detSaptPredariiSem2(date);
    }
    private Integer detSaptPredariiSem1(LocalDate date){
        LocalDate dateStartSem=semestru1.getStartDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        if(date.isBefore(semestru1.getStartVacation())){
            int weekNumberPred = date.get(weekFields.weekOfWeekBasedYear());
            int weekNumber=dateStartSem.get(weekFields.weekOfWeekBasedYear());
            return weekNumberPred-weekNumber+1;
        }
        else if(date.isAfter(semestru1.getEndVacation())){
            int weekCurrent=date.get(weekFields.weekOfWeekBasedYear())-1;
            int weekNumberPred = semestru1.getStartVacation().get(weekFields.weekOfWeekBasedYear());
            int weekNumber=dateStartSem.get(weekFields.weekOfWeekBasedYear());
            int nrweeks= weekNumberPred-weekNumber+1;
            return nrweeks+weekCurrent;
        }
        int weekNumberPred = semestru1.getStartVacation().get(weekFields.weekOfWeekBasedYear());
        int weekNumber=dateStartSem.get(weekFields.weekOfWeekBasedYear());
        return weekNumberPred-weekNumber+1;
    }
    private Integer detSaptPredariiSem2(LocalDate date){
        LocalDate dateStartSem=semestru2.getStartDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumberPred = date.get(weekFields.weekOfWeekBasedYear());
        int weekNumber=dateStartSem.get(weekFields.weekOfWeekBasedYear());
        return weekNumberPred-weekNumber+1;
    }
    public Semestru getSemestru1() {
        return semestru1;
    }

    public Semestru getSemestru2() {
        return semestru2;
    }
    public Integer getCurrentWeek(){
        if(getSemestru()==semestru1)
            return getCurrentWeekSem1();
        else return getCurrentWeekSem2();
    }
    private Integer getCurrentWeekSem1(){
        return detSaptPredariiSem1(LocalDate.now());
    }
    private Integer getCurrentWeekSem2(){
        return detSaptPredariiSem2(LocalDate.now());
    }

}
