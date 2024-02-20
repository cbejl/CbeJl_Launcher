package cbejl;

class JComponents {

    static class CheckBox implements JComponentsGetters {
        private boolean triggered;
        private String label;

        public CheckBox() {
            this("", false);
        }

        public CheckBox(String label) {
            this(label, false);
        }

        public CheckBox(String label, boolean triggered) {
            this.triggered = triggered;
            this.label = label;
        }

        @Override
        public boolean getDefaultState() {
            return triggered;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public int getMaxLength() {
            return -1;
        }
    }

    static class Label implements JComponentsGetters {
        private String label;
        public Label(String label) {
            this.label = label;
        }

        @Override
        public boolean getDefaultState() {
            return false;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public int getMaxLength() {
            return -1;
        }
    }

    static class FileChooser implements JComponentsGetters {
        private String label;
        public FileChooser(String buttonLabel) {
            this.label = buttonLabel;
        }

        @Override
        public boolean getDefaultState() {
            return false;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public int getMaxLength() {
            return -1;
        }
    }

    static class TextField implements JComponentsGetters {
        private String label;
        private int maxSymbols;
        private boolean isNumeric;

        public TextField() {
            this("", -1);
        }
        public TextField(int maxSymbols) {
            this("", maxSymbols);
        }

        public TextField(String text) {
            this(text, -1);
        }
        public TextField(String text, int maxSymbols) {
            this.label = text;
            this.maxSymbols = maxSymbols;
            this.isNumeric = false;
        }
        public TextField(boolean isNumeric) {
            this("", -1, isNumeric);
        }
        public TextField(int maxSymbols, boolean isNumeric) {
            this("", maxSymbols, isNumeric);
        }
        public TextField(String text, boolean isNumeric) {
            this(text, -1, isNumeric);
        }
        public TextField(String text, int maxSymbols, boolean isNumeric) {
            this.label = text;
            this.maxSymbols = maxSymbols;
            this.isNumeric = isNumeric;
        }

        @Override
        public boolean getDefaultState() {
            return isNumeric;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public int getMaxLength() {
            return maxSymbols;
        }
    }

/*
    LABEL,
    FILE_CHOOSER,
    TEXT_FIELD,
    INT_FIELD,*/

}

interface JComponentsGetters {
    boolean getDefaultState();
    String getLabel();
    int getMaxLength();
}
