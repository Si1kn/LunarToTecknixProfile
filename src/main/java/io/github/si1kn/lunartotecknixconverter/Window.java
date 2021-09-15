package io.github.si1kn.lunartotecknixconverter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Window {

    private final static Setup profiles = new Setup();


    public static Setup getProfiles() {
        return profiles;
    }

    public static void main(String[] args) {
        List<File> profile = profiles.loadProfiles();

        //Create the frame
        JFrame frame = new JFrame("Lunar to Tecknix profiles");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        //Create panel and add that to the jframe
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        //Add a label on what to-do and add that to the panel
        JLabel lbl = new JLabel("Select one of lunar profiles");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lbl);


        //Create a drop-down menu and add the profiles (folders) to the menu
        final JComboBox<String> cb = new JComboBox<>();

        for (File file : profile) {
            cb.addItem(file.getName());
        }

        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cb);


        //setup more info jlabel
        JLabel jLabel = new JLabel("Set name of new tecknix profile name");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(jLabel);


        //Setup a text field for rename of profile
        JTextField textField = new JTextField();
        textField.setColumns(20);
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton button = new JButton("Start converting");
        button.setMaximumSize(textField.getPreferredSize());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);


        //Setup button with the converter, and to add and set stuff to panel
        button.addActionListener(m -> {
            try {
                new LunarToTecknixConverter(textField.getText(), profile, cb.getSelectedIndex());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button.setVisible(true);
        textField.setVisible(true);
        panel.add(textField);
        panel.add(button);
        frame.setVisible(true);
    }
}
