<%-- 
    Document   : 
    Created on : 
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

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <a class="brand" href="./home">
                <img src="assets/img/pixshark_com_padlock-flat-icon_31277.png" style="width: 32px; height: 32px; margin-top: -14px;" />SecureChat
            </a>		

            <div class="nav-collapse">
                <ul class="nav pull-right">
                    <li class="dropdown">						
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-user"></i> 
                            &nbsp; <c:if test="${empty sessionScope.user}">Account</c:if>
                            <c:if test="${not empty sessionScope.user}">${fn:escapeXml(sessionScope.user.username)}
                            </c:if>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <style type="text/css">
                                @media (max-width:979px){
                                    .navbar .nav>li>a,.navbar .dropdown-menu a{color:#DDFFFF;}
                                }
                            </style>
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <li><a href="./settings"><i class="icon-cog"></i> &nbsp; Settings</a></li>
                                    <li><a href="./logout"><i class="icon-signout"></i> &nbsp; Logout</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li><a href="./login"><i class="icon-signin"></i> &nbsp; Login</a></li>
                                    <li><a href="./signup"><i class="icon-edit"></i> &nbsp; Sign Up</a></li>
                                    </c:otherwise>
                                </c:choose>
                            <li class="menu-divider"></li>
                            <li><a href="./help#navbar"><i class="icon-question"></i> &nbsp; Help</a></li>
                        </ul>						
                    </li>
                </ul>
            </div>

        </div> <!-- /container -->
    </div> <!-- /navbar-inner -->
</div> <!-- /navbar -->
