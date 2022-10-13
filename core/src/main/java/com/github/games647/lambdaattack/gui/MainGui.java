package com.github.games647.lambdaattack.gui;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.GameVersion;
import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.Options;
import com.github.games647.lambdaattack.logging.LogHandler;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;

public class MainGui {

    private final JFrame frame = new JFrame(LambdaAttack.PROJECT_NAME);

    private final LambdaAttack botManager;

    public MainGui(LambdaAttack botManager) {
        this.botManager = botManager;

        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLookAndFeel();
        setupPanels();
        this.frame.pack();
        this.frame.setVisible(true);

        LambdaAttack.getLogger().info("Starting program");
    }

    private void setupPanels() {
        // Top panel
        JPanel topPanel = new JPanel();
        frame.add(topPanel, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Host: "));
        JTextField hostInput = new JTextField("127.0.0.1");
        topPanel.add(hostInput);

        topPanel.add(new JLabel("Port: "));
        JTextField portInput = new JTextField("25565");
        topPanel.add(portInput);

        topPanel.add(new JLabel("Join delay (ms): "));
        JSpinner delay = new JSpinner();
        delay.setValue(1000);
        topPanel.add(delay);

        topPanel.add(new JLabel("Auto Register: "));
        JCheckBox autoRegister = new JCheckBox();
        topPanel.add(autoRegister);

        topPanel.add(new JLabel("Amount: "));
        JSpinner amount = new JSpinner();
        amount.setValue(20);
        topPanel.add(amount);

        topPanel.add(new JLabel("NameFormat: "));
        JTextField nameFormat = new JTextField("AbstractBot-%d");
        topPanel.add(nameFormat);

        JComboBox<String> versionBox = new JComboBox<>();
        Arrays.stream(GameVersion.values())
                .sorted(Comparator.reverseOrder())
                .map(GameVersion::getVersion)
                .forEach(versionBox::addItem);

        topPanel.add(versionBox);

        JButton loadNames = new JButton("Load Names");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("", "txt"));
        loadNames.addActionListener(new LoadNamesListener(botManager, frame, fileChooser));

        topPanel.add(loadNames);

        JButton loadProxies = new JButton("Load proxies");

        loadProxies.addActionListener(new LoadProxiesListener(botManager, frame, fileChooser));

        topPanel.add(loadProxies);

        // Console panel
        JScrollPane consolePane = new JScrollPane();
        frame.add(consolePane, BorderLayout.CENTER);
        consolePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        consolePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTextArea logArea = new JTextArea(10, 1);
        consolePane.getViewport().setView(logArea);

        LambdaAttack.getLogger().addHandler(new LogHandler(logArea));

        // Command panel
        JPanel commandPanel = new JPanel();
        frame.add(commandPanel, BorderLayout.PAGE_END);

        JTextField commandInput = new JTextField(60);
        ActionListener commandListener = actionEvent -> {
            String message = commandInput.getText();
            if (message == null || message.isEmpty()) return;
            botManager.getClients().forEach(client -> client.sendMessage(message));
            commandInput.setText("");
        };
        commandInput.addActionListener(commandListener);
        commandPanel.add(commandInput);

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton clearButton = new JButton("Clear");
        commandPanel.add(startButton);
        commandPanel.add(stopButton);
        commandPanel.add(clearButton);

        startButton.addActionListener(action -> {
            // collect the botOptions on the gui thread
            // for thread-safety
            Options options = new Options(
                    GameVersion.findByName((String) versionBox.getSelectedItem()),
                    hostInput.getText(),
                    Integer.parseInt(portInput.getText()),
                    (int) amount.getValue(),
                    (int) delay.getValue(),
                    nameFormat.getText(),
                    new BotOptions(
                            autoRegister.isSelected()
                    )
            );

            botManager.getThreadPool().submit(() -> {
                try {
                    botManager.start(options);
                } catch (Exception ex) {
                    LambdaAttack.getLogger().log(Level.INFO, ex.getMessage(), ex);
                }
            });
        });

        stopButton.addActionListener(action -> botManager.stop());

        clearButton.addActionListener(action -> logArea.setText(""));
    }

    private void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LambdaAttack.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}
