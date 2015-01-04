/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Cagri Celebi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.cagricelebi.securechat.web.servlet;

import com.cagricelebi.securechat.lib.dao.MessageDao;
import com.cagricelebi.securechat.lib.dao.UserDao;
import com.cagricelebi.securechat.lib.logging.Logger;
import com.cagricelebi.securechat.lib.model.Message;
import com.cagricelebi.securechat.lib.model.SimpleMessage;
import com.cagricelebi.securechat.lib.model.User;
import com.cagricelebi.securechat.lib.util.Helper;
import com.cagricelebi.securechat.web.util.WebHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author azureel
 */
@WebServlet(name = "AjaxServlet", urlPatterns = {"/ajax"})
public class AjaxServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AjaxServlet.class);

    @Inject
    UserDao userDao;
    @Inject
    MessageDao messageDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String op = request.getParameter("op") != null ? request.getParameter("op") : "";
            logger.log("op: " + op);
            if (Helper.isEmpty(op)) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("no op specified.");
                }
            } else if (op.contentEquals("get_public_key")) {
                String receiverName = request.getParameter("receiverName") != null ? request.getParameter("receiverName") : "";
                User matchedUser = userDao.searchWithUsername(receiverName);
                if (matchedUser != null) {
                    try (PrintWriter out = response.getWriter()) {
                        out.println(matchedUser.toJson());
                    }
                }
            } else if (op.contentEquals("send_message")) {
                // String senderId = request.getParameter("senderId") != null ? request.getParameter("senderId") : "";
                User user = WebHelper.getUserFromSession(request);
                long senderId = user.getId();
                String receiverId = request.getParameter("receiverId") != null ? request.getParameter("receiverId") : "";
                String senderBody = request.getParameter("senderBody") != null ? request.getParameter("senderBody") : "";
                // String senderPublicKeyId = request.getParameter("senderPublicKeyId") != null ? request.getParameter("senderPublicKeyId") : "";
                long senderPublicKeyId = user.getPublicKey().getId();
                String receiverBody = request.getParameter("receiverBody") != null ? request.getParameter("receiverBody") : "";
                String receiverPublicKeyId = request.getParameter("receiverPublicKeyId") != null ? request.getParameter("receiverPublicKeyId") : "";

                Message msg = new Message();
                msg.setReceiverBody(Helper.filterEncryptedUserInput(receiverBody));
                msg.setReceiverId(Long.parseLong(receiverId));
                msg.setReceiverPublicKeyId(Long.parseLong(receiverPublicKeyId));
                msg.setSenderBody(Helper.filterEncryptedUserInput(senderBody));
                msg.setSenderId(senderId);
                msg.setSenderPublicKeyId(senderPublicKeyId);
                msg = messageDao.insert(msg);

                try (PrintWriter out = response.getWriter()) {
                    out.println("message sent. id:#" + msg.getId());
                }
            } else if (op.contentEquals("delete_message")) {
                User user = WebHelper.getUserFromSession(request);
                long userId = user.getId();
                long messageId = 0;
                try {
                    messageId = request.getParameter("messageId") != null ? Long.parseLong(request.getParameter("messageId")) : 0;
                } catch (Exception e) {
                }

                String output = "";
                if (messageId == 0) {
                    output = "invalid messageId";
                } else {
                    try {
                        messageDao.delete(messageId, userId);
                        output = "message deleted.";
                    } catch (Exception e) {
                        output = e.getMessage();
                    }
                }

                try (PrintWriter out = response.getWriter()) {
                    out.println(output);
                }

            } else if (op.contentEquals("get_messages")) {
                User user = WebHelper.getUserFromSession(request);
                long partnerId = Long.parseLong(request.getParameter("partner_id"));
                User receiver = userDao.getReceiverInfo(partnerId);
                if (receiver != null) {
                    List<SimpleMessage> msgs = messageDao.getMessages(user.getUsername(), user.getId(), user.getPublicKey().getId(), partnerId);
                    request.setAttribute("messages", msgs);
                    request.setAttribute("receiver", receiver);
                }
                // logger.log(msgs);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/conversation.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            logger.log(e);
            try (PrintWriter out = response.getWriter()) {
                out.println("exception: " + e.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
