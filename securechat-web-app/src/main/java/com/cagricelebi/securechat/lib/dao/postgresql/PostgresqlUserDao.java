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
package com.cagricelebi.securechat.lib.dao.postgresql;

import com.cagricelebi.securechat.lib.dao.UserDao;
import static com.cagricelebi.securechat.lib.dao.postgresql.PostgresqlDao.SCHEMA_NAME;
import com.cagricelebi.securechat.lib.logging.Logger;
import com.cagricelebi.securechat.lib.model.PublicKey;
import com.cagricelebi.securechat.lib.model.User;
import com.cagricelebi.securechat.web.util.PasswordHash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author azureel
 */
public class PostgresqlUserDao extends PostgresqlDao implements UserDao {

    private static final Logger logger = Logger.getLogger(PostgresqlUserDao.class);

    @Override
    public User insert(User registrar) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "insert into " + SCHEMA_NAME + ".\"user\" "
                    + "(id, username, password_hash, public_key_id, create_time) values "
                    + "(default, ?, ?, null, default)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, registrar.getUsername());
                ps.setString(2, registrar.getPasswordHash());
                int row = ps.executeUpdate();
                if (row != 1) {
                    throw new SQLException("Error during insert. Inserted row != 1.");
                } else {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            registrar.setId(rs.getLong(1));
                        } else {
                            throw new SQLException("Error during insert. Cannot retrieve auto-generated keys.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
        return registrar;
    }

    @Override
    public User login(String username, char[] plainTextPassword) {
        try (Connection conn = conn()) {
            // select "user".id, username, password_hash, public_key_id, bit_length, public_key from "user" left join "public_key" on "public_key".id="user"."public_key_id" where "username"='azur';
            String sql = "select \"user\".id, username, password_hash, public_key_id, bit_length, public_key from "
                    + SCHEMA_NAME + ".\"user\" left join "
                    + SCHEMA_NAME + ".\"public_key\" on \"public_key\".id=\"user\".\"public_key_id\" "
                    + " where \"username\"=? ";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String passwordHashInDatabase = rs.getString("password_hash");
                        if (PasswordHash.validatePassword(plainTextPassword, passwordHashInDatabase)) {
                            User user = new User();
                            user.setId(rs.getLong("id"));
                            user.setUsername(rs.getString("username"));
                            // user.setPasswordHash(passwordHashInDatabase);
                            PublicKey publicKey = new PublicKey();
                            publicKey.setId(rs.getLong("public_key_id"));
                            publicKey.setBitLength(rs.getInt("bit_length"));
                            publicKey.setPublicKey(rs.getString("public_key"));
                            user.setPublicKey(publicKey);
                            return user;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return null;
    }

    @Override
    public String getPasswordHash(long userId) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "select \"user\".password_hash from " + SCHEMA_NAME + ".\"user\" where id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("password_hash");
                    } else {
                        throw new SQLException("User not found.");
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    @Override
    public User searchWithUsername(String username) {
        try (Connection conn = conn()) {
            String sql = "select \"user\".id, \"user\".username, \"user\".public_key_id, public_key.public_key from "
                    + SCHEMA_NAME + ".\"user\" left join "
                    + SCHEMA_NAME + ".\"public_key\" on \"public_key\".id=\"user\".\"public_key_id\" "
                    + " where username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        PublicKey publicKey = new PublicKey();
                        publicKey.setId(rs.getLong("public_key_id"));
                        publicKey.setPublicKey(rs.getString("public_key"));
                        user.setPublicKey(publicKey);
                        return user;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return null;
    }

    @Override
    public User getReceiverInfo(long userId) {
        try (Connection conn = conn()) {
            String sql = "select \"user\".id, \"user\".username, \"user\".public_key_id, public_key.public_key from "
                    + SCHEMA_NAME + ".\"user\" left join "
                    + SCHEMA_NAME + ".\"public_key\" on \"public_key\".id=\"user\".\"public_key_id\" "
                    + " where \"user\".id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        PublicKey publicKey = new PublicKey();
                        publicKey.setId(rs.getLong("public_key_id"));
                        publicKey.setPublicKey(rs.getString("public_key"));
                        user.setPublicKey(publicKey);
                        return user;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return null;
    }

    @Override
    public User update(User user, boolean passwordChanged, boolean publicKeyChanged) throws SQLException {
        try (Connection conn = conn()) {

            String sql;
            if (passwordChanged && publicKeyChanged) {
                sql = "update " + SCHEMA_NAME + ".\"user\" set password_hash=?, public_key_id=? WHERE id=?";
            } else if (passwordChanged && !publicKeyChanged) {
                sql = "update " + SCHEMA_NAME + ".\"user\" set password_hash=? WHERE id=?";
            } else if (!passwordChanged && publicKeyChanged) {
                sql = "update " + SCHEMA_NAME + ".\"user\" set public_key_id=? WHERE id=?";
            } else {
                throw new SQLException("no value changed.");
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if (passwordChanged && publicKeyChanged) {
                    ps.setString(1, user.getPasswordHash());
                    PublicKey publicKey = insert(user.getPublicKey());
                    user.setPublicKey(publicKey);
                    ps.setLong(2, user.getPublicKey().getId());
                    ps.setLong(3, user.getId());
                } else if (passwordChanged && !publicKeyChanged) {
                    ps.setString(1, user.getPasswordHash());
                    ps.setLong(2, user.getId());
                } else if (!passwordChanged && publicKeyChanged) {
                    PublicKey publicKey = insert(user.getPublicKey());
                    user.setPublicKey(publicKey);
                    ps.setLong(1, user.getPublicKey().getId());
                    ps.setLong(2, user.getId());
                }
                int row = ps.executeUpdate();
                if (row != 1) {
                    throw new SQLException("Error during insert. Inserted row != 1.");
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
        return user;
    }

    private PublicKey insert(PublicKey publicKey) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "insert into " + SCHEMA_NAME + ".public_key "
                    + "(id, bit_length, public_key, create_time) values "
                    + "(default, ?, ?, default)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, publicKey.getBitLength());
                ps.setString(2, publicKey.getPublicKey());
                int row = ps.executeUpdate();
                if (row != 1) {
                    throw new SQLException("Error during insert. Inserted row != 1.");
                } else {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            publicKey.setId(rs.getLong(1));
                        } else {
                            throw new SQLException("Error during insert. Cannot retrieve auto-generated keys.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
        return publicKey;
    }

}
