package com.example.day.jstree.model;

import lombok.Data;

import java.util.List;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-10-29 14:42
 **/
@Data
public class JSTreeModel {
    /**
     * 节点标题
     */
    private String text;
    /**
     * 节点唯一索引值，
     */
    private Long id;

    private Long pid;
    private String icon;
    private String type;
    private Content data;
    private List<JSTreeModel> children;
    private State state;

    @Data
    public class Content {
        private String newContent;
        private String oldContent;
        private String oldPath;
        private String newPath;
        private String oldText;
        private String newText;

    }

    @Data
    public class State {
        private boolean selected;
        private boolean opened;
        private boolean disabled;

    }
}
