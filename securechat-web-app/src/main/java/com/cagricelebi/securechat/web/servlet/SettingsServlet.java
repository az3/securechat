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

import com.cagricelebi.securechat.lib.dao.UserDao;
import com.cagricelebi.securechat.lib.logging.Logger;
import com.cagricelebi.securechat.lib.model.PublicKey;
import com.cagricelebi.securechat.lib.model.User;
import com.cagricelebi.securechat.lib.util.Helper;
import com.cagricelebi.securechat.web.util.PasswordHash;
import com.cagricelebi.securechat.web.util.WebHelper;
import java.io.IOException;
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
@WebServlet(name = "SettingsServlet", urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SettingsServlet.class);

    @Inject
    UserDao userDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String method = request.getMethod();

            if (!Helper.isEmpty(method) && method.toUpperCase().contentEquals("GET")) {
                String guide = request.getParameter("guide") != null ? request.getParameter("guide") : "";
                if (!Helper.isEmpty(guide)) {
                    request.setAttribute("guide", true);
                }
            }

            if (!Helper.isEmpty(method) && method.toUpperCase().contentEquals("POST")) {

                String old_password = request.getParameter("old_password") != null ? request.getParameter("old_password") : "";
                String password1 = request.getParameter("password1") != null ? request.getParameter("password1") : "";
                String password2 = request.getParameter("password2") != null ? request.getParameter("password2") : "";
                String sender_public_key = request.getParameter("sender_public_key") != null ? request.getParameter("sender_public_key") : "";
                String sender_rsa_bit_length = request.getParameter("sender_rsa_bit_length") != null ? request.getParameter("sender_rsa_bit_length") : "";

                // TODO write a seperate validator class and put methods there.
                if ((!Helper.isEmpty(password1) || !Helper.isEmpty(password1) || !Helper.isEmpty(sender_public_key) || !Helper.isEmpty(sender_rsa_bit_length))
                        && !Helper.isEmpty(old_password)) {
                    User user = WebHelper.getUserFromSession(request);
                    String currentPasswordHash = userDao.getPasswordHash(user.getId());
                    if (!PasswordHash.validatePassword(old_password.toCharArray(), currentPasswordHash)) {
                        request.setAttribute("alert", "Old password is not correct. No information updated.");
                    } else {
                        boolean passwordChanged = false, publicKeyChanged = false;
                        if (!Helper.isEmpty(password1) && !Helper.isEmpty(password2) && password1.contentEquals(password2)
                                && !PasswordHash.validatePassword(password1.toCharArray(), currentPasswordHash)) {
                            user.setPasswordHash(PasswordHash.createHash(password1.toCharArray()));
                            passwordChanged = true;
                        }
                        if (!Helper.isEmpty(sender_public_key) && !Helper.isEmpty(sender_rsa_bit_length)) {
                            if (user.getPublicKey() == null || user.getPublicKey().getPublicKey() == null
                                    || !user.getPublicKey().getPublicKey().contentEquals(sender_public_key)) {
                                PublicKey pkey = new PublicKey();
                                pkey.setBitLength(Integer.parseInt(sender_rsa_bit_length));
                                pkey.setPublicKey(Helper.filterEncryptedUserInput(sender_public_key));
                                user.setPublicKey(pkey);
                                publicKeyChanged = true;
                            }
                        }
                        // logger.log("passwordChanged: {0}, publicKeyChanged: {1}", passwordChanged, publicKeyChanged);
                        if (passwordChanged || publicKeyChanged) {
                            user = userDao.update(user, passwordChanged, publicKeyChanged);
                            request.getSession().setAttribute("user", user);
                            request.setAttribute("message", "User preferences updated.");
                        } else {
                            request.setAttribute("message", "No change detected, no information updated.");
                        }
                    }
                } else {
                    request.setAttribute("message", "No change detected, no information updated.");
                }
            }
        } catch (Exception e) {
            logger.log(e);
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/settings.jsp");
            requestDispatcher.forward(request, response);
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
