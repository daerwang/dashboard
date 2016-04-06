<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {

		
		
	});

</script>
<style>
body, html {
    height: 100%;
/* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#cedce7+0,596a72+100;Grey+3D+%231 */
background: #cedce7; /* Old browsers */
background: -moz-linear-gradient(top,  #cedce7 0%, #596a72 100%); /* FF3.6-15 */
background: -webkit-linear-gradient(top,  #cedce7 0%,#596a72 100%); /* Chrome10-25,Safari5.1-6 */
background: linear-gradient(to bottom,  #cedce7 0%,#596a72 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#cedce7', endColorstr='#596a72',GradientType=0 ); /* IE6-9 */

}

.card-container.card {
    max-width: 350px;
    padding: 40px 40px;
}

.btn {
    font-weight: 700;
    height: 36px;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    cursor: default;
}

/*
 * Card component
 */
.card {
    background-color: #F7F7F7;
    /* just in case there no content*/
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    /* shadows and rounded borders */
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
    width: 96px;
    height: 96px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}

/*
 * Form styles
 */
.profile-name-card {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    margin: 10px 0 0;
    min-height: 1em;
}

.reauth-email {
    display: block;
    color: #404040;
    line-height: 2;
    margin-bottom: 10px;
    font-size: 14px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin #inputEmail,
.form-signin #inputPassword {
    direction: ltr;
    height: 44px;
    font-size: 16px;
}

.form-signin input[type=email],
.form-signin input[type=password],
.form-signin input[type=text],
.form-signin button {
    width: 100%;
    display: block;
    margin-bottom: 10px;
    z-index: 1;
    position: relative;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin .form-control:focus {
    border-color: rgb(104, 145, 162);
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
}

.btn.btn-signin {
    /*background-color: #4d90fe; */
    background-color: rgb(104, 145, 162);
    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
    padding: 0px;
    font-weight: 700;
    font-size: 14px;
    height: 36px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    -o-transition: all 0.218s;
    -moz-transition: all 0.218s;
    -webkit-transition: all 0.218s;
    transition: all 0.218s;
}

.btn.btn-signin:hover,
.btn.btn-signin:active,
.btn.btn-signin:focus {
    background-color: rgb(12, 97, 33);
}

.forgot-password {
    color: rgb(104, 145, 162);
}

.forgot-password:hover,
.forgot-password:active,
.forgot-password:focus{
    color: rgb(12, 97, 33);
}
</style>

<div class="container">
        <div class="card card-container">
            <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
            <p id="profile-name" class="profile-name-card"></p>
            <form class="form-signin" name="f" action="j_spring_security_check" method="POST">
                <span id="reauth-email" class="reauth-email"></span>
                <input type="text" name="j_username" id="inputEmail" class="form-control" placeholder="Username" required autofocus>
                <input type="password" name="j_password" id="inputPassword" class="form-control" placeholder="Password" required>
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
            </form><!-- /form -->
            <a href="resetPassword" class="forgot-password">
                Forgot the password?
            </a>


        </div><!-- /card-container -->
            <c:if test="${param.error eq 'accountLocked'}">
			<div class="alert alert-error text-center" style="color:RED">
					The account is locked out. Please contact Admin.
			</div>
			</c:if>
			<c:if test="${param.error eq 'accountExpired'}">
			<div class="alert alert-error text-center" style="color:RED">
					The account is expired. Please contact Admin.
			</div>
			</c:if>
			<c:if test="${param.error eq 'badCredentials'}">
			<div class="alert alert-error text-center" style="color:RED">
				<spring:message code="message.badCredentials"></spring:message>
			</div>
			</c:if>
			<c:if test="${param.error eq 'badConnection'}">
			<div class="alert alert-error text-center" style="color:RED">
					There is bad server connection. Please contact Admin.
			</div>
			</c:if>
			<c:if test="${param.loggedout != null}">
			<div class="alert alert-error text-center" style="color:GREEN;">
					You have been logged out.
			</div>
			</c:if>
    </div><!-- /container -->

