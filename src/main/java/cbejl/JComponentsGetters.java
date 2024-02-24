package cbejl;

public interface JComponentsGetters {
    boolean getDefaultState();

    String getLabel();

    int getMaxLength();

    <T> T[] getObjects();
}
