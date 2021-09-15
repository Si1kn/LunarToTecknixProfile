package io.github.si1kn.lunartotecknixconverter;


import io.github.si1kn.lunartotecknixconverter.gson.Mod;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LunarToTecknixConverter {
    public LunarToTecknixConverter(String tecknixProfileName, List<File> profileList, int selectedProfileAsInt) throws IOException {
        for (File file : profileList.get(selectedProfileAsInt).listFiles())
            if (file.getName().equals("mods.json")) {
                List<String> tecknixModules = Window.getProfiles().setupTecknixProfiles(new File("C:\\Users\\si1kn\\AppData\\Roaming\\.minecraft\\Tecknix\\profiles-1.8.9\\default.cfg"));
                Map<String, Mod> map = Window.getProfiles().setupLunarModules(new ArrayList<>(Collections.singleton("skyblockAddons")), file);
                Map<String, Mod> lunarTecknixModuleMap = Window.getProfiles().setupMapForModules(map, tecknixModules);
                TecknixFileWriter fileWriter = new TecknixFileWriter(tecknixProfileName + ".cfg");


                String[] array = {
                        "DirectionHudModule", "CrosshairModule"
                };
                for (String tecknixModule : tecknixModules)
                    if (!tecknixModule.equals("GlobalSettingsModule")) {
                        fileWriter.append("---" + tecknixModule + "---\n");

                        if (lunarTecknixModuleMap.get(tecknixModule) == null) {
                            fileWriter.append(tecknixModule + "-X-:" + 0.0 + "\n");
                            fileWriter.append(tecknixModule + "-Y-:" + 0.0 + "\n");
                            fileWriter.append(tecknixModule + "-Size-:" + 1.0 + "\n");
                            fileWriter.append(tecknixModule + "-State-:" + false + "\n");
                            fileWriter.append(tecknixModule + "-Setting-Text-Color:" + 16777215 + "\n");
                            fileWriter.append(tecknixModule + "-Setting-Chroma:" + false + "\n");
                        } else {
                            fileWriter.append(tecknixModule + "-X-:" + lunarTecknixModuleMap.get(tecknixModule).x + "\n");
                            fileWriter.append(tecknixModule + "-Y-:" + lunarTecknixModuleMap.get(tecknixModule).y + "\n");
                            fileWriter.append(tecknixModule + "-Size-:" + 1.0 + "\n");
                            fileWriter.append(tecknixModule + "-State-:" + lunarTecknixModuleMap.get(tecknixModule).seen + "\n");


                            if (!Objects.equals(tecknixModule, "DirectionHudModule") && !Objects.equals(tecknixModule, "CrosshairModule") && !Objects.equals(tecknixModule, "ArmorStatusModule") && !Objects.equals(tecknixModule, "CooldownsModule") && !Objects.equals(tecknixModule, "FreelookModule")) {
                                System.out.println(tecknixModule);
                                fileWriter.append(tecknixModule + "-Setting-Text-Color:" + lunarTecknixModuleMap.get(tecknixModule).options.background_clr_nr.value + "\n");
                                fileWriter.append(tecknixModule + "-Setting-Chroma:" + lunarTecknixModuleMap.get(tecknixModule).options.background_clr_nr.chroma_bl + "\n");
                            } else {
                                System.out.println("Found a module without a text color / chroma from lunar, default setting!");
                                fileWriter.append(tecknixModule + "-Setting-Text-Color:" + 16777215 + "\n");
                                fileWriter.append(tecknixModule + "-Setting-Chroma:" + false + "\n");
                            }

                        }

                        //Set stuff that lunar doesnt have as default tecknix settings
                        fileWriter.append(tecknixModule + "-Setting-Border-Radius:0.0\n" +
                                tecknixModule + "-Setting-Box-Width:20.0\n" +
                                tecknixModule + "-Setting-Box-Height:1.8\n" + "\n");
                    }
                fileWriter.close();
            }
    }

}
