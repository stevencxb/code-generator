$(function() {
    load();
});

function load() {
    $('#tables')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                iconSize : 'outline',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                url : "/getTableList", // 服务器数据的加载地址
                //search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        pageNum: this.pageNumber,
                        pageSize:this.pageSize,
                        tableName:$("#tableName").val()
                    };
                },
                columns : [
                    {
                        field : 'tableName',
                        title : '表名',
                        align: "center"
                    },
                    {
                        field : 'engine',
                        title : '引擎',
                        align: "center"
                    },
                    {
                        field : 'tableComment',
                        title : '注释',
                        align: "center"
                    },
                    {
                        field : 'createTime',
                        title : '创建时间',
                        align: "center"
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var gen = '<a class="btn btn-danger btn-sm " href="#" mce_href="#" title="生成" onclick="gen(\''+ row.tableName+ '\')"><i class="fa fa-chain-broken "></i></a> ';
                            return gen;
                        }
                    }
                ]
            });
}

function reLoad() {
    $('#tables').bootstrapTable('refresh');
}

function gen(tableName) {
    location.href = "/code/" + tableName;
}