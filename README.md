# Ujian Akhir Praktikum Pemrograman Lanjut Kelas B

## Program Manajemen Data Kesehatan Bayi

**Dibuat oleh:**
- Ismail Dwi Muh. Anugerah - 202410370110013
- Akhmad Arjuan Syuhada - 202410370110043

---

## 📋 Tujuan Program

Program Manajemen Data Kesehatan Bayi adalah aplikasi desktop berbasis Java Swing yang dirancang untuk membantu tenaga kesehatan (nakes) dalam mencatat, mengelola, dan memantau data kesehatan bayi secara sistematis. Program ini bertujuan untuk:

1. **Memudahkan Pencatatan Data**: Memberikan interface yang user-friendly untuk mencatat data kesehatan bayi secara lengkap, termasuk data antropometri (berat badan, tinggi badan), suhu tubuh, dan status vaksinasi.

2. **Meningkatkan Efisiensi Kerja**: Mengurangi beban administrasi tenaga kesehatan dengan sistem penyimpanan data otomatis menggunakan file CSV, sehingga data dapat diakses dan dikelola dengan mudah.

3. **Mendukung Monitoring Kesehatan**: Menyediakan dashboard yang menampilkan data terbaru dan fitur history untuk melacak perkembangan kesehatan bayi dari waktu ke waktu.

4. **Meningkatkan Akurasi Data**: Mengimplementasikan validasi input untuk memastikan data yang dimasukkan sesuai dengan standar medis (rentang berat badan, tinggi badan, dan suhu tubuh yang wajar).

5. **Edukasi Vaksinasi**: Menyediakan informasi lengkap mengenai jadwal dan manfaat vaksin untuk mendukung program imunisasi bayi.

---

## ✨ Fitur Program

### 1. **Sistem Login**
- Interface login yang sederhana dan intuitif
- Fitur show/hide password untuk kemudahan pengguna
- Validasi input username dan password

