package repository;

import model.SummerActivity;
import model.*;
import model.Registration;
import services.ServicesClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationFileRepository extends RegistrationInMemoryRepository {
    private String filename;
    private SummerActivityRepository requestRepository;
    private static int idGenerator=0;

    public RegistrationFileRepository(String filename,SummerActivityRepository requestRepository) {
        this.filename = filename;
        this.requestRepository=requestRepository;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line=br.readLine();
            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator, starting from 0");
            }
            while((line=br.readLine())!=null){
                String[] elems=line.split(";");
                if (elems.length!=6){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(elems[0]);
                    int requestId=Integer.parseInt(elems[1]);
                    Integer age=Integer.parseInt(elems[5]);
                    SummerActivity crr=requestRepository.findById(requestId);
                    Registration crf=new Registration(id,crr,elems[2],elems[3],elems[4],age);
                    super.add(crf);

                }catch(NumberFormatException ex){
                    System.err.println("Invalid data "+ex);
                }catch (RepositoryException ex){
                    System.err.println("Repository Error "+ex);
                }
            }
        }catch (IOException ex){
            throw new RepositoryException("Error reading "+ex);
        }

    }

    @Override
    public Registration add(Registration el) {
        el.setID(getNextId());
        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(Registration el) {
        super.delete(el);
        writeToFile();
    }

    @Override
    public void update(Registration el, Integer integer) {
        super.update(el, integer);
        writeToFile();
    }

    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){
            pw.println(idGenerator);
            for(Registration crf:findAll()){
                pw.println(crf.getID()+";"+crf.getRequest().getID()+";"+ crf.getName()+";"+crf.getphone()+";"+crf.getDate()+";"+crf.getAge());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }
    }
    private static int getNextId(){
        return idGenerator++;
    }
}
