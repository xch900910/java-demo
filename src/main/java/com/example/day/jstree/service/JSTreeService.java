package com.example.day.jstree.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.day.jstree.model.JSTreeFileModel;
import com.example.day.jstree.model.JSTreeModel;
import com.example.day.jstree.model.JSTreePushInModel;
import com.example.day.util.FileUtil;
import com.example.day.util.GitUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-10-29 14:44
 **/
@Service
@Slf4j
public class JSTreeService {
    @Value("${git.localPath}")
    private String localPath;
    @Value("${git.basePath}")
    private String basePath;

    @Autowired
    private GitUtil gitUtil;
    private final static String os = System.getProperty("os.name").toLowerCase();
    private static List<JSTreeModel> node = new LinkedList();


    /**
     * 获取jstree
     *
     * @return
     */
    public JSONArray getTree(Integer workspaceId) {
        node = new LinkedList<>();
        try {
            gitUtil.cleanAndPull();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(localPath);
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        //获取目录生成树
        long level = 1;
        path = path + basePath;
        log.info("生成树根目录：{}", path);
        List<JSTreeModel> JSTreeModels = recursiveFiles(path, 100L, level);

        return JSONArray.parseArray(JSONObject.toJSONString(JSTreeModels));
    }

    /**
     * 推送到git
     */
    public void pushConfig(JSTreePushInModel inModel) {
        List<JSTreeFileModel> jsTreeFileModels = inModel.getCommitArr();
        List<String> deleteArr = inModel.getDeleteArr();
        for (String s : deleteArr) {
            deleteFile(s);
        }
        for (JSTreeFileModel model : jsTreeFileModels) {
            if (!StringUtils.isEmpty(model.getOldPath()) && !model.getOldPath().equals(model.getNewPath())) {
                //旧路径不为空，且与新路径不相等，则为重命名修改，需要将旧路径删除
                deleteFile(model.getOldPath());
            }
            String filePath = model.getNewPath();
            try {
                FileUtil.writeFileNewContent(filePath, model.getNewContent(), localPath, basePath);
//                log.info("提交到本地成功,filePath:{}", filePath);
            } catch (Exception e) {
                //失败需要将旧文件反写
                FileUtil.writeFileNewContent(filePath, model.getOldContent(), localPath, basePath);
                e.printStackTrace();
            }

        }
        gitUtil.push(inModel.getCommitMsg(), ".", inModel.getWorkspaceId(), inModel.getIterationName(), inModel.getGroupName());
        log.info("push到GIT完成，推送信息：" + inModel.getCommitMsg());
    }

    /**
     * Xiwi
     * 遍历文件/文件夹 - 函数
     * [String]path        文件路径
     */
    private List<JSTreeModel> recursiveFiles(String path, long id, long pid) {
        File file = new File(path);
        File files[] = file.listFiles();
        // 对象为空 直接返回
        if (files == null) {
            return null;
        }
        // 目录下文件
        if (files.length == 0) {
            return node;
        }
        // 存在文件 遍历 判断
        for (int i = 0; i < files.length; i++) {
            JSTreeModel treeVo = new JSTreeModel();
            treeVo.setText(files[i].getName());
            treeVo.setId(id);
            treeVo.setPid(pid);
            String filePath = files[i].getPath().replaceAll("\\\\", "/");
            // 判断是否为 文件夹
            if (files[i].isDirectory()) {
                JSTreeModel.Content content = treeVo.new Content();
                if (String.valueOf(pid).length() < 5) {
                    JSTreeModel.State state = treeVo.new State();
                    state.setOpened(true);
                    treeVo.setState(state);
                }
                content.setOldPath(filePath);
                content.setNewPath(filePath);
                treeVo.setData(content);
                node.add(treeVo);
                // 为 文件夹继续遍历
                recursiveFiles(files[i].getPath(), id * 100 + 1 + i, id);
                id++;
                // 判断是否为 文件
            } else if (files[i].isFile()) {
                String s = null;
                try {
                    s = FileUtil.readFile(files[i].getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                node.add(treeVo);
                treeVo.setIcon("glyphicon glyphicon-file");
                treeVo.setType("file");
                JSTreeModel.Content content = treeVo.new Content();
                content.setNewContent(s);
                content.setOldContent(s);
                content.setOldPath(filePath);
                content.setNewPath(filePath);
                content.setOldText(files[i].getName());
                content.setNewText(files[i].getName());
                treeVo.setData(content);

                id++;
            } else {
            }
        }
        return node;
    }

    private List<JSTreeModel> formToZtreeNode(List<JSTreeModel> list, Long parent) {
        List<JSTreeModel> out = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPid().equals(parent)) {
                List<JSTreeModel> children = formToZtreeNode(list, list.get(i).getId());
                if (children.size() > 0) {
                    list.get(i).setChildren(children);
                }
                out.add(list.get(i));
            }
        }
        return out;
    }


    private void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else if (file.isFile()) {
                file.delete();
            }
            log.info("删除文件，filePath：{}", file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
