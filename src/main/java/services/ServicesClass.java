package services;
import model.*;
import repository.*;

import java.util.ArrayList;
import java.util.List;

public class ServicesClass {
    private SummerActivityRepository class1Repository; // == requestRepository;
    private RegistrationRepository class2Repository; // == repairedRepository;

    public ServicesClass(SummerActivityRepository r1, RegistrationRepository r2){
        this.class1Repository=r1;
        this.class2Repository=r2;
    }

    public int addSummerActivity(String name,String date,Integer min_age,Integer max_age)throws ServicesException{
        try{
            SummerActivity obj= new SummerActivity(name,date,min_age,max_age);
            SummerActivity newObj=class1Repository.add(obj);
            return newObj.getID();
        }catch(RepositoryException ex){
            throw new ServicesException("Error adding request"+ex);
        }
    }

    public void deleteSummerActivity(Integer id,String name,String date,Integer min_age,Integer max_age)throws ServicesException{
        try{
            SummerActivity newObj=class1Repository.findById(id);
            class1Repository.delete(newObj);
        }catch(RepositoryException ex){
            throw new ServicesException("Error deleting request"+ex);
        }
    }

    public void updateSummerActivity(Integer id,String name,String date,Integer min_age,Integer max_age)throws ServicesException{
        try{
            SummerActivity newObj=class1Repository.findById(id);
            newObj.setName(name);
            newObj.setDate(date);
            newObj.setMax_age(max_age);
            newObj.setMin_age(min_age);
            class1Repository.update(newObj,id);
        }catch(RepositoryException ex){
            throw new ServicesException("Error updating request "+ex);
        }
    }

    public List<SummerActivity> getSummerActivityByName(String name){
        return class1Repository.filterByName(name);
    }

    public void addRegistration( SummerActivity obj,String name,String phone, String date,Integer age)throws ServicesException {
        try {
            Registration obje = new Registration( obj,name, phone, date,age);
            Registration objeNew = class2Repository.add(obje);
        } catch (RepositoryException ex) {
            throw new ServicesException("Error adding form" + ex);
        }
    }

    public List<SummerActivity> getAllSummerActivitys(){
        List<SummerActivity> lm=new ArrayList<>();
        for(SummerActivity m:class1Repository.findAll()){
            lm.add(m);
        }
        return lm;
    }

    public List<Registration> getRegistrationBySummerActivity(SummerActivity obj){
        return class2Repository.filterBySummerActivityObj(obj);
    }
    public List<Registration> getBlass2ByDate(String date){
        return class2Repository.filterByDate(date);
    }
}
