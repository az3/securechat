<%-- 
    Document   : help.jsp : /help : HelpServlet
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
        <title>SecureChat : Help</title>
        <jsp:include page="incl_010_head.jsp" />
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
                                    <h3>Help & FAQ</h3>
                                </div>
                                <!-- /widget-header -->
                                <div class="widget-content" style="padding: 20px;">
                                    Help page is not ready yet. Sorry.
                                </div>
                                <!-- /widget-content --> 
                            </div>
                            <!-- /widget -->
                        </div>

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
        <script type="text/javascript" charset="UTF-8">
            jQuery(document).ready(function () {
                $("#subnavbar-help").attr('class', 'active');
            });
        </script>
    </body>
</html>
