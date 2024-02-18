package cbejl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.TreeMap;

public class CLauncherWindow extends JFrame{

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final Dimension dimension = toolkit.getScreenSize();
    private Map<String, JComponents> componentMap;
    private Map<String, Boolean> checkboxesMap;

    public CLauncherWindow(Map<String, JComponents> componentMap) {
        this(componentMap, Main.L_NAME, Main.L_WIDTH, Main.L_HEIGHT);
    }

    public CLauncherWindow(Map<String, JComponents> componentMap, String title) {
        this(componentMap, title, Main.L_WIDTH, Main.L_HEIGHT);
    }


    public CLauncherWindow(Map<String, JComponents> componentMap, int width, int height) {
        this(componentMap, Main.L_NAME, width, height);
    }

    public CLauncherWindow(Map<String, JComponents> componentMap, String title, int width, int height) {
        this.componentMap = componentMap;
        this.setTitle(title);
        this.setSize(width, height);
        this.setLocation((dimension.width/2) - width/2, (dimension.height/2) - height/2);
        this.checkboxesMap = new TreeMap<>();
        mapEmptynator();
        launchLauncher();
    }

    public void launchLauncher() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        this.componentMap.forEach((key, value) -> {
            switch (value) {
                case LABEL -> {
                    panel.add(new JLabel(key));
                }
                case CHECKBOX -> {
                    JCheckBox jCheckBox = new JCheckBox(key);
                    jCheckBox.addItemListener(e -> checkboxesMap.put(key, e.getStateChange() == ItemEvent.SELECTED));
                    panel.add(jCheckBox);
                }
            }
        });
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(panel);
        this.setVisible(true);
    }

    private void mapEmptynator() {
        componentMap.forEach((key, value) -> {
            switch (value) {
                case CHECKBOX -> checkboxesMap.put(key, false);
            }
        });
    }

    public Map<String, Boolean> getCheckboxesMap() {
        return checkboxesMap;
    }

}
