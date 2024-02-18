package cbejl;

import java.util.Map;
import java.util.TreeMap;

public class Main {
    protected static final String L_NAME = "cbejl_launcher";
    protected static final int L_WIDTH = 800, L_HEIGHT = 600;
    public static void main(String[] args) {

        Map<String, JComponents> componentMap = new TreeMap<>();

        componentMap.put("Кнопка", JComponents.CHECKBOX);
        componentMap.put("Кнопка2", JComponents.CHECKBOX);
        componentMap.put("Кнопка3", JComponents.CHECKBOX);
        componentMap.put("Кнопка4", JComponents.CHECKBOX);
        componentMap.put("Кнопка5", JComponents.CHECKBOX);
        componentMap.put("Кнопка6", JComponents.CHECKBOX);
        componentMap.put("Кнопка7", JComponents.CHECKBOX);
        componentMap.put("Кнопка8", JComponents.CHECKBOX);

        CLauncherWindow clw = new CLauncherWindow(componentMap);
    }
}