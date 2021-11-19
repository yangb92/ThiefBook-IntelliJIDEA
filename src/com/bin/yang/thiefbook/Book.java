package com.bin.yang.thiefbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leo.yang
 * @date 2021/11/19
 */
public class Book {

    public static List<String> lines;
    public static Integer line = 0;
    public static Properties properties;
    public static File file = new File(System.getProperties().getProperty("user.home"), "Documents/book.properties");

    static {
        try {
            properties = new Properties();
            System.out.println(file.getAbsolutePath());
            properties.load(new FileInputStream(file));
            String path = properties.getProperty("path");
            line = Integer.valueOf(properties.getProperty("line", "0"));
            System.out.println(path + line);
            File book = new File(path);
            if (book.exists()) {
                FileInputStream bookFile = new FileInputStream(book);
                InputStreamReader inputStreamReader = new InputStreamReader(bookFile, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                lines = bufferedReader.lines().collect(Collectors.toList());
                bufferedReader.close();
                inputStreamReader.close();
                bookFile.close();
            } else {
                System.out.println("book is not exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void recordLine(Integer line){
        properties.setProperty("line", line + "");
        try {
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            properties.store(writer,"Book Properties");
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String nextLine(){
        if (check() != null) {
            return check();
        }
        if(line < lines.size()){
            recordLine(line++);
            return lines.get(line);
        }else {
            return lines.get(lines.size() - 1);
        }
    }

    public static String preLine(){
        if (check() != null) {
            return check();
        }
        if(line < 0){
            return lines.get(0);
        }else {
            recordLine(line--);
            return lines.get(line);
        }
    }

    public static String check(){
        if(lines == null){
            return "未设置路径: 用户目录下 Documents/book.properties 中设置, path=*** 和 line";
        }
        return null;
    }
}
