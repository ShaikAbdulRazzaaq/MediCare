package Hackathon.Projects.MediCare.ModelClasses;

public class PatientProfileModelClass {
    private String id,gender,name,DOB;

    public PatientProfileModelClass(String id, String gender, String name, String DOB) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.DOB = DOB;
    }

    public PatientProfileModelClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
