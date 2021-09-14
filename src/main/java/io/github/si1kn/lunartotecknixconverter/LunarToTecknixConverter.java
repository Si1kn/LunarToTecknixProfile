package io.github.si1kn.lunartotecknixconverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.si1kn.lunartotecknixconverter.gson.Mod;
import org.apache.commons.io.FileUtils;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class LunarToTecknixConverter {
    public LunarToTecknixConverter(String tecknixProfileName, List<File> profileList, int selectedProfileAsInt) {
        for (File file : profileList.get(selectedProfileAsInt).listFiles())
            if (file.getName().equals("mods.json")) {
                try {
                    List<String> tecknixModules = Window.getProfiles().setupTecknixProfiles(new File("C:\\Users\\si1kn\\AppData\\Roaming\\.minecraft\\Tecknix\\profiles-1.8.9\\default.cfg"));
                    List<String> blackListedMods = new ArrayList<>();
                    blackListedMods.add("skyblockAddons");
                    Map<String, Mod> map = Window.getProfiles().setupLunarModules(blackListedMods, file);


                    Map<String, Mod> lunarTecknixModuleMap = Window.getProfiles().setupMapForModules(map, tecknixModules);
                    TecknixFileWriter fileWriter = new TecknixFileWriter(tecknixProfileName + ".cfg");

                    for (String tecknixModule : tecknixModules)
                        if (!tecknixModule.equals("GlobalSettingsModule")) {
                            fileWriter.append("---" + tecknixModule + "---\n");
                            //x
                            if (lunarTecknixModuleMap.get(tecknixModule) == null) {
                                fileWriter.append(tecknixModule + "-X-:" + 0.0 + "\n");
                            } else {
                                fileWriter.append(tecknixModule + "-X-:" + lunarTecknixModuleMap.get(tecknixModule).x + "\n");
                            }
                            //y
                            if (lunarTecknixModuleMap.get(tecknixModule) == null) {
                                fileWriter.append(tecknixModule + "-Y-:" + 0.0 + "\n");

                            } else {
                                fileWriter.append(tecknixModule + "-Y-:" + lunarTecknixModuleMap.get(tecknixModule).y + "\n");
                            }

                            //size
                            fileWriter.append(tecknixModule + "-Size-:" + 1.0 + "\n");

                            //state
                            if (lunarTecknixModuleMap.get(tecknixModule) == null) {
                                fileWriter.append(tecknixModule + "-State-:" + false + "\n");
                            } else {
                                fileWriter.append(tecknixModule + "-State-:" + lunarTecknixModuleMap.get(tecknixModule).seen + "\n");
                            }

                            //Set stuff that lunar doesnt have as default tecknix settings
                            fileWriter.append(tecknixModule + "-Setting-Border-Radius:0.0\n" +
                                    tecknixModule + "-Setting-Box-Width:20.0\n" +
                                    tecknixModule + "-Setting-Box-Height:1.8\n" +
                                    "\n");


                        }

                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }


}
