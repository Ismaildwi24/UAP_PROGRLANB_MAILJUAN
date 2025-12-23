package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import util.CSVHelper;

public class HistoryPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private java.util.List<String[]> originalData; 

    public HistoryPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); 

        model = new DefaultTableModel(new String[]{
                "No", "ID Bayi", "Tanggal", "Nama", "Kelamin", "Nama Ibu", "BB", "TB", "BB Ideal", "Status BB", 
                "Suhu", "Status Suhu", "Hepatitis B", "BCG", "Polio", "Pentabio", "PCV", 
                "Rotavirus", "MR", "Catatan"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabelx gbisa diedit smbarangan
            }
        };

        table = new JTable(model);
        styleTable();
        
        // sorting base on id bayi
        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>(model);
        table.setRowSorter(tableSorter);
        // kolom nomor (index 0) tidak bisa di-sort
        tableSorter.setSortable(0, false);
        tableSorter.toggleSortOrder(1); // sort by ID column 
        
        loadData();

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(240, 248, 255));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel searchLabel = new JLabel("🔍 Cari ID Bayi:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchLabel.setForeground(new Color(25, 25, 112));
        
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchField.setToolTipText("Masukkan ID bayi (contoh: M0001, F0002)");
        
        // search logika
        final TableRowSorter<DefaultTableModel> finalSorter = tableSorter;
        searchField.addActionListener(e -> searchById(searchField.getText().trim(), finalSorter));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchById(searchField.getText().trim(), finalSorter); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchById(searchField.getText().trim(), finalSorter); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchById(searchField.getText().trim(), finalSorter); }
        });
        
        JButton clearSearchBtn = new JButton("✖️ Clear");
        clearSearchBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        clearSearchBtn.setBackground(new Color(220, 20, 60));
        clearSearchBtn.setForeground(Color.WHITE);
        clearSearchBtn.setFocusPainted(false);
        clearSearchBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        clearSearchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearSearchBtn.addActionListener(e -> {
            searchField.setText("");
            finalSorter.setRowFilter(null);
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(clearSearchBtn);

        JButton edit = createStyledButton("✏️ Edit");
        JButton delete = createStyledButton("🗑️ Hapus");

        edit.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                editRecord(modelRow);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        delete.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Yakin hapus data ini?", "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(modelRow);
                    saveBack();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        // byar bisa scroll horizontal
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(edit);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(delete);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void searchById(String searchText, TableRowSorter<DefaultTableModel> sorter) {
        if (searchText == null || searchText.trim().isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        
        // filter berdasarkan ID Bayi 
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String idValue = entry.getStringValue(1).toString().toUpperCase(); // Index 1 untuk ID Bayi
                String searchUpper = searchText.toUpperCase();
                return idValue.contains(searchUpper);
            }
        };
        
        sorter.setRowFilter(filter);
    }

    private void styleTable() {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(new Color(25, 25, 112)); 
        table.setGridColor(new Color(135, 206, 250)); 
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(0, 0, 139)); 
      
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(70, 130, 180)); 
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 40));
        
        // lebarx diatur byar kliatan smuax
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

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(new Color(70, 130, 180)); 
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(25, 25, 112), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
       
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(100, 149, 237)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });
        
        return btn;
    }

    private void loadData() {
        originalData = CSVHelper.readAll();
        for (String[] d : originalData) {
            // date,babyId,name,gender,motherName,weight,height,temp,hepB,bcg,polio,pentabio,pcv,rotavirus,mr,note
            if (d.length >= 10) {
                String tanggal = formatDate(d[0]);
                String babyId = "";
                String nama = "";
                String gender = "";
                String motherName = "";
                String suhuStr;
                double berat, tinggi;
                boolean hepB, bcg, polio, pentabio, pcv, rotavirus, mr;
                String catatan;
                
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
                    if (babyId.isEmpty() && !gender.isEmpty()) {
                        babyId = generateBabyIdForExisting(gender);
                    }
                } else if (d.length >= 13) {
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
                } else if (d.length == 12) {
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
                    catatan = "";
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
        }
        
        TableRowSorter<DefaultTableModel> currentSorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        if (currentSorter != null) {
            currentSorter.toggleSortOrder(0);
        }
    }
    
    private String generateBabyIdForExisting(String gender) {
        String prefix = gender.equals("Laki-laki") ? "M" : "F";
        
        int maxNumber = 0;
        for (String[] data : originalData) {
            if (data.length >= 16 && data[1] != null && !data[1].isEmpty()) {
                String existingId = data[1].trim();
                if (existingId.startsWith(prefix) && existingId.length() > 1) {
                    try {
                        String numberStr = existingId.substring(1);
                        int number = Integer.parseInt(numberStr);
                        if (number > maxNumber) {
                            maxNumber = number;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        
        int nextNumber = maxNumber + 1;
        return String.format("%s%04d", prefix, nextNumber);
    }

  // rumus brat bdn ideal
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
            
     // klasifikasi suhux
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
    
    public void refreshData() {
        model.setRowCount(0);
        loadData();
    }

    private void editRecord(int row) {
        String babyId = model.getValueAt(row, 1).toString().trim();
        String tanggal = model.getValueAt(row, 2).toString().trim();
        String nama = model.getValueAt(row, 3).toString().trim();
        String gender = model.getValueAt(row, 4).toString().trim();
        String motherName = model.getValueAt(row, 5).toString().trim();
        String bbStr = model.getValueAt(row, 6).toString()
            .replace(" kg", "").replace("kg", "").trim();
        String tbStr = model.getValueAt(row, 7).toString()
            .replace(" cm", "").replace("cm", "").trim();
        String suhuStr = model.getValueAt(row, 10).toString()
            .replace("°C", "").replace("°", "").replace("C", "").trim();
        suhuStr = suhuStr.replace(',', '.');
        String hepBStr = model.getValueAt(row, 12).toString();
        String bcgStr = model.getValueAt(row, 13).toString();
        String polioStr = model.getValueAt(row, 14).toString();
        String pentabioStr = model.getValueAt(row, 15).toString();
        String pcvStr = model.getValueAt(row, 16).toString();
        String rotavirusStr = model.getValueAt(row, 17).toString();
        String mrStr = model.getValueAt(row, 18).toString();
        String catatan = model.getValueAt(row, 19).toString();
        if (catatan.equals("false")) {
            catatan = "";
        }
        
        // dialogx
        JDialog editDialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Edit Data Kesehatan Bayi", true);
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(650, 750);
        editDialog.setLocationRelativeTo(this);
        editDialog.getContentPane().setBackground(new Color(240, 248, 255));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel headerLabel = new JLabel("✏️ Edit Data - ID: " + babyId);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        // Main form panel dengan scroll
        JPanel mainFormPanel = new JPanel();
        mainFormPanel.setLayout(new BoxLayout(mainFormPanel, BoxLayout.Y_AXIS));
        mainFormPanel.setBackground(new Color(240, 248, 255));
        mainFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Data Dasar Card
        JPanel basicCard = createEditCard("Data Dasar");
        JPanel basicForm = new JPanel(new GridLayout(0, 2, 15, 15));
        basicForm.setOpaque(false);
        basicForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField namaField = createEditTextField(nama);
        JTextField motherNameField = createEditTextField(motherName);
        JRadioButton lakiLaki = new JRadioButton("Laki-laki", gender.equals("Laki-laki"));
        JRadioButton perempuan = new JRadioButton("Perempuan", gender.equals("Perempuan"));
        lakiLaki.setFont(new Font("SansSerif", Font.PLAIN, 14));
        perempuan.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lakiLaki.setForeground(new Color(0, 0, 139));
        perempuan.setForeground(new Color(0, 0, 139));
        lakiLaki.setBackground(Color.WHITE);
        perempuan.setBackground(Color.WHITE);
        lakiLaki.setFocusPainted(false);
        perempuan.setFocusPainted(false);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiLaki);
        genderGroup.add(perempuan);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        genderPanel.setOpaque(false);
        genderPanel.add(lakiLaki);
        genderPanel.add(perempuan);
        
        JTextField bbField = createEditTextField(bbStr);
        JTextField tbField = createEditTextField(tbStr);
        JTextField suhuField = createEditTextField(suhuStr);
        
        basicForm.add(createEditLabel("Nama Bayi:"));
        basicForm.add(namaField);
        basicForm.add(createEditLabel("Kelamin:"));
        basicForm.add(genderPanel);
        basicForm.add(createEditLabel("Nama Ibu:"));
        basicForm.add(motherNameField);
        basicForm.add(createEditLabel("Berat (kg):"));
        basicForm.add(bbField);
        basicForm.add(createEditLabel("Tinggi (cm):"));
        basicForm.add(tbField);
        basicForm.add(createEditLabel("Suhu (°C):"));
        basicForm.add(suhuField);
        
        basicCard.add(basicForm, BorderLayout.CENTER);
        
        // vaksinasi Card
        JPanel vaccineCard = createEditCard("Status Vaksinasi");
        JPanel vaccineForm = new JPanel(new GridLayout(0, 2, 15, 15)); 
        vaccineForm.setOpaque(false);
        vaccineForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JCheckBox hepBCheck = createEditCheckbox("Hepatitis B", hepBStr.equals("✓"));
        JCheckBox bcgCheck = createEditCheckbox("BCG", bcgStr.equals("✓"));
        JCheckBox polioCheck = createEditCheckbox("Polio", polioStr.equals("✓"));
        JCheckBox pentabioCheck = createEditCheckbox("Pentabio", pentabioStr.equals("✓"));
        JCheckBox pcvCheck = createEditCheckbox("PCV", pcvStr.equals("✓"));
        JCheckBox rotavirusCheck = createEditCheckbox("Rotavirus", rotavirusStr.equals("✓"));
        JCheckBox mrCheck = createEditCheckbox("MR", mrStr.equals("✓"));
        
        vaccineForm.add(hepBCheck);
        vaccineForm.add(bcgCheck);
        vaccineForm.add(polioCheck);
        vaccineForm.add(pentabioCheck);
        vaccineForm.add(pcvCheck);
        vaccineForm.add(rotavirusCheck);
        vaccineForm.add(mrCheck);
        vaccineCard.add(vaccineForm, BorderLayout.CENTER);
        
        JPanel noteCard = createEditCard("Catatan Tambahan");
        JPanel noteForm = new JPanel(new BorderLayout());
        noteForm.setOpaque(false);
        noteForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea catatanArea = new JTextArea(catatan, 4, 30);
        catatanArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        catatanArea.setLineWrap(true);
        catatanArea.setWrapStyleWord(true);
        catatanArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane noteScroll = new JScrollPane(catatanArea);
        noteScroll.setBorder(null);
        noteForm.add(noteScroll, BorderLayout.CENTER);
        
        noteCard.add(noteForm, BorderLayout.CENTER);
        
        mainFormPanel.add(basicCard);
        mainFormPanel.add(Box.createVerticalStrut(15));
        mainFormPanel.add(vaccineCard);
        mainFormPanel.add(Box.createVerticalStrut(15));
        mainFormPanel.add(noteCard);
        
        JScrollPane formScroll = new JScrollPane(mainFormPanel);
        formScroll.setBorder(null);
        formScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JButton saveBtn = new JButton("💾 Simpan");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveBtn.setBackground(new Color(70, 130, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton cancelBtn = new JButton("❌ Batal");
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(220, 20, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        saveBtn.addActionListener(e -> {
            try {
                String namaInput = namaField.getText().trim();
                String bbInput = bbField.getText().trim().replace(',', '.');
                String tbInput = tbField.getText().trim().replace(',', '.');
                String suhuInput = suhuField.getText().trim().replace(',', '.');
                
                util.Validator.validateInput(
                    namaInput, 
                    bbInput, 
                    tbInput, 
                    suhuInput
                );
                
                String babyIdKey = babyId;
                for (int i = 0; i < originalData.size(); i++) {
                    String[] orig = originalData.get(i);
                    if (orig.length > 0) {
                        boolean match = false;
                        if (orig.length >= 16 && orig[1] != null && orig[1].equals(babyIdKey)) {
                            match = true;
                        } else if (orig.length < 16) {
                            String origDate = formatDate(orig[0]);
                            if (origDate.equals(tanggal)) {
                                match = true;
                            }
                        }
                        
                        if (match) {
                            if (orig.length < 16) {
                                String[] newOrig = new String[16];
                                System.arraycopy(orig, 0, newOrig, 0, Math.min(orig.length, newOrig.length));
                                if (newOrig[1] == null || newOrig[1].isEmpty()) {
                                    newOrig[1] = babyIdKey; 
                                }
                                // yg kosong diisi default
                                if (newOrig[3] == null || newOrig[3].isEmpty()) newOrig[3] = "";
                                if (newOrig[4] == null || newOrig[4].isEmpty()) newOrig[4] = "";
                                orig = newOrig;
                                originalData.set(i, orig);
                            }
                            
                            String genderValue = lakiLaki.isSelected() ? "Laki-laki" : (perempuan.isSelected() ? "Perempuan" : "");
                            
                            orig[1] = babyIdKey; 
                            orig[2] = namaInput;
                            orig[3] = genderValue;
                            orig[4] = motherNameField.getText().trim();
                            orig[5] = bbInput;
                            orig[6] = tbInput;
                            orig[7] = suhuInput;
                            orig[8] = String.valueOf(hepBCheck.isSelected());
                            orig[9] = String.valueOf(bcgCheck.isSelected());
                            orig[10] = String.valueOf(polioCheck.isSelected());
                            orig[11] = String.valueOf(pentabioCheck.isSelected());
                            orig[12] = String.valueOf(pcvCheck.isSelected());
                            orig[13] = String.valueOf(rotavirusCheck.isSelected());
                            orig[14] = String.valueOf(mrCheck.isSelected());
                            orig[15] = catatanArea.getText();
                            break;
                        }
                    }
                }
                
                // save trs refresh
                saveAllData();
                refreshData();
                editDialog.dispose();
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, ex.getMessage(), "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> editDialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelBtn);
        
        editDialog.add(headerPanel, BorderLayout.NORTH);
        editDialog.add(formScroll, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }
    
    private JPanel createEditCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        
        JPanel cardHeader = new JPanel();
        cardHeader.setLayout(new BoxLayout(cardHeader, BoxLayout.X_AXIS));
        cardHeader.setBackground(new Color(176, 224, 230));
        cardHeader.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        JLabel cardTitle = new JLabel(title);
        cardTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        cardTitle.setForeground(new Color(25, 25, 112));
        
        cardHeader.add(cardTitle);
        cardHeader.add(Box.createHorizontalGlue());
        
        card.add(cardHeader, BorderLayout.NORTH);
        return card;
    }
    
    private JLabel createEditLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(25, 25, 112));
        return label;
    }
    
    private JTextField createEditTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JCheckBox createEditCheckbox(String text, boolean selected) {
        JCheckBox checkbox = new JCheckBox(text, selected);
        checkbox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkbox.setForeground(new Color(0, 0, 139));
        checkbox.setBackground(Color.WHITE);
        checkbox.setOpaque(false); 
        checkbox.setFocusPainted(false);
        checkbox.setBorderPainted(false); 
        return checkbox;
    }
    
    private void saveAllData() {
        java.util.List<String> newData = new java.util.ArrayList<>();
        for (String[] d : originalData) {
            if (d.length >= 10) {
                String tanggal = formatDate(d[0]);
                if (d.length >= 16) {
                    String babyIdValue = (d.length > 1 && d[1] != null) ? d[1] : "";
                    String namaValue = (d.length > 2 && d[2] != null) ? d[2] : "";
                    String genderValue = (d.length > 3 && d[3] != null) ? d[3] : "";
                    String motherValue = (d.length > 4 && d[4] != null) ? d[4] : "";
                    String catatanValue = (d.length > 15 && d[15] != null && !d[15].equals("false")) ? d[15] : "";
                    newData.add(
                        tanggal + "," + babyIdValue + "," + namaValue + "," + genderValue + "," + motherValue + "," +
                        d[5] + "," + d[6] + "," + d[7] + "," + d[8] + "," + d[9] + "," +
                        d[10] + "," + d[11] + "," + d[12] + "," + d[13] + "," + d[14] + "," + catatanValue
                    );
                } else if (d.length >= 15) {
                    String genderValue = (d.length > 2 && d[2] != null) ? d[2] : "";
                    String motherValue = (d.length > 3 && d[3] != null) ? d[3] : "";
                    String catatanValue = (d.length > 14 && d[14] != null && !d[14].equals("false")) ? d[14] : "";
                    String babyIdValue = "";
                    if (!genderValue.isEmpty()) {
                        babyIdValue = generateBabyIdForExisting(genderValue);
                    }
                    newData.add(
                        tanggal + "," + babyIdValue + "," + d[1] + "," + genderValue + "," + motherValue + "," +
                        d[4] + "," + d[5] + "," + d[6] + "," + d[7] + "," + d[8] + "," +
                        d[9] + "," + d[10] + "," + d[11] + "," + d[12] + "," + d[13] + "," + catatanValue
                    );
                } else if (d.length >= 13) {
                    String catatanValue = (d.length > 12 && d[12] != null && !d[12].equals("false")) ? d[12] : "";
                    newData.add(
                        tanggal + ",," + d[1] + ",," + "," + 
                        d[2] + "," + d[3] + "," + d[4] + "," + d[5] + "," + d[6] + "," +
                        d[7] + "," + d[8] + "," + d[9] + "," + d[10] + "," + d[11] + "," + catatanValue
                    );
                } else {
                    newData.add(
                        tanggal + ",," + d[1] + ",," + "," + 
                        d[2] + "," + d[3] + "," + d[4] + "," + d[5] + ",false,false,false,false,false," +
                        (d.length > 6 ? d[6] : "false") + "," +
                        (d.length > 9 ? d[9] : "")
                    );
                }
            }
        }
        util.CSVHelper.overwrite(newData);
    }

    private void saveBack() {
        java.util.List<String> newData = new java.util.ArrayList<>();
        java.util.List<String[]> remainingData = new java.util.ArrayList<>();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String babyId = model.getValueAt(i, 1).toString(); 
            for (String[] orig : originalData) {
                if (orig.length > 0) {
                    boolean match = false;
                    if (orig.length >= 16 && orig[1] != null && orig[1].equals(babyId)) {
                        match = true;
                    } else if (orig.length < 16) {
                  
                        String tanggal = model.getValueAt(i, 2).toString(); 
                        String origDate = formatDate(orig[0]);
                        if (origDate.equals(tanggal)) {
                            match = true;
                        }
                    }
                    if (match) {
                        remainingData.add(orig);
                        break;
                    }
                }
            }
        }
        
        // convert ke CSV format
        for (String[] d : remainingData) {
            if (d.length >= 10) {
                // Format tanggal ke dd-mm-yyyy jika belum
                String tanggal = formatDate(d[0]);
                if (d.length >= 16) {
                    String babyIdValue = (d.length > 1 && d[1] != null) ? d[1] : "";
                    String namaValue = (d.length > 2 && d[2] != null) ? d[2] : "";
                    String genderValue = (d.length > 3 && d[3] != null) ? d[3] : "";
                    String motherValue = (d.length > 4 && d[4] != null) ? d[4] : "";
                    String catatanValue = (d.length > 15 && d[15] != null && !d[15].equals("false")) ? d[15] : "";
                    newData.add(
                        tanggal + "," + babyIdValue + "," + namaValue + "," + genderValue + "," + motherValue + "," +
                        d[5] + "," + d[6] + "," + d[7] + "," + d[8] + "," + d[9] + "," +
                        d[10] + "," + d[11] + "," + d[12] + "," + d[13] + "," + d[14] + "," + catatanValue
                    );
                } else if (d.length >= 15) {
                    String genderValue = (d.length > 2 && d[2] != null) ? d[2] : "";
                    String motherValue = (d.length > 3 && d[3] != null) ? d[3] : "";
                    String catatanValue = (d.length > 14 && d[14] != null && !d[14].equals("false")) ? d[14] : "";
                    String babyIdValue = "";
                    if (!genderValue.isEmpty()) {
                        babyIdValue = generateBabyIdForExisting(genderValue);
                    }
                    newData.add(
                        tanggal + "," + babyIdValue + "," + d[1] + "," + genderValue + "," + motherValue + "," +
                        d[4] + "," + d[5] + "," + d[6] + "," + d[7] + "," + d[8] + "," +
                        d[9] + "," + d[10] + "," + d[11] + "," + d[12] + "," + d[13] + "," + catatanValue
                    );
                } else if (d.length >= 13) {
                   
                    String catatanValue = (d.length > 12 && d[12] != null && !d[12].equals("false")) ? d[12] : "";
                    newData.add(
                        tanggal + ",," + d[1] + ",," + "," + 
                        d[2] + "," + d[3] + "," + d[4] + "," + d[5] + "," + d[6] + "," +
                        d[7] + "," + d[8] + "," + d[9] + "," + d[10] + "," + d[11] + "," + catatanValue
                    );
                } else {
            newData.add(
                        tanggal + ",," + d[1] + ",," + "," + 
                        d[2] + "," + d[3] + "," + d[4] + "," + d[5] + ",false,false,false,false,false," +
                        (d.length > 6 ? d[6] : "false") + "," +
                        (d.length > 9 ? d[9] : "")
                    );
                }
            }
        }
        
        CSVHelper.overwrite(newData);
        refreshData();
    }
}
