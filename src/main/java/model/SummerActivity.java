package model;
import java.io.Serializable;

public class SummerActivity implements Identifiable<Integer>, Serializable {
    private int ID;
    private String name;
    private  String date;
    private Integer min_age;
    private Integer max_age;

    public SummerActivity(){
        this.ID = 0;
        this.name = "";
        this.date ="";
        this.min_age=0;
        this.max_age=0;
    }
    public SummerActivity(int ID, String name, String date,Integer min_age,Integer max_age){
        this.ID = ID;
        this.name = name;
        this.date =date;
        this.min_age=min_age;
        this.max_age=max_age;
    }
    public SummerActivity( String name, String date,Integer min_age,Integer max_age){
        this.name = name;
        this.date =date;
        this.min_age=min_age;
        this.max_age=max_age;
    }

    //+ getters and setters
    //+toString

    public String getName() {
        return name;
    }
    public void setName(String ownerName) {
        this.name = ownerName;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Integer getID(){
        return ID;
    }
    public void setID(Integer id){
        ID = id;
    }

    public Integer getMin_age(){return min_age;}
    public void setMin_age(int a){this.min_age=a;}

    public Integer getMax_age(){return max_age;}
    public void setMax_age(int a){min_age=a;}

    @Override
    public String toString() {
        return "ID=" + ID +
                ", ownerName='" + name + '\'' +
                ", date='" + date + '\'' ;

    }
}
