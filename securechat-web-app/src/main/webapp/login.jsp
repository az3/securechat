<%-- 
    Document   : login.jsp : /login : LoginServlet
    Created on : Dec 23, 2014, 11:19:17 PM
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
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SecureChat : Login</title>
        <jsp:include page="incl_010_head.jsp" />
        <link href="assets/css/pages/signin.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="incl_020_navbar.jsp" />

        <div class="account-container">
            <div class="content clearfix">
                <form action="./login" method="post">
                    <h1>Member Login</h1>		
                    <div class="login-fields">
                        <p>Please provide your details</p>
                        <div class="field">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="" placeholder="Username" class="login username-field" />
                        </div> <!-- /field -->
                        <div class="field">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" value="" placeholder="Password" class="login password-field"/>
                        </div> <!-- /password -->
                    </div> <!-- /login-fields -->
                    <div class="login-actions">
                        <span class="login-checkbox">
                            <input id="Field" name="Field" type="checkbox" class="field login-checkbox" value="First Choice" tabindex="4" />
                            <label class="choice" for="Field">Keep me signed in</label>
                        </span>
                        <button class="button btn btn-success btn-large">Sign In</button>
                    </div> <!-- .actions -->
                </form>
            </div> <!-- /content -->
        </div> <!-- /account-container -->

        <div class="login-extra">
            Not a member? <a href="./signup">Sign up here</a>.
        </div> <!-- /login-extra -->

        <jsp:include page="incl_070_scripts.jsp" />
    </body>
</html>