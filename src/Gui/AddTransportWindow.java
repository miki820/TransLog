package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * AddTransportWindow is a GUI window for adding transport details.
 * It uses Swing components to create a user-friendly interface.
 */
public class AddTransportWindow extends JFrame {
    private static JButton lastSelectedButton = null;
    private static ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Constructor for AddTransportWindow.
     * Initializes the window and its components.
     */
    public AddTransportWindow() {
        setTitle("TransLog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);

        // Create the split pane
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(0);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // GridBagConstraints for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel textFields = new JPanel(new GridBagLayout());
        textFields.setBackground(Color.WHITE);
        textFields.setBorder(BorderFactory.createEmptyBorder(20, 18, 20, 20));

        // Configure GridBagConstraints
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 0, 10, 0); // Add spacing between text fields

        // Create and add text fields with placeholders
        RoundedTextField punktKoncowyField = createPlaceholderTextField("Punkt końcowy");
        punktKoncowyField.setBackground(new Color(220, 220, 220));
        punktKoncowyField.setPreferredSize(new Dimension(300, 30));
        textFields.add(punktKoncowyField, gbc);

        gbc.gridy++;
        RoundedTextField punktStartowyField = createPlaceholderTextField("Punkt startowy");
        punktStartowyField.setBackground(new Color(220, 220, 220));
        punktStartowyField.setPreferredSize(new Dimension(300, 30));
        textFields.add(punktStartowyField, gbc);

        gbc.gridy++;
        RoundedTextField przewozonyTowarField = createPlaceholderTextField("Przewożony towar");
        przewozonyTowarField.setBackground(new Color(220, 220, 220));
        przewozonyTowarField.setPreferredSize(new Dimension(300, 30));
        textFields.add(przewozonyTowarField, gbc);

        gbc.gridy++;
        RoundedTextField transportDateField = createPlaceholderTextField("Data transportu (YYYY-MM-DD)");
        transportDateField.setBackground(new Color(220, 220, 220));
        transportDateField.setPreferredSize(new Dimension(300, 30));
        textFields.add(transportDateField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);

        // Main content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label for content panel
        JLabel titleOfContentPage = new JLabel("Planowanie Transportu");
        titleOfContentPage.setFont(new Font("Arial", Font.BOLD, 22));

        // Add title label to content panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(titleOfContentPage, gbc);

        // Add text fields to content panel
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(textFields, gbc);

        // "Dodaj" button configuration
        JButton dodajButton = new JButton("Dodaj");
        dodajButton.setForeground(Color.WHITE);
        dodajButton.setBackground(new Color(29, 157, 250));
        dodajButton.setPreferredSize(new Dimension(100, 40));
        dodajButton.setFocusPainted(false);
        dodajButton.setBorderPainted(false);

        // Action listener for "Dodaj" button
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve and validate input data
                String startingPoint = punktStartowyField.getText();
                String endingPoint = punktKoncowyField.getText();
                String cargo = przewozonyTowarField.getText();
                String transportDateStr = transportDateField.getText();
                LocalDate transportDate;

                try {
                    transportDate = LocalDate.parse(transportDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(AddTransportWindow.this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Open VehicleWindow with input data
                VehicleWindow vehicleWindow = new VehicleWindow(startingPoint, endingPoint, cargo, transportDate);
                vehicleWindow.setVisible(true);
                AddTransportWindow.this.dispose();
            }
        });

        // Add "Dodaj" button to content panel
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        contentPanel.add(dodajButton, gbc);

        // Sidebar panel configuration
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

        // Sidebar title label
        JLabel titleOfSideBar = new JLabel("TransLog");
        titleOfSideBar.setFont(new Font("Arial", Font.BOLD, 30));
        titleOfSideBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create sidebar buttons
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

        // Add buttons to ButtonGroup
        buttonGroup.add(planning);
        buttonGroup.add(show);
        buttonGroup.add(analyze);

        // Add components to sidebar
        sidebar.add(titleOfSideBar);
        sidebar.add(Box.createVerticalStrut(50));
        sidebar.add(planning);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(show);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(analyze);

        // Set sidebar border
        Border margin = BorderFactory.createEmptyBorder(30, 0, 0, 0);
        Border blueLine = BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 157, 250));
        sidebar.setBorder(BorderFactory.createCompoundBorder(blueLine, margin));

        // Main panel configuration
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridBagLayout());
        splitPane.setRightComponent(mainPanel);

        // Set divider location
        splitPane.setDividerLocation(200);

        // Add content panel to main panel
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(contentPanel, gbc);

        // Set "Planowanie" button as selected by default
        planning.doClick();
    }

    /**
     * Creates a toggle button with specified text.
     * @param text The text to display on the button.
     * @return A configured JButton.
     */
    private static JButton createToggleButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener for toggle button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastSelectedButton != null && lastSelectedButton != button) {
                    lastSelectedButton.setOpaque(false);
                    lastSelectedButton.setForeground(Color.BLACK);
                }
                if (button == lastSelectedButton) {
                    button.setOpaque(false);
                    button.setForeground(Color.BLACK);
                    lastSelectedButton = null;
                } else {
                    button.setOpaque(true);
                    button.setBackground(new Color(29, 157, 250));
                    button.setForeground(Color.WHITE);
                    lastSelectedButton = button;
                }
            }
        });

        return button;
    }

    /**
     * Creates a rounded text field with a placeholder.
     * @param placeholder The placeholder text.
     * @return A configured RoundedTextField.
     */
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
        textField.setPreferredSize(new Dimension(300, 30));

        // Focus listener to repaint placeholder text on focus change
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
}
