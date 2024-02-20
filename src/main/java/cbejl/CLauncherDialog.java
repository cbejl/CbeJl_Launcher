package cbejl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

public class CLauncherDialog extends JDialog {
    private JPanel panel;
    private Map<String, JComponentsGetters> componentMap;
    private Map<String, Boolean> checkboxesMap;
    private Map<String, String> stringsMap;
    private Map<String, Integer> integerMap;
    private boolean isDone;

    public CLauncherDialog(Map<String, JComponentsGetters> componentMap) {
        this(componentMap, Main.L_NAME, Main.L_WIDTH, Main.L_HEIGHT);
    }

    public CLauncherDialog(Map<String, JComponentsGetters> componentMap, String title) {
        this(componentMap, title, Main.L_WIDTH, Main.L_HEIGHT);
    }


    public CLauncherDialog(Map<String, JComponentsGetters> componentMap, int width, int height) {
        this(componentMap, Main.L_NAME, width, height);
    }

    public CLauncherDialog(Map<String, JComponentsGetters> componentMap, String title, int width, int height) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension dimension = toolkit.getScreenSize();
        this.componentMap = componentMap;
        this.setTitle(title);
        this.setSize(width, height);
        this.setLocation((dimension.width/2) - width/2, (dimension.height/2) - height/2);
        this.checkboxesMap = new TreeMap<>();
        this.stringsMap = new TreeMap<>();
        this.integerMap = new TreeMap<>();
        this.panel = new JPanel();
        mapEmptynator();    //создаем пустые мапы, на случай если пользователь ничего не укажет
        launchLauncher();
    }

    private void launchLauncher() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        componentGenerator();       //генерируем компоненты

        JButton wDone = new JButton("ПУСК");
        wDone.addActionListener(e -> {
            isDone = true;                  //обрабатываем кнопку завершения настройки
            this.setVisible(false);
            this.dispose();
        });

        this.add(panel);
        this.add(wDone, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void componentGenerator() {         //генерируем компоненты
        this.componentMap.forEach((key, value) -> {
            if(value instanceof JComponents.CheckBox) {
                JCheckBox jCheckBox = new JCheckBox(value.getLabel(), value.getDefaultState());
                jCheckBox.addItemListener(e -> checkboxesMap.put(key, e.getStateChange() == ItemEvent.SELECTED));
                panel.add(jCheckBox);
            } else if (value instanceof JComponents.Label) {
                panel.add(new JLabel(value.getLabel()));

            } else if (value instanceof JComponents.FileChooser) {
                JPanel jp = new JPanel();
                JTextField tf = new JTextField(24);
                tf.setToolTipText(key);
                JButton jb = new JButton(value.getLabel());
                jb.addActionListener(e -> {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle(value.getLabel());
                    if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                        tf.setText(fc.getSelectedFile() + "");
                        stringsMap.put(key, tf.getText());
                    }
                });
                jp.add(tf);
                jp.add(jb);
                jp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                panel.add(jp);

            } else if (value instanceof JComponents.TextField) {
                JPanel jp = new JPanel();
                jp.setLayout(new BorderLayout());
                JLabel jl = new JLabel(value.getLabel());
                JTextField tf = new JTextField( 16);
                tf.setToolTipText(key);
                tf.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if(value.getMaxLength() <= tf.getText().length()) {
                            e.consume();
                        }
                        if(value.getDefaultState()) {
                            char c = e.getKeyChar();
                            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                                e.consume();  //если указано не число - игнорируем нажатие
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if(value.getDefaultState()) {
                            integerMap.put(key, Integer.valueOf(tf.getText()));
                        } else {
                            stringsMap.put(key, tf.getText());
                        }
                    }
                });
                jp.add(jl, BorderLayout.NORTH);
                jp.add(tf, BorderLayout.CENTER);
                jp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                panel.add(jp);
            }
        });
    }

    private void mapEmptynator() {      //создаем пустые мапы, на случай если пользователь ничего не укажет
        componentMap.forEach((key, value) -> {
            if(value instanceof JComponents.CheckBox) {
                checkboxesMap.put(key, value.getDefaultState());
            } else if (value instanceof JComponents.FileChooser || (value instanceof JComponents.TextField && !value.getDefaultState())) {
                stringsMap.put(key, "");
            } else if (value instanceof JComponents.TextField && value.getDefaultState()) {
                integerMap.put(key, 0);
            }
        });
    }

    public Map<String, Boolean> getCheckboxesMap() {
        return checkboxesMap;
    }

    public Map<String, String> getStringsMap() {
        return stringsMap;
    }

    public Map<String, Integer> getIntegerMap() {
        return integerMap;
    }

    public boolean isDone() {
        return isDone;
    }
}
