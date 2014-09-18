<html lang="en">
<head>
    <%@ include file="commonHeader.jsp" %>

    <link rel="shortcut icon" href="${rootPath}/jslib/flatlab/img/favicon.png">

    <link href="${rootPath}/jslib/flatlab/assets/metro-ui/css/metro-bootstrap.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/assets/metro-ui/css/metro-bootstrap-responsive.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/assets/metro-ui/css/iconFont.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/assets/metro-ui/css/docs.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/assets/metro-ui/js/prettify/prettify.css" rel="stylesheet">


    <!-- Load JavaScript Libraries -->
    <script src="${rootPath}/jslib/flatlab/js/jquery.js"></script>
    <script src="${rootPath}/jslib/flatlab/assets/metro-ui/js/jquery/jquery.widget.min.js"></script>
    <script src="${rootPath}/jslib/flatlab/assets/metro-ui/js/jquery/jquery.mousewheel.js"></script>
    <script src="${rootPath}/jslib/flatlab/assets/metro-ui/js/prettify/prettify.js"></script>
    <script src="${rootPath}/jslib/flatlab/assets/metro-ui/js/holder/holder.js"></script>

    <!-- Local JavaScript -->
    <script src="${rootPath}/jslib/flatlab/js/jquery.validate.min.js" type="text/javascript"></script>
    
    <style type="text/css">
    .red{
      color:red !important;
    }
    .errorMessage li{
       text-align: center !important;
       color:red !important;
       font-size: x-large;
    }

    .face {
        #border: 1px solid #CCCCCC;
        margin: 0 0 10px;
        min-height: 100px;
        padding: 20px 40px 20px 60px;
    }

    .error {
        color: red;
    }

    .required {
        color: red;
    }
    </style>
</head>

<body>
<div id="dialog_message" title="系统消息区"></div>
</body>
</html>
