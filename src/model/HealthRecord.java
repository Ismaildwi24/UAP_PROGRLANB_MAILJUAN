package model;

public class HealthRecord {

    public String date;
    public String babyId;
    public String babyName;
    public String gender;
    public String motherName;
    public double weight;
    public double height;
    public double temperature;
    public boolean hepB, bcg, polio, pentabio, pcv, rotavirus, mr;
    public String note;

    public HealthRecord(String date, String babyId, String babyName, String gender, String motherName,
                        double weight, double height, double temperature, 
                        boolean hepB, boolean bcg, boolean polio, boolean pentabio, 
                        boolean pcv, boolean rotavirus, boolean mr, String note) {
        this.date = date;
        this.babyId = babyId;
        this.babyName = babyName;
        this.gender = gender;
        this.motherName = motherName;
        this.weight = weight;
        this.height = height;
        this.temperature = temperature;
        this.hepB = hepB;
        this.bcg = bcg;
        this.polio = polio;
        this.pentabio = pentabio;
        this.pcv = pcv;
        this.rotavirus = rotavirus;
        this.mr = mr;
        this.note = note;
    }

    public String toCSV() {
        // Pastikan catatan tidak null, jika kosong gunakan string kosong
        String noteValue = (note == null) ? "" : note;
        String genderValue = (gender == null) ? "" : gender;
        String motherValue = (motherName == null) ? "" : motherName;
        String babyIdValue = (babyId == null) ? "" : babyId;
        return date + "," + babyIdValue + "," + babyName + "," + genderValue + "," + motherValue + "," + 
                weight + "," + height + "," + temperature + "," + 
                hepB + "," + bcg + "," + polio + "," + pentabio + "," +
                pcv + "," + rotavirus + "," + mr + "," + noteValue;
    }
}
