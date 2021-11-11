package model;

public class Registration implements Identifiable<Integer>{
    private int id;
    //ComputerRepairRequest, services, phone, date, employee
    private SummerActivity obj1;
    private String name;
    private String phone;
    private String date;
    private Integer age;

    public Registration(SummerActivity request,String name, String phone, String date,Integer age) {
        this.name=name;
        this.obj1 = request;
        this.phone = phone;
        this.date = date;
        this.age=age;
    }
    public Registration(int id, SummerActivity request,String name, String phone, String date,Integer age) {
        this.id=id;
        this.name=name;
        this.obj1 = request;
        this.phone = phone;
        this.date = date;
        this.age=age;
    }

    //+ getters and setters
    //+toString

    public String getName() {
        return name;
    }
    public void setName(String ownerName) {
        this.name = ownerName;
    }

    public SummerActivity getRequest() {
        return obj1;
    }
    public void setRequest(SummerActivity request) {
        this.obj1= request;
    }

    public String getphone() {
        return phone;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAge(){return age;}
    public void setAge(int az){age=az;}

    @Override
    public Integer getID() {
        return id;
    }
    @Override
    public void setID(Integer id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", request=" + obj1 +
                ", phone=" + phone +
                ", date='" + date + '\''+
                '}';
    }
}
