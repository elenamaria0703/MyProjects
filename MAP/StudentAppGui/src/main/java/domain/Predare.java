package domain;

public class Predare extends Entity<IdNota> {
    private Integer nrStudent;
    private Integer nrTema;
    private Integer nrNota;
    private Integer saptPredare;
    private Integer deadline;
    private String feedback;
    private String profesor;

    public Predare(Integer nrStudent,Integer nrTema, Integer nrNota, Integer saptPredare, Integer deadline, String feedback,String profesor) {
        this.nrStudent=nrStudent;
        this.nrTema = nrTema;
        this.nrNota = nrNota;
        this.saptPredare = saptPredare;
        this.deadline = deadline;
        this.feedback = feedback;
        this.profesor=profesor;
    }

    public String getProfesor() {
        return profesor;
    }

    public Integer getNrStudent() {
        return nrStudent;
    }
    public Integer getNrTema() {
        return nrTema;
    }

    public Integer getNrNota() {
        return nrNota;
    }

    public Integer getSaptPredare() {
        return saptPredare;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "Predare{" +
                "nrStudent=" + nrStudent +
                ", nrTema=" + nrTema +
                ", nrNota=" + nrNota +
                ", saptPredare=" + saptPredare +
                ", deadline=" + deadline +
                ", feedback='" + feedback + '\'' +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
