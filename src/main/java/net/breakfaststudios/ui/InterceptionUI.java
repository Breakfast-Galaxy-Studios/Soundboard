package net.breakfaststudios.ui;

import net.breakfaststudios.soundboard.interception.InterceptionMain;
import net.breakfaststudios.util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates the UI for interception. This option will only display on Windows machines.
 */
public class InterceptionUI {
    /**
     * Creates the menu and initializes listeners.
     */
    private final JDialog interceptionMenuDialog = new JDialog();

    /**
     * Create the interception settings ui & init all listeners for it
     */
    public void interceptionMenu() {
        // TODO for some reason the panel isn't aligned to the main window.

        // Declare all components
        ArrayList<Panel> p = new ArrayList<>();
        Panel interceptionMenuPanel = new Panel();
        JTextField deviceIDTextField = new JTextField();
        JLabel deviceIDLabel = new JLabel();
        JLabel InterceptionCheckboxLabel = new JLabel();
        JCheckBox interceptionCheckbox = new JCheckBox();
        JButton devIDButton = new JButton("...");
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        // Make all needed changes to all the components.
        p.add(interceptionMenuPanel);
        deviceIDLabel.setText("Device ID");
        InterceptionCheckboxLabel.setText("Interception");
        interceptionMenuDialog.setMaximumSize(new Dimension(325, 200));
        interceptionMenuDialog.setMinimumSize(new Dimension(325, 200));

        // Define group layouts
        GroupLayout panelLayout = new GroupLayout(interceptionMenuPanel);
        interceptionMenuPanel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(InterceptionCheckboxLabel)
                                                        .addComponent(deviceIDLabel))
                                                .addGap(18, 18, 18)
                                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelLayout.createSequentialGroup()
                                                                .addComponent(deviceIDTextField, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(devIDButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(interceptionCheckbox)))
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(confirmButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cancelButton)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(deviceIDTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(devIDButton)
                                        .addComponent(deviceIDLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(interceptionCheckbox)
                                        .addComponent(InterceptionCheckboxLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirmButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout interceptionMenuLayout = new GroupLayout(interceptionMenuDialog.getContentPane());
        interceptionMenuDialog.getContentPane().setLayout(interceptionMenuLayout);
        interceptionMenuLayout.setHorizontalGroup(
                interceptionMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(interceptionMenuLayout.createSequentialGroup()
                                .addComponent(interceptionMenuPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        interceptionMenuLayout.setVerticalGroup(
                interceptionMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(interceptionMenuLayout.createSequentialGroup()
                                .addComponent(interceptionMenuPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setVisible(true);
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // End creation of UI & begin creation of action listeners
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------

        // Save settings on confirm & close window
        confirmButton.addActionListener(e -> {
            if(!(InterceptionMain.updateInterceptionProperties(deviceIDTextField.getText(), String.valueOf(interceptionCheckbox.isSelected())))){
                JOptionPane.showMessageDialog(null, "Failed to update interception settings. Try restarting the program.");
            }
            setVisible(false);
        });

        // Close window on cancel
        cancelButton.addActionListener(e -> setVisible(false));

        // -----------------------------------------------------------------
        // Dark mode
        // -----------------------------------------------------------------
        if (Util.getSettingsFile().getProperty("darkMode").equals("true")) {
            Color grey = new Color(51, 51, 51);
            Color white = new Color(255, 255, 255);
            Util.setTheme(p, grey, white);
        } else {
            Color black = new Color(0, 0, 0);
            Color white = new Color(242, 242, 242);
            Util.setTheme(p, white, black);
        }
    }

    /**
     * Makes setting the visibility of the menu easier to change
     */
    private void setVisible(boolean b) { interceptionMenuDialog.setVisible(b); }
}
