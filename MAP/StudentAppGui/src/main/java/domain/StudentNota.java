package domain;

public class StudentNota {
    private Integer tema;
    private Integer nota;
    private Integer saptPred;
    private Integer deadline;
    private String feedback;

    public StudentNota(Integer tema, Integer nota, Integer saptPred, Integer deadline, String feedback) {
        this.tema = tema;
        this.nota = nota;
        this.saptPred = saptPred;
        this.deadline = deadline;
        this.feedback = feedback;
    }

    public Integer getTema() {
        return tema;
    }

    public Integer getNota() {
        return nota;
    }

    public Integer getSaptPred() {
        return saptPred;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "StudentNota{" +
                "tema=" + tema +
                ", nota=" + nota +
                ", saptPred=" + saptPred +
                ", deadline=" + deadline +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
