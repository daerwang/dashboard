<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function() {
	
	var executeAmlBatchRequestUpload = "<c:url value="${executeAmlBatchRequestUpload}"/>";
	var table = $('#dashboardUploadDatatable').DataTable();
	var requestId = "${requestId}";
	
	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	$(function () {
		
	    $('#fileupload').fileupload({
	        url: getContextPath() + '/aml/attachment',
	        dataType: 'json',
	        formData: {requestId: requestId},
	        add: function (e, data) {
	            data.submit();
	            console.log('here 1');
	            $('#progress .progress-bar').css('width', '100%');
            	setTimeout(function() {
            		table.ajax.reload();
            		uploadAmlBatchRequestDialog.close();

				}, 2000);

	        },
	        done: function (e, data) {
	            
        		//console.log(data);
        		//$('<p/>').text(data.result.fileName + ', ' + data.result.fileSize).appendTo('#files');

	        },
	        //dropZone: $('#drop-zone'),
	        fail: function(e, data){
	        	console.log('here 2');
	        	console.log(data);
	            var res = data.jqXHR.responseText;
	            console.log(res);
		    	var json = '[' + res + ']';
		    	var errorMsg = '';
		    	var code = '';
		    	$.each(JSON.parse(json), function(idx, obj) {
		    		code = obj.code;
		    		errorMsg = obj.message;
		    	});
		    	
		    	BootstrapDialog.alert({
	            	title: 'WARNING',
		            message: code + '</br></br>' + errorMsg,
		            type: BootstrapDialog.TYPE_DANGER, 
		            closable: true, 
		            draggable: true, 
		            buttonLabel: 'Ok'
		        });	
		    	
		    	$('#progress .progress-bar').css('width','0%');
	        }
	    }).prop('disabled', !$.support.fileInput)
	        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	});
	
	// Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });
	
});

</script>
<style>
/* layout.css Style */
.upload-drop-zone {
  height: 200px;
  border-width: 2px;
  margin-bottom: 20px;
}

/* skin.css Style*/
.upload-drop-zone {
  color: #ccc;
  border-style: dashed;
  border-color: #ccc;
  line-height: 200px;
  text-align: center
}
.upload-drop-zone.drop {
  color: #222;
  border-color: #222;
}
</style>

<div>
 
    <!-- The fileinput-button span is used to style the file input field as button -->
    <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>Upload File</span>
        <!-- The file input field used as target for the file upload widget -->
        <input id="fileupload" type="file" name="files">
    </span>
    <br>
 	<br>
    <!-- Drop Zone 
    <h4>Or drag and drop an Excel file below</h4>
    <div class="upload-drop-zone" id="drop-zone">
      Just drag and drop ONE Excel file here
    </div>
 	-->
    <!-- The global progress bar -->
    <div id="progress" class="progress">
        <div class="progress-bar progress-bar-success"></div>
    </div>
 
 	<!-- The container for the uploaded files -->
    <div id="files" class="files"></div>
    
 
</div>

