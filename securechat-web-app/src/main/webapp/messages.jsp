<%-- 
    Document   : messages.jsp : /messages : MessagesServlet
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
        <title>SecureChat : Messages</title>
        <jsp:include page="incl_010_head.jsp" />
        <link type="text/css" rel="stylesheet" href="assets/css/ios-bubble.css">
        <link type="text/css" rel="stylesheet" href="assets/css/ui-tabs-fix.css" >
        <c:if test="${not empty guide}">
            <link type="text/css" rel="stylesheet" href="assets/js/guidely/guidely.css" />
        </c:if>
    </head>
    <body>
        <jsp:include page="incl_020_navbar.jsp" />
        <jsp:include page="incl_030_subnavbar.jsp" />

        <div class="main">
            <div class="main-inner">
                <div class="container">
                    <div class="row" id="hint_row">
                        <div class="span12">
                            <div class="widget" style="margin-bottom: 0.5em;">
                                <div class="widget-header" style="height: auto;">
                                    <h3>Enter your passphrase to decrypt messages <i class="icon-key"></i> 
                                        <input type="hidden" name="sender_rsa_bit_length" id="sender_rsa_bit_length" value="${fn:escapeXml(sessionScope.user.publicKey.bitLength)}" />
                                        <input class="span2 m-wrap" id="sender_passphrase" name="sender_passphrase" type="text" 
                                               style="margin-bottom: 0px;" value="" placeholder="passphrase, not password." onkeypress="return checkEnterDecrypt(this, event);">
                                        <button class="btn" type="button" id='test_decryption'>Start decryption</button>
                                    </h3>
                                </div>
                            </div>
                        </div> <!-- /span12 -->
                    </div> <!-- /row -->
                    <div class="row">
                        <div class="span12">      		
                            <div class="widget ">
                                <div class="widget-header"  style="height: 30px; border: none;">
                                    <ul class="nav nav-tabs" style="background-color: #f9f6f1;">
                                        <li class="active">
                                            <a href="#conversations" data-toggle="tab">Conversations</a>
                                        </li>
                                        <li>
                                            <a href="#drafts" data-toggle="tab">Drafts</a>
                                        </li>
                                        <li>
                                            <a href="#archived" data-toggle="tab">Archived</a>
                                        </li>
                                    </ul>
                                </div> <!-- /widget-header -->

                                <div class="widget-content">
                                    <div class="tabbable" style="margin: -20px -15px -15px;">
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="conversations">
                                                <div id="tabs">
                                                    <ul>
                                                        <li><a href="#tabs-1" id="send-new-message" style="color: red;">+ New</a></li>
                                                            <c:if test="${not empty userList}">
                                                                <c:forEach var="partner" items="${userList}">
                                                                <li><a href="./ajax?op=get_messages&partner_id=${partner.id}">${fn:escapeXml(partner.username)}</a></li>
                                                                </c:forEach>
                                                            </c:if>
                                                    </ul>
                                                    <div id="tabs-1">  
                                                        <c:if test="${not empty userList}">
                                                            <p style="margin-left: 11em;">Click on a name from the list to see conversation. To send a new message, click "<span id="send-new-message-2" style="color: red;">+ New</span>" or use the form below.</p>
                                                        </c:if>
                                                        <c:if test="${empty userList}">
                                                            <p style="margin-left: 11em;" >Your inbox is empty. To send a new message, click "<span id="send-new-message-3" style="color: red;">+ New</span>" or use the form below.</p>
                                                        </c:if>
                                                        <div id="ios-send-box" class="ios-bubble-container">
                                                            <div class="ios-bubble ios-bubble--alt">
                                                                <span class="ios-bubble-details--alt">${fn:escapeXml(sessionScope.user.username)} -&gt; ... : </span>
                                                                <div>
                                                                    <input type="text" name="receiver_username_new" id="receiver_username_new" value="" placeholder="Receiver name"/>
                                                                    <textarea id="sender_decrypted_message_new" style="width: 200px;"></textarea>
                                                                    <button class="btn" type="button" id='encrypt_message_for_two_then_send_new'>Send</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane" id="drafts">
                                                <div>
                                                    drafts
                                                </div>
                                            </div>

                                            <div class="tab-pane" id="archived">
                                                <div>
                                                    archived
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div> <!-- /widget-content -->
                            </div> <!-- /widget -->
                        </div> <!-- /span12 -->
                    </div> <!-- /row -->
                </div> <!-- /container -->
            </div> <!-- /main-inner -->
        </div> <!-- /main -->

        <jsp:include page="incl_050_extra.jsp" />
        <jsp:include page="incl_060_footer.jsp" />
        <jsp:include page="incl_070_scripts.jsp" />

        <c:if test="${not empty guide}">
            <script type="text/javascript" src="assets/js/guidely/guidely.min.js"></script>
            <script>
            </script>
            <script type="text/javascript" charset="UTF-8">
                $(function () {
                    guidely.add({
                        attachTo: '#receiver_username_new'
                        , anchor: 'bottom-middle'
                        , title: 'Receiver'
                        , text: 'The participant you want to send message.'
                    });
                    guidely.add({
                        attachTo: '#sender_decrypted_message_new'
                        , anchor: 'middle-middle'
                        , title: 'Message'
                        , text: 'Enter the message you want to send, then click \'Send\'.'
                    });
                    guidely.add({
                        attachTo: '#send-new-message'
                        , anchor: 'bottom-middle'
                        , title: 'Username Selector'
                        , text: 'Your conversations with people are listed on the left, select someone to see message history.'
                    });
                    guidely.add({
                        attachTo: '#sender_passphrase'
                        , anchor: 'bottom-middle'
                        , title: 'Passphrase'
                        , text: 'Your passphrase is needed, only for decoding.'
                    });
                    guidely.init({welcome: true, startTrigger: false,
                        welcomeTitle: 'Messages Page',
                        welcomeText: 'Here you can do 3 things.<br/>\n1. Send new messages to a recepient (you must know his/her username).<br/>\n2. See your current conversation with a participant.<br/>\n3. Decode your messages (warning: If you have changed your passphrase, old messages may not be visible for some participants).'
                    });
                });
            </script>
        </c:if>

        <script type="text/javascript" charset="UTF-8">
            jQuery(document).ready(function () {
                $("#subnavbar-messages").attr('class', 'active');

                $('#test_decryption').click(function () {
                    decryptConversation('${fn:escapeXml(sessionScope.user.username)}');
                });

                $(function () {
                    // $("#tabs").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
                    $('#tabs').tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
                    $('#tabs li').removeClass('ui-corner-top').addClass('ui-corner-left');
                });

                $('#encrypt_message_for_two_then_send_new').click(function () {
                    var receiver_username = $('#receiver_username_new').val();
                    var sender_public_key = '${fn:escapeXml(sessionScope.user.publicKey.publicKey)}';
                    var sender_decrypted_message = $('#sender_decrypted_message_new').val();
                    ajaxGetInfoAndSendMessage(receiver_username, sender_public_key, sender_decrypted_message, postOpsMessages);
                });
            });

            function postOpsMessages() {
                $('#sender_decrypted_message_new').val('');
                location.reload();
            }

            function checkEnterDecrypt(elem, ev) {
                if (ev && ev.keyCode === 13) {
                    $('#test_decryption').click();
                }
            }

        </script>
    </body>
</html>
