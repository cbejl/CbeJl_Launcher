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
    private Map<String, JComponents> componentMap;
    private Map<String, Boolean> checkboxesMap;
    private Map<String, String> stringsMap;
    private Map<String, Integer> integerMap;
    private boolean isDone;

    public CLauncherDialog(Map<String, JComponents> componentMap) {
        this(componentMap, Main.L_NAME, Main.L_WIDTH, Main.L_HEIGHT);
    }

    public CLauncherDialog(Map<String, JComponents> componentMap, String title) {
        this(componentMap, title, Main.L_WIDTH, Main.L_HEIGHT);
    }


    public CLauncherDialog(Map<String, JComponents> componentMap, int width, int height) {
        this(componentMap, Main.L_NAME, width, height);
    }

    public CLauncherDialog(Map<String, JComponents> componentMap, String title, int width, int height) {
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
            switch (value) {
                case LABEL -> {
                    panel.add(new JLabel(key));
                }
                case CHECKBOX -> {
                    JCheckBox jCheckBox = new JCheckBox(key);
                    jCheckBox.addItemListener(e -> checkboxesMap.put(key, e.getStateChange() == ItemEvent.SELECTED));
                    panel.add(jCheckBox);
                }
                case FILE_CHOOSER -> {
                    JPanel jp = new JPanel();
                    JTextField tf = new JTextField(key,24);
                    tf.setToolTipText(key);
                    JButton jb = new JButton(key);
                    jb.addActionListener(e -> {
                        JFileChooser fc = new JFileChooser();
                        fc.setDialogTitle(key);
                        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                            tf.setText(fc.getSelectedFile() + "");
                            stringsMap.put(key, tf.getText() == key ? null : tf.getText());
                        }
                    });
                    jp.add(tf);
                    jp.add(jb);
                    jp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                    panel.add(jp);
                }
                case TEXT_FIELD -> {
                    JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    JLabel jl = new JLabel(key);
                    JTextField tf = new JTextField( 16);
                    tf.setToolTipText(key);
                    JButton jb = new JButton("save");
                    jb.addActionListener(e -> {
                        stringsMap.put(key, tf.getText());
                    });
                    jp.add(jl, BorderLayout.NORTH);
                    jp.add(tf, BorderLayout.CENTER);
                    jp.add(jb, BorderLayout.EAST);
                    jp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                    panel.add(jp);
                }
                case INT_FIELD -> {
                    JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    JLabel jl = new JLabel(key);
                    JTextField tf = new JTextField();
                    tf.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                                e.consume();  //если указано не число - игнорируем нажатие
                            }
                        }
                    });
                    tf.setColumns(8);
                    tf.setToolTipText(key);
                    JButton jb = new JButton("save");
                    jb.addActionListener(e -> {
                        integerMap.put(key, Integer.valueOf(tf.getText()));
                    });
                    jp.add(jl, BorderLayout.NORTH);
                    jp.add(tf, BorderLayout.CENTER);
                    jp.add(jb, BorderLayout.EAST);
                    jp.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                    panel.add(jp);
                }
            }
        });
    }

    private void mapEmptynator() {      //создаем пустые мапы, на случай если пользователь ничего не укажет
        componentMap.forEach((key, value) -> {
            switch (value) {
                case CHECKBOX -> checkboxesMap.put(key, false);
                case FILE_CHOOSER, TEXT_FIELD -> stringsMap.put(key, null);
                case INT_FIELD -> integerMap.put(key, null);
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
