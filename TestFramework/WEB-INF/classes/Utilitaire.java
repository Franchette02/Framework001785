package etu1785.framework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Utilitaire {
    public static String getUrl(String urlInput, String ctx) {
        ctx += "/";
        int ctxIndex = urlInput.indexOf(ctx);
        return urlInput.substring(ctxIndex + ctx.length());
    }

    public static List<Class<?>> getAllClasses(String path, String tomPath) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(path);

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                List<Class<?>> innerClasses = getAllClasses(file.getAbsolutePath(), tomPath);
                classes.addAll(innerClasses);
            } else if (file.getName().endsWith(".class")) {
                String absolutePath = file.getPath().replace("\\", "/");
                int tomPathIndex = absolutePath.indexOf(tomPath);

                String className = absolutePath.substring(tomPathIndex + tomPath.length())
                        .replace(".class", "")
                        .replace("/", ".");
                Class<?> clazz = Class.forName(className);

                classes.add(clazz);
            }
        }
        return classes;
    }

    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                File directory = new File(URLDecoder.decode(resource.getFile(), "UTF-8"));
                if (directory.isDirectory()) {
                    for (File file : directory.listFiles()) {
                        String fileName = file.getName();
                        if (fileName.endsWith(".class")) {
                            String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
                            classes.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        return classes;
    }
}
