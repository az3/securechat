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
package com.cagricelebi.securechat.lib.dao;

import com.cagricelebi.securechat.lib.model.User;
import java.sql.SQLException;

/**
 *
 * @author azureel
 */
public interface UserDao {

    User insert(User registrar) throws SQLException;

    /**
     * Using plainText password, to see if the hashed version in database matches.
     * https://crackstation.net/hashing-security.htm
     *
     * @param username
     * @param plainTextPassword
     * @return
     */
    User login(String username, char[] plainTextPassword);

    String getPasswordHash(long userId) throws SQLException;

    User searchWithUsername(String username);

    User getReceiverInfo(long userId);

    User update(User user, boolean passwordChanged, boolean publicKeyChanged) throws SQLException;

}