### 2. **Dashboard**
- Menampilkan 10 data kesehatan bayi terbaru
- Informasi lengkap meliputi:
  - ID Bayi (auto-generated: M#### untuk laki-laki, F#### untuk perempuan)
  - Tanggal pencatatan
  - Nama bayi dan nama ibu
  - Data antropometri (Berat Badan, Tinggi Badan)
  - **Berat Badan Ideal** (dihitung otomatis berdasarkan tinggi badan)
  - **Status Berat Badan** (Ideal/Kurang/Berlebih)
  - Suhu tubuh dengan **klasifikasi status** (Normal/Demam Ringan/Demam/Demam Tinggi/Hipotermia)
  - Status vaksinasi (7 jenis vaksin: Hepatitis B, BCG, Polio, Pentabio, PCV, Rotavirus, MR)
  - Catatan tambahan

### 3. **Input Data Kesehatan**
- Form input terstruktur dengan 3 bagian:
  - **Data Dasar**: Nama bayi, jenis kelamin, nama ibu, berat badan, tinggi badan, suhu tubuh
  - **Status Vaksinasi**: Checklist untuk 7 jenis vaksin
  - **Catatan Tambahan**: Text area untuk catatan khusus
- **Validasi Input**:
  - Validasi field wajib tidak boleh kosong
  - Validasi format dan rentang berat badan (0.1 - 50 kg)
  - Validasi format dan rentang tinggi badan (0.1 - 200 cm)
  - Validasi format suhu tubuh (mendukung koma dan titik desimal)
- Auto-generate ID bayi berdasarkan jenis kelamin
- Auto-generate tanggal pencatatan
- Konfirmasi sebelum menyimpan data

### 4. **History Data**
- Menampilkan semua data kesehatan yang pernah dicatat
- **Fitur Pencarian**: Pencarian berdasarkan ID Bayi dengan real-time filtering
- **Fitur Sorting**: Tabel dapat di-sort berdasarkan kolom tertentu
- **Fitur Edit**: 
  - Edit data yang sudah tersimpan
  - Dialog edit dengan form lengkap
  - Validasi input saat edit
- **Fitur Hapus**: 
  - Hapus data dengan konfirmasi
  - Data langsung terupdate di file CSV
- Tampilan tabel dengan informasi lengkap dan styling yang rapi

### 5. **Informasi Vaksin**
- **Jadwal Vaksinasi**: Tabel jadwal pemberian vaksin berdasarkan usia (0-24 jam hingga 18 bulan)
- **Detail Vaksin**: Informasi lengkap untuk 7 jenis vaksin:
  - Hepatitis B (HB0)
  - BCG
  - Polio (OPV & IPV)
  - Pentabio (DPT-HB-Hib)
  - PCV (Pneumokokus)
  - Rotavirus
  - MR (Measles & Rubella)
- Setiap vaksin dilengkapi penjelasan mengenai:
  - Penyakit yang dicegah
  - Bahaya penyakit tersebut
  - Manfaat vaksin
  - Cara pemberian (jika relevan)

### 6. **Sistem Penyimpanan Data**
- Penyimpanan data menggunakan file CSV (`data_kesehatan_balita.csv`)
- Format data terstruktur dan dapat dibuka dengan aplikasi spreadsheet
- Backup dan restore data mudah dilakukan

### 7. **Fitur Tambahan**
- **Perhitungan Berat Badan Ideal**: Otomatis menghitung berat badan ideal berdasarkan tinggi badan menggunakan formula medis
- **Klasifikasi Status Berat Badan**: Menentukan status berat badan (Ideal/Kurang/Berlebih) dengan toleransi ±10% dari berat ideal
- **Klasifikasi Status Suhu**: Mengklasifikasikan suhu tubuh ke dalam kategori medis
- **Auto-refresh**: Data di dashboard dan history otomatis terupdate setelah input/edit/hapus
- **UI/UX yang Modern**: Desain interface dengan warna yang nyaman untuk mata dan layout yang rapi

---

## 📁 Struktur Program

```
UAP_Pemrograman_Lanjut/
│
├── src/
│   ├── model/
│   │   ├── Baby.java              # Model data bayi (id, nama, jenis kelamin, tanggal lahir, nama orang tua)
│   │   └── HealthRecord.java      # Model data kesehatan (tanggal, data bayi, antropometri, vaksin, catatan)
│   │
│   ├── ui/
│   │   ├── MainApp.java           # Main application frame dengan CardLayout dan sidebar navigation
│   │   ├── LoginPanel.java        # Panel untuk halaman login
│   │   ├── DashboardPanel.java    # Panel dashboard menampilkan 10 data terbaru
│   │   ├── InputHealthPanel.java  # Panel form input data kesehatan baru
│   │   ├── HistoryPanel.java      # Panel history dengan fitur search, edit, delete
│   │   └── VaccineInfoPanel.java  # Panel informasi vaksin dan jadwal
│   │
│   └── util/
│       ├── CSVHelper.java         # Utility class untuk operasi file CSV (read, write, overwrite)
│       └── Validator.java         # Utility class untuk validasi input data
│
├── src/assests/
│   └── medicalillustration.png    # Asset gambar untuk halaman login
│
├── out/                           # Folder output kompilasi Java
│
└── README.md                      # Dokumentasi proyek
```

### Penjelasan Komponen:

#### **Model Layer**
- **Baby.java**: Menyimpan informasi dasar bayi (ID, nama, jenis kelamin, tanggal lahir, nama orang tua)
- **HealthRecord.java**: Menyimpan data kesehatan lengkap termasuk antropometri, suhu, status vaksin, dan catatan

#### **UI Layer**
- **MainApp.java**: Controller utama aplikasi yang mengatur navigasi antar panel menggunakan CardLayout
- **LoginPanel.java**: Interface login dengan validasi sederhana
- **DashboardPanel.java**: Menampilkan ringkasan data terbaru dengan perhitungan status otomatis
- **InputHealthPanel.java**: Form input dengan validasi dan auto-generate ID
- **HistoryPanel.java**: Manajemen data lengkap dengan CRUD operations
- **VaccineInfoPanel.java**: Panel informatif untuk edukasi vaksinasi

#### **Utility Layer**
- **CSVHelper.java**: Menangani semua operasi file I/O untuk penyimpanan data
- **Validator.java**: Menyediakan fungsi validasi untuk memastikan data input sesuai standar

---

## 💡 Mengapa Program Ini Penting?

Program Manajemen Data Kesehatan Bayi memiliki peran penting dalam sistem kesehatan, terutama dalam konteks pelayanan kesehatan bayi dan balita. Berikut adalah alasan-alasan mengapa program ini penting:

### **1. Meningkatkan Kualitas Pelayanan Kesehatan**
Program ini membantu tenaga kesehatan dalam memberikan pelayanan yang lebih terstruktur dan sistematis. Dengan sistem pencatatan yang rapi, tenaga kesehatan dapat dengan mudah mengakses riwayat kesehatan bayi, sehingga dapat memberikan diagnosis dan perawatan yang lebih akurat.

### **2. Mencegah Kehilangan Data**
Sebelum adanya sistem digital, data kesehatan sering kali dicatat secara manual di buku atau kertas yang rentan hilang, rusak, atau sulit dicari. Program ini menyimpan data dalam format digital (CSV) yang lebih aman, mudah di-backup, dan dapat diakses kapan saja.

### **3. Mendukung Monitoring Tumbuh Kembang**
Dengan fitur history dan dashboard, program ini memungkinkan tenaga kesehatan untuk memantau perkembangan bayi dari waktu ke waktu. Fitur perhitungan berat badan ideal dan klasifikasi status membantu mengidentifikasi masalah pertumbuhan lebih dini.

### **4. Meningkatkan Cakupan Imunisasi**
Panel informasi vaksin yang lengkap membantu tenaga kesehatan dalam memberikan edukasi kepada orang tua mengenai pentingnya vaksinasi. Dengan informasi jadwal dan manfaat vaksin yang jelas, diharapkan cakupan imunisasi dapat meningkat.

### **5. Efisiensi Waktu dan Sumber Daya**
Program ini mengurangi beban administrasi tenaga kesehatan dengan sistem pencatatan yang cepat dan otomatis. Waktu yang biasanya digunakan untuk menulis manual dapat dialihkan untuk pelayanan langsung kepada pasien.

### **6. Validasi Data untuk Akurasi**
Sistem validasi input memastikan bahwa data yang dimasukkan sesuai dengan standar medis. Hal ini mengurangi kesalahan pencatatan yang dapat berdampak pada diagnosis dan perawatan.

### **7. Dukungan untuk Program Kesehatan Nasional**
Program ini sejalan dengan program kesehatan nasional Indonesia, seperti program imunisasi dan program kesehatan ibu dan anak (KIA). Data yang terstruktur dapat digunakan untuk pelaporan dan evaluasi program kesehatan.

### **8. Aksesibilitas dan Kemudahan Penggunaan**
Interface yang user-friendly memungkinkan tenaga kesehatan dengan berbagai tingkat kemampuan teknis untuk menggunakan program ini dengan mudah. Tidak diperlukan pelatihan khusus yang rumit.

### **9. Dasar untuk Pengembangan Sistem yang Lebih Kompleks**
Program ini dapat menjadi dasar untuk pengembangan sistem informasi kesehatan yang lebih canggih di masa depan, seperti integrasi dengan database, sistem pelaporan otomatis, atau bahkan aplikasi mobile.

### **10. Kontribusi pada Kesehatan Masyarakat**
Pada akhirnya, program ini berkontribusi pada peningkatan kesehatan masyarakat secara keseluruhan. Dengan pencatatan yang baik dan monitoring yang efektif, masalah kesehatan bayi dapat terdeteksi dan ditangani lebih cepat, sehingga mengurangi angka kesakitan dan kematian bayi.

---

## 🚀 Cara Menjalankan Program

1. Pastikan Java Development Kit (JDK) sudah terinstall di sistem Anda
2. Buka terminal/command prompt di folder project
3. Compile semua file Java:
   ```
   javac -d out src/model/*.java src/ui/*.java src/util/*.java
   ```
4. Jalankan program:
   ```
   java -cp out ui.MainApp
   ```
5. Atau gunakan IDE seperti IntelliJ IDEA atau Eclipse untuk menjalankan program

---

## 📝 Catatan

- Program menggunakan file CSV (`data_kesehatan_balita.csv`) untuk penyimpanan data
- File CSV akan dibuat otomatis saat pertama kali menyimpan data
- Pastikan aplikasi memiliki permission untuk membaca dan menulis file di direktori project

---

**Dibuat dengan ❤️ untuk mendukung pelayanan kesehatan bayi di Indonesia**

---

## 🧪 Skema Testing Fitur Program

### **1. Testing Fitur Login**

| No | Test Case | Input | Expected Output | Status |
|----|-----------|-------|-----------------|--------|
| 1.1 | Login dengan username dan password kosong | Username: "", Password: "" | Error message: "Username dan password harus diisi!" | ⬜ |
| 1.2 | Login dengan username kosong | Username: "", Password: "test" | Error message: "Username dan password harus diisi!" | ⬜ |
| 1.3 | Login dengan password kosong | Username: "test", Password: "" | Error message: "Username dan password harus diisi!" | ⬜ |
| 1.4 | Login dengan data valid | Username: "nakes", Password: "123" | Berhasil login, masuk ke dashboard | ⬜ |
| 1.5 | Test fitur show/hide password | Klik tombol 👁️ | Password terlihat/tersembunyi bergantian | ⬜ |

### **2. Testing Fitur Input Data Kesehatan**

| No | Test Case | Input | Expected Output | Status |
|----|-----------|-------|-----------------|--------|
| 2.1 | Input dengan field kosong | Semua field kosong | Error: "Semua field wajib diisi!" | ⬜ |
| 2.2 | Input nama bayi kosong | Nama: "", BB: 3.5, TB: 50, Suhu: 36.5 | Error: "Semua field wajib diisi!" | ⬜ |
| 2.3 | Input berat badan invalid (bukan angka) | BB: "abc" | Error: "Berat badan harus berupa angka yang valid" | ⬜ |
| 2.4 | Input berat badan di luar rentang (negatif) | BB: "-1" | Error: "Berat badan harus antara 0.1 - 50 kg" | ⬜ |
| 2.5 | Input berat badan di luar rentang (>50) | BB: "60" | Error: "Berat badan harus antara 0.1 - 50 kg" | ⬜ |
| 2.6 | Input tinggi badan invalid | TB: "xyz" | Error: "Tinggi badan harus berupa angka yang valid" | ⬜ |
| 2.7 | Input tinggi badan di luar rentang | TB: "250" | Error: "Tinggi badan harus antara 0.1 - 200 cm" | ⬜ |
| 2.8 | Input suhu invalid | Suhu: "abc" | Error: "Suhu harus berupa angka yang valid" | ⬜ |
| 2.9 | Input suhu dengan koma desimal | Suhu: "36,5" | Berhasil (dikonversi ke 36.5) | ⬜ |
| 2.10 | Input tanpa pilih jenis kelamin | Semua field terisi kecuali gender | Error: "Pilih kelamin bayi terlebih dahulu" | ⬜ |
| 2.11 | Input data lengkap valid | Semua field terisi dengan benar | Data tersimpan, form reset, muncul pesan sukses | ⬜ |
| 2.12 | Verifikasi auto-generate ID laki-laki | Gender: Laki-laki | ID format: M0001, M0002, dst. | ⬜ |
| 2.13 | Verifikasi auto-generate ID perempuan | Gender: Perempuan | ID format: F0001, F0002, dst. | ⬜ |
| 2.14 | Input dengan semua vaksin tercentang | Semua checkbox vaksin: ✓ | Data tersimpan dengan semua vaksin = true | ⬜ |
| 2.15 | Input tanpa vaksin | Semua checkbox vaksin: ✗ | Data tersimpan dengan semua vaksin = false | ⬜ |
| 2.16 | Input dengan catatan tambahan | Catatan: "Bayi rewel saat pemeriksaan" | Catatan tersimpan di data | ⬜ |

### **3. Testing Fitur Dashboard**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 3.1 | Tampilan dashboard saat belum ada data | Buka dashboard | Tabel kosong atau tidak ada data | ⬜ |
| 3.2 | Tampilan dashboard dengan data | Ada data di CSV | Menampilkan maksimal 10 data terbaru | ⬜ |
| 3.3 | Verifikasi perhitungan berat ideal | TB: 50 cm | BB Ideal sesuai formula (≈ 23 kg untuk tinggi 50 cm) | ⬜ |
| 3.4 | Verifikasi status berat badan ideal | BB: 3.5 kg, BB Ideal: 3.5 kg | Status: "Ideal" | ⬜ |
| 3.5 | Verifikasi status berat badan kurang | BB: 2.5 kg, BB Ideal: 3.5 kg | Status: "Kurang" | ⬜ |
| 3.6 | Verifikasi status berat badan berlebih | BB: 4.5 kg, BB Ideal: 3.5 kg | Status: "Berlebih" | ⬜ |
| 3.7 | Verifikasi klasifikasi suhu normal | Suhu: 36.5°C | Status: "Normal" | ⬜ |
| 3.8 | Verifikasi klasifikasi demam ringan | Suhu: 38.0°C | Status: "Demam Ringan" | ⬜ |
| 3.9 | Verifikasi klasifikasi demam | Suhu: 39.0°C | Status: "Demam" | ⬜ |
| 3.10 | Verifikasi klasifikasi demam tinggi | Suhu: 40.0°C | Status: "Demam Tinggi" | ⬜ |
| 3.11 | Verifikasi klasifikasi hipotermia | Suhu: 35.0°C | Status: "Hipotermia" | ⬜ |
| 3.12 | Refresh dashboard setelah input data baru | Input data baru, kembali ke dashboard | Data baru muncul di tabel | ⬜ |

### **4. Testing Fitur History**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 4.1 | Tampilan history semua data | Buka halaman history | Semua data ditampilkan dalam tabel | ⬜ |
| 4.2 | Pencarian dengan ID yang ada | Search: "M0001" | Hanya menampilkan data dengan ID M0001 | ⬜ |
| 4.3 | Pencarian dengan ID yang tidak ada | Search: "M9999" | Tabel kosong (tidak ada hasil) | ⬜ |
| 4.4 | Pencarian dengan partial ID | Search: "M00" | Menampilkan semua ID yang mengandung "M00" | ⬜ |
| 4.5 | Clear search | Klik tombol "Clear" | Semua data ditampilkan kembali | ⬜ |
| 4.6 | Sorting tabel | Klik header kolom | Data terurut sesuai kolom yang diklik | ⬜ |
| 4.7 | Edit data tanpa memilih baris | Klik "Edit" tanpa select | Warning: "Pilih data yang ingin diedit" | ⬜ |
| 4.8 | Edit data dengan memilih baris | Select baris, klik "Edit" | Dialog edit muncul dengan data terisi | ⬜ |
| 4.9 | Edit dengan input invalid | Edit BB: "abc" | Error message sesuai validasi | ⬜ |
| 4.10 | Edit data dengan input valid | Edit semua field, klik "Simpan" | Data terupdate, muncul pesan sukses | ⬜ |
| 4.11 | Cancel edit | Klik "Batal" di dialog edit | Dialog tertutup, data tidak berubah | ⬜ |
| 4.12 | Hapus data tanpa memilih | Klik "Hapus" tanpa select | Warning: "Pilih data yang ingin dihapus" | ⬜ |
| 4.13 | Hapus data dengan konfirmasi No | Select baris, klik "Hapus", pilih No | Data tidak terhapus | ⬜ |
| 4.14 | Hapus data dengan konfirmasi Yes | Select baris, klik "Hapus", pilih Yes | Data terhapus, muncul pesan sukses | ⬜ |
| 4.15 | Verifikasi data terhapus dari CSV | Hapus data, tutup aplikasi, buka lagi | Data yang dihapus tidak muncul | ⬜ |

### **5. Testing Fitur Informasi Vaksin**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 5.1 | Tampilan jadwal vaksin | Buka halaman Informasi Vaksin | Tabel jadwal vaksin ditampilkan | ⬜ |
| 5.2 | Verifikasi isi jadwal vaksin | Scroll tabel jadwal | Semua usia (0-24 jam hingga 18 bulan) ada | ⬜ |
| 5.3 | Tampilan detail vaksin | Scroll ke bawah | 7 card detail vaksin ditampilkan | ⬜ |
| 5.4 | Verifikasi konten detail vaksin | Baca setiap card | Setiap vaksin memiliki penjelasan lengkap | ⬜ |
| 5.5 | Scroll konten informasi | Scroll panel | Konten dapat di-scroll dengan smooth | ⬜ |

### **6. Testing Fitur Navigasi**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 6.1 | Navigasi ke Dashboard | Klik tombol "Dashboard" di sidebar | Halaman dashboard ditampilkan | ⬜ |
| 6.2 | Navigasi ke Input Data | Klik tombol "Catat Kesehatan" | Halaman input form ditampilkan | ⬜ |
| 6.3 | Navigasi ke History | Klik tombol "History" | Halaman history ditampilkan | ⬜ |
| 6.4 | Navigasi ke Informasi Vaksin | Klik tombol "Informasi Vaksin" | Halaman informasi vaksin ditampilkan | ⬜ |
| 6.5 | Logout dengan konfirmasi No | Klik "Logout", pilih No | Tetap di aplikasi | ⬜ |
| 6.6 | Logout dengan konfirmasi Yes | Klik "Logout", pilih Yes | Kembali ke halaman login | ⬜ |
| 6.7 | Highlight tombol aktif | Klik tombol navigasi | Tombol yang aktif ter-highlight | ⬜ |

### **7. Testing Fitur Penyimpanan Data**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 7.1 | Simpan data baru | Input dan simpan data | File CSV terupdate dengan data baru | ⬜ |
| 7.2 | Buka file CSV manual | Buka `data_kesehatan_balita.csv` dengan Excel/Notepad | Data dapat dibaca dengan format yang benar | ⬜ |
| 7.3 | Verifikasi format CSV | Cek struktur file CSV | Format: tanggal,id,nama,gender,ibu,bb,tb,suhu,vaksin...,catatan | ⬜ |
| 7.4 | Edit data dan verifikasi CSV | Edit data, simpan | File CSV terupdate dengan data yang diubah | ⬜ |
| 7.5 | Hapus data dan verifikasi CSV | Hapus data | Data terhapus dari file CSV | ⬜ |
| 7.6 | Restart aplikasi | Tutup aplikasi, buka lagi | Data yang tersimpan masih ada | ⬜ |

### **8. Testing Edge Cases**

| No | Test Case | Input/Aksi | Expected Output | Status |
|----|-----------|------------|-----------------|--------|
| 8.1 | Input dengan karakter khusus di nama | Nama: "Bayi'123 @#$" | Data tersimpan dengan karakter khusus | ⬜ |
| 8.2 | Input dengan nilai batas berat badan | BB: 0.1 kg atau 50 kg | Data tersimpan (valid) | ⬜ |
| 8.3 | Input dengan nilai batas tinggi badan | TB: 0.1 cm atau 200 cm | Data tersimpan (valid) | ⬜ |
| 8.4 | Input dengan suhu ekstrem | Suhu: 30.0°C atau 45.0°C | Data tersimpan, status diklasifikasikan | ⬜ |
| 8.5 | Input banyak data berturut-turut | Input 20 data sekaligus | Semua data tersimpan dengan ID unik | ⬜ |
| 8.6 | Edit data yang sudah dihapus | Hapus data, coba edit data lain | Tidak ada error, edit berjalan normal | ⬜ |
| 8.7 | Pencarian dengan case berbeda | Search: "m0001" (lowercase) | Tetap menemukan "M0001" (case insensitive) | ⬜ |
