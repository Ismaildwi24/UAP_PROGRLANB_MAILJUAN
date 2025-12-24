package ui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton showPasswordBtn;
    private MainApp app;
    private boolean passwordVisible = false;

    public LoginPanel(MainApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setBackground(new Color(176, 224, 230)); 
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Selamat Datang di Sistem Pencatatan Kesehatan Bayi");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(25, 25, 112)); 
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Aplikasi ini digunakan oleh tenaga kesehatan untuk memantau tumbuh kembang bayi."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(0, 0, 139)); 
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        // ===== MAIN CONTENT =====
        JPanel mainContent = new JPanel(new BorderLayout(30, 0));
        mainContent.setBackground(new Color(240, 248, 255));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // ===== PANEL GAMBAR =====
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(240, 248, 255));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0)); 
        
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        
        try {
            ImageIcon icon = new ImageIcon("src/assets/medicalillustration.png");
            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {      
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int maxWidth = (int) (screenSize.width * 0.45); 
                int maxHeight = (int) (screenSize.height * 0.65); 
                
                int originalWidth = icon.getIconWidth();
                int originalHeight = icon.getIconHeight();
                
                double widthRatio = (double) maxWidth / originalWidth;
                double heightRatio = (double) maxHeight / originalHeight;
                double ratio = Math.min(widthRatio, heightRatio);
                
                int newWidth = (int) (originalWidth * ratio);
                int newHeight = (int) (originalHeight * ratio);
                
                Image img = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
        }

        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // ===== PANEL FORM LOGIN (KANAN) =====
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        loginPanel.setPreferredSize(new Dimension(450, 0));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 10, 10, 10);
        gbcForm.anchor = GridBagConstraints.WEST;

        // login form Card
        JPanel loginCard = new JPanel(new BorderLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(30, 50, 40, 50) 
        ));

        JPanel loginForm = new JPanel();
        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
        loginForm.setOpaque(false);

        // title login
        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        loginTitle.setForeground(new Color(25, 25, 112));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); 

        // username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        usernameLabel.setForeground(new Color(25, 25, 112));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        usernameField.setPreferredSize(new Dimension(0, 50));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT); 
        // password field ad tombol show ama hidenya
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        passwordLabel.setForeground(new Color(25, 25, 112));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setOpaque(false);
        passwordPanel.setBorder(null);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        showPasswordBtn = new JButton("👁️");
        showPasswordBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        showPasswordBtn.setBackground(new Color(135, 206, 250));
        showPasswordBtn.setForeground(new Color(25, 25, 112));
        showPasswordBtn.setFocusPainted(false);
        showPasswordBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        showPasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPasswordBtn.setPreferredSize(new Dimension(60, 50));

        showPasswordBtn.addActionListener(e -> {
            passwordVisible = !passwordVisible;
            if (passwordVisible) {
                passwordField.setEchoChar((char) 0);
                showPasswordBtn.setText("🙈");
            } else {
                passwordField.setEchoChar('•');
                showPasswordBtn.setText("👁️");
            }
        });

        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordBtn, BorderLayout.EAST);

        // login button
        JButton loginBtn = new JButton("🔐 Login");
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginBtn.setBackground(new Color(70, 130, 180)); 
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(25, 25, 112), 2),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(100, 149, 237)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(70, 130, 180)); 
            }
        });

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            // validasi
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password harus diisi!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // login
            app.showMainApp();
        });

        passwordField.addActionListener(e -> loginBtn.doClick());
        usernameField.addActionListener(e -> passwordField.requestFocus());

        loginForm.add(loginTitle);
        loginForm.add(Box.createVerticalStrut(10));
        loginForm.add(usernameLabel);
        loginForm.add(usernameField);
        loginForm.add(Box.createVerticalStrut(5));
        loginForm.add(passwordLabel);
        loginForm.add(passwordPanel);
        loginForm.add(Box.createVerticalStrut(10)); 
        loginForm.add(loginBtn);

        loginCard.add(loginForm, BorderLayout.CENTER);

        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        gbcForm.fill = GridBagConstraints.BOTH;
        gbcForm.weightx = 1.0;
        gbcForm.weighty = 1.0;
        loginPanel.add(loginCard, gbcForm);

        // ada gambar di kiri ama form di kanan
        mainContent.add(imagePanel, BorderLayout.WEST);
        mainContent.add(loginPanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }
}
