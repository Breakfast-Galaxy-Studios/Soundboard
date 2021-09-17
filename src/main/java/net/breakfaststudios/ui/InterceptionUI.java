package net.breakfaststudios.ui;

import net.breakfaststudios.soundboard.listeners.InterceptionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class InterceptionUI {
    public void interceptionMenu(JFrame mainFrame, ArrayList<JPanel> panels){
        // todo just redo this entire class
        JDialog interceptionMenu = new JDialog();
        JPanel panel1 = new JPanel();
        JTextField devIdField = new JTextField();
        JButton devIdRecordButton = new JButton();
        JLabel devIdLabel = new JLabel();
        JLabel interceptionToggleLabel = new JLabel();
        JCheckBox interceptionToggle = new JCheckBox();
        JButton interceptionMenuConfirm = new JButton();
        JButton interceptionMenuCancel = new JButton();

        devIdRecordButton.setText("...");
        devIdLabel.setText("Device ID: ");
        interceptionToggleLabel.setText("interceptionToggleLabel");
        interceptionMenuConfirm.setText("Confirm");
        interceptionMenuCancel.setText("Cancel");
        interceptionMenu.setMaximumSize(new Dimension(350, 255));
        interceptionMenu.setMinimumSize(new Dimension(350, 255));
        interceptionMenu.setPreferredSize(new Dimension(350, 255));

        GroupLayout panel1Layout = new GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(interceptionToggleLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(interceptionToggle, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(devIdLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(devIdField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(devIdRecordButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(interceptionMenuConfirm)
                                                .addGap(10, 10, 10)
                                                .addComponent(interceptionMenuCancel)))
                                .addGap(39, 39, 39))
        );
        panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(interceptionToggleLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(interceptionToggle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(devIdLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(devIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(devIdRecordButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(interceptionMenuConfirm)
                                        .addComponent(interceptionMenuCancel)))
        );

        GroupLayout soundAddMenuLayout = new GroupLayout(interceptionMenu.getContentPane());
        interceptionMenu.getContentPane().setLayout(soundAddMenuLayout);
        soundAddMenuLayout.setHorizontalGroup(
                soundAddMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(soundAddMenuLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        soundAddMenuLayout.setVerticalGroup(
                soundAddMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        interceptionMenu.setVisible(true);

        devIdRecordButton.addActionListener(e-> {
            try {
                devIdField.setText(new InterceptionListener().listenForDevID());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
