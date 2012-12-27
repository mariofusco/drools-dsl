package org.drools.dsl.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassUtil {

    public static Class[] getClasses(String packageName) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<String> dirs = new ArrayList<String>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(resource.getFile());
            }
            Collection<String> classes = new TreeSet<String>();
            for (String directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
            List<Class> classList = new ArrayList<Class>();
            for (String clazz : classes) {
                classList.add(Class.forName(clazz));
            }
            return classList.toArray(new Class[classes.size()]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Collection<String> findClasses(String directory, String packageName) throws Exception {
        Collection<String> classes = new TreeSet<String>();
        if (directory.startsWith("file:") && directory.contains("!")) {
            String[] split = directory.split("!");
            URL jar = new URL(split[0]);
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            ZipEntry entry = null;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
                    classes.add(className);
                }
            }
        }
        File dir = new File(directory);
        if (!dir.exists()) {
            return classes;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file.getAbsolutePath(), packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
            }
        }
        return classes;
    }
}
