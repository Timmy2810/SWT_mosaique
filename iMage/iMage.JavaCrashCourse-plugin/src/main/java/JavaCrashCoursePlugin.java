import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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
        Random rn = new Random();
        int random = rn.nextInt(javaReleases.size()) + 8;

        System.out.println(
        switch (random) {
            case 14 -> "Keeping updates";
            case 13 -> "Running late";
            case 12 -> "Running late";
            case 11 -> "Running late";
            case 10 -> "Running late";
            case 9 -> "Running late";
            case 8 -> "Running late";
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
        JFrame fenster = new JFrame("Java Releases");
        fenster.setSize(200, 200);
        fenster.setLocation(100, 100);

        Stream.of(javaReleases).forEach(n -> fenster.add(new JLabel("Java " + n)));

        fenster.setVisible(true);
    }


}
