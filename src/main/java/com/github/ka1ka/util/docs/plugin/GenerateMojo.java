package com.github.ka1ka.util.docs.plugin;

import com.github.ka1ka.util.docs.Docs;
import com.github.ka1ka.util.docs.DocsConfig;
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
 * 执行类
 *
 * @author 05697-LongLiHua
 * @version Id: GenerateMojo.java, v 0.1 2021/1/22 10:29  LongLiHua Exp $
 * @Description
 */
@Mojo(name = "generate")
public class GenerateMojo extends AbstractMojo {
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
        generateDocs();
    }

    /**
     * 获取doc文件
     *
     * @param
     * @return void
     **/
    private void generateDocs() {
        Map<String, Object> pluginContextMap = (ConcurrentHashMap<String, Object>) this.getPluginContext();
        Iterator<String> iterator = pluginContextMap.keySet().iterator();
        while (iterator.hasNext()) {
            //获取项目信息
            if ("project".equals(iterator.next())) {
                MavenProject mavenProject = (MavenProject) pluginContextMap.get("project");
                //获取model信息
                Model model = mavenProject.getModel();
                //执行生成文档操作
                DocsConfig config = new DocsConfig();
                config.setProjectPath(mavenProject.getBasedir().toString()); // 项目根目录
                config.setProjectName(model.getDescription() == null || model.getDescription() == "" ? model.getName() : model.getDescription()); //
                // 项目名称
                config.setApiVersion("V" + model.getVersion());       // 声明该API的版本
                if (impload) {
                    if (file.isDirectory()) {
                        config.setDocsPath(file.toString());//生成API 文档所在目录
                    }
                }

                config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
                try {
                    Docs.buildHtmlDocs(config); // 执行生成文档
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
    