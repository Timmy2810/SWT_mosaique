import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;

import javax.swing.*;
import java.util.List;
import java.util.Random;

import org.kohsuke.MetaInfServices;

@MetaInfServices
public class JavaCrashCoursePlugin extends PluginForJmjrst {

    private String name;
    private int numberOfParaemters;
    private Main m;
    private boolean configureable;
    private List javaReleases;

    public JavaCrashCoursePlugin() {
        javaReleases = List.of("8", "9", "10", "11", "12", "13", "14");
        numberOfParaemters = javaReleases.size();
        configureable = true;
        name = "JavaCrashCourse";
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
        System.out.println("Found " + this.getNumberOfParameters() + " Java versions since Java 8");
    }

    @Override
    public void run() {
        Random rn = new Random();
        int random = rn.nextInt(javaReleases.size()) + 8;

        System.out.println(
            switch (random) {
                case 14 -> "Keeping updates";
                case 13, 12, 11, 10, 9, 8 -> "Running late";
                default -> this.getName() + "(" + this.getNumberOfParameters() + ")";
            }
        );
    }

    @Override
    public boolean isConfigurable() {
        return configureable;
    }

    @Override
    public void configure() {
        StringBuilder builder = new StringBuilder();
        javaReleases.stream().forEach(n -> builder.append("Java " + n + "\n"));

        JOptionPane.showMessageDialog(null, builder.toString());
    }


}
