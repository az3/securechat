<%-- 
    Document   : home.jsp : /home : HomeServlet
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
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SecureChat : Home</title>
        <jsp:include page="incl_010_head.jsp" />
        <link type="text/css" rel="stylesheet" href="assets/css/pages/dashboard.css" />
    </head>
    <body>
        <jsp:include page="incl_020_navbar.jsp" />
        <jsp:include page="incl_030_subnavbar.jsp" />

        <div class="main">
            <div class="main-inner">
                <div class="container">
                    <div class="row">
                        <div class="span6">
                            <div class="widget widget-nopad">
                                <div class="widget-header"> <i class="icon-list-alt"></i>
                                    <h3> Recent News</h3>
                                </div>
                                <!-- /widget-header -->
                                <div class="widget-content">
                                    <ul class="news-items">
                                        <li>
                                            <div class="news-item-date"> <span class="news-item-day">28</span> <span class="news-item-month">Dec</span> </div>
                                            <div class="news-item-detail"> <a href="#" class="news-item-title" >Completion</a>
                                                <p class="news-item-preview">I implemented the code, and later found a bootstrap template, which is easy to alter.
                                                    The site is becoming useful, at last. It would be perfect to have an SSL certificate...</p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="news-item-date"> <span class="news-item-day">22</span> <span class="news-item-month">Dec</span> </div>
                                            <div class="news-item-detail"> <a href="#" class="news-item-title" >Proof of Concept</a>
                                                <p class="news-item-preview">The algorithm consists of RSA with ECDH, using key pairs for all users. 
                                                    Encryption and decryption is being done on client side.
                                                    So site owners will never have the decrypted message.
                                                    Even ISPs will not be able read the plain text communications data.</p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="news-item-date"> <span class="news-item-day">21</span> <span class="news-item-month">Dec</span> </div>
                                            <div class="news-item-detail"> <a href="#" class="news-item-title" >First Idea</a>
                                                <p class="news-item-preview">While talking about the user privacy about a new website, 
                                                    I proposed to encrypt the user to user messages on client side, 
                                                    so that only the sender and the receiver could decrypt the message.
                                                    After that, I started doing this web site, as a poc.</p>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <!-- /widget-content --> 
                            </div>
                            <!-- /widget -->
                        </div>
                        <!-- /span6 -->
                        <div class="span6">
                            <div class="widget">
                                <div class="widget-header"> <i class="icon-bookmark"></i>
                                    <h3>Shortcuts</h3>
                                </div>
                                <!-- /widget-header -->
                                <div class="widget-content">
                                    <div class="shortcuts"> 
                                        <a href="./messages" class="shortcut"><i class="shortcut-icon icon-envelope"></i><span class="shortcut-label">Messages</span></a>
                                        <a href="./settings" class="shortcut"><i class="shortcut-icon icon-cog"></i><span class="shortcut-label">Settings</span></a>
                                        <!--
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-list-alt"></i><span class="shortcut-label">Apps</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-bookmark"></i><span class="shortcut-label">Bookmarks</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-signal"></i> <span class="shortcut-label">Reports</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-comment"></i><span class="shortcut-label">Comments</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-user"></i><span class="shortcut-label">Users</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-file"></i><span class="shortcut-label">Notes</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-picture"></i><span class="shortcut-label">Photos</span></a>
                                        <a href="javascript:;" class="shortcut"><i class="shortcut-icon icon-tag"></i><span class="shortcut-label">Tags</span></a> 
                                        -->
                                    </div>
                                    <!-- /shortcuts --> 
                                </div>
                                <!-- /widget-content --> 
                            </div>
                            <!-- /widget -->
                            <div class="widget widget-nopad">
                                <div class="widget-header"> <i class="icon-list-alt"></i>
                                    <h3> Today's Stats</h3>
                                </div>
                                <!-- /widget-header -->
                                <div class="widget-content">
                                    <div class="widget big-stats-container">
                                        <div class="widget-content">
                                            <h6 class="bigstats">1.837.837 users tried our product, and all of them are happy!</h6>
                                            <div id="big_stats" class="cf">
                                                <div class="stat"> <i class="icon-user"></i> <span class="value">2.016</span> </div>
                                                <!-- .stat -->

                                                <div class="stat"> <i class="icon-shield"></i> <span class="value">9.238</span> </div>
                                                <!-- .stat -->

                                                <div class="stat"> <i class="icon-key"></i> <span class="value">11.506</span> </div>
                                                <!-- .stat -->

                                                <div class="stat"> <i class="icon-rocket"></i> <span class="value">40.760</span> </div>
                                                <!-- .stat --> 
                                            </div>
                                        </div>
                                        <!-- /widget-content --> 

                                    </div>
                                </div>
                            </div>
                            <!-- /widget -->
                        </div>
                        <!-- /span6 --> 
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
        <script src="assets/js/excanvas.min.js"></script> 
        <script type="text/javascript" charset="UTF-8">
            jQuery(document).ready(function () {
                $("#subnavbar-home").attr('class', 'active');
            });
        </script>
    </body>
</html>
