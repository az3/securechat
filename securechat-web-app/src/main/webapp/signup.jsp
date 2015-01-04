<%-- 
    Document   : signup.jsp : /signup : SignUpServlet
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
        <title>SecureChat : Sign Up</title>
        <jsp:include page="incl_010_head.jsp" />
        <link href="assets/css/pages/signin.css" type="text/css" rel="stylesheet">
        <c:if test="${not empty success}">
            <meta http-equiv="refresh" content="2; url=./login">
        </c:if>
    </head>
    <body>
        <jsp:include page="incl_020_navbar.jsp" />

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Alert:</strong> ${fn:escapeXml(error)}
            </div>
        </c:if>
        <c:choose>
            <c:when test="${not empty success}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Success:</strong> ${fn:escapeXml(success)}
                </div>
                <p style="margin-left: 2em; margin-top: 2em;">
                    You are being redirected to <a href="./login">Login page</a> in a few seconds.
                </p>
            </c:when>
            <c:otherwise>
                <div class="account-container register">
                    <div class="content clearfix">
                        <form action="./signup" method="post">
                            <h1>Signup</h1>			
                            <div class="login-fields">
                                <p>Create your free account:</p>

                                <div class="field">
                                    <!--<p class="help-block">Username</p>-->
                                    <label for="username">Username</label>
                                    <input type="text" id="username" name="username" value="" placeholder="Username" class="login"/>
                                    Your username is for logging in and cannot be changed.
                                </div> <!-- /field -->

                                <div class="field">
                                    <!--<p class="help-block">Password</p>-->
                                    <label for="password">Password</label>
                                    <input type="password" id="password1" name="password1" value="" placeholder="Password" class="login"/>
                                    Password is just for logging in, this is not your encryption key.
                                </div> <!-- /field -->

                                <div class="field">
                                    <!--<p class="help-block">Confirm Password</p>-->
                                    <label for="password2">Confirm Password</label>
                                    <input type="password" id="password2" name="password2" value="" placeholder="Confirm Password" class="login"/>
                                </div> <!-- /field -->
                            </div> <!-- /login-fields -->
                            <div class="login-actions">
                                <span class="login-checkbox">
                                    <input id="Field" name="Field" type="checkbox" class="field login-checkbox" value="First Choice" tabindex="4" />
                                    <label class="choice" for="Field">Agree with the <a href="./help#terms-of-use">Terms & Conditions</a>.</label>
                                </span>
                                <button class="button btn btn-primary btn-large">Register</button>
                            </div> <!-- .actions -->
                        </form>
                    </div> <!-- /content -->
                </div> <!-- /account-container -->
                <!-- Text Under Box -->
                <div class="login-extra">
                    Already have an account? <a href="./login">Login to your account</a>.
                </div> <!-- /login-extra -->
            </c:otherwise>
        </c:choose>

        <jsp:include page="incl_070_scripts.jsp" />
    </body>
</html>
