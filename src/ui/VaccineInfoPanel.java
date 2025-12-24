package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VaccineInfoPanel extends JPanel {

    public VaccineInfoPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(176, 224, 230)); 
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Informasi Vaksin untuk Bayi");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(25, 25, 112)); 
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitle = new JLabel("Panduan lengkap mengenai jadwal dan manfaat vaksin");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(new Color(0, 0, 139));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitle);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel schedulePanel = createScheduleTable();
        contentPanel.add(schedulePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        
        JPanel detailPanel = createVaccineDetails();
        contentPanel.add(detailPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createScheduleTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JLabel title = new JLabel("Jadwal Pemberian Vaksin Berdasarkan Usia");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(25, 25, 112));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        String[] columns = {"Usia Anak", "Jenis Vaksin yang Diberikan"};
        Object[][] data = {
            {"0 - 24 Jam", "Hepatitis B (HBO)"},
            {"1 Bulan", "BCG, Polio Tetes 1 (OPV)"},
            {"2 Bulan", "DPT-HB-Hib 1, Polio Tetes 2, PCV 1, Rotavirus 1"},
            {"3 Bulan", "DPT-HB-Hib 2, Polio Tetes 3, PCV 2, Rotavirus 2"},
            {"4 Bulan", "DPT-HB-Hib 3, Polio Tetes 4, Polio Suntik 1 & 2 (IPV)*, Rotavirus 3"},
            {"9 Bulan", "Campak-Rubella (MR), Polio Suntik 2 (IPV)*"},
            {"12 Bulan", "PCV 3"},
            {"18 Bulan", "DPT-HB-Hib (Lanjutan/Booster), Campak-Rubella (Lanjutan/Booster)"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(50);
        table.setShowGrid(false); 
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(0, 0, 139));
        table.setIntercellSpacing(new Dimension(0, 0)); 
        
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 17));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 45));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(700);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 250), 2));
        tableScroll.setBackground(Color.WHITE);

        panel.add(title, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);

        JLabel note = new JLabel("* IPV dapat diberikan bersamaan dengan vaksin lainnya");
        note.setFont(new Font("SansSerif", Font.ITALIC, 12));
        note.setForeground(new Color(100, 100, 100));
        note.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(note, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createVaccineDetails() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(new Color(240, 248, 255));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Informasi Detail Vaksin");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(25, 25, 112));
        title.setHorizontalAlignment(JLabel.CENTER);
        
        titlePanel.add(title);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        panel.add(titlePanel);
        
        panel.add(createVaccineCard("1. Vaksin Hepatitis B (HB0 & Lanjutan)",
            "Penyakit: Hepatitis B adalah infeksi virus yang menyerang organ hati (liver).\n\n" +
            "Bahayanya: Pada bayi, infeksi ini sering kali tidak menunjukkan gejala awal, namun sangat mungkin menjadi kronis (seumur hidup). Jika bayi terinfeksi, risiko mereka terkena sirosis (kerusakan hati permanen) dan kanker hati saat dewasa meningkat drastis hingga 90%.\n\n" +
            "Manfaat Vaksin: Vaksin HB0 diberikan saat bayi baru lahir (0-24 jam) untuk memutus rantai penularan dari ibu ke bayi saat proses persalinan. Ini adalah \"tameng\" pertama bayi."));
        
        panel.add(createVaccineCard("2. Vaksin BCG",
            "Penyakit: Tuberkulosis (TBC). Indonesia adalah salah satu negara dengan kasus TBC tertinggi di dunia.\n\n" +
            "Bahayanya: Meskipun TBC sering dikenal menyerang paru-paru, pada bayi dan balita, kuman TBC bisa menyebar lewat darah ke organ lain. Yang paling ditakuti adalah Meningitis TB (radang selaput otak) dan TBC Tulang. Meningitis TB bisa menyebabkan kerusakan otak permanen atau kematian.\n\n" +
            "Manfaat Vaksin: BCG mungkin tidak 100% mencegah infeksi TBC paru ringan, tapi sangat efektif mencegah bentuk TBC berat yang mematikan tersebut.\n\n" +
            "Ciri: Biasanya akan muncul benjolan kecil atau bisul kecil di lengan kanan atas beberapa minggu setelah suntikan, yang akan membekas (scar). Ini adalah reaksi normal."));
        
        panel.add(createVaccineCard("3. Vaksin Polio (OPV & IPV)",
            "Penyakit: Poliomyelitis. Virus ini menyerang sistem saraf pusat di sumsum tulang belakang.\n\n" +
            "Bahayanya: Polio menyebabkan kelumpuhan permanen yang mendadak (biasanya pada kaki yang menjadi layu/mengecil). Jika virus menyerang otot pernapasan, pasien tidak bisa bernapas dan bisa meninggal dunia. Polio tidak ada obatnya, hanya bisa dicegah.\n\n" +
            "Manfaat Vaksin: Memberikan kekebalan ganda (lewat usus dan darah) untuk memastikan virus polio tidak bisa berkembang biak di tubuh anak maupun menular ke orang lain.\n\n" +
            "Jenis:\n" +
            "• OPV: Diteteskan ke mulut (rasanya manis/pahit sedikit).\n" +
            "• IPV: Disuntikkan (memberikan kekebalan lebih kuat dalam darah)."));

        panel.add(createVaccineCard("4. Vaksin DPT-HB-Hib (Pentabio)",
            "Vaksin ini adalah \"paket hemat\" yang melindungi dari 5 penyakit serius sekaligus:\n\n" +
            "D (Difteri): Bakteri yang membuat selaput tebal berwarna abu-abu di tenggorokan. Selaput ini bisa menyumbat jalan napas sehingga anak tidak bisa bernapas. Racunnya juga bisa merusak jantung.\n\n" +
            "P (Pertusis / Batuk Rejan): Dikenal sebagai \"batuk 100 hari\". Batuknya sangat keras dan panjang hingga anak sulit mengambil napas (bunyi whoop), wajah membiru, bahkan bisa menyebabkan pendarahan mata atau patah tulang rusuk akibat kerasnya batuk. Sangat mematikan bagi bayi di bawah 6 bulan.\n\n" +
            "T (Tetanus): Bakteri masuk lewat luka (bahkan luka tali pusar). Menyebabkan kejang otot yang sangat menyakitkan di seluruh tubuh. Rahang akan terkunci (tidak bisa membuka mulut/menyusu) dan tubuh kaku seperti papan.\n\n" +
            "Hib (Haemophilus influenzae tipe b): Bukan virus flu biasa. Bakteri ini adalah penyebab utama radang selaput otak (meningitis) dan pneumonia pada bayi sebelum adanya vaksin. Meningitis Hib sering meninggalkan cacat seperti tuli atau keterbelakangan mental.\n\n" +
            "Hepatitis B: (Penjelasan sama seperti poin no. 1, ini adalah dosis lanjutannya)."));
        
        panel.add(createVaccineCard("5. Vaksin PCV (Pneumokokus)",
            "Penyakit: Infeksi bakteri Pneumococcus.\n\n" +
            "Bahayanya: Bakteri ini adalah pembunuh balita nomor 1 di dunia melalui penyakit Pneumonia (radang paru-paru). Paru-paru anak akan penuh lendir/nanah sehingga sesak napas berat. Bakteri ini juga menyebabkan infeksi telinga (otitis media) yang bisa berujung pada tuli, serta infeksi darah (sepsis).\n\n" +
            "Manfaat Vaksin: Menurunkan risiko anak terkena radang paru-paru berat dan radang telinga secara signifikan."));
        
        panel.add(createVaccineCard("6. Vaksin Rotavirus",
            "Penyakit: Diare akibat Rotavirus.\n\n" +
            "Bahayanya: Berbeda dengan diare biasa, diare rotavirus menyebabkan buang air cair berkali-kali disertai muntah hebat. Bayi sangat cepat kehilangan cairan. Dehidrasi berat akibat rotavirus bisa menyebabkan syok dan kematian dalam hitungan jam jika tidak segera diinfus.\n\n" +
            "Manfaat Vaksin: Mencegah diare berat sehingga bayi tidak perlu dirawat inap di rumah sakit karena dehidrasi parah.\n\n" +
            "Cara: Diberikan dengan cara diteteskan ke mulut (bukan disuntik)."));
        
        panel.add(createVaccineCard("7. Vaksin MR (Measles & Rubella)",
            "M (Measles/Campak): Bukan sekadar ruam kulit. Campak menyebabkan demam sangat tinggi, mata merah, dan batuk. Komplikasinya mengerikan: Pneumonia (radang paru), kebutaan (karena kekurangan vitamin A saat sakit), dan radang otak (ensefalitis). Campak juga \"menghapus\" memori sistem imun anak, membuat mereka mudah sakit penyakit lain setelah sembuh.\n\n" +
            "R (Rubella/Campak Jerman): Pada anak gejalanya ringan. Tapi vaksin ini penting untuk memutus penularan ke ibu hamil. Jika ibu hamil tertular Rubella, bayi dalam kandungan bisa lahir dengan Sindrom Rubella Kongenital (Buta, Tuli, Bocor Jantung, dan Katarak)."));

        return panel;
    }

    private JPanel createVaccineCard(String title, String content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(new Color(25, 25, 112));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentArea.setForeground(new Color(0, 0, 139));
        contentArea.setBackground(Color.WHITE);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setOpaque(false);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(contentArea, BorderLayout.CENTER);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(240, 248, 255));
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(20));

        return wrapper;
    }
}
