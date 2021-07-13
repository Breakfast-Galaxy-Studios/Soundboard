package net.breakfaststudios;

import com.sun.javafx.application.PlatformImpl;
import javafx.stage.FileChooser;
import net.breakfaststudios.util.Updater;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.SoundBoard;
import net.breakfaststudios.soundboard.listeners.GlobalKeyListener;
import net.breakfaststudios.soundboard.listeners.KeybindRecorder;
import net.breakfaststudios.util.Converter;
import net.breakfaststudios.util.SoundManager;
import net.breakfaststudios.util.Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.json.JSONObject;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;



public class BreakfastSounds extends JFrame {

    // For every release make sure this is changed. It should correspond to the github tag for the release.
    private static final String currentVersion = "v1.1.1";


    private static final String os = Util.os;
    public static String SELECTED_AUDIO_DEVICE;
    private static SoundBoard soundBoard;
    private static NativeKeyListener listener;
    private JDialog soundAddMenu;
    private JDialog settingsPopup;
    private JDialog recordKeybindDialog;

    /**
     * Creates the UI and init's listeners.
     */

    public BreakfastSounds() {
        initComponents();
    }

    public static void main(String[] args) {
        soundBoard = new SoundBoard();

        /**
         * Make sure autoupdater is deleted if it exist.
         * This is to minimize security risk.
         */

        if(Files.exists(Path.of(Util.getMainDirectory() + "autoupdater.jar"))){
            try {
                Files.delete(Path.of(Util.getMainDirectory() + "autoupdater.jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
            Settings validation - Yes this is a bit messy and can be optimized
            This was designed to be implemented in a hotfix
            TODO: Optimize this chunk of code.
         */
        if (!Files.exists(Path.of(Util.getMainDirectory() + "settings.properties"))){
            String soundOutput = "Primary Sound Driver";
            Util.updateSettings(soundOutput, false, false);
        } else {
            Properties settings = Util.getSettingsFile();
            Properties validateSettings = new Properties();
            if (settings.getProperty("openToTray") == null){
                validateSettings.setProperty("openToTray", String.valueOf(false));
            } else {
                validateSettings.setProperty("openToTray", settings.getProperty("openToTray"));
            }
            if(settings.getProperty("keyCompatMode") == null){
                validateSettings.setProperty("keyCompatMode", String.valueOf(false));
            } else {
                validateSettings.setProperty("keyCompatMode", settings.getProperty("keyCompatMode"));
            }
            if (settings.getProperty("soundOutput") == null){
                validateSettings.setProperty("soundOutput", "Primary Sound Driver");
            } else {
                validateSettings.setProperty("soundOutput", settings.getProperty("soundOutput"));
            }

            if (!validateSettings.equals(settings)){
                Util.updateSettings(validateSettings.getProperty("soundOutput"), Boolean.parseBoolean(validateSettings.getProperty("keyCompatMode")), Boolean.parseBoolean(validateSettings.getProperty("openToTray")));
            }
        }


        // UI Scaling will be slightly messed up outside of a Windows OS.
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore){
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (Exception ignored) { }
        }


        // Create and display the UI
        makeAppDir();
        EventQueue.invokeLater(() -> new BreakfastSounds());

        //Register native hook so we can actually listen for keystrokes
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(53);
        }

        //Load some stuff from settings
        Properties prop = Util.getSettingsFile();
        if (prop != null) {
            SELECTED_AUDIO_DEVICE = prop.getProperty("soundOutput");
        } else {
            // Makes new config if it doesn't exist
            String soundOutput = "Primary Sound Driver";
            Util.updateSettings(soundOutput, false, false);
            Properties newProp = Util.getSettingsFile();
            if (newProp != null) {
                SELECTED_AUDIO_DEVICE = newProp.getProperty("soundOutput");
            } else {
                // If it still doesn't exist there is a problem.
                JOptionPane.showMessageDialog(null, "Fatal error when reading file. \nPlease report this error to the Github Repo.");
                System.exit(125);
            }
        }


        //Disable annoying logger output
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);


        //Register key listeners
        listener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(listener);


        // -----------------------------------------------------------------
        // Auto Updater
        // -----------------------------------------------------------------

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.github.com/repos/Breakfast-Galaxy-Studios/Soundboard/releases/latest");
            request.addHeader("accept", "application/vnd.github.v3+json");
            CloseableHttpResponse result = httpClient.execute(request);
            String js = EntityUtils.toString(result.getEntity(), "UTF-8");
            result.close();
            httpClient.close();
            JSONObject json = new JSONObject(js);
            String version = json.getString("tag_name");
            if (!version.equals(currentVersion)){
                JOptionPane updatePrompt = new JOptionPane("");
                updatePrompt.setMessageType(JOptionPane.YES_NO_OPTION);
                updatePrompt.setVisible(true);
                int updateResult = JOptionPane.showConfirmDialog(null,"A new update is available. Would you like to update?", "New Soundboard Update Available",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(updateResult == JOptionPane.YES_OPTION){
                    Updater.updater(version);
                }
            }
        } catch (IOException ignored) { }

    }

    private static void makeAppDir() {
        Path soundDir = Paths.get(Util.getSoundDirectory());
        if (!Files.exists(soundDir)) {
            try {
                Files.createDirectories(soundDir);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fatal error when creating dir. \nError is as follows:\n" + e + "\nPlease report this error to the Github Repo.");
                System.exit(3);
            }
        }

    }

    public static SoundBoard getSoundBoard() {
        return soundBoard;
    }

    private void initComponents() {
        soundAddMenu = new JDialog();
        settingsPopup = new JDialog();
        recordKeybindDialog = new JDialog();
        JLabel keyboardCompatLabel = new JLabel();
        JCheckBox keyboardCompatCheckbox = new JCheckBox();
        JPanel jPanel1 = new JPanel();
        JButton cancelAddSound = new JButton();
        JLabel fileLabel = new JLabel();
        JTextField newSoundNameField = new JTextField();
        JTextField newKeybindField = new JTextField();
        JSlider volumeSlider = new JSlider();
        JTextField newSoundFileField = new JTextField();
        JLabel keybindLabel = new JLabel();
        JButton recordKeybind = new JButton();
        JButton confirmAddSound = new JButton();
        JLabel volumeLabel = new JLabel();
        JButton fileAdd = new JButton();
        JLabel nameLabel = new JLabel();
        JPanel jPanel2 = new JPanel();
        JScrollPane tablePane = new JScrollPane();
        JTable soundTable = new JTable();
        JButton addButton = new JButton();
        JButton removeButton = new JButton();
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu();
        JTextField hiddenTextField = new JTextField();
        JPanel settingsPanel = new JPanel();
        JLabel soundOutputLabel = new JLabel();
        JComboBox<String> soundOutputDropdown = new JComboBox<>();
        JButton ConfirmSettings = new JButton();
        JButton cancelSettings = new JButton();
        JPanel recordKeybindPanel = new JPanel();
        JLabel openToTray = new JLabel("Open To Tray:");
        JCheckBox openToTrayCheckbox = new JCheckBox();




        hiddenTextField.setEditable(false);
        hiddenTextField.setVisible(false);

        settingsPopup.setTitle("Settings");
        settingsPopup.setAlwaysOnTop(false);
        settingsPopup.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        settingsPopup.setMaximumSize(new Dimension(350, 175));
        settingsPopup.setMinimumSize(new Dimension(350, 175));
        settingsPopup.setPreferredSize(new Dimension(350, 175));
        settingsPopup.setResizable(false);

        soundAddMenu.setTitle("Add Sound");
        soundAddMenu.setAlwaysOnTop(false);
        soundAddMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        soundAddMenu.setMaximumSize(new Dimension(350, 255));
        soundAddMenu.setMinimumSize(new Dimension(350, 255));
        soundAddMenu.setPreferredSize(new Dimension(350, 255));
        soundAddMenu.setResizable(false);

        jPanel1.setMaximumSize(new Dimension(350, 255));
        jPanel1.setMinimumSize(new Dimension(350, 255));
        jPanel1.setPreferredSize(new Dimension(350, 255));

        recordKeybindDialog.setMaximumSize(new Dimension(200, 115));
        recordKeybindDialog.setMinimumSize(new Dimension(200, 115));
        recordKeybindDialog.setPreferredSize(new Dimension(200, 115));
        recordKeybindDialog.setResizable(false);
        cancelAddSound.setText("Cancel");
        fileLabel.setText("File:");
        newSoundNameField.setMaximumSize(new Dimension(7, 20));
        volumeSlider.setMaximumSize(new Dimension(36, 26));
        newSoundFileField.setEditable(false);
        keybindLabel.setText("Key/s:");
        recordKeybind.setText("jButton1");
        confirmAddSound.setText("Confirm");
        volumeLabel.setText("Volume:");
        fileAdd.setText("jButton1");
        nameLabel.setText("Name:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newSoundNameField, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newSoundFileField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fileAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(keybindLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(newKeybindField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(recordKeybind, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(confirmAddSound)
                                                .addGap(10, 10, 10)
                                                .addComponent(cancelAddSound)))
                                .addGap(39, 39, 39))
                        .addComponent(hiddenTextField, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(newSoundNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newSoundFileField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(fileAdd, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(keybindLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newKeybindField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(recordKeybind, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        soundAddMenuLayout.setVerticalGroup(
                soundAddMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );


        soundOutputDropdown.setModel(new DefaultComboBoxModel<>(new String[]{SELECTED_AUDIO_DEVICE}));
        ConfirmSettings.setText("Confirm");
        soundOutputLabel.setText("Sound Output:");
        keyboardCompatLabel.setText("Keybind Recording Compatibility mode:");
        cancelSettings.setText("Cancel");
        JButton recordKeybindCancel = new JButton("Cancel");
        JLabel recordKeybindLabel = new JLabel("Recording.....");
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
                                                        .addComponent(openToTray)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(openToTrayCheckbox)
                                                        .addGap(0,0,32767))
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
                        .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(ConfirmSettings)
                                .addComponent(cancelSettings))
                        .addContainerGap()));

        settingsPopup.add(settingsPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("BGS Soundboard");

        DefaultTableModel soundTableModel = new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Name", "Keybind"
                }
        ) {
            final boolean[] canEdit = new boolean[]{
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        soundTable.setModel(soundTableModel);
        tablePane.setViewportView(soundTable);
        if (soundTable.getColumnModel().getColumnCount() > 0) {
            soundTable.getColumnModel().getColumn(0).setResizable(false);
            soundTable.getColumnModel().getColumn(1).setResizable(false);
        }


        addButton.setText("Add...");
        addButton.setMaximumSize(new Dimension(83, 23));
        addButton.setMinimumSize(new Dimension(83, 23));
        addButton.setPreferredSize(new Dimension(83, 23));


        removeButton.setText("Remove...");

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

        settingsMenu.setText("Settings");
        menuBar.add(settingsMenu);
        JMenuItem settingsMenuItem = new JMenuItem("Settings...");
        settingsMenu.add(settingsMenuItem);
        setJMenuBar(menuBar);

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
        newKeybindField.setEditable(false);
        volumeSlider.setValue(100);
        recordKeybindDialog.setVisible(false);
        keyboardCompatLabel.setToolTipText("If recording keybinds constantly records keys that aren't pressed, or doesn't record certain keys, turn this on.");


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
        settingsMenuItem.addActionListener(e -> {
            Properties settings = Util.getSettingsFile();
            if (settings != null) {
                soundOutputDropdown.setSelectedItem(settings.getProperty("soundOutput"));
                keyboardCompatCheckbox.setSelected(Boolean.parseBoolean(settings.getProperty("keyCompatMode")));
                openToTrayCheckbox.setSelected(Boolean.parseBoolean(settings.getProperty("openToTray")));
            }
            settingsPopup.setVisible(true);
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
            Util.updateSettings(soundOutput, keyboardCompatCheckbox.isSelected(), openToTrayCheckbox.isSelected());
            SELECTED_AUDIO_DEVICE = soundOutput;
            settingsPopup.setVisible(false);
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
                    if(!f.exists()) {
                        file.close();
                        new File(Util.getSoundDirectory() + s).delete();
                        throw new Exception();
                    }

                    float volume = Float.parseFloat(prop.getProperty("volume"));

                    StringBuilder keyBind = new StringBuilder();


                    //Convert keybind text to Integer array and parse the text of the keybind column
                    ArrayList<Integer> keys = new ArrayList<>();
                    for (String key : rawKeybinds.split("_")) {
                        if (key.equalsIgnoreCase("none"))
                            break;

                        int toAdd = Integer.parseInt(key);
                        keys.add(toAdd);
                        keyBind.append(NativeKeyEvent.getKeyText(toAdd)).append(" + ");
                    }

                    // ArrayList to Array Conversion.
                    Integer[] arr = new Integer[keys.size()];
                    for (int i = 0; i < keys.size(); i++)
                        arr[i] = keys.get(i);

                    String todo = keyBind.toString();
                    soundTableModel.addRow(new Object[]{name, todo.substring(0, todo.length() - 3)});
                    soundBoard.addSound(new Sound(name, path, arr, volume));
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
            soundAddMenu.setVisible(true);
            // Make sure all fields are cleared
            newSoundNameField.setText("");
            newKeybindField.setText("");
            hiddenTextField.setText("");
            newSoundFileField.setText("");
        });

        removeButton.addActionListener(e -> {
            try {
                int column = 0;
                int row = soundTable.getSelectedRow();
                String value = soundTable.getModel().getValueAt(row, column).toString();
                if (SoundManager.removeSound(Util.getSoundDirectory(), value + ".properties")) {
                    soundTableModel.removeRow(row);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete that sound.");
                }
            } catch (Exception ignored) {
            }
        });


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

                if (!newSoundFileField.getText().equals("") || !hiddenTextField.getText().equals("")) {
                    StringBuilder rawCodes = new StringBuilder();
                    StringBuilder keyBind = new StringBuilder();

                    for (String character : keybindField.split("_")) {
                        rawCodes.append(Converter.getKeyCode(character)).append("_");
                    }
                    String newRawCodes = rawCodes.substring(0, rawCodes.length() - 1);

                    ArrayList<Integer> keys = new ArrayList<>();
                    for (String key : newRawCodes.split("_")) {
                        if (key.equalsIgnoreCase("none"))
                            break;

                        int toAdd = Integer.parseInt(key);
                        keys.add(toAdd);
                        keyBind.append(NativeKeyEvent.getKeyText(toAdd)).append(" + ");
                    }

                    // ArrayList to Array Conversion.
                    Integer[] arr = new Integer[keys.size()];
                    for (int i = 0; i < keys.size(); i++) {
                        arr[i] = keys.get(i);
                    }

                    String todo = keyBind.toString();
                    SoundManager.createNewSound(newSoundNameField.getText(), hiddenTextField.getText(), newRawCodes, volumeSlider.getValue());
                    soundBoard.addSound(new Sound(newSoundNameField.getText(), hiddenTextField.getText(), arr, volumeSlider.getValue() / (float) 100));
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

            public void keyPressed(KeyEvent e) {
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


        // All things to do with putting app to system tray, and sets the window visible.
        minimizeToTray();
    }

    private void minimizeToTray(){
        // -----------------------------------------------------------------
        // Minimize to system tray on windows / supported OS's
        // -----------------------------------------------------------------
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
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
                // If minimized put in tray
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                    } catch (AWTException ignored) {
                    }
                }
                /* Might be necessary on other OS
                if(e.getNewState()==7){
                    try{
                        tray.add(trayIcon);
                        setVisible(false);
                    }catch(AWTException ignored){ }
                }
                */

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
            boolean openToTrayExist = Boolean.parseBoolean(Util.getSettingsFile().getProperty("openToTray"));
            if (openToTrayExist){
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

    private void resetKeyListener() {
        GlobalScreen.removeNativeKeyListener(listener);
        listener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(listener);
    }

    private String[] getFileList() {
        File file = new File(Util.getSoundDirectory());
        return file.list();
    }
}
