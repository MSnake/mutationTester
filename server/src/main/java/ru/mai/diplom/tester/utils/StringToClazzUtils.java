package ru.mai.diplom.tester.utils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Утилита для преобразования строки исходного кода к класс
 */
public class StringToClazzUtils {

    /**
     * Компиляция класса
     *
     * @param packageName имя пакета
     * @param className   имя класса
     * @param classBody   содержимое класса
     */
    private static void compileClass(String packageName, String className,
                                     String classBody) throws IOException {
        String path = packageName;
        path = path.replace(".", File.separator);

        String absSrcPath = StringToClazzUtils.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();

        path = absSrcPath + File.separator + path;

        // формирование древовидной структуры папок(аналог формирования пакетов)
        String dirName = absSrcPath;
        for (String subdirName : packageName.split("\\.")) {
            dirName += File.separator + subdirName.trim();
            File dir = new File(dirName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        // Формирование временного файла класса с исходным кодом
        File sourceFile = new File(path + File.separator + className + ".java");
        sourceFile.createNewFile();

        FileWriter writer = new FileWriter(sourceFile);
        writer.write(classBody);
        writer.close();

        JavaCompiler jCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = jCompiler.getStandardFileManager(
                null, null, null);

        fileManager.setLocation(StandardLocation.SOURCE_PATH,
                Arrays.asList(new File("" + StandardLocation.SOURCE_PATH)));
        // компиляция файла
        jCompiler.getTask(
                null,
                fileManager,
                null,
                null,
                null,
                fileManager.getJavaFileObjectsFromFiles(Arrays
                        .asList(sourceFile))).call();
        fileManager.close();

        // удаление временного фалйа класса с исходным кодом
        sourceFile.deleteOnExit();

    }

    /**
     * Загрузка класса из строки
     *
     * @param classBody строка с содержимым класса
     */
    public static Class<?> load(String classBody) throws IOException {
        String className = parseClassName(classBody);
        String packageName = parsePackageName(classBody);
        compileClass(packageName, className, classBody);
        return loadClass(packageName + (packageName.isEmpty() ? "" : ".") + className);
    }

    /**
     * Загрузка класса из строки с указанным именем пакета
     *
     * @param classBody   строка с содержимым класса
     * @param packageName название пакета
     */
    public static Class<?> load(String classBody, String packageName) throws IOException {
        String className = parseClassName(classBody);
        compileClass(packageName, className, classBody);
        return loadClass(packageName + (packageName.isEmpty() ? "" : ".") + className);
    }


    /**
     * Получение имени пакета из стоки исходного кода класса
     *
     * @param classBody строка с содержимым класса
     */
    private static String parsePackageName(String classBody) {
        String pkgName = "";
        for (String pkgKeyword : new String[]{"package"}) {
            int index = classBody.indexOf(pkgKeyword);
            if (index != -1) {
                pkgName = classBody.substring(index + pkgKeyword.length(),
                        classBody.indexOf(";", index)).trim();
            }
        }
        return pkgName;
    }

    /**
     * Получение имени класса из стоки исходного кода класса
     *
     * @param classBody строка с содержимым класса
     */
    private static String parseClassName(String classBody) {
        String className = null;
        for (String classKeyword : new String[]{"class", "interface", "enum"}) {
            int index = classBody.indexOf(classKeyword);
            if (index != -1) {
                className = classBody.substring(index + classKeyword.length(),
                        classBody.indexOf("{", index));
                className = className.trim().split("\\s+")[0];
                break;
            }
        }
        return className;
    }

    /**
     * Получение экземпляра скомпилированного класса из контекста приложения
     *
     * @param className название класса
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}