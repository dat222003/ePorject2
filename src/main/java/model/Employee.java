package model;

public class Employee {
    private int userid;

    private double salary;
    private int emp_id;
    private String user;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String idcard;
    private int gender;






    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee() {
    }



    public Employee(int userid, double salary, int emp_id, String user, String password, String name, String phone, String email, String idcard, int gender) {
        this.userid = userid;
        this.salary = salary;
        this.emp_id = emp_id;
        this.user = user;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idcard = idcard;
        this.gender = gender;
    }

    public Employee(String user, String password, String name, String phone, String email, double salary, String idcard, int gender) {
        this.salary = salary;
        this.user = user;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idcard = idcard;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userid=" + userid +
                ", salary=" + salary +
                ", empid=" + emp_id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", idcard='" + idcard + '\'' +
                ", gender=" + gender +
                '}';
    }
}
