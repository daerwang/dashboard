<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JQuery File Upload 9.9.3 -->
<script src="<c:url value="/bootstrap/fileupload/jquery.fileupload.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.iframe-transport.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.ui.widget.js" />"></script>
<link href="<c:url value="/bootstrap/fileupload/jquery.fileupload.css" />" rel="stylesheet">

<style>
.placeholder{color: grey;}
select option:first-child{color: grey; display: none;}
select option{color: #555;} 
.user-success-dialog .modal-dialog {width: 300px;}
.modal .modal-dialog { width: 600px; }


</style>
<script type="text/javascript" charset="utf-8">

	var newAmlBatchCifDialog;
	var updateAmlBatchCifDialog;
	var uploadExcelFileDialog;
	var uploadAmlBatchRequestDialog;
	
	$(document).ready(function() {
	
		var amlRequestId = "${amlRequestId}";
		var amlBatchRequestPage = "<c:url value="${amlBatchRequestPage}"/>";
		var amlBatchRequestLogPage = "<c:url value="${amlBatchRequestLogPage}"/>";
		var amlBatchCifModal = "${amlBatchCifModal}";
		amlBatchCifModal = amlBatchCifModal.substring(0, amlBatchCifModal.indexOf('{'));
		var showCreateDashboardCommentModal = "${showCreateDashboardCommentModal}";
		showCreateDashboardCommentModal = showCreateDashboardCommentModal.substring(0, showCreateDashboardCommentModal.indexOf('{'));
		var showUpdateDashboardCommentModal = "${showUpdateDashboardCommentModal}";
		showUpdateDashboardCommentModal = showUpdateDashboardCommentModal.substring(0, showUpdateDashboardCommentModal.indexOf('{'));
		var updateAmlBatchCifModal = "${updateAmlBatchCifModal}";
		updateAmlBatchCifModal = updateAmlBatchCifModal.substring(0, updateAmlBatchCifModal.indexOf('{'));
		
		var amlBatchCifDatatable = "<c:url value="${amlBatchCifDatatable}"/>";
		var amlBatchUploadDatatable = "<c:url value="${amlBatchUploadDatatable}"/>";
		var dashboardCommentDatatable = "<c:url value="${dashboardCommentDatatable}"/>";
		var uploadExcelModal = "${uploadExcelModal}";
		uploadExcelModal = uploadExcelModal.substring(0, uploadExcelModal.indexOf('{'));
		var uploadAmlBatchRequest = "${uploadAmlBatchRequest}";
		uploadAmlBatchRequest = uploadAmlBatchRequest.substring(0, uploadAmlBatchRequest.indexOf('{'));
		var openUploadAmlBatchRequest = "${openUploadAmlBatchRequest}";
		openUploadAmlBatchRequest = openUploadAmlBatchRequest.substring(0, openUploadAmlBatchRequest.indexOf('{'));
		var deleteDashboardUpload = "${deleteDashboardUpload}";
		deleteDashboardUpload = deleteDashboardUpload.substring(0, deleteDashboardUpload.indexOf('{'));
		
		var deleteAmlBatchCif = "${deleteAmlBatchCif}";
		deleteAmlBatchCif = deleteAmlBatchCif.substring(0, deleteAmlBatchCif.indexOf('{'));
		var executeAmlBatchApproval = "${executeAmlBatchApproval}";
		executeAmlBatchApproval = executeAmlBatchApproval.substring(0, executeAmlBatchApproval.indexOf('{'));
		var executeAmlBatchReversal = "${executeAmlBatchReversal}";
		executeAmlBatchReversal = executeAmlBatchReversal.substring(0, executeAmlBatchReversal.indexOf('{'));
		var executeAmlBatchDisapproval = "${executeAmlBatchDisapproval}";
		executeAmlBatchDisapproval = executeAmlBatchDisapproval.substring(0, executeAmlBatchDisapproval.indexOf('{'));
		var isOwner = "${isOwner}";
		
		
		var highlight = 'active'; 
		var highlightUpload = 'active';
		var highlightComment = 'active';
		
		var isHighlight = false;
		var isHighlightUpload = false;
		var isHighlightComment = false;
		
		var row_id;
		var row_idUpload;
		var row_idComment;
		
		var selected = [];
		var selectedUpload = [];
		var selectedComment = []; 

		var amlBatchCifRequestId = $('#requestIdParam').val();

		var table = $('#amlBatchCifDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : {
	            "url": amlBatchCifDatatable,
	            "data": function ( d ) {
	                d.amlBatchRequestId = amlBatchCifRequestId;
	            }
	        }
			 
		});
		
		$('#amlBatchCifDatatable tbody').on('click', 'tr', function () {
			
			row_id = this.id;
			
	        if ( $(this).hasClass(highlight) ) {
                $(this).removeClass(highlight);
                isHighlight = false;
            }
            else {
            	// remove all highlighted rows first
            	table.$('tr.' + highlight).removeClass(highlight);
            	// apply highlight to new row
                $(this).addClass(highlight);
                
                isHighlight = true;
            }
            
	    });
		
		
		var table2 = $('#dashboardUploadDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"searching": false,
			"ajax" : {
	            "url": amlBatchUploadDatatable,
	            "data": function ( d ) {
	                d.amlRequestId = amlRequestId;
	            }
	        }
			 
		});

		$('#dashboardUploadDatatable tbody').on('click', 'tr', function () {
			
			row_idUpload = this.id;
	        if ( $(this).hasClass(highlight) ) {
                $(this).removeClass(highlight);
                isHighlightUpload = false;
                console.log('1. ' + highlight);
            }
            else {
            	// remove all highlighted rows first
            	table2.$('tr.' + highlight).removeClass(highlight);
            	// apply highlight to new row
                $(this).addClass(highlight);
                console.log('2. ' + highlight);
                isHighlightUpload = true;
            }
            
	    });
		
		var table3 = $('#dashboardCommentDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"searching": false,
			"ajax" : {
	            "url": dashboardCommentDatatable,
	            "data": function ( d ) {
	                d.amlRequestId = amlRequestId;
	            }
	        }, 
			"columns": [
			               { "width": "50%"},
			               { "width": "25%"},
			               { "width": "25%"}
			             ]
			 
		});
		
		$('#dashboardCommentDatatable tbody').on('click', 'tr', function () {
			
			row_idComment = this.id;
			
	        if ( $(this).hasClass(highlight) ) {
                $(this).removeClass(highlight);
                isHighlightComment = false;
            }
            else {
            	// remove all highlighted rows first
            	table3.$('tr.' + highlight).removeClass(highlight);
            	// apply highlight to new row
                $(this).addClass(highlight);
                
                isHighlightComment = true;
            }
            
	    });
		
		
		$.fn.serializeObject = function()
		{
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		        	o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};	
		
		var noSelectionDialog = new BootstrapDialog({
			type : BootstrapDialog.TYPE_DANGER,
            title: 'Error',
            message: 'There is no selection made.',
            buttons: [{
                label: 'Close',
                cssClass: 'btn btn-danger',
                action: function(dialog){
                    dialog.close();
                }
            }]
        });
		
		if(isOwner.toLowerCase() === 'false'){
			console.log('false');	
			$('#amlBatchRequestForm input').attr('readonly', 'readonly');
			$("button[type=button]").attr("disabled", "disabled");
			$('button').prop('disabled', true);
			
			$('#openUploadButton').prop('disabled', false);
			
		}
		
		if ($("#selectedType").val() != "") {
			$('#selectedType').attr('disabled', true);
		}
		if ($("#bankSchema").val() != "") {
			$('#bankSchema').attr('disabled', true);
		}
		
		$('#historyLink').on('click', function(e) {
			
			e.preventDefault();	
			$(this).prop('href', amlBatchRequestLogPage + '?id=' + amlBatchCifRequestId);
			window.open(this.href, '_self');

		});   // History Button
		
		
		$('#newButton').on('click', function(event) {
			var selected = $("#selectedType").val();
			
			if($('#selectedType').is(':enabled')){
			
			    BootstrapDialog.alert({
	            	title: 'WARNING',
		            message: 'Please save the request first before adding CIF.',
		            type: BootstrapDialog.TYPE_WARNING, 
		            closable: true, 
		            draggable: true, 
		            buttonLabel: 'Ok'
		        });	
		        
			}else{
				
				newAmlBatchCifDialog = new BootstrapDialog({
						title : 'Create AML Batch CIF',
						draggable: true, 
						message : function(dialog) {
							var $message = $('<div></div>');
							var pageToLoad = dialog.getData('pageToLoad');
							$message.load(pageToLoad);
							return $message;
						},
						data : {
							'pageToLoad' : '../..' +  amlBatchCifModal + amlBatchCifRequestId
						}
					 });
					 
				newAmlBatchCifDialog.open();
			
			}

		});   // new Button
		
		$('#deleteButton').on('click', function(e) {
			e.preventDefault();
			
			if(!isHighlight){
				noSelectionDialog.open();
			}else{

				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
		            title: 'Information',
		            draggable: true, 
		            message: 'Do you really want to delete?',
		            buttons: [{
		            	label: 'Delete',
		            	action: function(dialog){
		            		dialog.close();
		            		$.ajax({
		        		    	type: "DELETE",
		        			  	url: '../..' + deleteAmlBatchCif + row_id,
		        			    success: function(data) { 
		        			    	   table = $('#amlBatchCifDatatable').DataTable();
		        			    	   table.ajax.reload();
		        					   isHighlight = false;
		        			    },
		        			    error:function(data) { 
		        			        isHighlight = false;
		        			        
		        			        var res = data.responseJSON.message;
							    	BootstrapDialog.alert({
		        		            	title: 'WARNING',
		        			            message: res,
		        			            type: BootstrapDialog.TYPE_DANGER, 
		        			            closable: true, 
		        			            draggable: true, 
		        			            buttonLabel: 'Ok'
		        			        });	
		        			    }
		        			});
		            	}
		            },{
		                label: 'Close',
		                action: function(dialog){
		                    dialog.close();
		                }
		            }]
		        });
				
			}
			
			
		}); // Delete Button
		
		$('#editButton').on('click', function(e) {
			e.preventDefault();
			
			if(!isHighlight){
				noSelectionDialog.open();

			}else{
				
				updateAmlBatchCifDialog = new BootstrapDialog({
						title : 'Update AML Batch CIF',
						draggable: true, 
						message : function(dialog) {
							var $message = $('<div></div>');
							var pageToLoad = dialog.getData('pageToLoad');
							$message.load(pageToLoad);
							return $message;
						},
						data : {
							'pageToLoad' : '../..' +  updateAmlBatchCifModal + row_id
						}
					 });
					 
				updateAmlBatchCifDialog.open();
			}
			
			
		});   // Edit Button
		
		$('#uploadButton').on('click', function(e) {
			e.preventDefault();
			
			uploadExcelFileDialog = new BootstrapDialog({
				title : 'Transfer Data From Excel',
				draggable: true, 
				message : function(dialog) {
					var $message = $('<div></div>');
					var pageToLoad = dialog.getData('pageToLoad');
					$message.load(pageToLoad);
					return $message;
				},
				data : {
					'pageToLoad' : '../..' + uploadExcelModal + amlBatchCifRequestId
				}
			 });
			
			if($('#selectedType').is(':enabled')){
				
			    BootstrapDialog.alert({
	            	title: 'WARNING',
		            message: 'Please save the request first before adding CIF.',
		            type: BootstrapDialog.TYPE_WARNING, 
		            closable: true, 
		            draggable: true, 
		            buttonLabel: 'Ok'
		        });	
		        
			}else{
			
				uploadExcelFileDialog.open();
			}
			
			
		});   // Upload Button
		
		$('#newUploadButton').on('click', function(e) {
			e.preventDefault();
			
			uploadAmlBatchRequestDialog = new BootstrapDialog({
				title : 'Attachment',
				draggable: true, 
				message : function(dialog) {
					var $message = $('<div></div>');
					var pageToLoad = dialog.getData('pageToLoad');
					$message.load(pageToLoad);
					return $message;
				},
				data : {
					'pageToLoad' : '../..' + uploadAmlBatchRequest + amlBatchCifRequestId
				}
			 });
			
			uploadAmlBatchRequestDialog.open();
		});
		
		$('#openUploadButton').on('click', function(e) {
			e.preventDefault();
			var openUploadUrl = '../..' + openUploadAmlBatchRequest + row_idUpload;
			if(!isHighlightUpload){
				noSelectionDialog.open();
			}else{
				
				window.open(openUploadUrl, '_blank');

			}
			
		}); 
		
		
		$('#deleteUploadButton').on('click', function(e) {
			e.preventDefault();
			
			if(!isHighlightUpload){
				noSelectionDialog.open();
			}else{

				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
		            title: 'Information',
		            draggable: true, 
		            message: 'Do you really want to delete?',
		            buttons: [{
		            	label: 'Delete',
		            	action: function(dialog){
		            		dialog.close();
		            		$.ajax({
		        		    	type: "DELETE",
		        			  	url: '../..' + deleteDashboardUpload + row_idUpload,
		        			    success: function(data) { 
		        			    	   table = $('#dashboardUploadDatatable').DataTable();
		        			    	   table.ajax.reload();
		        					   isHighlight = false;
		        			    },
		        			    error:function(data) { 
		        			        isHighlight = false;
		        			        
		        			        var res = data.responseJSON.message;
							    	BootstrapDialog.alert({
		        		            	title: 'WARNING',
		        			            message: res,
		        			            type: BootstrapDialog.TYPE_DANGER, 
		        			            closable: true, 
		        			            draggable: true, 
		        			            buttonLabel: 'Ok'
		        			        });	
		        			    }
		        			});
		            	}
		            },{
		                label: 'Close',
		                action: function(dialog){
		                    dialog.close();
		                }
		            }]
		        });
				
			}
			
			
		});
		
		
		var progress;
		var $bar = $('.progress-bar');
		
		function progressBarModal(){
			if(progress){
				clearInterval(progress);
			}
			progress = setInterval(function() {
			    if ($bar.width() >= 600) {
			        clearInterval(progress);
			        $('.progress').removeClass('active');
			    } else {
			        $bar.width($bar.width() + 60);
			    }
			    $bar.text($bar.width()/6 + "%");
			}, 600);
		};

		$('#executeButton').on('click', function(e) {
			
			var selected = $("#selectedType").val();
			var approval = "approval";
			var reversal = "reversal";
			var disapproval = "disapproval";
			var serviceUrl = '';
			var area = '';
			if (approval.toLowerCase() === selected.toLowerCase()){
				serviceUrl = '../..' + executeAmlBatchApproval + amlBatchCifRequestId;
				area = 'Approval';
			}
			if (reversal.toLowerCase() === selected.toLowerCase()){
				serviceUrl = '../..' + executeAmlBatchReversal + amlBatchCifRequestId;
				area = 'Reversal';
			}
			if (disapproval.toLowerCase() === selected.toLowerCase()){
				serviceUrl = '../..' + executeAmlBatchDisapproval + amlBatchCifRequestId;
				area = 'Disapproval';
			}
			
			if($('#selectedType').is(':enabled')){
			
			    BootstrapDialog.alert({
	            	title: 'WARNING',
		            message: 'Please save the request first before executing request.',
		            type: BootstrapDialog.TYPE_WARNING, 
		            closable: true, 
		            draggable: true, 
		            buttonLabel: 'Ok'
		        });	
		        
			}else{
				// initialize loading effects
				e.preventDefault();
				
				$('.progress-bar').css('width', '100%');
				$('#myModal').modal({
					  backdrop: 'static',
					  keyboard: false
				});

				$.ajax({
					type: "GET",
       			  	url: '../../amlbatchrequest/' + amlBatchCifRequestId + '/approveOrDisapprove',
				    success: function(data) { 
				    	
				    	$('#myModal').modal('hide');
				    	
				    	
				    	BootstrapDialog.show({
							closable: false,
				            title: 'Success',
				            message: 'The AML Batch Request ' + area + ' execution is successful.',
				            buttons: [{
				                label: 'Ok',
				                action: function(dialog){
				                    dialog.close();
				                    table = $('#amlBatchCifDatatable').DataTable();
							    	table.ajax.reload();
				                }
				            }]
				        });
				    	
				    	$('#batchStatus').val(data.status);
				    },
				    error: function(data) { 
				    	var res = data.responseJSON.message;
				    	var json = '[' + res + ']';
				    	var errorMsg = '';
				    	var code = '';
				    	$.each(JSON.parse(json), function(idx, obj) {
				    		code = obj.code;
				    		errorMsg = obj.message;
				    	});
				    	
				    	BootstrapDialog.show({
							closable: false,
				            title: 'WARNING',
				            draggable: true, 
				            message: code + '</br>' + errorMsg,
				            type: BootstrapDialog.TYPE_DANGER, 
				            buttons: [{
				                label: 'Ok',
				                action: function(dialog){
				                    dialog.close();
				                    $('#myModal').modal('hide');
				                    table = $('#amlBatchCifDatatable').DataTable();
							    	table.ajax.reload();
				                }
				            }]
				        });
				    }
				});
			}
			
		});
		
		$(function() {
		    function reposition() {
		        var modal = $(this),
		            dialog = modal.find('.modal-dialog');
		        modal.css('display', 'block');
		        
		        // Dividing by two centers the modal exactly, but dividing by three 
		        // or four works better for larger screens.
		        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
		    }
		    // Reposition when a modal is shown
		    $('.modal').on('show.bs.modal', reposition);
		    // Reposition when the window is resized
		    $(window).on('resize', function() {
		        $('.modal:visible').each(reposition);
		    });
		});
		
		
		var successDialog = new BootstrapDialog({
			closable: false,
            title: 'Success',
            message: 'The AML Batch Request is updated successfully.',
            buttons: [{
                label: 'Ok',
                action: function(dialog){
                    dialog.close();
                    table = $('#amlBatchCifDatatable').DataTable();
			    	table.ajax.reload();
                    newUserDialog.close();
                }
            }]
        });	
		
		var successDialog2 = new BootstrapDialog({
			closable: false,
            title: 'Success',
            message: 'The AML Batch Request Approval execution is successful.',
            buttons: [{
                label: 'Ok',
                action: function(dialog){
                    dialog.close();
                    table = $('#amlBatchCifDatatable').DataTable();
			    	table.ajax.reload();
                }
            }]
        });
		
		function getContextPath() {
			return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
		
		function submitAmlBatchRequestForm(form){
			$('#selectedType').attr('disabled', false);
			var url = getContextPath() + '/aml';
			console.log('it is ' + url);
			
			var formObj = form.serializeObject();
        	delete formObj.amlBatchCifDatatable_length
        	delete formObj.dashboardCommentDatatable_length
        	delete formObj.dashboardUploadDatatable_length
			var jsonData = JSON.stringify(formObj);
		    $.ajax({
		    	type: "POST",
			  	url: url,
			  	dataType: 'json', 
			    data: jsonData, 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    	$('#selectedType').attr('disabled', true);
					successDialog.open();        	
			    },
			    error:function(data) { 
			    	var res = data.responseJSON.message;
			    	var json = '[' + res + ']';
			    	var errorMsg = '';
			    	var code = '';
			    	$.each(JSON.parse(json), function(idx, obj) {
			    		code = obj.code;
			    		errorMsg = obj.message;
			    	});

			    	BootstrapDialog.alert({
		            	title: 'WARNING',
			            message: code + '</br>' + errorMsg,
			            type: BootstrapDialog.TYPE_DANGER,
			            closable: true,
			            draggable: true,
			            buttonLabel: 'Ok'
			        });
			    }
			});
		}
		
		$('#amlBatchRequestForm').bootstrapValidator({
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        submitHandler: function(validator, form, submitButton){
	        	
	        	
	        	if($('#selectedType').is(':enabled')){
	        		
		    		BootstrapDialog.show({
						closable: false,
			            message: 'The Transaction Type can no longer be changed after save. Do you really want to save?',
			            title: 'WARNING',
			            type: BootstrapDialog.TYPE_WARNING, 
			            buttons: [{
			                label: 'Save',
			                action: function(dialog){
			                	
			                	console.log('saving here ....');
			                	submitAmlBatchRequestForm(form);
							    dialog.close();
			                }
			            },{
			                label: 'Close',
			                action: function(dialog){
			                    dialog.close();
			                }
			            }]
			        });	
		    		
		    	}else{
		    		
		    		submitAmlBatchRequestForm(form);
				    
		    	}
				
	        },
	        submitButtons : 'button[type="submit"]',
	        fields: {
	        	name: {
		                message: 'The Name must have value',
		                validators: {
		                    notEmpty: {
		                        message: 'The Name is required and cannot be empty'
		                    }
		                }
		            },
		        description: {
		                message: 'The Description must have value',
		                validators: {
		                    notEmpty: {
		                        message: 'The Description is required and cannot be empty'
		                    }
		                }
		            },
		        transactionType: {
		                message: 'The Transaction Type must have value',
		                validators: {
		                    notEmpty: {
		                        message: 'The Transaction Type is required and cannot be empty'
		                    }
		                }
		            },
		        bankSchema: {
		                message: 'The Bank must have value',
		                validators: {
		                    notEmpty: {
		                        message: 'The Bank is required and cannot be empty'
		                    }
		                }
		            }    
	        }
	    }); // bootstrap validator
		
		
		
	    $('#newCommentButton').on('click', function(event) {
	    	newDashboarcCommentDialog = new BootstrapDialog({
				title : 'Create Comment',
				draggable: true, 
				message : function(dialog) {
					var $message = $('<div></div>');
					var pageToLoad = dialog.getData('pageToLoad');
					$message.load(pageToLoad);
					return $message;
				},
				data : {
					'pageToLoad' : '../..' +  showCreateDashboardCommentModal + amlRequestId
				}
			 });
			 
	    	newDashboarcCommentDialog.open();

		});   // new Button
		
		$('#editCommentButton').on('click', function(e) {
			e.preventDefault();
			
			if(!isHighlightComment){
				noSelectionDialog.open();

			}else{
				
				updateDashboardCommentDialog = new BootstrapDialog({
						title : 'Update Dashboard Comment',
						draggable: true, 
						message : function(dialog) {
							var $message = $('<div></div>');
							var pageToLoad = dialog.getData('pageToLoad');
							$message.load(pageToLoad);
							return $message;
						},
						data : {
							'pageToLoad' : '../..' +  showUpdateDashboardCommentModal + row_idComment
						}
					 });
					 
				updateDashboardCommentDialog.open();
			}
			
			
		});   // Edit Button
		
	});
