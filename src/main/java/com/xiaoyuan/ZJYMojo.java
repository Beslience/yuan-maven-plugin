/**
 * @Author: zhengjiayuan
 * @Date: 2019/1/24 0:58
 * @Version 1.0
 */
package com.xiaoyuan;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Stack;

@Mojo(name = "findtype", defaultPhase = LifecyclePhase.PACKAGE)
public class ZJYMojo extends AbstractMojo {

    /**
     * 默认读project下src文件下的文件
     */
    @Parameter(property = "project.directory")
    private String path;

    /**
     * 默认判断文件类型是java
     */
    @Parameter(property = "type", defaultValue = "java")
    private String type;

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("xiaoyuan define plugin " + path);
        // 读取路径下的java文件个数
        // 递归实现
        System.out.println(type + " Files' number in project (Recursive)" + readRecursive(path, ".*?\\."+ type +"$"));
        // 非递归实现
        System.out.println(type + " Files' number in project " + read(path, ".*?\\."+ type +"$"));
    }

    /**
     * 递归实现某路径下 存在regex字符串的文件个数
     * @param path
     * @return
     */
    private int readRecursive(String path, String regex) {
        File f = new File(path);
        if (f.isFile()) {
            return f.getName().matches(regex) ? 1 : 0;
        }
        File[] childFiles = f.listFiles();
        int count = 0;
        for (File file : childFiles) {
            count += read(file.getPath(), regex);
        }
        return count;
    }

    /**
     * 非递归实现某路径下 存在regex字符串的文件个数
     * @param path
     * @param regex
     * @return
     */
    private int read(String path, String regex) {
        Stack<String> stack = new Stack<String>();
        int count = 0;
        stack.push(path);
        while (!stack.isEmpty()) {
            String newPath = stack.pop();
            File f = new File(newPath);
            if (f.isFile()) {
                count = f.getName().matches(regex) ? count + 1 : count;
            } else {
                File[] childFiles = f.listFiles();
                for (File file : childFiles) {
                    stack.push(file.getPath());
                }
            }
        }
        return count;
    }
}
