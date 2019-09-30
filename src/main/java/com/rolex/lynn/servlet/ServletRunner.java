package com.rolex.lynn.servlet;

import com.rolex.lynn.beans.BeanUtils;
import com.rolex.lynn.filter.FilterLoader;
import com.rolex.lynn.filter.FilterProcessor;
import com.rolex.lynn.filter.GenericFilter;
import com.sun.tools.javac.jvm.Gen;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@Slf4j
public class ServletRunner {
    private static final String BASE_PACKAGE = "com.rolex.lynn.filter";

    public void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        log.info("ServletRunner.init()");
        putFilters();
    }

    public void pre() {
        log.info("ServletRunner.pre()");
        FilterProcessor.getInstance().pre();
    }

    public void routing() {
        log.info("ServletRunner.routing()");
        FilterProcessor.getInstance().routing();
    }

    public void post() {
        log.info("ServletRunner.post()");
        FilterProcessor.getInstance().post();
    }

    public void putFilters() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 从文件或jar读取所有类，判断是否是GenericFilter的子类
        List<String> list = new ArrayList<>();
        List<String> classes = doScan(BASE_PACKAGE, list, false);
        for (String className : classes) {
            Class clazz = BeanUtils.loadClass(className).get();
            Object obj = clazz.newInstance();
            if (obj instanceof GenericFilter) {
                String type = ((GenericFilter) obj).filterType();
                FilterLoader.putFilter(type, (GenericFilter) obj);
            }
        }

    }

    public List<String> doScan(String basePackage, List<String> nameList, boolean includeInnerClass) throws IOException {
        ClassLoader cl = GenericFilter.class.getClassLoader();
        String splashPath = dotToSplash(basePackage);

        URL url = cl.getResource(splashPath);
        if (url != null) {
            String filePath = getRootPath(url);
            List<String> names;
            if (isJarFile(filePath)) {
                names = readFromJarFile(filePath, splashPath);
            } else {
                names = readFromDirectory(filePath);
            }

            for (String name : names) {
                if (isClassFile(name)) {
                    if (includeInnerClass) {
                        nameList.add(toFullyQualifiedName(name, basePackage));
                    } else {
                        if (!isInnerClassFile(name)) {
                            nameList.add(toFullyQualifiedName(name, basePackage));
                        }
                    }
                } else {
                    doScan(basePackage + "." + name, nameList, includeInnerClass);
                }
            }
        }
        return nameList;
    }

    private static String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(trimExtension(shortName));

        return sb.toString();
    }

    public static String trimExtension(String name) {
        int pos = name.lastIndexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }
        return name;
    }

    private static boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    private static List readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private static List readFromJarFile(String jarPath, String splashedPackageName) throws IOException {

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                name = name.replace("/", ".").substring(splashedPackageName.length() + 1, name.length());
                nameList.add(name);
            }
            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private static boolean isInnerClassFile(String name) {
        return name.contains("$");
    }

    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }
}
