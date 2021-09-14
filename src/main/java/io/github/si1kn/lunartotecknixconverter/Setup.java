package io.github.si1kn.lunartotecknixconverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.si1kn.lunartotecknixconverter.gson.Mod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Setup {

    public List<File> loadProfiles() {
        File[] directories = new File("C:\\Users\\" + System.getProperty("user.name") + "\\.lunarclient\\settings\\game").listFiles(File::isDirectory);

        if (directories != null)
            return new ArrayList<>(Arrays.asList(directories));
        else {
            return new ArrayList<>();
        }
    }


    private String readFile(File file) throws IOException {
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }


    public Map<String, Mod> setupMapForModules(Map<String, Mod> map, List<String> tecknixModules) {
        Map<String, Mod> map1 = new HashMap<>();
        for (Map.Entry<String, Mod> entry : map.entrySet())
            for (String tecknixModule : tecknixModules) {
                if (entry.getKey().toLowerCase(Locale.ROOT).contains(tecknixModule.replace("Module", "").toLowerCase(Locale.ROOT))) {
                    map1.put(tecknixModule, entry.getValue());
                    //       System.out.println("Found tecknix mod: " + tecknixModule + " That matches lunar module: " + entry.getKey());
                }

            }
        return map1;
    }

    public Map<String, Mod> setupLunarModules(List<String> blackListedMods, File file) {
        Gson gson = new Gson();
        Map<String, Mod> map = new HashMap<>();

        Type listType = new TypeToken<Map<String, Mod>>() {
        }.getType();
        Map<String, Mod> stringModMap = null;
        try {
            stringModMap = gson.fromJson(readFile(file), listType);
            System.out.println("Gson read correctly.");
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

}
