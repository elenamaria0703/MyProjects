package domain;

import java.util.Objects;

public class IdNota {
    Integer idStudent;
    Integer idTema;

    public IdNota(Integer idStudent, Integer idTema) {
        this.idStudent = idStudent;
        this.idTema = idTema;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public Integer getIdTema() {
        return idTema;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdNota idNota = (IdNota) o;
        return (this.idStudent==idNota.getIdStudent() && idTema==idNota.getIdTema());
    }


    @Override
    public int hashCode() {
        return Objects.hash(idStudent, idTema);
    }
}
