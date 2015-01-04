<%-- 
    Document   : settings.jsp : /settings : SettingsServlet
    Created on : Dec 28, 2014, 4:31:05 PM
    Author     : azureel
    Licence    : The MIT License (MIT)

Copyright (c) 2015 Cagri Celebi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SecureChat : Settings</title>
        <jsp:include page="incl_010_head.jsp" />
        <c:if test="${not empty guide}">
            <link type="text/css" rel="stylesheet" href="assets/js/guidely/guidely.css">
        </c:if>
    </head>
    <body>
        <jsp:include page="incl_020_navbar.jsp" />
        <jsp:include page="incl_030_subnavbar.jsp" />
        <div class="main">
            <div class="main-inner">
                <div class="container">
                    <div class="row">
                        <div class="span12">
                            <div class="widget widget-nopad">
                                <div class="widget-header"> <i class="icon-list-alt"></i>
                                    <h3>Account Settings</h3>
                                </div>
                                <!-- /widget-header -->
                                <div class="widget-content" style="padding: 20px;">
                                    <fieldset>
                                        <c:if test="${not empty alert}">
                                            <div class="alert alert-success">
                                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                <strong>Alert:</strong> ${alert}
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty message}">
                                            <div class="alert alert-success">
                                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                <strong>Message:</strong> ${message}
                                            </div>
                                        </c:if>
                                        <form id="edit-profile" class="form-horizontal" method="POST">
                                            <div class="control-group" >											
                                                <label class="control-label" for="id">Id</label>
                                                <div class="controls">
                                                    <input type="text" class="span6 disabled" id="userid" value="${sessionScope.user.id}" disabled>
                                                    <p class="help-block span6">Your user id is for public key storage and cannot be changed.</p>
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->

                                            <div class="control-group"  >											
                                                <label class="control-label" for="username">Username</label>
                                                <div class="controls">
                                                    <input type="text" class="span6 disabled" id="username" value="${sessionScope.user.username}" disabled>
                                                    <p class="help-block span6">Your username is for logging in and cannot be changed.</p>
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->


                                            <div class="control-group" >											
                                                <label class="control-label" for="password1">Current Password</label>
                                                <div class="controls" >
                                                    <input type="password" class="span4" id="old_password" name="old_password" placeholder="Enter your current password." value="">
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->

                                            <div class="control-group">											
                                                <label class="control-label" for="password1">New Password</label>
                                                <div class="controls">
                                                    <input type="password" class="span4" id="password1" name="password1" placeholder="This will be your new password." value="">
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->

                                            <div class="control-group">											
                                                <label class="control-label" for="password2">Confirm New Password</label>
                                                <div class="controls">
                                                    <input type="password" class="span4" id="password2" name="password2" placeholder="Enter new password again." value="">
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->

                                            <br />

                                            <div class="control-group">											
                                                <label class="control-label" for="passphrase">Passphrase</label>

                                                <div class="controls">
                                                    <div class="input-append">
                                                        <i class="icon-key"></i>
                                                        <input class="span2 m-wrap" id="sender_passphrase" type="text">
                                                        <button class="btn" type="button" id='create_sender_public_key'>Generate New Public Key</button>
                                                    </div>
                                                    <p class="help-block span6">Passphrase is strictly yours. It used for decrypting your messages.</p>
                                                </div>	<!-- /controls -->			
                                            </div> <!-- /control-group -->

                                            <div class="control-group">											
                                                <label class="control-label" for="radiobtns">Cypher Length</label>
                                                <div class="controls">
                                                    <div class="input-prepend input-append">
                                                        <span class="add-on">RSA</span>
                                                        <input class="span2" type="text" name="sender_rsa_bit_length" id="sender_rsa_bit_length" value="<c:if test="${empty sessionScope.user.publicKey.publicKey}">512</c:if><c:if test="${not empty sessionScope.user.publicKey.publicKey}">${sessionScope.user.publicKey.bitLength}</c:if>">
                                                            <span class="add-on">bits</span>
                                                        </div>
                                                        <p class="help-block">Bit lenth must be 512 or 1024. You will not be safe with lower complexity, and your browser will crash for higher computations.</p>
                                                    </div>	<!-- /controls -->			
                                                </div> <!-- /control-group -->

                                                <div class="control-group">											
                                                    <label class="control-label" for="username">Public Key</label>
                                                    <div class="controls">
                                                        <!--<input type="text" class="span6 disabled" id="public_key" name='public_key' value="" readonly="">-->
                                                        <textarea name="sender_public_key" id="sender_public_key" readonly="" class="span6 disabled" 
                                                                  style="height: 100px;">${sessionScope.user.publicKey.publicKey}</textarea>
                                                    <p class="help-block">We will store your public key, and share with people.</p>
                                                </div> <!-- /controls -->				
                                            </div> <!-- /control-group -->

                                            <!-- 
                                            <br />
                                            <div class="control-group">	
                                                <label class="control-label" for="email">Email Address</label>
                                                <div class="controls">
                                                    <input type="text" class="span4" id="email" name='email' value="">
                                                    <p class="help-block">Optional.</p>
                                                </div>
                                            </div> -->

                                            <div class="form-actions">
                                                <button type="submit" class="btn btn-primary">Save</button> 
                                                <button class="btn" type="reset">Cancel</button>
                                            </div> <!-- /form-actions -->

                                        </form>
                                    </fieldset>
                                </div>
                                <!-- /widget-content --> 
                            </div>
                            <!-- /widget -->
                        </div>
                        <!-- /span12 -->
                    </div>
                    <!-- /row --> 
                </div>
                <!-- /container --> 
            </div>
            <!-- /main-inner --> 
        </div>
        <!-- /main -->

        <jsp:include page="incl_050_extra.jsp" />
        <jsp:include page="incl_060_footer.jsp" />
        <jsp:include page="incl_070_scripts.jsp" />
        <c:if test="${not empty guide}">
            <script type="text/javascript" src="assets/js/guidely/guidely.min.js"></script>
            <script type="text/javascript" charset="UTF-8">
                $(function () {
                    guidely.add({
                        attachTo: '#userid'
                        , anchor: 'bottom-middle'
                        , title: 'Id'
                        , text: 'Your user id is displayed here, it is for debug purposes and cannot be changed.'
                    });
                    guidely.add({
                        attachTo: '#username'
                        , anchor: 'bottom-middle'
                        , title: 'Username'
                        , text: 'Your username is displayed here, it cannot be changed.'
                    });
                    guidely.add({
                        attachTo: '#old_password'
                        , anchor: 'bottom-middle'
                        , title: 'Password'
                        , text: 'You use your password for logging in, and saving public key. This is not your encryption key.'
                    });
                    guidely.add({
                        attachTo: '#password1'
                        , anchor: 'bottom-middle'
                        , title: 'New Password'
                        , text: 'To change a password, you also need to provide your old password.'
                    });
                    guidely.add({
                        attachTo: '#password2'
                        , anchor: 'bottom-middle'
                        , title: 'New Password Check'
                        , text: 'Be sure you wrote your new password correct.'
                    });
                    guidely.add({
                        attachTo: '#sender_passphrase'
                        , anchor: 'bottom-middle'
                        , title: 'Passphrase'
                        , text: 'Decode your incoming messages.'
                    });
                    guidely.add({
                        attachTo: '#create_sender_public_key'
                        , anchor: 'bottom-middle'
                        , title: 'Create Public Key'
                        , text: 'Creates public key, based on your passphrase. Public keys are one-sided -> you cannot decrypt a message via public key. You can only encode open texts, to encrypted texts.'
                    });
                    guidely.add({
                        attachTo: '#sender_rsa_bit_length'
                        , anchor: 'bottom-middle'
                        , title: 'Bit Length'
                        , text: 'Complexity of your encryption.'
                    });
                    guidely.add({
                        attachTo: '#sender_public_key'
                        , anchor: 'middle-middle'
                        , title: 'Public Key'
                        , text: 'Encode messages that are coming to you. Can only be dectypted by your passphrase.'
                    });

                    guidely.init({welcome: true, startTrigger: false,
                        welcomeTitle: 'Settings Page',
                        welcomeText: 'Here you can do 3 things.<br/>\n1. Change your login password.<br/>\n2. See your current public key.<br/>\n3. Change your public key (warning: old messages may not be visible if you choose this option).'
                    });
                });
            </script>

        </c:if>
        <script type="text/javascript" charset="UTF-8">
            jQuery(document).ready(function () {
                $("#subnavbar-settings").attr('class', 'active');

                $('#create_sender_public_key').click(function () {
                    var username = "${sessionScope.user.username}";
                    var sender_passphrase = $('#sender_passphrase').val();
                    var sender_rsa_bit_length = $('#sender_rsa_bit_length').val();
                    generatePublicKey(username, sender_passphrase, sender_rsa_bit_length, work);
                });

                function work(sender_public_key) {
                    $('#sender_public_key').val(sender_public_key);
                }
            });
        </script>
    </body>
</html>
