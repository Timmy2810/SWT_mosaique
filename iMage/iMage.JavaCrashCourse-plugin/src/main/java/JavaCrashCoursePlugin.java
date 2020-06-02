import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;

import java.util.List;

public class JavaCrashCoursePlugin extends PluginForJmjrst {

    private String name;
    private int numberOfParaemters;
    private Main m;

    public JavaCrashCoursePlugin() {
        List javaReleases = List.of(
            "Java SE 8", "Java SE 9", "Java SE 10", "Java SE 11", "Java SE 12", "Java SE 13", "Java SE 14");
        numberOfParaemters = javaReleases.size();
    }

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
