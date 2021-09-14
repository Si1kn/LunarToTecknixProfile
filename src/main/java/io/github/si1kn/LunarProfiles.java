package io.github.si1kn;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LunarProfiles {

    public List<File> loadProfiles() {


        File[] directories = new File("C:\\Users\\" + System.getProperty("user.name") + "\\.lunarclient\\settings\\game").listFiles(File::isDirectory);

        if (directories != null)
            return new ArrayList<>(Arrays.asList(directories));
        else {
            return new ArrayList<>();
        }
    }
}
