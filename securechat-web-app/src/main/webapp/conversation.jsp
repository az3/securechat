<%-- 
    Document   : conversation.jsp : /ajax?op=get_messages : AjaxServlet
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="ios-bubble-container" class="ios-bubble-container">
    <c:if test="${not empty messages}">
        <c:forEach var="msg" items="${messages}">
            <c:choose>
                <c:when test="${msg.senderId eq sessionScope.user.id}">
                    <div class="ios-bubble ios-bubble--alt">
                        <span class="ios-bubble-details--alt">${fn:escapeXml(msg.senderUsername)}<!-- -&gt; ${fn:escapeXml(msg.receiverUsername)} -->: </span>
                        <div  id="enc_body_${msg.id}">${fn:escapeXml(msg.body)}</div>
                        <div  id="dec_body_${msg.id}" style="display: none;"></div>
                        <span class="ios-bubble-details--alt">(<fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${msg.createTime}" />)</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="ios-bubble">
                        <span class="ios-bubble-details">${fn:escapeXml(msg.senderUsername)}<!-- -&gt; ${fn:escapeXml(msg.receiverUsername)} -->: </span>
                        <div  id="enc_body_${msg.id}">${fn:escapeXml(msg.body)}</div>
                        <div  id="dec_body_${msg.id}" style="display: none;"></div>
                        <span class="ios-bubble-details">(<fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${msg.createTime}" />)</span>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
</div>

<c:if test="${not empty messages}">
    <div id="ios-send-box" class="ios-bubble-container">
        <div class="ios-bubble ios-bubble--alt">
            <span class="ios-bubble-details--alt">${fn:escapeXml(sessionScope.user.username)} -&gt; ${fn:escapeXml(receiver.username)} : </span>
            <div>
                <input type="hidden" name="receiver_id" id="receiver_id" value="${receiver.id}"/>
                <input type="hidden" name="receiver_username" id="receiver_username" value="${fn:escapeXml(receiver.username)}"/>
                <input type="hidden" name="receiver_public_key" id="receiver_public_key" value="${fn:escapeXml(receiver.publicKey.publicKey)}"/>
                <input type="hidden" name="receiver_public_key_id" id="receiver_public_key_id" value="${receiver.publicKey.id}"/>
                <textarea id="sender_decrypted_message" style="width: 200px;"></textarea>
                <button class="btn" type="button" id='encrypt_message_for_two_then_send'>Send</button>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${empty messages}">
    <!-- I doubt someone will enter here... -->
    <div id="ios-send-box" class="ios-bubble-container">
        <div class="ios-bubble ios-bubble--alt">
            <span style="font-size: x-small; color: #d55;">${fn:escapeXml(sessionScope.user.username)} -&gt; ${fn:escapeXml(receiver.username)} : </span>
            <div>
                <input type="text" name="receiver_username" id="receiver_username" value="${fn:escapeXml(receiver.username)}" placeholder="Receiver name"/>
                <textarea name="sender_decrypted_message" id="sender_decrypted_message" style="width: 200px;"></textarea>
                <button class="btn" type="button" id='encrypt_message_for_two_then_send'>Send</button>
            </div>
        </div>
    </div>
</c:if>

<!-- 
                    Thanks Aleksey <3 http://ixti.net/development/javascript/2011/11/11/base64-encodedecode-of-utf8-in-browser-with-js.html
-->

<script type="text/javascript" charset="UTF-8">
    jQuery(document).ready(function () {
        $('#encrypt_message_for_two_then_send').click(function () {
            var receiver_username = '${fn:escapeXml(receiver.username)}';
            var sender_public_key = '${fn:escapeXml(sessionScope.user.publicKey.publicKey)}';
            var sender_decrypted_message = $('#sender_decrypted_message').val();
            ajaxGetInfoAndSendMessage(receiver_username, sender_public_key, sender_decrypted_message, appendConversation);
        });
        
        var counter = 0;
        function appendConversation() {
            counter++;
            var sender_decrypted_message = $('#sender_decrypted_message').val();
            var divLike = '<div class="ios-bubble ios-bubble--alt">'
                + ' <span style="font-size: x-small; color: #d55;">${fn:escapeXml(sessionScope.user.username)}<!-- -&gt; ${fn:escapeXml(receiver.username)} -->: </span>'
                + ' <div  id="enc_body_-' + counter + '">' + sender_decrypted_message + '</div>'
                + ' <div  id="dec_body_-' + counter + '" style="display: none;"></div>'
                + ' <span style="font-size: x-small; color: #d55;">(just now)</span>'
                + ' </div>';
            var container = $('#ios-bubble-container');
            $(divLike).appendTo(container);
            $('#sender_decrypted_message').val('');
        }
    });
</script>

