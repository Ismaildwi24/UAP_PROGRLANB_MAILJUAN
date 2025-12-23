package model;

public class Baby {
    private String id;
    private String name;
    private String gender;
    private String birthDate;
    private String parentName;

    public Baby(String id, String name, String gender, String birthDate, String parentName) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.parentName = parentName;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getParentName() { return parentName; }

    @Override
    public String toString() {
        return name + " (" + gender + ")";
    }
}