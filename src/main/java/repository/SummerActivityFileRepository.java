package repository;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SummerActivityFileRepository extends SummerActivityInMemoryRepository{
    private String filename;
    private static int idGenerator=0;

    public SummerActivityFileRepository(String filename) {
        this.filename = filename;
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
                try{
                    if (elems.length!=5){
                        System.err.println("Invalid line ..."+line);
                        continue;
                    }
                    int id=Integer.parseInt(elems[0]);
                    int min_age=Integer.parseInt(elems[3]);
                    int max_age=Integer.parseInt(elems[4]);
                    SummerActivity crr=new SummerActivity(id,elems[1],elems[2],min_age,max_age);
                    super.add(crr);
                }catch(NumberFormatException ex){
                    System.err.println("Error converting "+elems[0]);
                }
            }
        }catch(IOException ex){
            throw new RepositoryException("Error reading "+ex);
        }
    }

    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){
            pw.println(idGenerator);
            for(SummerActivity crr:findAll()){
                pw.println(crr.getID()+";"+crr.getName()+";"+crr.getDate()+";"+crr.getMin_age()+";"+crr.getMax_age());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }
    }

    @Override
    public SummerActivity add(SummerActivity el) {
        el.setID(getNextId());
        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(SummerActivity el) {
        super.delete(el);
        writeToFile();
    }

    @Override
    public void update(SummerActivity el, Integer integer) {
        super.update(el, integer);
        writeToFile();
    }

    private static int getNextId(){
        return idGenerator++;
    }
}
