package net.breakfaststudios;

import com.sun.javafx.application.PlatformImpl;
import javafx.stage.FileChooser;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.listeners.KeybindRecorder;
import net.breakfaststudios.util.Converter;
import net.breakfaststudios.util.SoundManager;
import net.breakfaststudios.util.Util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

import static java.awt.Color.black;
import static net.breakfaststudios.BreakfastSounds.*;

public class UI extends JFrame {

    private final ArrayList<JPanel> panels;
    private boolean editSound;

    public UI() {
        editSound = false;
        panels = new ArrayList<>();
    }

    /**
     * Creates all UI elements, initializes listeners,
     */
    public void build() {

        // -----------------------------------------------------------------
        // Define all components
        // -----------------------------------------------------------------

        // Checkboxes
        JCheckBox openToTrayCheckbox = new JCheckBox();
        JCheckBox keyboardCompatCheckbox = new JCheckBox();
        JCheckBox darkModeCheckbox = new JCheckBox();

        // Panels and dialog boxes
        JDialog soundAddMenu = new JDialog();
        JDialog settingsPopup = new JDialog();
        JDialog recordKeybindDialog = new JDialog();
        JPanel recordKeybindPanel = new JPanel();
        JPanel settingsPanel = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel soundAddMenuPanel = new JPanel();

        panels.add(soundAddMenuPanel);
        panels.add(jPanel2);
        panels.add(settingsPanel);
        panels.add(recordKeybindPanel);
        panels.add((JPanel) this.getContentPane());

        // Text fields
        JTextField newSoundNameField = new JTextField();
        JTextField newKeybindField = new JTextField();
        JTextField newSoundFileField = new JTextField();
        JTextField hiddenTextField = new JTextField();

        // Labels
        JLabel openToTray = new JLabel("Open To Tray:");
        JLabel keyboardCompatLabel = new JLabel();
        JLabel fileLabel = new JLabel();
        JLabel keybindLabel = new JLabel();
        JLabel volumeLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel soundOutputLabel = new JLabel();
        JLabel darkModeLabel = new JLabel("Dark Mode:");
        JLabel recordKeybindLabel = new JLabel("Recording.....");

        // Buttons
        JButton recordKeybind = new JButton();
        JButton confirmAddSound = new JButton();
        JButton cancelAddSound = new JButton();
        JButton fileAdd = new JButton();
        JButton addButton = new JButton();
        JButton removeButton = new JButton();
        JButton ConfirmSettings = new JButton();
        JButton cancelSettings = new JButton();
        JPopupMenu editMenu = new JPopupMenu();
        JMenuItem editMenuItem = new JMenuItem("Edit...");
        JMenuItem deleteSound = new JMenuItem("Delete");
        JButton recordKeybindCancel = new JButton("Cancel");


        // Other components
        JScrollPane tablePane = new JScrollPane();
        JTable soundTable = new JTable();
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu();
        JComboBox<String> soundOutputDropdown = new JComboBox<>();
        JSlider volumeSlider = new JSlider();


        // -----------------------------------------------------------------
        // Set state, size, and content of all UI components.
        // -----------------------------------------------------------------

        // Add sound menu
        soundAddMenu.setTitle("Add Sound");
        soundAddMenu.setAlwaysOnTop(false);
        soundAddMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        soundAddMenu.setMaximumSize(new Dimension(350, 255));
        soundAddMenu.setMinimumSize(new Dimension(350, 255));
        soundAddMenu.setPreferredSize(new Dimension(350, 255));
        soundAddMenu.setResizable(false);
        hiddenTextField.setEditable(false);
        hiddenTextField.setVisible(false);
        cancelAddSound.setText("Cancel");
        fileLabel.setText("File:");
        newSoundNameField.setMaximumSize(new Dimension(7, 20));
        volumeSlider.setMaximumSize(new Dimension(36, 26));
        newSoundFileField.setEditable(false);
        keybindLabel.setText("Key/s:");
        recordKeybind.setText("jButton1");
        confirmAddSound.setText("Confirm");
        volumeLabel.setText("Volume:");
        fileAdd.setText("...");
        nameLabel.setText("Name:");
        soundAddMenuPanel.setMaximumSize(new Dimension(350, 255));
        soundAddMenuPanel.setMinimumSize(new Dimension(350, 255));
        soundAddMenuPanel.setPreferredSize(new Dimension(350, 255));
        recordKeybindDialog.setMaximumSize(new Dimension(200, 115));
        recordKeybindDialog.setMinimumSize(new Dimension(200, 115));
        recordKeybindDialog.setPreferredSize(new Dimension(200, 115));
        recordKeybindDialog.setResizable(false);
        recordKeybindDialog.setVisible(false);
        newKeybindField.setEditable(false);
        volumeSlider.setValue(100);

        // Settings
        soundOutputDropdown.setModel(new DefaultComboBoxModel<>(new String[]{SELECTED_AUDIO_DEVICE}));
        ConfirmSettings.setText("Confirm");
        soundOutputLabel.setText("Sound Output:");
        keyboardCompatLabel.setText("Keybind Recording Compatibility mode:");
        cancelSettings.setText("Cancel");
        settingsPopup.setTitle("Settings");
        settingsPopup.setAlwaysOnTop(false);
        settingsPopup.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        settingsPopup.setMaximumSize(new Dimension(350, 200));
        settingsPopup.setMinimumSize(new Dimension(350, 200));
        settingsPopup.setPreferredSize(new Dimension(350, 200));
        settingsPopup.setResizable(false);
        keyboardCompatLabel.setToolTipText("If recording keybinds constantly records keys that aren't pressed, or doesn't record certain keys, turn this on.");
        openToTray.setToolTipText("Open to tray at startup on supported OS's.");
        darkModeLabel.setToolTipText("Changing this setting will take affect at next startup.");
        settingsMenu.setText("Settings");

        // Main JFrame (this)
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon.png"));
        this.setIconImage(icon);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("BGS Soundboard");
        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);

