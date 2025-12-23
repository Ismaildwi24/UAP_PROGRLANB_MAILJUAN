package util;

import java.io.*;
import java.util.*;

public class CSVHelper {

    private static final String FILE = "data_kesehatan_balita.csv";

    public static void save(String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readAll() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Split dengan limit untuk menangani catatan yang mungkin mengandung koma
                // Format terbaru (16 fields): date,babyId,name,gender,motherName,weight,height,temp,hepB,bcg,polio,pentabio,pcv,rotavirus,mr,note
    
                // Total maksimal 16 field, jadi split dengan limit 16
                String[] parts = line.split(",", 16);
                list.add(parts);
            }
        } catch (IOException e) {
            // file belum ada
        }
        return list;
    }

    public static void overwrite(List<String> newData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (String s : newData) {
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
