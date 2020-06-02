package org.iMage.plugins;

import java.util.*;

/**
 * Knows all available plug-ins and is responsible for using the service loader API to detect them.
 *
 * @author Dominik Fuchss
 */
public final class PluginManagement {


    /**
     * No constructor for utility class.
     */
    private PluginManagement() {
        throw new IllegalAccessError();
    }

    /**
     * Return an {@link Iterable} Object with all available {@link PluginForJmjrst PluginForJmjrsts}
     * sorted alphabetically according to their name. In case of equally named Plugins sort them by
     * the number of parameters they have (less first).
     *
     * @return an {@link Iterable} Object containing all available plug-ins
     */
    public static Iterable<PluginForJmjrst> getPlugins() {
        ServiceLoader<PluginForJmjrst> loader = ServiceLoader.load(PluginForJmjrst.class);
        Iterator<PluginForJmjrst> plugins = loader.iterator();

        List<PluginForJmjrst> list = new ArrayList<>();
        while (plugins.hasNext()) {
            list.add(plugins.next());
        }

        Collections.sort(list);

        return list;
    }

}
