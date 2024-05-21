package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigitalSignatureApp extends JFrame {
    private JTextField inputField;
    private JTextField signatureField;
    private JTextField validationField;
    private JButton computeButton;
    private JButton validateButton;
    private JLabel statusLabel;

    public DigitalSignatureApp() {
        super("Digital Signature Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(5, 2));

        inputField = new JTextField();
        signatureField = new JTextField();
        validationField = new JTextField();
        computeButton = new JButton("Compute Signature");
        validateButton = new JButton("Validate Signature");
        statusLabel = new JLabel("Status: Awaiting input");

        add(new JLabel("Input Text:"));
        add(inputField);
        add(new JLabel("Digital Signature:"));
        add(signatureField);
        add(new JLabel("Validation Text:"));
        add(validationField);
        add(computeButton);
        add(validateButton);
        add(statusLabel);

        computeButton.addActionListener(e -> computeSignature());
        validateButton.addActionListener(e -> validateSignature());

        setVisible(true);
    }

    private void computeSignature() {
        String input = inputField.getText();
        String signature = generateSignature(input);
        signatureField.setText(signature);
    }

    private void validateSignature() {
        String validationText = validationField.getText();
        String computedSignature = generateSignature(validationText);
        String providedSignature = signatureField.getText();

        if (computedSignature.equals(providedSignature)) {
            statusLabel.setText("Status: Signature Validated");
        } else {
            statusLabel.setText("Status: Invalid Signature");
        }
    }

    private String generateSignature(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Error: Algorithm not supported";
        }
    }

    public static void main(String[] args) {
        new DigitalSignatureApp();
    }
}
