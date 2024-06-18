package GUI;

import VehicleModel.Vehicle;
import VehicleModel.Transport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryWindow extends JFrame {

    private Transport transport;

    public SummaryWindow(String startingPoint, String endingPoint, String cargo, String transportDate, List<Vehicle> selectedVehicles) {
        // Create a new Transport object
        this.transport = new Transport(startingPoint, endingPoint, cargo, LocalDate.parse(transportDate), selectedVehicles);

        setTitle("Podsumowanie");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new RoundedPanel(15);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Podsumowanie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        String vehicleInfo = selectedVehicles.stream()
                .map(vehicle -> vehicle.getBrand() + " " + vehicle.getLicencePlateNumber())
                .collect(Collectors.joining(", "));

        String driverInfo = selectedVehicles.stream()
                .flatMap(vehicle -> vehicle.getAllDrivers().stream())
                .map(driver -> driver.getName())
                .collect(Collectors.joining(", "));

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setBackground(new Color(220, 220, 220));
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 16));
        summaryArea.setText(String.format("Miejsce wyjazdu: %s\nMiejsce docelowe: %s\nTowar: %s\nData: %s\nPojazdy: %s\nKierowcy: %s",
                startingPoint, endingPoint, cargo, transportDate, vehicleInfo, driverInfo));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(summaryArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton acceptButton = new JButton("Akceptuj");
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setBackground(new Color(29, 157, 250));
        acceptButton.setPreferredSize(new Dimension(100, 40));
        acceptButton.setFocusPainted(false);
        acceptButton.setBorderPainted(false);
        acceptButton.addActionListener(e -> {
            // Transport is already added to the system in the constructor
            JOptionPane.showMessageDialog(SummaryWindow.this, "Transport zaakceptowany.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            showRestartOption();
        });

        JButton rejectButton = new JButton("Nie Akceptuj");
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setBackground(new Color(29, 157, 250));
        rejectButton.setPreferredSize(new Dimension(100, 40));
        rejectButton.setFocusPainted(false);
        rejectButton.setBorderPainted(false);
        rejectButton.addActionListener(e -> {
            // Remove the transport from the system
            transport.removeTransport();
            JOptionPane.showMessageDialog(SummaryWindow.this, "Transport odrzucony.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            showRestartOption();
        });

        buttonPanel.add(rejectButton);
        buttonPanel.add(acceptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void showRestartOption() {
        int response = JOptionPane.showConfirmDialog(SummaryWindow.this, "Czy chcesz dodać nowy transport?", "Nowy Transport", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            // Open the main GUI for planning transport
            GUI mainGUI = new GUI();
            mainGUI.setVisible(true);
        }
        // Close the current window
        dispose();
    }
}