package io.github.si1kn;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class LunarToTecknixConverter {
    public LunarToTecknixConverter(String tecknixProfileName, List<File> profileList, int selectedProfileAsInt) {
        Gson gson = new Gson();
        for (File file : profileList.get(selectedProfileAsInt).listFiles())
            if (file.getName().equals("mods.json")) {
                try {
                    List<String> tecknixModules = setupTecknixProfiles(new File("C:\\Users\\si1kn\\AppData\\Roaming\\.minecraft\\Tecknix\\profiles-1.8.9\\default.cfg"));
                    List<String> blackListedMods = new ArrayList<>();

                    blackListedMods.add("skyblockAddons");
                    Map<String, Mod> map = setupLunarModules(blackListedMods, file);


                    TecknixFileWriter fileWriter = new TecknixFileWriter(tecknixProfileName + ".cfg");

                    for (String tecknixModule : tecknixModules)
                        if (!tecknixModule.equals("GlobalSettingsModule")) {
                            fileWriter.append("---" + tecknixModule + "---\n");
                            //x
                            fileWriter.append(tecknixModule + "-X-:" + 0.0 + "\n");
                            //y
                            fileWriter.append(tecknixModule + "-Y-:" + 0.0 + "\n");
                            //size
                            fileWriter.append(tecknixModule + "-Size-:" + 1.0 + "\n");
                            //state
                            fileWriter.append(tecknixModule + "-State-:" + true + "\n");
                            //background
                            fileWriter.append(tecknixModule + "Setting-Background-:" + "\n");
                            //brackets
                            //chroma
                            //shadow

                            //Set stuff that lunar doesnt have as default tecknix settings
                            fileWriter.append(tecknixModule + "-Setting-Border-Radius:0.0\n" +
                                    tecknixModule + "-Setting-Box-Width:20.0\n" +
                                    tecknixModule + "-Setting-Box-Height:1.8");

                            fileWriter.append(tecknixModule + "\n" +
                                    "\n");
                        }

                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    private Map<String, Mod> setupLunarModules(List<String> blackListedMods, File file) {
        Gson gson = new Gson();
        Map<String, Mod> map = new HashMap<>();

        Type listType = new TypeToken<Map<String, Mod>>() {
        }.getType();
        Map<String, Mod> stringModMap = null;
        try {
            stringModMap = gson.fromJson(readFile(file), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Map.Entry<String, Mod> entry : stringModMap.entrySet())
            for (String blackListedMod : blackListedMods)
                if (!entry.getKey().equals(blackListedMod)) {
                    map.put(entry.getKey(), entry.getValue());
                } else {
                    System.out.println("found blacklisted mod");
                }


        return map;
    }


    public List<String> setupTecknixProfiles(File tecknixFile) {
        List<String> tecknixModules = new ArrayList<>();
        try {
            for (String s : readFile(tecknixFile).split("\\r?\\n")) {
                if (!s.equals("GlobalSettingsModule"))
                    if (s.matches("---[a-zA-Z]+---"))
                        tecknixModules.add(s.replaceAll("---", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tecknixModules;
    }

    public String readFile(File file) throws IOException {
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }


    public class Mod {
        float x, y;
        int panelIndex;
        boolean seen;
        Option options;
    }

    public class Option {
        Background background_clr_nr;
    }

    public class Background {
        long value;
        boolean chroma_bl;
    }


}
