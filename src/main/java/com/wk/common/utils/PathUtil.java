package com.wk.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class PathUtil {
    private static final String SPEA = File.separator;

    /**
     * 调试时输出：D:\workspace\copyfilesfromjar
     * windows jar包输出： D:\workspace\copyfilesfromjar\target
     * linux jar包输出：/home/dongweihang
     *
     * @return 返回jar执行目录
     */
    private static String getApplicationClassPath() {
        return System.getProperty("user.dir");
    }

    /**
     * @return 返回resources目录（需要提前把jar包中的文件解压到系统目录中）
     */
    public static String getResourcePath() {
        File file = new File(getApplicationClassPath());
        if (file.exists() && file.isDirectory() && file.listFiles() != null) {
            String jarFileName = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .map(File::getName).filter(s -> s.contains(".jar")).findFirst().orElse(null);
            String resourcePath;
            if (jarFileName != null) {
                //如果目录下包含jar ，说明是用jar包运行的，先用jar -xf解压
                try {
                    Process process = Runtime.getRuntime().exec("jar -xf " + jarFileName);
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    log.error("can not exec jar -xf", e);
                    return null;
                }
                resourcePath = file.getAbsolutePath().concat(SPEA).concat("BOOT-INF").concat(SPEA).concat("classes").concat(SPEA);
            } else {
                //不包含jar，说明在调试模式中 后接\target\classes
                resourcePath = file.getAbsolutePath().concat(SPEA).concat("target").concat(SPEA).concat("classes").concat(SPEA);
            }
            return resourcePath;
        }
        return null;
    }
}