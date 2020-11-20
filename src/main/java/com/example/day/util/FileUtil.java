package com.example.day.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-09-02 13:56
 **/
@Slf4j
public class FileUtil {

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        line = bufferedReader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        inputStream.close();
    }

    public static String readFile(String filePath) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        readToBuffer(stringBuffer, filePath);
        return stringBuffer.toString();
    }

    /**
     * 往文件写入新内容
     *
     * @param fileName
     * @param content
     */
    public static void writeFileNewContent(String fileName, String content, String localPath, String gitBasePath) {
        log.info("filename:{},content:{}", fileName, content);
        File file = null;
        String os = System.getProperty("os.name").toLowerCase();
        OutputStreamWriter outputStreamWriter = null;
        try {
            if (os.indexOf("windows") >= 0) {
                file = new File(fileName);
                log.info("文件路径：{},文件内容：{}", fileName, content);
                if (!file.exists()) {
                    createFile(fileName);
                }

            } else {
                String s = fileName.replaceAll("\\\\", "/");
                String path = localPath + s.substring(s.indexOf(gitBasePath) + 1);
                file = new File(path);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                log.info("文件路径：" + path);
            }

            if (file.isFile()) {
                FileUtils.writeStringToFile(file, "");
                FileWriter fileWriter = new FileWriter(file, true);
                content = content + System.getProperty("line.separator");
                fileWriter.write(content);
                fileWriter.flush();
                fileWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {


        }

    }

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }


    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (!createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }

}
