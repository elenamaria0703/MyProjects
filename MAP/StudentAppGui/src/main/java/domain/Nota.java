package domain;

import java.time.LocalDate;

public class Nota extends Entity<IdNota>{
    private LocalDate data;
    private String Profesor;
    private Integer valoare;

    public Nota(LocalDate data, String profesor, Integer valoare) {
        this.data = data;
        Profesor = profesor;
        this.valoare = valoare;
    }

    public LocalDate getData() {
        return data;
    }

    public String getProfesor() {
        return Profesor;
    }

    public Integer getValoare() {
        return valoare;
    }

    public void setProfesor(String profesor) {
        Profesor = profesor;
    }

    public void setValoare(Integer valoare) {
        this.valoare = valoare;
    }

}
