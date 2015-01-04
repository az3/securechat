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
import com.cagricelebi.securechat.lib.model.User;
import com.cagricelebi.securechat.lib.util.Helper;
import com.cagricelebi.securechat.web.util.PasswordHash;
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
@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SignUpServlet.class);
    @Inject
    UserDao userDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String method = request.getMethod();
            if (!Helper.isEmpty(method) && method.toUpperCase().contentEquals("POST")) {

                String username = request.getParameter("username") != null ? request.getParameter("username") : "";
                String password1 = request.getParameter("password1") != null ? request.getParameter("password1") : "";
                String password2 = request.getParameter("password2") != null ? request.getParameter("password2") : "";

                String error;

                // TODO do form validation on js side, pre submit. pls...
                if (Helper.isEmpty(username) || Helper.isEmpty(password1) || Helper.isEmpty(password2)) {
                    error = "Please enter all 3 fields: username, password and password confirmation.";
                    request.setAttribute("error", error);
                    return;
                }

                if (!password1.contentEquals(password2)) {
                    error = "Password fields do not match.";
                    request.setAttribute("error", error);
                    return;
                }

                if (userDao.searchWithUsername(username) != null) {
                    error = "Someone already took that username.";
                    request.setAttribute("error", error);
                    return;
                }

                User registrar = new User();
                registrar.setUsername(username);
                registrar.setPasswordHash(PasswordHash.createHash(password2.toCharArray()));

                registrar = userDao.insert(registrar);
                String success = "You have registered successfully. Please login with your username and password.";
                request.setAttribute("success", success);
                // TODO maybe login automatically?
            }

        } catch (Exception e) {
            logger.log(e);
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/signup.jsp");
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
