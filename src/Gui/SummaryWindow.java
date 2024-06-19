package Gui;

import VehicleModel.Transport;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;
import Extent.ExtentManager;
import WorkerModel.Worker;

public class SummaryWindow extends JFrame {

    // Constructor to initialize the summary window with the given transport details
    public SummaryWindow(Transport transport) {

        setTitle("Podsumowanie");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with rounded corners and a border layout
        JPanel mainPanel = new RoundedPanel(15);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label for the summary window
        JLabel titleLabel = new JLabel("Podsumowanie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Generate vehicle and driver info for the summary
        String vehicleInfo = transport.getVehicles().stream()
                .map(vehicle -> vehicle.getBrand() + " " + vehicle.getLicencePlateNumber())
                .collect(Collectors.joining(", "));

        String driverInfo = transport.getVehicles().stream()
                .flatMap(vehicle -> vehicle.getAllDrivers().stream())
                .map(Worker::getName)
                .collect(Collectors.joining(", "));

        // Text area to display the summary information
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setBackground(new Color(220, 220, 220));
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 16));
        summaryArea.setText(String.format("Miejsce wyjazdu: %s\nMiejsce docelowe: %s\nTowar: %s\nData: %s\nPojazdy: %s\nKierowcy: %s",
                transport.getStartingPoint(), transport.getEndingPoint(), transport.getCargo(), transport.getTransportDate().toString(), vehicleInfo, driverInfo));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(summaryArea, BorderLayout.CENTER);

        // Panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // Create accept button
        JButton acceptButton = new JButton("Akceptuj");
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setBackground(new Color(29, 157, 250));
        acceptButton.setPreferredSize(new Dimension(100, 40));
        acceptButton.setFocusPainted(false);
        acceptButton.setBorderPainted(false);
        acceptButton.addActionListener(e -> {
            // Action to perform when the accept button is clicked
            JOptionPane.showMessageDialog(SummaryWindow.this, "Transport zaakceptowany.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            showRestartOption();
            ExtentManager.saveExtent();
            ExtentManager.clearAllData();
            ExtentManager.loadExtent();
            Transport.showAllTransports();
        });

        // Create reject button
        JButton rejectButton = new JButton("Nie Akceptuj");
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setBackground(new Color(29, 157, 250));
        rejectButton.setPreferredSize(new Dimension(100, 40));
        rejectButton.setFocusPainted(false);
        rejectButton.setBorderPainted(false);
        rejectButton.addActionListener(e -> {
            // Action to perform when the reject button is clicked
            transport.removeTransport();
            JOptionPane.showMessageDialog(SummaryWindow.this, "Transport odrzucony.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            showRestartOption();
            ExtentManager.saveExtent();
            ExtentManager.clearAllData();
            ExtentManager.loadExtent();
            Transport.showAllTransports();
        });

        buttonPanel.add(rejectButton);
        buttonPanel.add(acceptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    /**
     * Displays a dialog to ask if the user wants to add a new transport.
     * If yes, opens the main GUI for planning a new transport.
     * Closes the current window in any case.
     */
    private void showRestartOption() {
        int response = JOptionPane.showConfirmDialog(SummaryWindow.this, "Czy chcesz dodać nowy transport?", "Nowy Transport", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            // Open the main GUI for planning transport
            AddTransportWindow mainAddTransportWindow = new AddTransportWindow();
            mainAddTransportWindow.setVisible(true);
        }
        // Close the current window
        dispose();
    }
}
