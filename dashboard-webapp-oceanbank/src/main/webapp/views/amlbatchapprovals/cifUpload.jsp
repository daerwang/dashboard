<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function() {
	
	var executeUploadExcel = "<c:url value="${executeUploadExcel}"/>";
	var table = $('#amlBatchCifDatatable').DataTable();
	var table2 = $('#dashboardUploadDatatable').DataTable();
	var requestId = "${requestId}";
	
	
	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	function detectIE() {
	    var ua = window.navigator.userAgent;

	    var msie = ua.indexOf('MSIE ');
	    if (msie > 0) {
	        // IE 10 or older => return version number
	        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
	    }

	    var trident = ua.indexOf('Trident/');
	    if (trident > 0) {
	        // IE 11 => return version number
	        var rv = ua.indexOf('rv:');
	        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
	    }

	    var edge = ua.indexOf('Edge/');
	    if (edge > 0) {
	       // IE 12 => return version number
	       return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
	    }

	    // other browser
	    return false;
	}
	var url1 = getContextPath() + '/aml/cifUpload';
	
	$(function () {
		
	    $('#fileupload').fileupload({
	        url: url1,
	        //forceIframeTransport: true,
	        dataType: 'json',
	        formData: {requestId: requestId},
	        add: function (e, data) {
	        	data.submit();
	            
	            time = setInterval(function() {
            		$.get(url1 + "/.progress", function(data) {
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
	            setTimeout(function() {
	            	table.ajax.reload();
	            	table2.ajax.reload();
	            	uploadExcelFileDialog.close();
				}, 2000);
	        },
	        //dropZone: $('#drop-zone'),
	        fail: function(e, data){
	        	clearInterval(time);  
        		$('#progress .progress-bar').css('width', '0%');
	        	uploadExcelFileDialog.close();
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
        <span>Transfer Data</span>
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

