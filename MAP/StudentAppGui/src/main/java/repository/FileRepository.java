package repository;

import config.IOHandler;
import domain.Entity;
import domain.validators.Validator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileRepository extends InMemoryRepository {
    private String fileName;
    public FileRepository(Validator validator, String fileN) {
        super(validator);
        this.fileName=fileN;
    }

    public abstract Entity createEntityFromLine(String[] args);

    public void loadDataFile() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = IOHandler.initializeBufferReader(fileName);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] args = line.split(";");
                Entity entity = createEntityFromLine(args);
                super.save(entity);
            }
        } catch (IOException e) {
            System.out.println("Errors while loading the data");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Errors while closing the buffer:");
            }
        }
    }
    private void writeDataFile(){
        FileWriter fileWriter=null;
        try{
            fileWriter=IOHandler.initializeDataWriter(fileName);
            FileWriter finalFileWriter = fileWriter;
            super.findAll().forEach(e-> {
                try {
                    finalFileWriter.write(e.toString());
                } catch (IOException ex) {
                    System.out.println("Error while writing to file");;
                }
            });
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Error while write int file");
        }
    }

    public void deleteFromFile(Integer id){
        super.delete(id);
        writeDataFile();
    }
    public void saveToFile(Entity entity){
        super.save(entity);
        writeDataFile();
    }
    public void updateFromFile(Entity entity){
        super.update(entity);
        writeDataFile();
    }
}
