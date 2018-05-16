var keyword = {};

keyword.oTable = null;

keyword.oTableAoColumns = [
    {'mData': 'content'},
    {'mData': 'person'},
    {'mData': 'location'},
    {'mData': 'org'},
    {'mData': 'company'}
];

keyword.keywordList = function () {
    if (keyword.oTable == null) {
        keyword.oTable = $('#keywordTable').dataTable({
            'paging': true,
            'info': true,
            'searching': false,
            'pageLength': 15,
            'lengthChange': false,
            'ordering': false,
            'serverSide': true,
            'sAjaxSource': CONTEXT_PATH + '/getTrainData',
            'columns': keyword.oTableAoColumns,
            'pagingType': 'full_numbers',
            'oLanguage': {
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sInfo": "从 _START_ 条到 _END_ 条/共 _TOTAL_ 条数据",
                "sInfoEmpty": "没有数据",
                "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                "sZeroRecords": "没有检索到数据",
                "sSearch": "名称:",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上一页",
                    "sNext": "下一页",
                    "sLast": "尾页"
                }
            }
        });
    } else {
        keyword.oTable._fnDraw();
    }
};


$(function () {
    keyword.keywordList();
});