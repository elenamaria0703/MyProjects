package domain;
public class Tema extends Entity<Integer> {
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Tema(String descriere) {
        this.descriere = descriere;
        this.startWeek= AnUniversitar.INSTANCE.getCurrentWeek();
        this.deadlineWeek=this.startWeek+1;
    }

    /**
     *
     * @return descrierea temei
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     *
     * @return saptamana in care se adauga tema
     */
    public Integer getStartWeek() {
        return startWeek;
    }

    /**
     *
     * @return saptamana in care se preda tema
     */
    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    /**
     *
     * @param deadlineWeek este noua valoare a datei de predare
     */
    public void setDeadlineWeek(Integer deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public void setDescriere(String descriere){
        this.descriere=descriere;
    }
    @Override
    public String toString() {
        return this.getId().toString()+";"+descriere+";StartWeek: "+startWeek+";DeadlineWeek: "+deadlineWeek;
    }
}
