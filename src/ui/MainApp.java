package ui;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel sidebar;
    private String currentPage = "login";
    private JButton dashButton, inputButton, historyButton, vaccineInfoButton, logoutButton;
    private HistoryPanel historyPanel;
    private DashboardPanel dashboardPanel;

    public MainApp() {
        setTitle("Sistem Pencatatan Kesehatan Bayi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255)); 
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(240, 248, 255));

        LoginPanel loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel, "login");

        dashboardPanel = new DashboardPanel(this);
        InputHealthPanel input = new InputHealthPanel(this);
        historyPanel = new HistoryPanel(this);
        VaccineInfoPanel vaccineInfo = new VaccineInfoPanel(this);

        mainPanel.add(dashboardPanel, "dashboard");
        mainPanel.add(input, "input");
        mainPanel.add(historyPanel, "history");
        mainPanel.add(vaccineInfo, "vaccineInfo");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "login");
    }

    public void showMainApp() {
        getContentPane().removeAll();
        
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        add(mainPanel, BorderLayout.CENTER);
        
        showPage("dashboard");
        
        revalidate();
        repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(70, 130, 180));
        sidebar.setPreferredSize(new Dimension(160, 0)); 
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));

        dashButton = createSlimButton("📊", "Dashboard", "dashboard");
        inputButton = createSlimButton("✏️", "Catat Kesehatan", "input");
        historyButton = createSlimButton("📋", "History", "history");
        vaccineInfoButton = createSlimButton("💉", "Informasi Vaksin", "vaccineInfo");
        logoutButton = createSlimButton("🚪", "Logout", "logout");

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(dashButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(inputButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(historyButton);
        sidebar.add(Box.createVerticalGlue()); 
        sidebar.add(vaccineInfoButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(logoutButton);

        return sidebar;
    }

    private JButton createSlimButton(String icon, String text, String page) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setBackground(new Color(135, 206, 250));
        btn.setForeground(new Color(25, 25, 112));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(130, 70));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setForeground(new Color(25, 25, 112));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setForeground(new Color(25, 25, 112));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(textLabel);

        btn.add(contentPanel, BorderLayout.CENTER);

        if (page.equals("logout")) {
            btn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin logout?", "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    logout();
                }
            });
        } else {
            btn.addActionListener(e -> showPage(page));
        }

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!currentPage.equals(page)) {
                    btn.setBackground(new Color(173, 216, 230)); 
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!currentPage.equals(page)) {
                    btn.setBackground(new Color(135, 206, 250)); 
                }
            }
        });

        return btn;
    }

    public void showPage(String page) {
        currentPage = page;
        cardLayout.show(mainPanel, page);
        
        if (sidebar != null) {
            updateSidebarHighlight();
        }
        
        if (page.equals("history") && historyPanel != null) {
            historyPanel.refreshData();
        } else if (page.equals("dashboard") && dashboardPanel != null) {
            dashboardPanel.refreshData();
        }
    }

    private void logout() {
        if (sidebar != null) {
            remove(sidebar);
            sidebar = null;
        }
        
        currentPage = "login";
        cardLayout.show(mainPanel, "login");
        
        revalidate();
        repaint();
    }
    
    public void refreshHistory() {
        if (historyPanel != null) {
            historyPanel.refreshData();
        }
    }

    private void updateSidebarHighlight() {
        resetButton(dashButton, "dashboard");
        resetButton(inputButton, "input");
        resetButton(historyButton, "history");
        resetButton(vaccineInfoButton, "vaccineInfo");
        JButton activeButton = null;
        if (currentPage.equals("dashboard")) activeButton = dashButton;
        else if (currentPage.equals("input")) activeButton = inputButton;
        else if (currentPage.equals("history")) activeButton = historyButton;
        else if (currentPage.equals("vaccineInfo")) activeButton = vaccineInfoButton;

        if (activeButton != null) {
            activeButton.setBackground(new Color(100, 149, 237)); 
            activeButton.setForeground(Color.WHITE);
            Component[] comps = ((JPanel) activeButton.getComponent(0)).getComponents();
            for (Component comp : comps) {
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setForeground(Color.WHITE);
                }
            }
        }
    }

    private void resetButton(JButton btn, String page) {
        if (!currentPage.equals(page)) {
            btn.setBackground(new Color(135, 206, 250)); 
            btn.setForeground(new Color(25, 25, 112));
            Component[] comps = ((JPanel) btn.getComponent(0)).getComponents();
            for (Component comp : comps) {
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setForeground(new Color(25, 25, 112));
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
