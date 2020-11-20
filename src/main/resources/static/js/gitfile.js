//config的设置是全局的
layui.config({
    base: '../js/examples/dist/' //假设这是你存放拓展模块的根目录
}).use(['form', 'jstree'], function () {
    var form = layui.form,
        jstree = layui.jstree;
    var $ = layui.$;
    selectListData('group', '/common/getWorkspaces', null);

    // inline data demo

    function createTree(workspaceId) {
        var config = {
            "core": {
                "multiple": false,
                "animation": 0,
                "check_callback": true,
                "themes": {"stripes": true, 'responsive': false},
                'data':
                    {
                        "url": "/jstree/getTree?workspaceId=" + workspaceId,
                        "dataType": "json" // needed only if you do not supply JSON headers
                    }
            },
            'contextmenu': {
                'items': function (node) {
                    var tmp = $.jstree.defaults.contextmenu.items();
                    delete tmp.ccp;
                    delete tmp.create.action;
                    tmp.create.label = "New";
                    tmp.create.submenu = {
                        "create_folder": {
                            "separator_after": true,
                            "label": "Folder",
                            "action": function (data) {
                                var inst = $.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                inst.create_node(obj, {type: "default"}, "last", function (new_node) {
                                    setTimeout(function () {
                                        inst.edit(new_node);
                                    }, 0);
                                });
                            }
                        },
                        "create_file": {
                            "label": "File",
                            "action": function (data) {
                                var inst = $.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                inst.create_node(obj, {type: "file"}, "last", function (new_node) {
                                    setTimeout(function () {
                                        inst.edit(new_node);
                                    }, 0);
                                });
                            }
                        }
                    };
                    if (this.get_type(node) === "file") {
                        delete tmp.create;
                    }
                    return tmp;
                }
            },
            "types": {
                "root": {
                    "icon": "/static/3.3.10/assets/images/tree_icon.png",
                    "valid_children": ["default"]
                },
                "default": {
                    "valid_children": ["default", "file"]
                },
                "file": {
                    "icon": "glyphicon glyphicon-file",
                    "valid_children": []
                }
            },
            "plugins": [
                "contextmenu", "search",
                "types", "wholerow"
            ]
        }
        if ($('#data').jstree(true)) {
            $('#data').jstree(true).destroy();
        }
        $('#data').jstree(config).on('select_node.jstree', function (e, data) {
            $('#node_id').val(data.node.id);
            $('#node_type').val(data.node.type);
            if (data.node.type != 'file') {
                let arr = data.node.parents;

                let path = '';
                for (let i = arr.length - 1; i >= 0; i--) {
                    if (arr[i] != "#") {
                        path = path + "/" + $("#data").jstree('get_node', arr[i]).text;
                    }
                }

                $("#mergely").mergely('rhs', "当前目录：" + path + "/" + data.node.text);
                $("#mergely").mergely('lhs', "当前目录：" + path + "/" + data.node.text);
                $("#mergely").mergely('cm', 'rhs').options.readOnly = 'nocursor';
                $("#mergely").mergely('cm', 'lhs').options.readOnly = 'nocursor';
            } else {
                $("#mergely").mergely('lhs', data.node.data != null ? data.node.data.oldContent : '');
                $("#mergely").mergely('rhs', data.node.data != null ? data.node.data.newContent : '');
                $("#mergely").mergely('cm', 'rhs').options.readOnly = false;
                $("#mergely").mergely('cm', 'lhs').options.readOnly = 'nocursor';
            }


        }).on('delete_node.jstree', function (e, data) {
            //删除时需要判断文件在本地是否存在，如果不存在，不用管。存在的文件路径需要保存下来
            var pattern = /^[0-9]*$/;
            if (pattern.test(data.node.id)) {
                //如果ID为纯数字，则为本地存在的文件
                let path = data.node.data.oldPath;
                layui.sessionData('deleteData', {
                    key: data.node.id
                    , value: path
                });
            }
        }).on('create_node.jstree', function (e, data) {

        }).on('rename_node.jstree', function (e, data) {
            let node = data.node;

            //新增节点
            if (node.data == null) {
                let parentNode = $("#data").jstree('get_node', node.parent);
                let parentPath = parentNode.data.newPath;
                let content = {
                    'newContent': '',
                    'oldContent': '',
                    'newPath': parentPath + "/" + node.text,
                    'oldPath': '',
                    'newText': node.text,
                    'oldText': ''
                };
                node.data = content;

            }
            //修改文件名称
            var pattern = /^[0-9]*$/;
            if (pattern.test(node.id) && node.type == 'file') {
                //表示文件已在本地存在
                // layui.sessionData('updateData', {
                //     key: node.id
                //     , value: path
                // });
                let path = node.data.newPath;
                let content = {
                    'newContent': node.data.newContent,
                    'oldContent': node.data.oldContent,
                    'newPath': path.replace("/" + data.old, "/" + data.text),
                    'oldPath': node.data.oldPath,
                    'newText': node.text,
                    'oldText': node.data.oldText
                }
                node.data = content;
            }
            //修改目录名称
            let children = data.node.children_d;
            if (children.length > 0) {
                //判断是否有子节点
                for (let i in children) {
                    let node = $("#data").jstree('get_node', children[i]);
                    let path = node.data.newPath;
                    let content = {
                        'newContent': node.data.newContent,
                        'oldContent': node.data.oldContent,
                        'oldText': node.data.oldText,
                        'newText': node.data.newText,
                        'oldPath': node.data.oldPath,
                        'newPath': path.replace("/" + data.old + "/", "/" + data.text + "/")
                    }
                    node.data = content;
                    if (node.type == 'file' && node.data.oldPath != node.data.newPath) {
                        let style = {"style": "color:#4169E1"};
                        node.a_attr = style;
                        data.instance.set_text(node, node.text)
                    }
                }
            }
            //文件的text不相等了，加颜色标明
            if (node.type == 'file' && node.data.oldText != node.data.newText) {
                let style = {"style": "color:#4169E1"};
                node.a_attr = style;
                data.instance.set_text(node, node.text)
            }

        }).on("changed.jstree", function (e, data) {
            let node = $("#data").jstree('get_node', $('#node_id').val());
            if (node != null && node.type == 'file') {
                let form2 = document.getElementById("mergely");
                // form2.addEventListener('DOMNodeInserted', function () {
                $("#mergely").bind('DOMNodeInserted', function (e) {
                    node = $("#data").jstree('get_node', $('#node_id').val());
                    let right = $("#mergely").mergely('get', 'rhs');
                    let left = $("#mergely").mergely('get', 'lhs');
                    //先判断文件名是否相等，如果不等，不用再判断内容是否相等
                    if (node.data != null && node.data.oldText != node.data.newText) {
                        let style = {"style": "color:#4169E1"};
                        node.a_attr = style;
                        data.instance.set_text(node, node.text);
                    } else {
                        if (left != right) {
                            let style = {"style": "color:#4169E1"};
                            node.a_attr = style;
                            data.instance.set_text(node, node.text);
                        } else {
                            let style = {};
                            node.a_attr = style;
                            data.instance.set_text(node, node.text);
                        }
                    }

                })
            }
        }).on('copy_node.jstree', function (e, data) {
            console.log(data);
        }).on('copy.jstree', function (e, data) {
            console.log(data);
        }).on('cut.jstree', function (e, data) {
            console.log(data);
        }).on('paste.jstree', function (e, data) {
            console.log(data);
        });
    }


    $("#search_from").submit(function (e) {
        e.preventDefault();
        $("#data").jstree(true).search($("#search_content").val());
    });

    $('#mergely').mergely({
        cmsettings: {
            readOnly: false,
            lineWrapping: true
        },
        wrap_lines: true,
        editor_width: 'calc(50% - 25px)',
        editor_height: '100%'

    });
    $("#mergely").mergely('cm', 'rhs').options.readOnly = 'nocursor';
    $("#mergely").mergely('cm', 'lhs').options.readOnly = 'nocursor';
    form.on('select(group)', function (data) {
        selectIterationListData('iteration', '/common/getIterations', null);

    })
    var form1 = document.getElementById("mergely");


    form1.addEventListener("focus", function (event) {

    }, true);
    form1.addEventListener("blur", function (event) {
        // let nodes = $("#data").jstree("get_checked");
        let node = $("#data").jstree('get_node', $('#node_id').val());
        let right = $("#mergely").mergely('get', 'rhs');
        if (node.data != null) {
            let content = {
                'newContent': right,
                'oldContent': node.data.oldContent,
                'oldPath': node.data.oldPath,
                'oldText': node.data.oldText,
                'newPath': node.data.newPath,
                'newText': node.data.newText
            };
            node.data = content;
        }


    }, true);


    function selectListData(select_id, url, value) {
        $.post(url, function (data) {
            if (select_id.indexOf('#') != 0) {
                select_id = '#' + select_id;
            }
            $(select_id).empty();
            $(select_id).append("<option value=''> 请选择 </option >");
            let data1 = data.data;
            for (let k in data1) {
                $(select_id).append("<option value='" + data1[k].workspaceId + "'>" + data1[k].name + "</option>");
            }
            if (value != undefined && value != null && value != '') {
                $(select_id).val(value);
            }
            form.render();

        })
    }

    function selectIterationListData(select_id, url, value) {
        if ($('#group').val() == '' || $('#group').val() == undefined) {
            if (select_id.indexOf('#') != 0) {
                select_id = '#' + select_id;
            }
            $(select_id).empty();
            $(select_id).append("<option value=''> 请选择 </option >");
            $(select_id).val('');
            form.render();
            return;
        }
        createTree($('#group').val());
        $.post(url, {
            workspaceId: $('#group').val()
        }, function (data) {
            if (select_id.indexOf('#') != 0) {
                select_id = '#' + select_id;
            }
            let data1 = data.data;
            let l = data1.length;
            $(select_id).empty();
            $(select_id).append("<option value='" + data1[l - 1].iterationId + "' selected>" + data1[l - 1].name + " </option >");
            for (let k = 0; k < data1.length - 1; k++) {
                $(select_id).append("<option value='" + data1[k].iterationId + "'>" + data1[k].name + "</option>");
            }
            if (value != undefined && value != null && value != '') {
                $(select_id).val(value);
            }
            form.render();

        });
    }

    $('#push').click(function () {
        //找到所有修改过的节点
        // $.each($("#data").jstree('full').find("li"), function (index, element)
        // {
        //     console.log($(element));
        // });
        //找删除的节点
        let deleteData = layui.sessionData('deleteData');
        let deleteArr = []
        for (const key in deleteData) {
            deleteArr.push(deleteData[key]);
            layui.sessionData('deleteData', {
                key: key
                , remove: true
            });
        }

        let arr = $('#data').jstree()._model.data;
        let commitArr = []
        for (const key in arr) {
            let node = arr[key];
            if (node.type == 'file' && node.a_attr != undefined && node.a_attr.style != undefined) {
                let file = {
                    'newPath': node.data.newPath,
                    'oldPath': node.data.oldPath,
                    'newContent': node.data.newContent,
                    'oldContent': node.data.oldContent
                };
                commitArr.push(file);
            }
        }
        if (commitArr.length == 0 && deleteArr.length == 0) {
            layer.msg("无变更记录");
            return false;
        }
        let commitMsg = $('#commit_msg').val();
        if ($('#group').val().trim() == '' || $('#group').val().trim() == null || $('#group').val().trim() == '请选择') {
            layer.msg("事业组不能为空");
            return false;
        }
        if ($('#iteration').val().trim() == '' || $('#iteration').val().trim() == null || $('#iteration').val().trim() == '请选择') {
            layer.msg("迭代不能为空");
            return false;
        }
        if ($('#commit_msg').val().trim() == '' || $('#commit_msg').val().trim() == null) {
            layer.msg("提交信息不能为空");
            return false;
        }
        layer.confirm('确认发布变更吗？', function (index) {
            layer.close(index);

            var load1 = layer.load(0, {
                shade: [0.4, '#000'],
                content: '发布变更中，请等待...',
                success: function (layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '40px',//图标与样式会重合，这样设置可以错开
                        'width': '200px'//文字显示的宽度
                    });
                }
            }); //添加laoding,0-2两种
            let data = {
                commitMsg: commitMsg,
                workspaceId: $('#group').val().trim(),
                iterationName: $('#iteration option:selected').text().trim(),
                groupName: $('#group option:selected').text().trim(),
                commitArr: commitArr,
                deleteArr: deleteArr
            }
            $.ajax({
                url: '/jstree/pushConfig',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (res) {
                    if (res.code == 200) {
                        $('#commit_msg').val('');
                        $("#mergely").mergely('lhs', '');
                        $("#mergely").mergely('rhs', '');
                        $("#mergely").mergely('cm', 'rhs').options.readOnly = 'nocursor';
                        $('#iteration').val('');
                        $('#group').val('');
                        $('#file_name').html('');
                        form.render();
                        $('#data').jstree(true).refresh();

                        layer.close(load1);
                        layer.msg("发布变更成功");
                    }
                }
                , error: function () {
                    layer.close(load1);
                }
            });
        })

    });
})