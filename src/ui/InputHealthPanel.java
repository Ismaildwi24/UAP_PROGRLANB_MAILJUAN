package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.HealthRecord;
import util.*;

public class InputHealthPanel extends JPanel {

    private MainApp app;

    public InputHealthPanel(MainApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); 

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(176, 224, 230)); 
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel headerTitle = new JLabel("Catat Data Kesehatan Bayi");
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerTitle.setForeground(new Color(25, 25, 112)); 
        headerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headerSubtitle = new JLabel("Isi form di bawah untuk mencatat data kesehatan");
        headerSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        headerSubtitle.setForeground(new Color(0, 0, 139)); 
        headerSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(headerTitle);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(headerSubtitle);


        JPanel mainFormPanel = new JPanel();
        mainFormPanel.setLayout(new BoxLayout(mainFormPanel, BoxLayout.Y_AXIS));
        mainFormPanel.setBackground(new Color(240, 248, 255));
        mainFormPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel basicDataCard = createCard("Data Dasar");
        JPanel basicDataForm = new JPanel(new GridLayout(0, 2, 25, 25));
        basicDataForm.setOpaque(false);
        basicDataForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField baby = createStyledTextField();
        JTextField motherName = createStyledTextField();
        JTextField weight = createStyledTextField();
        JTextField height = createStyledTextField();
        JTextField temp = createStyledTextField();

        JRadioButton lakiLaki = new JRadioButton("Laki-laki");
        JRadioButton perempuan = new JRadioButton("Perempuan");
        lakiLaki.setFont(new Font("SansSerif", Font.PLAIN, 15));
        perempuan.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lakiLaki.setForeground(new Color(0, 0, 139));
        perempuan.setForeground(new Color(0, 0, 139));
        lakiLaki.setBackground(Color.WHITE);
        perempuan.setBackground(Color.WHITE);
        lakiLaki.setFocusPainted(false);
        perempuan.setFocusPainted(false);
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiLaki);
        genderGroup.add(perempuan);
        
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setOpaque(false);
        genderPanel.add(lakiLaki);
        genderPanel.add(perempuan);

        basicDataForm.add(createStyledLabel("Nama Bayi"));
        basicDataForm.add(baby);
        basicDataForm.add(createStyledLabel("Kelamin"));
        basicDataForm.add(genderPanel);
        basicDataForm.add(createStyledLabel("Nama Ibu"));
        basicDataForm.add(motherName);
        basicDataForm.add(createStyledLabel("Berat (kg)"));
        basicDataForm.add(weight);
        basicDataForm.add(createStyledLabel("Tinggi (cm)"));
        basicDataForm.add(height);
        
        JLabel tempLabel = createStyledLabel("Suhu (°C)");
        JPanel tempPanel = new JPanel(new BorderLayout());
        tempPanel.setOpaque(false);
        tempPanel.add(temp, BorderLayout.CENTER);
        JLabel tempHint = new JLabel("  Contoh: 36.5 atau 36,5 (akan diklasifikasikan di history)");
        tempHint.setFont(new Font("SansSerif", Font.ITALIC, 12));
        tempHint.setForeground(new Color(100, 100, 100));
        tempPanel.add(tempHint, BorderLayout.SOUTH);
        
        basicDataForm.add(tempLabel);
        basicDataForm.add(tempPanel);

        basicDataCard.add(basicDataForm, BorderLayout.CENTER);

        JPanel vaccineCard = createCard("Status Vaksinasi");
        JPanel vaccinePanel = new JPanel(new GridLayout(0, 2, 20, 20)); 
        vaccinePanel.setOpaque(false);
        vaccinePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JCheckBox hepB = createStyledCheckbox("Hepatitis B");
        JCheckBox bcg = createStyledCheckbox("BCG");
        JCheckBox polio = createStyledCheckbox("Polio");
        JCheckBox pentabio = createStyledCheckbox("Pentabio");
        JCheckBox pcv = createStyledCheckbox("PCV");
        JCheckBox rotavirus = createStyledCheckbox("Rotavirus");
        JCheckBox mr = createStyledCheckbox("MR");

        vaccinePanel.add(hepB);
        vaccinePanel.add(bcg);
        vaccinePanel.add(polio);
        vaccinePanel.add(pentabio);
        vaccinePanel.add(pcv);
        vaccinePanel.add(rotavirus);
        vaccinePanel.add(mr);

        vaccineCard.add(vaccinePanel, BorderLayout.CENTER);

        JPanel noteCard = createCard("Catatan Tambahan");
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setOpaque(false);
        notePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea note = createStyledTextArea();
        JScrollPane noteScroll = new JScrollPane(note);
        noteScroll.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 250), 2));
        noteScroll.setBackground(Color.WHITE);
        notePanel.add(noteScroll, BorderLayout.CENTER);

        noteCard.add(notePanel, BorderLayout.CENTER);

        mainFormPanel.add(basicDataCard);
        mainFormPanel.add(Box.createVerticalStrut(30));
        mainFormPanel.add(vaccineCard);
        mainFormPanel.add(Box.createVerticalStrut(30));
        mainFormPanel.add(noteCard);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton save = createStyledButton("💾 Simpan Data");
        save.addActionListener(e -> {
            try {
                Validator.validateInput(baby.getText(), weight.getText(), height.getText(), temp.getText());
                
                String catatanText = note.getText() != null ? note.getText() : "";
                
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin simpan data?", "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (!lakiLaki.isSelected() && !perempuan.isSelected()) {
                        JOptionPane.showMessageDialog(this, "Pilih kelamin bayi terlebih dahulu", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDate = LocalDate.now().format(formatter);
                    
                    String gender = lakiLaki.isSelected() ? "Laki-laki" : "Perempuan";
                    String motherNameText = motherName.getText().trim();
                    
                    String babyId = generateBabyId(gender);
                    
                    HealthRecord r = new HealthRecord(
                            formattedDate,
                            babyId,
                            baby.getText(),
                            gender,
                            motherNameText,
                            Double.parseDouble(weight.getText()),
                            Double.parseDouble(height.getText()),
                            Double.parseDouble(temp.getText()),
                            hepB.isSelected(), bcg.isSelected(),
                            polio.isSelected(), pentabio.isSelected(),
                            pcv.isSelected(), rotavirus.isSelected(),
                            mr.isSelected(),
                            catatanText
                    );
                    CSVHelper.save(r.toCSV());
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Sukses", 
                        JOptionPane.INFORMATION_MESSAGE);
                    

                    app.refreshHistory();
                    
                    baby.setText("");
                    motherName.setText("");
                    weight.setText("");
                    height.setText("");
                    temp.setText("");
                    note.setText("");
                    genderGroup.clearSelection();
                    hepB.setSelected(false);
                    bcg.setSelected(false);
                    polio.setSelected(false);
                    pentabio.setSelected(false);
                    pcv.setSelected(false);
                    rotavirus.setSelected(false);
                    mr.setSelected(false);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(save);

        JScrollPane scrollPane = new JScrollPane(mainFormPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel cardHeader = new JPanel();
        cardHeader.setLayout(new BoxLayout(cardHeader, BoxLayout.X_AXIS));
        cardHeader.setBackground(new Color(176, 224, 230)); 
        cardHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel cardTitle = new JLabel(title);
        cardTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardTitle.setForeground(new Color(25, 25, 112)); 

        cardHeader.add(cardTitle);
        cardHeader.add(Box.createHorizontalGlue());

        card.add(cardHeader, BorderLayout.NORTH);

        return card;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        field.setBackground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 40));
        return field;
    }

    private JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea(4, 25);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        area.setBackground(Color.WHITE);
        area.setFont(new Font("SansSerif", Font.PLAIN, 15));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setForeground(new Color(25, 25, 112)); 
        return label;
    }

    private JCheckBox createStyledCheckbox(String text) {
        JCheckBox checkbox = new JCheckBox(text);
        checkbox.setFont(new Font("SansSerif", Font.PLAIN, 18)); 
        checkbox.setForeground(new Color(0, 0, 139)); 
        checkbox.setBackground(Color.WHITE);
        checkbox.setOpaque(false); 
        checkbox.setFocusPainted(false);
        checkbox.setBorderPainted(false); 
        checkbox.setPreferredSize(new Dimension(checkbox.getPreferredSize().width, 35));
        checkbox.setIconTextGap(10); 
        return checkbox;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(new Color(70, 130, 180)); 
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(25, 25, 112), 2),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, 50));
        
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
    
    private String generateBabyId(String gender) {
        String prefix = gender.equals("Laki-laki") ? "M" : "F";
        
        java.util.List<String[]> allData = util.CSVHelper.readAll();
        int maxNumber = 0;
        
        for (String[] data : allData) {
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
}
