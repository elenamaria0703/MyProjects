package domain;

import java.util.Objects;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private Integer grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    public Student(String nume, String prenume, Integer grupa, String email, String cadruDidacticIndrumatorLab) {
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    /**
     *
     * @return numele studentului
     */
    public String getNume() {
        return nume;
    }

    /**
     *
     * @return prenumele studentului
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     *
     * @return grupa studentului
     */
    public Integer getGrupa() {
        return grupa;
    }

    public String getEmail() {
        return email;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Student)) return false;
        Student stud=(Student) o;
        return stud.getId()==this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume);
    }

    @Override
    public String toString() {
        return this.getId()+";"+nume+";"+prenume+";"+grupa+";"+email+";"+cadruDidacticIndrumatorLab+"\n";
    }
}
