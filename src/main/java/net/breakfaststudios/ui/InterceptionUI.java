package net.breakfaststudios.ui;

import javax.swing.*;
import java.awt.*;

public class InterceptionUI extends JDialog {

    public InterceptionUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        JPanel settingsPanel = new JPanel();
        JLabel statusLabel = new JLabel("Interception Status:");
        JLabel tempLabel = new JLabel("Other settings coming soon™");
        JTextField statusField = new JTextField("OFF");
        JLabel devIdLabel = new JLabel("Device ID:");
        JTextField devIdField = new JTextField("N/A");
        JLabel activateLabel = new JLabel("Activate:");
        JCheckBox activateCheckbox = new JCheckBox();
        JButton saveButton = new JButton("Save");
        JButton refreshButton = new JButton("Refresh");
        JLabel settingsLabel = new JLabel("Interception Settings");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        tempLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusField.setHorizontalAlignment(JTextField.CENTER);
        statusField.setForeground(new java.awt.Color(255, 0, 0));
        statusField.setFocusable(false);

        devIdLabel.setToolTipText("To change the intercepted device, use BGS-Macros");

        devIdField.setHorizontalAlignment(JTextField.CENTER);
        devIdField.setToolTipText("To change the intercepted device, use BGS-Macros");
        devIdField.setFocusable(false);

        activateLabel.setToolTipText("To change the intercepted device, use BGS-Macros");

        settingsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsLabel.setText("Interception Settings");

        GroupLayout settingsPanelLayout = new GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
                settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tempLabel, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                                .addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(statusField))
                                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                                .addComponent(activateLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(activateCheckbox, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                                .addComponent(devIdLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(devIdField)))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(settingsLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
        );
        settingsPanelLayout.setVerticalGroup(
                settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(statusField))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(settingsLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(activateLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(activateCheckbox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(devIdLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(devIdField))
                                .addGap(13, 13, 13)
                                .addComponent(tempLabel, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(refreshButton)
                                        .addComponent(saveButton))
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(settingsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(settingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        Dimension defaultDimension = new Dimension(310, 400);
        setMaximumSize(defaultDimension);
        setMinimumSize(defaultDimension);
        setPreferredSize(defaultDimension);
        settingsPanel.setPreferredSize(defaultDimension);
        settingsPanel.setMaximumSize(defaultDimension);
        settingsPanel.setMinimumSize(defaultDimension);

        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // START OF ACTION LISTENERS
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------

    }
}