</script>



<div class="page-header text-center">
	<h3 id="referenceIdHeader">${amlBatchRequest.requestId}</h3>

</div>

<ul class="nav nav-tabs">
  <li role="presentation" class="active"><a href="#">Information</a></li>
  <li role="presentation"><a href="#" id="historyLink">History</a></li>
</ul>
<br/>
<form id="amlBatchRequestForm" class="form-horizontal">
	<input type="hidden" id="requestIdParam" name="requestId" value="<c:out value="${amlBatchRequest.requestId}" />">

	<div class="form-group">
		<label class="control-label col-md-1">Name</label> 
		<div class="col-md-3">
			<input class="form-control" name="name" placeholder="Enter Name" value="<c:out value="${amlBatchRequest.name}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-1">Description</label> 
		<div class="col-md-4">
			<input class="form-control" name="description" placeholder="Enter Description" value="<c:out value="${amlBatchRequest.description}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-1">Transaction </label> 
		<div class="col-md-3">
			<select class="form-control placeholder" name="transactionType" id="selectedType">
			    <c:forEach items="${amlBatchRequest.selectableTypes}" var="type">
			        <option value="${type}" ${type == amlBatchRequest.transactionType or type == '' ? 'selected' : ''}>${type == '' ? 'Select Transaction Type' : type}</option>
			    </c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-1">Bank</label> 
		<div class="col-md-3">
			<select class="form-control placeholder" name="bankSchema" id="bankSchema">
			    <c:forEach items="${amlBatchRequest.bankSchemas}" var="type">
			        <option value="${type}" ${type == amlBatchRequest.bankSchema or type == '' ? 'selected' : ''}>${type == '' ? 'Select Bank Schema' : type}</option>
			    </c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-1">Status</label> 
		<div class="col-md-3">
			<input class="form-control" name="status" id="batchStatus" value="<c:out value="${amlBatchRequest.status}"/>" readonly>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-1"></label> 
		<div class="col-md-11">
	
			<h4>CIF table for Execution</h4>
			<p>Upload or create CIF to be executed for this request. The "Load CIF From Excel" button will automatically save the loaded Excel file in Attachment section.<strong class="text-danger"> This table is required to have CIF data.</strong></p>
			<hr>
			<p>
				<button type="button" class="btn btn-default btn-sm" id="newButton">New</button>
				<button type="button" class="btn btn-default btn-sm" id="editButton">Edit</button>
				<button type="button" class="btn btn-default btn-sm" id="deleteButton">Delete</button>
				<button type="button" class="btn btn-default btn-sm" id="uploadButton">Load CIF From Excel</button>
			</p>
			
			<table id="amlBatchCifDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>CIF Reference</th>
						<th>Audit Description</th>
						<th>Status</th>
						<th>Executed By</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="control-label col-md-1"></label> 
		<div class="col-md-11">
	
			<h4>Attachments</h4>
			<p>All other Excel files can be uploaded manually here for reference. This can only upload Excel files.</p>
			<hr>
			<p>
				<button type="button" class="btn btn-default btn-sm" id="openUploadButton">Open</button>
				<button type="button" class="btn btn-default btn-sm" id="deleteUploadButton">Delete</button>
				<button type="button" class="btn btn-default btn-sm" id="newUploadButton">Upload Excel</button>
			</p>
			
			<table id="dashboardUploadDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>File Name</th>
						<th>Created By</th>
						<th>Created On</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-1"></label> 
		<div class="col-md-11">
	
			<h4>Comment</h4>
			<p>Add your comment here.</p>
			<hr>
			<p>
				<button type="button" class="btn btn-default btn-sm" id="newCommentButton">New</button>
				<button type="button" class="btn btn-default btn-sm" id="editCommentButton">Edit</button>
			</p>
			
			<table id="dashboardCommentDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Message</th>
						<th>Created By</th>
						<th>Created On</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<br/><br/>
	<div class="form-group">
		<label class="control-label col-md-1"></label> 
		<div class="col-md-4">
			<button class="btn btn-info" id="saveButton" type="submit">Save</button>
			<button class="btn btn-info" id="executeButton">Execute</button>
			<a href="<c:url value="${amlBatchPage}"/>" class="btn btn-info" id="backButton">Back</a>
		</div>
	</div>
	
</form>




<!-- Modal -->
<div class="modal fade modal-cif" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Processing</h4>
      </div>
      <div class="modal-body">

	    <div class="progress">
		  <div class="progress-bar progress-bar-success progress-bar-striped active">
		  </div>
		</div>
      </div>
<!--       <div class="modal-footer"> -->
<!--         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
<!--         <button type="button" class="btn btn-primary">Save changes</button> -->
<!--       </div> -->
    </div>
  </div>
</div>

    



