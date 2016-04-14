<script>

$(document).ready(function(){
	
	var token = Cookies.get('restToken');
	var restApi = Cookies.get('restApi');

	setToken();
	
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	function setToken(){
		
		$.ajax({
			type: 'GET',
			url: 'login/getApiToken',
			success: function(data){
				Cookies.set('restToken', data.accessToken);
				Cookies.set('restApi', data.restApi);
				
				token = data.accessToken;
				restApi = data.restApi;
			}
		});
	}
	
	function sendActivationEmail(url){
		$.ajax({
			type : "GET",
			url: url,
			data:'',
			success : function(data) {

				BootstrapDialog.show({
			        type : BootstrapDialog.TYPE_SUCCESS,
		            title: 'Success',
		            message: 'The activation link has been sent to your email.',
		            buttons: [{
		                label: 'Ok',
		                cssClass: 'btn-success',
		                action: function(dialog){
		                    dialog.close();
		                    $(location).attr('href', getContextPath() + '/login');
		                }
		            }]
		         });
				
			},
		    error: function (data, status, er) {
		        var json = data.responseText;
		        var errorObj = $.parseJSON(json);
		        BootstrapDialog.show({
			        type : BootstrapDialog.TYPE_WARNING,
		            title: 'Warning',
		            message: 'Error: ' + errorObj.message + ' by ' + errorObj.cause,
		            buttons: [{
		                label: 'Ok',
		                cssClass: 'btn-warning',
		                action: function(dialog){
		                    dialog.close();
		                    location.reload();
		                }
		            }]
		         });
		    }
		});
	}
	
	function getResetToken(url){

		$.ajax({
			type: 'GET',
			url: url,
			data:'',
			contentType : 'application/json',
			mimeType : 'application/json',
			success: function(data){
				var username = data.username;
				var url = getContextPath() + '/login/sendActivationLinkEmail/' + username;
				console.log(url);
				sendActivationEmail(url);
				
			},
			beforeSend: function (xhr) {
			    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			},
		    error: function (data, status, er) {
		        var json = data.responseText;
		        var errorObj = $.parseJSON(json);
		        BootstrapDialog.show({
			        type : BootstrapDialog.TYPE_WARNING,
		            title: 'Warning',
		            message: 'Error: ' + errorObj.message + ' by ' + errorObj.cause,
		            buttons: [{
		                label: 'Ok',
		                cssClass: 'btn-warning',
		                action: function(dialog){
		                    dialog.close();
		                    location.reload();
		                }
		            }]
		         });
		    }
		});
	}
	
	$('#userForm1')
	.bootstrapValidator(
	{
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		submitHandler : function(validator,form, submitButton) {
			
 			var formObj = form.serializeObject();
 			var url = restApi + '/api/user/resetToken/' + formObj.username;

 			getResetToken(url);

		},
		submitButtons : 'button[type="submit"]',
		fields : {
			username : {
				validators : {
					notEmpty : {
						message : 'The Username is required and cannot be empty'
					}
				}
			}
		}
	});
	
});

</script>


<div class="container">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3">
			<form id="userForm1" role="form">
				<h2>
					Forgot Password
				</h2>
				<p>Enter your Username or Email Address to receive an email account activation link.</p>
				<hr class="colorgraph">
				
				<div class="row">
					<div class="col-xs-12 col-sm-8">
						<div class="form-group">
							<input name="username"
								class="form-control input-lg" placeholder="Username or Email Address"
								tabindex="1" value="">
						</div>
					</div>
				</div>

				
				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-12 col-md-4">
						<button class="btn btn-primary btn-block btn-lg" 
							type="submit" tabindex="2">Send Email</button>
					</div>
				</div>
				
			</form>
		</div>
	</div>
</div>