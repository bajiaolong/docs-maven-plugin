package com.github.ka1ka.util.docs.plugin;

import org.apache.maven.model.Model;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 删除文档 默认只删除当前版本
 *
 * @author 05697-LongLiHua
 * @version Id: GenerateMojo.java, v 0.1 2021/1/22 10:29  LongLiHua Exp $
 * @Description
 */
@Mojo(name = "clean")
public class CleanMojo extends AbstractMojo {
    /**
     * 是否集成到项目中  是-默认在static中
     */
    @Parameter(property = "impload", defaultValue = "false")
    private Boolean impload;

    /**
     * 是否集成到项目中  是-默认在static中
     */
    @Parameter(property = "outFilePath")
    private File file;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        clean();
    }

    /**
     * 获取doc文件
     *
     * @param
     * @return void
     **/
    private void clean() {
        Map<String, Object> pluginContextMap = (ConcurrentHashMap<String, Object>) this.getPluginContext();
        Iterator<String> iterator = pluginContextMap.keySet().iterator();
        while (iterator.hasNext()) {
            //获取项目信息
            if ("project".equals(iterator.next())) {
                MavenProject mavenProject = (MavenProject) pluginContextMap.get("project");

                //获取model信息
                Model model = mavenProject.getModel();
                String version = "V" + model.getVersion();
                String docPath = "";
                if (impload) {
                    if (file.isDirectory()) {
                        docPath = file.toString() + "//docs//" + version;//生成API 文档所在目录
                    }
                } else {
                    docPath = mavenProject.getBasedir().toString() + "//src//main//resources//static//docs//" + version;
                }


                delFolder(docPath);
                /*File versionFile = new File(docPath);
                boolean delete=false;
                if (versionFile.isDirectory()) {
                    delete = versionFile.delete();
                }*/

            }
        }
    }


    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下的所有文件
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
    