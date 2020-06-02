import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;

public class JavaCrashCoursePlugin extends PluginForJmjrst {

    private String name;
    private int numberOfParaemters;
    private Main m;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfParameters() {
        return numberOfParaemters;
    }

    @Override
    public void init(Main main) {
        this.m = main;
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public void configure() {

    }
}
