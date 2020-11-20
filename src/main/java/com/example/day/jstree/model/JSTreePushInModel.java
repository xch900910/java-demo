package com.example.day.jstree.model;

import lombok.Data;

import java.util.List;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-10-30 18:06
 **/
@Data
public class JSTreePushInModel {
    private String commitMsg;
    private Integer workspaceId;
    private String iterationName;
    private String groupName;
    private List<JSTreeFileModel> commitArr;
    private List<String> deleteArr;
}
