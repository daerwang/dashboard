<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function() {
	
	var executeUploadExcel = "<c:url value="${executeUploadExcel}"/>";
	var table = $('#amlBatchCifDatatable').DataTable();
	var requestId = "${requestId}";
	
	$(function () {
		
	    $('#fileupload').fileupload({
	        url: executeUploadExcel,
	        forceIframeTransport: true,
	        //dataType: 'json',
	        formData: {requestId: requestId},
	        add: function (e, data) {
	            data.submit();
	            
	            time = setInterval(function() {
	                $.get(executeUploadExcel + "/.progress", function(data) {
	                    if(!data){
	                    	console.log('no data');
	                    	return;
	                    }
	                    
	                    data = data.split("/");

	                    var progress = Math.round(data[0] / data[1] * 100);
	                    $('.progress-bar').text(progress + "%");
	    	            $('#progress .progress-bar').css('width', progress + '%');
	                }); 
	           }, 1500);
	        },
	        done: function (e, data) {
	            
	            clearInterval(time);  
        		$('#progress .progress-bar').css('width', '100%');
        		//console.log(data);
        		//$('<p/>').text(data.result.fileName + ', ' + data.result.fileSize).appendTo('#files');
	            setTimeout(function() {
	            	table.ajax.reload();
	            	uploadExcelFileDialog.close();

				}, 2000);
	        },
	        //dropZone: $('#drop-zone'),
	        fail: function(e, data){
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
        <span>Upload Excel file</span>
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
        <div class="progress-bar progress-bar-success progress-bar-striped active"></div>
    </div>
 
 	<!-- The container for the uploaded files -->
    <div id="files" class="files"></div>
    
 
</div>

