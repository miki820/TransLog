package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import VehicleModel.Vehicle;
import VehicleModel.Transport;

public class VehicleWindow extends JFrame {
    // Static variable to keep track of the last selected button in the sidebar
    private static JButton lastSelectedButton = null;

    // List to store checkboxes for vehicle selection
    private List<JCheckBox> vehicleCheckBoxes = new ArrayList<>();

    // Variables to store transport details
    private String startingPoint;
    private String endingPoint;
    private String cargo;
    private LocalDate transportDate;

    // Constructor to initialize the VehicleWindow with transport details
    public VehicleWindow(String startingPoint, String endingPoint, String cargo, LocalDate transportDate) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.cargo = cargo;
        this.transportDate = transportDate;

        setTitle("TransLog - Select Vehicles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);

        // Create split pane for sidebar and main content
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(0);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Setup vehicle selection panel
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel vehicleSelectionPanel = new JPanel(new GridBagLayout());
        vehicleSelectionPanel.setBackground(Color.WHITE);
        vehicleSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create vehicle selection checkboxes
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 0, 10, 0); // Add spacing between checkboxes
        for (Vehicle vehicle : Vehicle.getAllVehicles()) {
            String displayText = vehicle.getBrand() + " " + vehicle.getLicencePlateNumber();
            JCheckBox checkBox = new JCheckBox(displayText);
            checkBox.setBackground(new Color(220, 220, 220));
            checkBox.setForeground(Color.BLACK);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
            checkBox.setPreferredSize(new Dimension(300, 30));
            vehicleCheckBoxes.add(checkBox);
            vehicleSelectionPanel.add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);

        // Setup content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleOfContentPage = new JLabel("Wybór Pojazdów");
        titleOfContentPage.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(titleOfContentPage, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(vehicleSelectionPanel, gbc);

        // "Proceed" button
        JButton proceedButton = new JButton("Dodaj");
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setBackground(new Color(29, 157, 250));
        proceedButton.setPreferredSize(new Dimension(100, 40));
        proceedButton.setFocusPainted(false);
        proceedButton.setBorderPainted(false);
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Vehicle> selectedVehicles = getSelectedVehicles();
                if (selectedVehicles.size() < 1 || selectedVehicles.size() > 2) {
                    JOptionPane.showMessageDialog(VehicleWindow.this, "Please select at least one and at most two vehicles.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create a new VehicleModel.Transport object
                    Transport transport = new Transport(startingPoint, endingPoint, cargo, transportDate, selectedVehicles);

                    // Open summary window
                    SummaryWindow summaryWindow = new SummaryWindow(transport);
                    summaryWindow.setVisible(true);
                    VehicleWindow.this.dispose();
                }
            }
        });

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        contentPanel.add(proceedButton, gbc);

        // Setup sidebar
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

        // Group the buttons to ensure only one can be selected at a time
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(planning);
        buttonGroup.add(show);
        buttonGroup.add(analyze);

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

        // Setup main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridBagLayout());
        splitPane.setRightComponent(mainPanel);

        // Set divider location between sidebar and main panel
        splitPane.setDividerLocation(200);

        // Setting component in the main panel
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(contentPanel, gbc);

        // Set "Planowanie" button as selected by default
        planning.doClick();
    }

    // Helper method to create toggle buttons for the sidebar
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
                JButton clickedButton = (JButton) e.getSource();
                if (lastSelectedButton != null && lastSelectedButton != clickedButton) {
                    lastSelectedButton.setOpaque(false);
                    lastSelectedButton.setForeground(Color.BLACK);
                }
                if (clickedButton == lastSelectedButton) {
                    clickedButton.setOpaque(false);
                    clickedButton.setForeground(Color.BLACK);
                    lastSelectedButton = null;
                } else {
                    clickedButton.setOpaque(true);
                    clickedButton.setBackground(new Color(29, 157, 250));
                    clickedButton.setForeground(Color.WHITE);
                    lastSelectedButton = clickedButton;
                }
            }
        });

        return button;
    }

    // Helper method to get the selected vehicles from the checkboxes
    private List<Vehicle> getSelectedVehicles() {
        List<Vehicle> selectedVehicles = new ArrayList<>();
        for (JCheckBox checkBox : vehicleCheckBoxes) {
            if (checkBox.isSelected()) {
                for (Vehicle vehicle : Vehicle.getAllVehicles()) {
                    if (checkBox.getText().equals(vehicle.getBrand() + " " + vehicle.getLicencePlateNumber())) {
                        selectedVehicles.add(vehicle);
                    }
                }
            }
        }
        return selectedVehicles;
    }
}
