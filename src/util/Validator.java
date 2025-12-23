package util;

public class Validator {

    public static void validateInput(String baby, String w, String h, String t) throws Exception {
        // menghilangkan spasi
        baby = baby.trim();
        w = w.trim();
        h = h.trim();
        t = t.trim();

        // validasi field kosong
        if (baby.isEmpty() || w.isEmpty() || h.isEmpty() || t.isEmpty()) {
            throw new Exception("Semua field wajib diisi!");
        }
        //bb
        double weight;
        try {
            weight = Double.parseDouble(w);
            if (weight <= 0 || weight > 50) {
                throw new Exception("Berat badan harus antara 0.1 - 50 kg");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Berat badan harus berupa angka yang valid (contoh: 3.5 atau 3,5)");
        }
        
        //tb
        double height;
        try {
            height = Double.parseDouble(h);
            if (height <= 0 || height > 200) {
                throw new Exception("Tinggi badan harus antara 0.1 - 200 cm");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Tinggi badan harus berupa angka yang valid (contoh: 50.5 atau 50,5)");
        }

        // parse dan validasi suhu 
        try {
            String tempStr = t.replace(',', '.');
            Double.parseDouble(tempStr); 
        } catch (NumberFormatException e) {
            throw new Exception("Suhu harus berupa angka yang valid (contoh: 36.5 atau 36,5)");
        }
    }
}
