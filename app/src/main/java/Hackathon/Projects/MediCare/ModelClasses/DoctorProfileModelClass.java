package Hackathon.Projects.MediCare.ModelClasses;

public class DoctorProfileModelClass {
    private String name, Degree, HospitalName, ClinicName, TypeOfDoctor, ID;

    public DoctorProfileModelClass(String name, String degree, String hospitalName, String clinicName, String typeOfDoctor, String ID) {
        this.name = name;
        Degree = degree;
        HospitalName = hospitalName;
        ClinicName = clinicName;
        TypeOfDoctor = typeOfDoctor;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public String getTypeOfDoctor() {
        return TypeOfDoctor;
    }

    public void setTypeOfDoctor(String typeOfDoctor) {
        TypeOfDoctor = typeOfDoctor;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}