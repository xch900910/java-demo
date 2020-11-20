package com.example.day.jstree.model;

import lombok.Data;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-10-30 17:50
 **/
@Data
public class JSTreeFileModel {
    private String oldPath;
    private String newPath;
    private String newContent;
    private String oldContent;
}
