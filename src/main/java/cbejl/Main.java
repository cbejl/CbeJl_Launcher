package cbejl;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    protected static final String L_NAME = "cbejl_launcher";    //дефолтные значения (если не указали в конструкторе)
    protected static final int L_WIDTH = 400, L_HEIGHT = 600;

    public static void main(String[] args) throws InterruptedException {
        //ПРИМЕР

        Map<String, Integer> integerMap; //тут будут храниться итоговые значения
        Map<String, String> stringMap;
        Map<String, Boolean> checkboxesMap;
        Map<String, Object> objectsMap;

        Map<String, JComponentsGetters> componentMap = new LinkedHashMap<>(); //сюда задаем какие компоненты нам будут нужны

        componentMap.put("чекбокс", new JComponents.CheckBox());
        componentMap.put("лейбл", new JComponents.Label("Label"));
        componentMap.put("обзор", new JComponents.FileChooser("Обзор"));
        componentMap.put("текст", new JComponents.TextField("текстовое поле с ограничением в 10 символов", 10));
        componentMap.put("числа", new JComponents.TextField("числовое поле с ограничением в 12 символов", 12, true));
        componentMap.put("дропдаун", new JComponents.DropDownMenu(new String[]{"800x600", "1440x900", "1920x1080"}));
        componentMap.put("дропдаун2", new JComponents.DropDownMenu(new Integer[]{1,2,3,4,5}));

        CLauncherDialog clw = new CLauncherDialog(componentMap); //создаем лаунчер, передаем в него список компонентов

        while (true) {               //ждем пока лаунчер завершит свою работу
            if (clw.isDone()) {     //удачный исход
                integerMap = clw.getIntegerMap();
                checkboxesMap = clw.getCheckboxesMap(); //забераем значения
                stringMap = clw.getStringsMap();
                objectsMap = clw.getObjectsMap();
                break;
            } else if (!clw.isDone() && !clw.isVisible()) {
                System.exit(1);     //лаунчер закрыли. КОНЕЦ.
            } else {
                Thread.sleep(1);    //ждем
            }
        }

        System.out.println(integerMap);
        System.out.println(checkboxesMap);
        System.out.println(stringMap);
        System.out.println(objectsMap);

        System.out.println("DO IT"); //работаем
    }
}