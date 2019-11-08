package com.gtx.common.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author LILONGTAO
 * @date 2019-11-08
 */
public class AnnotationUtils {

    private static String BASE_PACKAGE_PATH = "com/gtx";

    private static String BASE_PACKAGE = "com.gtx";

    private static final List<Class> CLASS_LIST;

    private static final ConcurrentMap<Class, Set<Class>> ANNOTATION_MAP = new ConcurrentHashMap<>();

    static {
        CLASS_LIST = new ArrayList<>();

        List<Class<?>> classes = getClasses();
        CLASS_LIST.addAll(classes);
        for (Class<?> aClass : classes) {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                Set<Class> classSet = ANNOTATION_MAP.computeIfAbsent(annotation.annotationType(), key -> new HashSet<>());
                classSet.add(aClass);
            }
        }
    }

    /**
     * 获取同一路径下所有子类或接口实现类
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class> getAllClassByAnn(Class<?> cls) {
        return ANNOTATION_MAP.get(cls);
    }

    public static boolean hasAnn(Parameter parameter, Class ann) {
        Annotation[] annotations = parameter.getAnnotations();
        if (annotations == null) {
            return false;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == ann) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得当前类路径下的所有类
     *
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getClasses() {
        String pk = BASE_PACKAGE;
        String path = BASE_PACKAGE_PATH;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        assert url != null;
        return getClasses(new File(url.getFile()), pk);
    }

    /**
     * 迭代查找类
     *
     * @param dir
     * @param pk
     * @return
     */
    private static List<Class<?>> getClasses(File dir, String pk) {
        List<Class<?>> classes = new ArrayList<>();
        if (!dir.exists()) {
            return classes;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(getClasses(f, pk + "." + f.getName()));
            }
            String name = f.getName();
            if (name.endsWith(".class")) {
                try {
                    classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}