        // Main panel buttons
        addButton.setText("Add...");
        addButton.setMaximumSize(new Dimension(83, 23));
        addButton.setMinimumSize(new Dimension(83, 23));
        addButton.setPreferredSize(new Dimension(83, 23));
        removeButton.setText("Remove...");

        // Sound Table
        DefaultTableModel soundTableModel = new DefaultTableModel(
                new Object[][]{

                },
                new String[]{

                }
        ) {
            final boolean[] canEdit = new boolean[]{
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        String[] cols = {"Name", "Keybind"};
        soundTableModel.setColumnIdentifiers(cols);
        soundTable.setModel(soundTableModel);
        soundTable.getTableHeader().setOpaque(false);
        tablePane.setViewportView(soundTable);
        if (soundTable.getColumnModel().getColumnCount() > 0) {
            soundTable.getColumnModel().getColumn(0).setResizable(false);
            soundTable.getColumnModel().getColumn(1).setResizable(false);
        }
        soundTable.getTableHeader().setReorderingAllowed(false);

        // -----------------------------------------------------------------
        // Positioning of all UI elements
        // -----------------------------------------------------------------
        GroupLayout soundAddMenuPanelLayout = new GroupLayout(soundAddMenuPanel);
        soundAddMenuPanel.setLayout(soundAddMenuPanelLayout);
        soundAddMenuPanelLayout.setHorizontalGroup(
                soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newSoundNameField, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newSoundFileField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fileAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addComponent(keybindLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newKeybindField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(recordKeybind, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addComponent(confirmAddSound)
                                                .addGap(10, 10, 10)
                                                .addComponent(cancelAddSound)))
                                .addGap(39, 39, 39))
                        .addComponent(hiddenTextField, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        soundAddMenuPanelLayout.setVerticalGroup(
                soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(newSoundNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newSoundFileField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(fileAdd, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(keybindLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newKeybindField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(recordKeybind, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(soundAddMenuPanelLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(soundAddMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(confirmAddSound)
                                        .addComponent(cancelAddSound))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(hiddenTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout soundAddMenuLayout = new GroupLayout(soundAddMenu.getContentPane());
        soundAddMenu.getContentPane().setLayout(soundAddMenuLayout);
        soundAddMenuLayout.setHorizontalGroup(
                soundAddMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(soundAddMenuLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(soundAddMenuPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        soundAddMenuLayout.setVerticalGroup(
                soundAddMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(soundAddMenuPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );




        GroupLayout recordKeybindPanelLayout = new GroupLayout(recordKeybindPanel);
        recordKeybindPanel.setLayout(recordKeybindPanelLayout);
        recordKeybindPanelLayout.setHorizontalGroup(
                recordKeybindPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(recordKeybindPanelLayout.createSequentialGroup()
                                .addGroup(recordKeybindPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(recordKeybindPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(recordKeybindCancel))
                                        .addGroup(recordKeybindPanelLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(recordKeybindLabel)))
                                .addContainerGap())
        );
        recordKeybindPanelLayout.setVerticalGroup(
                recordKeybindPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(recordKeybindPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(recordKeybindLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(recordKeybindPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(recordKeybindCancel))
                                .addContainerGap())
        );

        GroupLayout recordKeybindDialogLayout = new GroupLayout(recordKeybindDialog.getContentPane());
        recordKeybindDialog.getContentPane().setLayout(recordKeybindDialogLayout);
        recordKeybindDialogLayout.setHorizontalGroup(
                recordKeybindDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(recordKeybindDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(recordKeybindPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        recordKeybindDialogLayout.setVerticalGroup(
                recordKeybindDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(recordKeybindDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(recordKeybindPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        GroupLayout settingsPanelLayout = new GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(settingsPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                                        .addComponent(darkModeLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(darkModeCheckbox)
                                                        .addGap(0, 0, 32767))
                                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                                        .addComponent(openToTray)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(openToTrayCheckbox)
                                                        .addGap(0, 0, 32767))
                                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                                        .addComponent(keyboardCompatLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(keyboardCompatCheckbox)
                                                        .addGap(0, 0, 32767))
                                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                                        .addComponent(soundOutputLabel, -2, 80, -2)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 54, 32767)
                                                        .addComponent(soundOutputDropdown, -2, 190, -2))))
                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(ConfirmSettings)
                                        .addGap(10, 10, 10)
                                        .addComponent(cancelSettings)))
                        .addContainerGap()));
        settingsPanelLayout.setVerticalGroup(settingsPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(soundOutputLabel, -2, 25, -2)
                                .addComponent(soundOutputDropdown, -2, -1, -2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(keyboardCompatCheckbox)
                                .addComponent(keyboardCompatLabel, -2, 25, -2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(openToTrayCheckbox)
                                .addComponent(openToTray, -2, 25, -2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(darkModeCheckbox)
                                .addComponent(darkModeLabel, -2, 25, -2))
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(ConfirmSettings)
                                .addComponent(cancelSettings))
                        .addContainerGap()));

        settingsPopup.add(settingsPanel);



        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(removeButton)
                                                .addContainerGap(163, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        )))
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(removeButton))
                        .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );




        pack();
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // End creation of UI
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------

        // -----------------------------------------------------------------
        // Settings
        // -----------------------------------------------------------------

        // Opens settings window and sets defaults to current settings
        settingsMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Properties settings = Util.getSettingsFile();
                if (settings != null) {
                    soundOutputDropdown.setSelectedItem(settings.getProperty("soundOutput"));
                    keyboardCompatCheckbox.setSelected(Boolean.parseBoolean(settings.getProperty("keyCompatMode")));
                    openToTrayCheckbox.setSelected(Boolean.parseBoolean(settings.getProperty("openToTray")));
                    darkModeCheckbox.setSelected(Boolean.parseBoolean(settings.getProperty("darkMode")));
                }
                settingsPopup.setVisible(true);
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}

        });

        // Close settings window
        cancelSettings.addActionListener(e -> settingsPopup.setVisible(false));

        // Adds Sound outputs (Speakers) to soundOutputDropdown
        ArrayList<String> deviceList = new ArrayList<>();

        for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
            if (mi.getName().equalsIgnoreCase("Primary Sound Capture Driver")) {
                break;
            }
            if (!mi.getName().contains("Port"))
                deviceList.add(mi.getName());
        }
        deviceList.add("No sound output devices detected.");

        String[] deviceArray = new String[deviceList.size()];
        for (int i = 0; i < deviceArray.length; i++) {
            deviceArray[i] = deviceList.get(i);
        }

        soundOutputDropdown.setModel(new DefaultComboBoxModel<>(deviceArray));

        if (SELECTED_AUDIO_DEVICE.equalsIgnoreCase("No sound output devices detected.")) {
            soundOutputDropdown.setSelectedIndex(deviceArray.length - 1);
        }

        ConfirmSettings.addActionListener(e -> {
            String soundOutput = (String) soundOutputDropdown.getSelectedItem();
            Util.updateSettings(soundOutput, keyboardCompatCheckbox.isSelected(), openToTrayCheckbox.isSelected(), darkModeCheckbox.isSelected());
            SELECTED_AUDIO_DEVICE = soundOutput;
            settingsPopup.setVisible(false);
            if (darkModeCheckbox.isSelected()) {
                Color grey = new Color(51, 51, 51);
                Color white = new Color(255, 255, 255);
                setTheme(tablePane, soundTable, menuBar, settingsMenu, soundOutputDropdown, true, grey, white);
            } else {
                Color black = new Color(0, 0, 0);
                Color white = new Color(242, 242, 242);
                setTheme(tablePane, soundTable, menuBar, settingsMenu, soundOutputDropdown, false, white, black);
            }
        });


        // -----------------------------------------------------------------
        // Dynamically add rows to table
        // -----------------------------------------------------------------
        String[] fileList = getFileList();
        if (fileList != null) {
            for (String s : fileList) {
                try (FileInputStream file = new FileInputStream(Util.getSoundDirectory() + s)) {
                    Properties prop = new Properties();
                    prop.load(file);
                    String name = prop.getProperty("name");
                    String rawKeybinds = prop.getProperty("keybind");
                    String path = prop.getProperty("filepath");

                    File f = new File(path);
                    if (!f.exists()) {
                        file.close();
                        if(new File(Util.getSoundDirectory() + s).delete()){
                            System.out.println("Deleted File");
                        }
                        JOptionPane.showMessageDialog(null, "Failed to load file: \n" + f + "\nThe sound registered to this file has been removed.");
                    }

                    float volume = Float.parseFloat(prop.getProperty("volume"));

                    //Convert keybind text to Integer array and parse the text of the keybind column
                    StringBuilder keyBind = new StringBuilder();
                    ArrayList<Integer> keys = new ArrayList<>();
                    Util.parseRawCodeText(rawKeybinds, keys, keyBind);
                    Integer[] arr = Util.intListToArray(keys);

                    //Add sound to cache and rows
                    String todo = keyBind.toString();
                    soundTableModel.addRow(new Object[]{name, todo.substring(0, todo.length() - 3)});
                    getSoundBoard().addSound(new Sound(name, path, arr, volume));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fatal error when loading sounds. \nError is as follows:\n" + ex + "\nPlease report this to the Github Repo.");
                    System.exit(125);
                }
            }
        }


        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // START OF ACTION LISTENERS
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------


        // -----------------------------------------------------------------
        // Main Panel functions
        // -----------------------------------------------------------------
        addButton.addActionListener(e -> {
            // Reset title
            soundAddMenu.setTitle("Add New Sound");

            // Make sure all fields are cleared
            newSoundNameField.setText("");
            newKeybindField.setText("");
            hiddenTextField.setText("");
            newSoundFileField.setText("");
            volumeSlider.setValue(100);

            editSound = false;
            soundAddMenu.setVisible(true);
        });

        removeButton.addActionListener(e -> removeSound(soundTable, soundTableModel));


        // -----------------------------------------------------------------
        // Add sound panel functions
        // -----------------------------------------------------------------
        fileAdd.addActionListener(e -> PlatformImpl.startup(() -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.au", "*.ogg", "*.flac");
            fileChooser.getExtensionFilters().add(ext);
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                String[] a;
                if (os.contains("win")) {
                    a = file.getPath().split("\\\\");
                } else {
                    a = file.getPath().split("/");
                }
                newSoundFileField.setText(a[a.length - 1]);
                hiddenTextField.setText(file.getPath());
            }
        }));

        cancelAddSound.addActionListener(e -> soundAddMenu.setVisible(false));
        // Confirm button inside AddSound panel, creates a new sound.properties file, adds new sound to table
        confirmAddSound.addActionListener(e -> {
            String[] a = getFileList();
            boolean uniqueName;
            if (a != null) {
                uniqueName = a.length == 0;
            } else {
                uniqueName = true;
            }
            if (!uniqueName) {
                for (String s : a) {
                    if (s.equals(newSoundNameField.getText() + ".properties")) {
                        if (!editSound)
                            JOptionPane.showMessageDialog(null, "Sounds cannot have the same name.");
                    }
                    if (s.equals("")) {
                        JOptionPane.showMessageDialog(null, "Name field cannot be empty.");
                    } else {
                        uniqueName = true;
                    }
                }
            }
            if (uniqueName) {
                String keybindField;
                if (newKeybindField.getText().equals("")) {
                    keybindField = "None";
                } else {
                    keybindField = newKeybindField.getText();
                }
                if (hiddenTextField.getText().endsWith("mp3")) {
                    JOptionPane.showMessageDialog(null, "MP3 files are not supported.");
                } else if (!newSoundFileField.getText().equals("") || !hiddenTextField.getText().equals("")) {
                    if (editSound) {
                        try {
                            int column = 0;
                            int row = soundTable.getSelectedRow();
                            String value = soundTable.getModel().getValueAt(row, column).toString();
                            if (SoundManager.removeSound(Util.getSoundDirectory(), value + ".properties")) {
                                soundTableModel.removeRow(row);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to change that sound.");
                            }
                        } catch (Exception ignored) { }
                    }

                    StringBuilder rawCodes = new StringBuilder();
                    StringBuilder keyBind = new StringBuilder();

                    for (String character : keybindField.split("_")) {
                        rawCodes.append(Converter.getKeyCode(character)).append("_");
                    }
                    String newRawCodes = rawCodes.substring(0, rawCodes.length() - 1);

                    ArrayList<Integer> keys = new ArrayList<>();
                    Util.parseRawCodeText(newRawCodes, keys, keyBind);
                    Integer[] arr = Util.intListToArray(keys);

                    String todo = keyBind.toString();
                    SoundManager.createNewSound(newSoundNameField.getText(), hiddenTextField.getText(), newRawCodes, volumeSlider.getValue());
                    getSoundBoard().addSound(new Sound(newSoundNameField.getText(), hiddenTextField.getText(), arr, volumeSlider.getValue() / (float) 100));
                    resetKeyListener();
                    soundTableModel.addRow(new Object[]{newSoundNameField.getText(), todo.substring(0, todo.length() - 3)});

                    soundAddMenu.setVisible(false);
                    newSoundNameField.setText("");
                    newKeybindField.setText("");
                    newSoundFileField.setText("");
                    volumeSlider.setValue(100);
                } else if (hiddenTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "File must be linked.");
                } else {
                    JOptionPane.showMessageDialog(null, "Name field cannot be empty.");
                }
            }
        });

        final ArrayList<String> characters = new ArrayList<>();
        KeyListener keybindListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed( KeyEvent e) {
                if (!characters.contains(KeyEvent.getKeyText(e.getKeyCode())))
                    characters.add(KeyEvent.getKeyText(e.getKeyCode()));
            }

            public void keyReleased(KeyEvent e) {
                recordKeybindDialog.removeKeyListener(this);
                recordKeybindDialog.setVisible(false);
                newKeybindField.setText(String.join("_", characters));
            }
        };

        recordKeybindCancel.setFocusable(false);

        recordKeybind.addActionListener(e -> {
            Properties prop = Util.getSettingsFile();
            if (prop != null && Boolean.parseBoolean(prop.getProperty("keyCompatMode"))) {
                recordKeybindDialog.setVisible(true);
                characters.clear();
                recordKeybindDialog.addKeyListener(keybindListener);
            } else {
                KeybindRecorder.startRecording(recordKeybindDialog, newKeybindField);
            }
        });
        recordKeybindCancel.addActionListener(e -> {
            Properties prop = Util.getSettingsFile();
            if (prop != null && Boolean.parseBoolean(prop.getProperty("keyCompatMode"))) {
                recordKeybindDialog.setVisible(false);
                characters.clear();
                recordKeybindDialog.removeKeyListener(keybindListener);
            } else {
                KeybindRecorder.cancelKeybindRecording();
            }
        });


        editMenu.add(editMenuItem);
        editMenu.add(deleteSound);
        // -----------------------------------------------------------------
        // Remove sound
        // -----------------------------------------------------------------
        deleteSound.addActionListener(e -> removeSound(soundTable, soundTableModel));

        // -----------------------------------------------------------------
        // Edit sound
        // -----------------------------------------------------------------
        soundTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == 3) {
                    editMenu.show(e.getComponent(), e.getX(), e.getY());
                    int row = soundTable.rowAtPoint(e.getPoint());
                    soundTable.setRowSelectionInterval(row, row);
                }
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });

        editMenuItem.addActionListener(e -> {
            int col = 0;
            int row = soundTable.getSelectedRow();
            Properties soundProp = SoundManager.getSoundConfig(soundTable.getValueAt(row, col).toString());
            if (soundProp != null) {
                // Change title
                soundAddMenu.setTitle("Edit sound");

                // Fill all fields
                newSoundNameField.setText(soundProp.getProperty("name"));


                StringBuilder keyBind = new StringBuilder();
                ArrayList<Integer> keys = new ArrayList<>();
                Util.parseRawCodeText(soundProp.getProperty("keybind"), keys, keyBind);

                // todo fix this
                newKeybindField.setText(keyBind.substring(0, keyBind.length() - 3).replaceAll(" ", "").replaceAll("\\+", "_"));

                volumeSlider.setValue((int) (Float.parseFloat(soundProp.getProperty("volume")) * 100));

                String[] a;
                if (os.contains("win")) {
                    a = soundProp.getProperty("filepath").split("\\\\");
                } else {
                    a = soundProp.getProperty("filepath").split("/");
                }
                newSoundFileField.setText(a[a.length - 1]);
                hiddenTextField.setText(soundProp.getProperty("filepath"));

                soundAddMenu.setVisible(true);
                editSound = true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to load that sound file.");
            }

        });


        // -----------------------------------------------------------------
        // Darkmode
        // -----------------------------------------------------------------
        if (Objects.requireNonNull(Util.getSettingsFile()).getProperty("darkMode").equals("true")) {
            Color grey = new Color(51, 51, 51);
            Color white = new Color(255, 255, 255);
            setTheme(tablePane, soundTable, menuBar, settingsMenu, soundOutputDropdown, true, grey, white);
        } else {
            Color black = new Color(0, 0, 0);
            Color white = new Color(242, 242, 242);
            setTheme(tablePane, soundTable, menuBar, settingsMenu, soundOutputDropdown, false, white, black);
        }

        // All things to do with putting app to system tray, and sets the window visible.
        minimizeToTray();
    }


    private void setTheme(JScrollPane tablePane, JTable soundTable, JMenuBar menuBar, JMenu settingsMenu, JComboBox<String> soundOutputDropdown, boolean darkMode, Color backgroundColor, Color textColor) {
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        ImageIcon settingsIcon;
        Image settingsImage = darkMode ? toolKit.getImage(this.getClass().getClassLoader().getResource("iconblack.png")) : toolKit.getImage(this.getClass().getClassLoader().getResource("iconwhite.png"));

        //All components
        panels.forEach(panel -> {
            for (Component c : panel.getComponents()) {
                JComponent jc = (JComponent) c;
                if (jc instanceof JButton) {
                    jc.setOpaque(true);
                    jc.setBackground(backgroundColor);
                } else {
                    jc.setBackground(backgroundColor);
                    jc.setForeground(textColor);
                    jc.setOpaque(true);
                }
            }
            panel.setBackground(backgroundColor);
        });

        //Sound table
        tablePane.getViewport().setBackground(backgroundColor);

        soundTable.getTableHeader().setBackground(backgroundColor);
        soundTable.getTableHeader().setForeground(textColor);

        soundTable.setBackground(backgroundColor);
        soundTable.setForeground(textColor);

        //Menu bar
        menuBar.setBorderPainted(false);
        menuBar.setUI(new BasicMenuBarUI() {
            public void paint(Graphics g, JComponent c) {
                g.setColor(backgroundColor);
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });

        //Big boi frame
        getContentPane().setBackground(backgroundColor);
        getContentPane().setForeground(textColor);

        //Sound dropdown
        soundOutputDropdown.setForeground(black);
        soundOutputDropdown.setOpaque(false);

        //Settings menu
        settingsIcon = new ImageIcon(settingsImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        settingsMenu.setIcon(settingsIcon);
        settingsMenu.setForeground(textColor);

    }


    private void removeSound(JTable soundTable, DefaultTableModel soundTableModel) {
        try {
            int column = 0;
            int row = soundTable.getSelectedRow();
            String value = soundTable.getModel().getValueAt(row, column).toString();
            if (SoundManager.removeSound(Util.getSoundDirectory(), value + ".properties")) {
                soundTableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete that sound.");
            }
        } catch (Exception ignored) { }
    }

    /**
     * Allows the program to minimize to tray.
     */
    private void minimizeToTray() {
        // -----------------------------------------------------------------
        // Minimize to system tray on Windows / supported OS's
        // -----------------------------------------------------------------
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon.png"));
            TrayIcon trayIcon = new TrayIcon(image, "BGS-Soundboard");
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setVisible(true);
                    tray.remove(trayIcon);
                    setState(NORMAL);
                }
            });
            addWindowStateListener(e -> {
                // If minimized put in-tray
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                    } catch (AWTException ignored) {
                    }
                }

                // Both make sure that when it is pulled from system tray it opens fully.
                if (e.getNewState() == MAXIMIZED_BOTH) {
                    tray.remove(trayIcon);
                    setVisible(true);
                }
                if (e.getNewState() == NORMAL) {
                    tray.remove(trayIcon);
                    setVisible(true);
                }
            });
            boolean openToTrayExist = Boolean.parseBoolean(Objects.requireNonNull(Util.getSettingsFile()).getProperty("openToTray"));
            if (openToTrayExist) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                } catch (AWTException e) {
                    setVisible(true);
                    JOptionPane.showMessageDialog(null, "Failed to open to tray.");
                }
            } else {
                setVisible(true);
            }
        } else {
            setVisible(true);
        }
    }

    /**
     * @return String[] of all files in the sound-config directory.
     */
    private String[] getFileList() {
        File file = new File(Util.getSoundDirectory());
        return file.list();
    }
}