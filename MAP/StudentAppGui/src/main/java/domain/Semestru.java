package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;


public class Semestru {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate startVacation;
    private LocalDate endVacation;

    public Semestru(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     * @param year anul vacantei
     * @param month luna vacantei
     * @param day ziua in care incepe vacanta
     */
    public void setStartVacation(int year,int month,int day){
        this.startVacation=LocalDate.of(year,month,day);
    }

    public void setEndVacation(int year,int month,int day) {
        this.endVacation = LocalDate.of(year,month,day);
    }

    public LocalDate getStartVacation() {
        return startVacation;
    }
    public LocalDate getEndVacation(){return endVacation;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
