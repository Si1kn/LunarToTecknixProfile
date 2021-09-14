package io.github.si1kn.lunartotecknixconverter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Used for the default tecknix FileWriter
 */
public class TecknixFileWriter {
    private final FileWriter fileWriter;

    public TecknixFileWriter(String fileName) throws IOException {
        fileWriter = new FileWriter(fileName);

        fileWriter.append("--------------------------------------\n" +
                "|     Tecknix Client Profile File    |\n" +
                "|        DO NOT EDIT THIS FILE       |\n" +
                "-------------------------------------- \n" +
                "\n");


        fileWriter.append("---GlobalSettingsModule---\n" +
                "GlobalSettingsModule-X-:0.0\n" +
                "GlobalSettingsModule-Y-:0.0\n" +
                "GlobalSettingsModule-Size-:1.0\n" +
                "GlobalSettingsModule-State-:false\n" +
                "GlobalSettingsModule-Setting-Branding-Color:13158600\n" +
                "GlobalSettingsModule-Setting-Menu-Text-Shadow:true\n" +
                "GlobalSettingsModule-Setting-Module-Snapping:true\n" +
                "GlobalSettingsModule-Setting-Gui-Backgrounds:false\n" +
                "GlobalSettingsModule-Setting-UI-Blur:true\n" +
                "GlobalSettingsModule-Setting-Debug-Hud-Text:false\n" +
                "GlobalSettingsModule-Setting-Render-Cosmetics:true\n" +
                "GlobalSettingsModule-Setting-Borderless-Window:false\n" +
                "GlobalSettingsModule-Setting-Main-Menu-Particles:false\n" +
                "GlobalSettingsModule-Setting-ViewBobbing-on-sprint:false\n" +
                "GlobalSettingsModule-Setting-Mod-Background-opacity:102.0\n" +
                "GlobalSettingsModule-Setting-Blur-Intensity:20.0\n" +
                "\n");
    }

    public void append(String s) {
        try {
            fileWriter.append(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fileWriter.append("| This config was made by Si1kn's tool LunarToTecknixProfile. https://github.com/si1kn");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}