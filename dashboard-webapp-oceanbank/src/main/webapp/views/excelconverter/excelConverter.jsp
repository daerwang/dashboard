<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JQuery File Upload 9.9.3 -->
<script src="<c:url value="/bootstrap/fileupload/jquery.fileupload.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.iframe-transport.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.ui.widget.js" />"></script>
<link href="<c:url value="/bootstrap/fileupload/jquery.fileupload.css" />" rel="stylesheet">

<!--[if lt IE 10]>
<script src="<c:url value="/bootstrap/fileupload/jquery.iframe-transport.js" />"></script>
<![endif]-->

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var uploadExcelModal = "${uploadExcelModal}";
		var uploadExcelModal2 = "<c:url value="${uploadExcelModal}"/>";
		var openTextFileNewWindow = "<c:url value="${openTextFileNewWindow}"/>";

		// 'excelConverter/upload/modal'
		$('#uploadButton').on('click', function(e) {
			e.preventDefault();
			uploadExcelFileDialog = new BootstrapDialog({
				title : 'Upload Excel File',
				draggable: true, 
				message : function(dialog) {
					var $message = $('<div></div>');
					var pageToLoad = dialog.getData('pageToLoad');
					$message.load(pageToLoad);
					return $message;
				},
				data : {
					'pageToLoad' : 'excelConverter/upload/modal'
				}
			 });
			
			uploadExcelFileDialog.open();
		});   // Upload Button
		
		
		$('#openButton').on('click', function(event) {
			window.open(openTextFileNewWindow, '_blank');
		});   
		

	});
</script>

<div class="page-header">
	<h1>Advisor File Converter</h1>
	<p class="lead">Convert an Excel file to a text file to be used in IMS.</p>
</div>



<p></p>
<h3>Step 1: Upload an Excel file</h3>
<p>Upload an excel file here. There is no need to go to Step 2 if you are using <strong>IE 10 and above, Chrome or Firefox browsers.</strong>.</p>
<button type="button" class="btn btn-default btn-sm" id="uploadButton">Upload Excel</button>
	

<p></p>
<h3>Step 2: Download Text File</h3>
<p>For <strong>IE 9 and below</strong>, please click below button for the converted text file.</p>
<button type="button" class="btn btn-default btn-sm" id="openButton">Open Text File</button>

