package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import util.CSVHelper;

public class DashboardPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public DashboardPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); 

        // ===== HEADER SECTION =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(176, 224, 230)); 
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Selamat Datang, Nakes!");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(25, 25, 112));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Selamat Bekerja");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(0, 0, 139)); 
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitle);

        // ===== TABLE SECTION =====
        model = new DefaultTableModel(new String[]{
                "No", "ID Bayi", "Tanggal", "Nama", "Kelamin", "Nama Ibu", "BB", "TB", "BB Ideal", "Status BB", 
                "Suhu", "Status Suhu", "Hepatitis B", "BCG", "Polio", "Pentabio", "PCV", 
                "Rotavirus", "MR", "Catatan"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        styleTable();
        
        loadRecentData();

        // 10 baris tabel udh header jg
        int rowHeight = table.getRowHeight();
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int maxTableHeight = (10 * rowHeight) + headerHeight + 5;
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, maxTableHeight));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // ===== MAIN CONTENT PANEL =====
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel tableTitle = new JLabel("10 Data Bayi Terbaru");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        tableTitle.setForeground(new Color(25, 25, 112));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        contentPanel.add(tableTitle, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void styleTable() {
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(173, 216, 230)); 
        table.setSelectionForeground(new Color(25, 25, 112)); 
        table.setGridColor(new Color(135, 206, 250)); 
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(0, 0, 139)); 
        
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180)); 
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 40));
        
        // lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // No
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // ID Bayi
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Tanggal
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Nama
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Kelamin
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Nama Ibu
        table.getColumnModel().getColumn(6).setPreferredWidth(100); // BB
        table.getColumnModel().getColumn(7).setPreferredWidth(100); // TB
        table.getColumnModel().getColumn(8).setPreferredWidth(110); // BB Ideal
        table.getColumnModel().getColumn(9).setPreferredWidth(100); // Status BB
        table.getColumnModel().getColumn(10).setPreferredWidth(100); // Suhu
        table.getColumnModel().getColumn(11).setPreferredWidth(120); // Status Suhu
        table.getColumnModel().getColumn(12).setPreferredWidth(90); // Hepatitis B
        table.getColumnModel().getColumn(13).setPreferredWidth(80); // BCG
        table.getColumnModel().getColumn(14).setPreferredWidth(80); // Polio
        table.getColumnModel().getColumn(15).setPreferredWidth(90); // Pentabio
        table.getColumnModel().getColumn(16).setPreferredWidth(80); // PCV
        table.getColumnModel().getColumn(17).setPreferredWidth(90); // Rotavirus
        table.getColumnModel().getColumn(18).setPreferredWidth(80); // MR
        table.getColumnModel().getColumn(19).setPreferredWidth(200); // Catatan
        
       
        table.getColumnModel().getColumn(0).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                ((JLabel) c).setText(String.valueOf(row + 1));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
    }

    private void loadRecentData() {
        java.util.List<String[]> allData = CSVHelper.readAll();
        
        // ngambil maks 10 data terbaru
        int startIndex = Math.max(0, allData.size() - 10);
        
        for (int i = startIndex; i < allData.size(); i++) {
            String[] d = allData.get(i);
            if (d.length >= 10) {
                addRowToTable(d);
            }
        }
    }

    private void addRowToTable(String[] d) {
        String tanggal = formatDate(d[0]);
        String babyId = "";
        String nama = "";
        String gender = "";
        String motherName = "";
        String suhuStr;
        double berat, tinggi;
        boolean hepB, bcg, polio, pentabio, pcv, rotavirus, mr;
        String catatan;
        
        // Deteksi format berdasarkan jumlah field
        if (d.length >= 16) {
            babyId = (d.length > 1 && d[1] != null) ? d[1] : "";
            nama = (d.length > 2 && d[2] != null) ? d[2] : "";
            gender = (d.length > 3 && d[3] != null) ? d[3] : "";
            motherName = (d.length > 4 && d[4] != null) ? d[4] : "";
            suhuStr = d[7];
            berat = Double.parseDouble(d[5]);
            tinggi = Double.parseDouble(d[6]);
            hepB = parseBooleanSafe(d[8]);
            bcg = parseBooleanSafe(d[9]);
            polio = parseBooleanSafe(d[10]);
            pentabio = parseBooleanSafe(d[11]);
            pcv = parseBooleanSafe(d[12]);
            rotavirus = parseBooleanSafe(d[13]);
            mr = parseBooleanSafe(d[14]);
            catatan = (d.length > 15 && d[15] != null && !d[15].equals("false")) ? d[15] : "";
        } else if (d.length >= 15) {
            // Format dengan gender dan motherName tanpa ID
            babyId = "";
            nama = d[1];
            gender = (d.length > 2 && d[2] != null) ? d[2] : "";
            motherName = (d.length > 3 && d[3] != null) ? d[3] : "";
            suhuStr = d[6];
            berat = Double.parseDouble(d[4]);
            tinggi = Double.parseDouble(d[5]);
            hepB = parseBooleanSafe(d[7]);
            bcg = parseBooleanSafe(d[8]);
            polio = parseBooleanSafe(d[9]);
            pentabio = parseBooleanSafe(d[10]);
            pcv = parseBooleanSafe(d[11]);
            rotavirus = parseBooleanSafe(d[12]);
            mr = parseBooleanSafe(d[13]);
            catatan = (d.length > 14 && d[14] != null && !d[14].equals("false")) ? d[14] : "";
        } else if (d.length >= 13) {
            //gada gender, motherName, dan ID
            babyId = "";
            nama = d[1];
            gender = "";
            motherName = "";
            suhuStr = d[4];
            berat = Double.parseDouble(d[2]);
            tinggi = Double.parseDouble(d[3]);
            hepB = parseBooleanSafe(d[5]);
            bcg = parseBooleanSafe(d[6]);
            polio = parseBooleanSafe(d[7]);
            pentabio = parseBooleanSafe(d[8]);
            pcv = parseBooleanSafe(d[9]);
            rotavirus = parseBooleanSafe(d[10]);
            mr = parseBooleanSafe(d[11]);
            catatan = (d.length > 12 && d[12] != null && !d[12].equals("false")) ? d[12] : "";
        } else {
            babyId = "";
            nama = d[1];
            gender = "";
            motherName = "";
            suhuStr = d[4];
            berat = Double.parseDouble(d[2]);
            tinggi = Double.parseDouble(d[3]);
            hepB = parseBooleanSafe(d[5]);
            bcg = false;
            polio = false;
            pentabio = false;
            pcv = false;
            rotavirus = false;
            mr = d.length > 6 ? parseBooleanSafe(d[6]) : false;
            catatan = d.length > 9 ? d[9] : "";
            if (catatan.equals("false")) {
                catatan = "";
            }
        }
        
        String statusSuhu = classifyTemperature(suhuStr);
        double bbIdeal = calculateIdealWeight(tinggi);
        String statusBB = classifyWeightStatus(berat, bbIdeal);
        String catatanDisplay = (catatan == null || catatan.equals("false") || catatan.trim().isEmpty()) ? "" : catatan;
    
        model.addRow(new Object[]{
                        "", 
                        babyId, tanggal, nama, gender, motherName,
                        String.format("%.1f", berat) + " kg", 
                        String.format("%.1f", tinggi) + " cm",
                        String.format("%.1f", bbIdeal) + " kg",
                        statusBB,
                        suhuStr + "°C", 
                        statusSuhu,
                        hepB ? "✓" : "✗",
                        bcg ? "✓" : "✗",
                        polio ? "✓" : "✗",
                        pentabio ? "✓" : "✗",
                        pcv ? "✓" : "✗",
                        rotavirus ? "✓" : "✗",
                        mr ? "✓" : "✗",
                        catatanDisplay
                });
    }

    private String formatDate(String dateStr) {
        try {
            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return date.format(formatter);
            }
            return dateStr;
        } catch (Exception e) {
            return dateStr;
        }
    }
    
    private boolean parseBooleanSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            return Boolean.parseBoolean(value.trim());
        } catch (Exception e) {
            return false;
        }
    }
    
    private String classifyTemperature(String suhuStr) {
        try {
            String tempStr = suhuStr.replace(',', '.');
            double temp = Double.parseDouble(tempStr);
            
            if (temp >= 36.5 && temp <= 37.5) {
                return "Normal";
            } else if (temp > 37.5 && temp <= 38.5) {
                return "Demam Ringan";
            } else if (temp > 38.5 && temp <= 39.5) {
                return "Demam";
            } else if (temp > 39.5) {
                return "Demam Tinggi";
            } else {
                return "Hipotermia";
            }
        } catch (Exception e) {
            return "Tidak Valid";
        }
    }

    private double calculateIdealWeight(double height) {
        if (height < 60) {
            return Math.max(2.0, (height / 2.0) - 2.0);
        } else if (height < 100) {
            double result = (height - 100) * 0.9;
            if (result <= 0) {
                return height * 0.4;
            }
            return result;
        } else {
            return (height - 100) * 0.9;
        }
    }
    
    private String classifyWeightStatus(double actualWeight, double idealWeight) {
        double minWeight = idealWeight * 0.9;
        double maxWeight = idealWeight * 1.1;
        
        if (actualWeight >= minWeight && actualWeight <= maxWeight) {
            return "Ideal";
        } else if (actualWeight < minWeight) {
            return "Kurang";
        } else {
            return "Berlebih";
        }
    }

    public void refreshData() {
        model.setRowCount(0);
        loadRecentData();
    }
}
