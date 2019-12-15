<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="/jquery-3.2.1.min.js"></script>
    <title>代码生成器</title>
</head>
<body>
<div>代码生成</div>
<form id="form" action="#">
    <label>
        接口模板文件：<input type="file" name="file">
        <a href="/interfaceTemplate.xlsx">下载</a>
    </label>
    <br/>
    <label>
        项目名：<input type="text" name="projectName">
    </label>
    <br/>
    <label>
        包名前缀：<input type="text" name="packageNamePrefix">
    </label>
    <br/>
    <label>
        工程前缀：<input type="text" name="projectPrefix">
    </label>
    <br/>
    <label>
        接口前缀：<input type="text" name="interfacePrefix">
    </label>
    <br/>
    <label>
        接口描述：<input type="text" name="interfaceDesc">
    </label>
    <br/>
    <label>
        创建人：<input type="text" name="creatorName">
    </label>
    <br/>
    <label>
        文件保存路径前缀：<input type="text" name="filePathPrefix">
    </label>
    <br/>
    <input type="button" onclick="generateCode()" value="生成">
</form>
</body>
<script>
    function generateCode() {
        $.ajax({
            type: "POST",
            url: "/interfaceCodeGenerate",
            data: new FormData(document.querySelector("#form")),
            processData: false,  // 不处理数据
            contentType: false,   // 不设置内容类型
            success: function (data) {
                alert(JSON.stringify(data));
            },
            error: function (resp, ajaxOptions, thrownError) {
                alert(JSON.stringify(resp));
            }
        });
    }
</script>
</html>