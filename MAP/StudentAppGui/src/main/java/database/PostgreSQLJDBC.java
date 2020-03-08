package database;
import domain.IdNota;
import domain.Predare;
import domain.Student;
import domain.Tema;

import java.sql.*;

public class PostgreSQLJDBC {
    public static Connection c;
    public static void main() {
        c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void createTableStudenti(){
        try {
            Statement stmt=c.createStatement();

            String sqlStudenti="create table Studenti"+
                    "(Id int primary key not null,"+
                    "Nume varchar(20) not null,"+
                    "Prenume varchar(20) not null,"+
                    "Grupa int not null,"+
                    "Email varchar(50) not null,"+
                    "CadruDidactic varchar(20) not null)";
            stmt.executeUpdate(sqlStudenti);
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Table Studenti was created");
//        Statement stmt = null;
//        try {
//            stmt = c.createStatement();
//            String sql="DROP TABLE Studenti";
//            stmt.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
    public static void createTableTema(){
        try {
            Statement stmt=c.createStatement();
            String sqlTema="create table Teme"+
                    "(Id int primary key not null,"+
                    "Descriere varchar(50) not null,"+
                    "StartWeek int not null,"+
                    "DeadlineWeek int not null)";
            stmt.executeUpdate(sqlTema);
            stmt.close();
            c.close();
            System.out.println("Table Teme was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void createTablePredare(){
        try {
            Statement stmt=c.createStatement();
            String sqlPredare="create table Predari"+
                    "(IdStudent int not null,"+
                    "IdTema int not null,"+
                    "Nota int not null,"+
                    "SaptPredare int not null,"+
                    "Deadline int not null,"+
                    "Feedback varchar(1000),"+
                    "Profesor varchar(50),"+
                    "Constraint pk_Predare primary key (IdStudent,IdTema))";
            stmt.executeUpdate(sqlPredare);
            stmt.close();
            c.close();
            System.out.println("Table Predari was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void populatePredari(){
        try {
            Statement stmt=c.createStatement();
            String sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (22,4,4,12,10,'mai exerseaza','Andrei');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (25,4,4,12,10,'mai exerseaza','Andrei');";
            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (19,1,8,8,7,'bine','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (19,3,8,10,9,'ai uitat sa validezi o entitate','Andrei');";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,1,9,7,7,'bine','Gabi');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,2,8,9,8,'repository trebuie sa fie Singleton','Gabi');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,3,9,10,9,'ai refacut foarte bine partea de service','Gabi');";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (19,4,5,10,10,'nu ai rezolvat doua functionalitati','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (19,5,8,12,11,'aproape bine','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (19,6,10,13,12,'foarte bine','Andrei');";
//            stmt.executeUpdate(sql);

//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (17,3,8,10,9,'ar fi mai eficient daca  ai folosi DTO','Sergiu');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (17,4,5,12,10,'mai ai de lucrat','Sergiu');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (17,5,8,12,11,'aproape bine','Sergiu');";
//            stmt.executeUpdate(sql);

//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,4,4,12,10,'reciteste cusurile','Gabi');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,5,8,12,11,'bine','Gabi');";
//            stmt.executeUpdate(sql);


//            //MariaE
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,1,10,7,7,'foarte bine','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,2,8,10,8,'ai putea sa modifici clasa\nde repo pentru mai multa claritate','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,3,9,10,9,'bravo','Vlad');";


//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (3,6,10,13,12,'foarte bine','Gabi');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,7,9,14,13,'ai reusit foarte bine sa refactorizezi \ncodul','Gabi');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (21,8,10,14,14,'felicitari','Gabi');";
//            stmt.executeUpdate(sql);

//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,5,9,12,11,'foarte bine','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,4,9,11,10,'ai mai putea sa te uiti peste\nbaza de date','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (1,6,7,13,12,'nu ai fost atenta la indicatie','Vlad');";
//            stmt.executeUpdate(sql);
//            //Bianca
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (6,2,10,8,8,'sunt cateva cazuri de testare pe\ncare nu le-ai tratat','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (6,3,10,9,9,'ai ales foarte bine clasele','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (6,4,10,10,10,'ai ales foarte bine clasele','Andrei');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (6,5,10,11,11,'ai ales foarte bine clasele','Andrei');";
//            stmt.executeUpdate(sql);
//           //Roxi
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (18,3,10,9,9,'felicitari pentru precizie','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (18,4,9,10,10,'iti lipseste o functionalitate','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (18,5,9,11,11,'iti lipseste o functionalitate','Vlad');";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor)"+"VALUES (18,8,9,14,14,'bravo','Vlad');";
//            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void populateTeme(){
        try {
            Statement stmt=c.createStatement();
//            String sql = "INSERT INTO Teme (Id,Descriere,StartWeek,DeadlineWeek) "
//                    + "VALUES (7,'Laborator7 MAP',12,13 );";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Teme (Id,Descriere,StartWeek,DeadlineWeek) "
//                    + "VALUES (2,'Laborator2 MAP',7,8 );";
            //String sql = "DELETE from Teme where ID = "+8+";";
            String sql = "UPDATE Teme "
                    + "SET Descriere = 'Laborator7 MAP' "
                    + ",StartWeek = 12 "
                    + ",DeadlineWeek = 13 "
                    + "WHERE Id = 7";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void populateStudents(){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic)"+" values (28, 'Idaline', 'Hocking', 221, 'idir@scs.ubbcluj.ro', 'Andrei');";
            stmt.executeUpdate(sql1);
            sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic)"+" values (29, 'Barbara', 'Todman', 221, 'rxir@scs.ubbcluj.ro', 'Vlad');";
            stmt.executeUpdate(sql1);
            sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic) "+"values (30, 'Roddy', 'Jeays', 223, 'ycir@scs.ubbcluj.ro', 'Sergiu');";
            stmt.executeUpdate(sql1);
             sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic)"+" values (31, 'Lief', 'Tart', 225, 'hdir@scs.ubbcluj.ro', 'Gabi');";
            stmt.executeUpdate(sql1);
            sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic) "+"values (32, 'Dale', 'Doggrell', 222, 'yqir@scs.ubbcluj.ro', 'Gabi');";
            stmt.executeUpdate(sql1);
            sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic)"+" values (33, 'Putnem', 'Albro', 224, 'iyir@scs.ubbcluj.ro', 'Gabi');";
            stmt.executeUpdate(sql1);
             sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic) "+"values (34, 'Joli', 'Di Roberto', 224, 'qfir@scs.ubbcluj.ro', 'Vlad');";
            stmt.executeUpdate(sql1);
            sql1="INSERT INTO Studenti (Id, Nume, Prenume, Grupa, Email, CadruDidactic)"+" values (35, 'Miranda', 'Brend', 224, 'jcir@scs.ubbcluj.ro', 'Gabi');";
            stmt.executeUpdate(sql1);
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllPredari(){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Predari;" );
            while ( rs.next() ) {
                int idStudent = rs.getInt("IdStudent");
                int idTema=rs.getInt("IdTema");
                int nota=rs.getInt("Nota");
                int predSapt=rs.getInt("SaptPredare");
                int deadline=rs.getInt("Deadline");
                String feedback=rs.getString("Feedback");
                String profesor=rs.getString("Profesor");
                IdNota idNota=new IdNota(idStudent,idTema);
                Predare predare=new Predare(idStudent,idTema,nota,predSapt,deadline,feedback,profesor);
                predare.setId(idNota);
                System.out.println(predare.toString());
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void printAllStudenti(){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Studenti;" );
            while ( rs.next() ) {
                int id = rs.getInt("Id");
                String  nume = rs.getString("Nume");
                String prenume=rs.getString("Prenume");
                int grupa=rs.getInt("Grupa");
                String email=rs.getString("Email");
                String cadru=rs.getString("CadruDidactic");
                Student student=new Student(nume,prenume,grupa,email,cadru);
                student.setId(id);
                System.out.print(student.toString());
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void printAllTeme(){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Teme;" );
            while ( rs.next() ) {
                int id = rs.getInt("Id");
                String  descriere = rs.getString("Descriere");
                int start=rs.getInt("StartWeek");
                int deadline=rs.getInt("DeadlineWeek");
                Tema tema=new Tema(descriere);
                tema.setStartWeek(start);
                tema.setDeadlineWeek(deadline);
                tema.setId(id);
                System.out.println(tema.toString());
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}