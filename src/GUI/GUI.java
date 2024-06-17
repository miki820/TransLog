package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GUI extends JFrame {
    private static JButton lastSelectedButton = null;
    public GUI() {
        setTitle("TransLog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(854, 480);

        // Utworzenie podziału okna
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(0);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Text Fields
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel textFields = new JPanel(new GridBagLayout());
        textFields.setBackground(Color.WHITE);
        textFields.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridwidth = 1;
        gbc.gridy++;
        RoundedTextField punktKoncowyField = createPlaceholderTextField("Punkt końcowy");
        punktKoncowyField.setBackground(Color.WHITE);
        textFields.add(punktKoncowyField, gbc);

        gbc.gridy++;
        RoundedTextField punktStartowyField = createPlaceholderTextField("Punkt startowy");
        punktStartowyField.setBackground(Color.WHITE);
        textFields.add(punktStartowyField, gbc);

        gbc.gridy++;
        RoundedTextField przewozonyTowarField = createPlaceholderTextField("Przewożony towar");
        przewozonyTowarField.setBackground(Color.WHITE);
        textFields.add(przewozonyTowarField, gbc);

        JButton dodajButton = new JButton("Dodaj");
        dodajButton.setFont(new Font("Arial", Font.PLAIN, 18));
        dodajButton.setBackground(new Color(30, 144, 255)); // Blue color
        dodajButton.setForeground(Color.WHITE);
        dodajButton.setFocusPainted(false);
        dodajButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);

        // Panel w środku
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleOfContentPage = new JLabel("Planowanie Transportu");
        titleOfContentPage.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(titleOfContentPage, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(textFields, gbc);
        contentPanel.add(dodajButton, gbc);


        // Panel boczny (sidebar)
        Image planningImage = new ImageIcon("src/images/plan_icon.png").getImage();
        Icon planningIcon = new ImageIcon(planningImage);
        Image showImage = new ImageIcon("src/images/show_icon.png").getImage();
        Icon showIcon = new ImageIcon(showImage);
        Image analyzeImage = new ImageIcon("src/images/analyze_icon.png").getImage();
        Icon analyzeIcon = new ImageIcon(analyzeImage);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(Color.WHITE);
        splitPane.setLeftComponent(sidebar);

        JLabel titleOfSideBar = new JLabel("TransLog");
        titleOfSideBar.setFont(new Font("Arial", Font.BOLD, 30));
        titleOfSideBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton planning = createToggleButton("Planowanie");
        planning.setFont(new Font("Arial", Font.BOLD, 20));
        planning.setAlignmentX(Component.CENTER_ALIGNMENT);
        planning.setIcon(planningIcon);

        JButton show = createToggleButton("Przegląd");
        show.setFont(new Font("Arial", Font.BOLD, 20));
        show.setAlignmentX(Component.CENTER_ALIGNMENT);
        show.setIcon(showIcon);
        show.setIconTextGap(22);

        JButton analyze = createToggleButton("Analiza");
        analyze.setFont(new Font("Arial", Font.BOLD, 20));
        analyze.setAlignmentX(Component.CENTER_ALIGNMENT);
        analyze.setIcon(analyzeIcon);
        analyze.setIconTextGap(34);

        sidebar.add(titleOfSideBar);
        sidebar.add(Box.createVerticalStrut(50));
        sidebar.add(planning);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(show);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(analyze);

        Border margin = BorderFactory.createEmptyBorder(30, 0, 0, 0);
        Border blueLine = BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 157, 250));
        sidebar.setBorder(BorderFactory.createCompoundBorder(blueLine, margin));

        // Główna część aplikacji
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridBagLayout());
        splitPane.setRightComponent(mainPanel);

        // Ustawienie podziału
        splitPane.setDividerLocation(200);

        //Setting component
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(contentPanel, gbc);

    }

    private static JButton createToggleButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastSelectedButton != null) {
                    lastSelectedButton.setOpaque(false);
                    lastSelectedButton.setForeground(Color.BLACK);
                }
                button.setOpaque(true);
                button.setBackground(new Color(223, 234, 255));
                button.setForeground(Color.BLACK);
                lastSelectedButton = button;
            }
        });
        return button;
    }

    private static RoundedTextField createPlaceholderTextField(String placeholder) {
        RoundedTextField textField = new RoundedTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!this.isFocusOwner() && getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY);
                    g2.drawString(placeholder, 10, 20); // Adjust the position as needed
                    g2.dispose();
                }
            }
        };
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setBackground(new Color(220, 220, 220));
        textField.setPreferredSize(new Dimension(200, 30));

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.repaint();
            }
        });
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();
            frame.setVisible(true);
        });
    }
}
