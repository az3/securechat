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

import com.cagricelebi.securechat.lib.dao.MessageDao;
import com.cagricelebi.securechat.lib.logging.Logger;
import com.cagricelebi.securechat.lib.model.Message;
import com.cagricelebi.securechat.lib.model.SimpleMessage;
import com.cagricelebi.securechat.lib.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author azureel
 */
public class PostgresqlMessageDao extends PostgresqlDao implements MessageDao {

    private static final Logger logger = Logger.getLogger(PostgresqlMessageDao.class);

    @Override
    public Message insert(Message message) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "insert into " + SCHEMA_NAME + ".message "
                    + "(id, sender_id, receiver_id, receiver_read, "
                    + " sender_body, sender_public_key_id, receiver_body, receiver_public_key_id) values "
                    + "(default, ?, ?, default, "
                    + " ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, message.getSenderId());
                ps.setLong(2, message.getReceiverId());
                ps.setString(3, message.getSenderBody());
                ps.setLong(4, message.getSenderPublicKeyId());
                ps.setString(5, message.getReceiverBody());
                ps.setLong(6, message.getReceiverPublicKeyId());
                int row = ps.executeUpdate();
                if (row != 1) {
                    throw new SQLException("Error during insert. Inserted row != 1.");
                } else {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            message.setId(rs.getLong(1));
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
        return message;
    }

    private Message getMessage(long messageId) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "select * from __SCHEMANAME__.message where id =?".replaceFirst(SCHEMA_NAME_PATTERN, SCHEMA_NAME);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, messageId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    return getMessageFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    @Override
    public List<SimpleMessage> getMessages(String username, long userId, long publicKeyId, long partnerId) throws SQLException {

        try (Connection conn = conn()) {
            String sql = "select * from (\n"
                    + "	(select message.id, sender_id, \"user\".username as sender_username, receiver_id, ? as receiver_username, receiver_read, receiver_body as body, \n"
                    + "   receiver_public_key_id as public_key_id, receiver_deleted as deleted, message.create_time \n"
                    + "   from " + SCHEMA_NAME + ".message left join " + SCHEMA_NAME + ".\"user\" on \"user\".id=\"message\".sender_id where receiver_id=? and receiver_public_key_id=? and sender_id=?)\n"
                    + "	union all\n"
                    + "	(select message.id, sender_id, ? as sender_username, receiver_id, \"user\".username as receiver_username, receiver_read, sender_body as body, \n"
                    + "   sender_public_key_id as public_key_id, sender_deleted as deleted, message.create_time \n"
                    + "   from " + SCHEMA_NAME + ".message left join " + SCHEMA_NAME + ".\"user\" on \"user\".id=\"message\".receiver_id where sender_id=? and sender_public_key_id=? and receiver_id=?)\n"
                    + ") temp order by create_time asc";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setLong(2, userId);
                ps.setLong(3, publicKeyId);
                ps.setLong(4, partnerId);
                ps.setString(5, username);
                ps.setLong(6, userId);
                ps.setLong(7, publicKeyId);
                ps.setLong(8, partnerId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<SimpleMessage> list = new ArrayList<>();
                    while (rs.next()) {
                        SimpleMessage sm = getSimpleMessageFromResultSet(rs);
                        list.add(sm);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    @Override
    public List<User> getConversations(long userId, long publicKeyId) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "select us.username, user_id, max(last_message_time) as last_message_time from ( "
                    + " (select distinct sender_id as user_id, max(create_time) as last_message_time from " + SCHEMA_NAME + ".message where receiver_id=? and receiver_public_key_id=? group by sender_id) "
                    + " union all\n"
                    + " (select distinct receiver_id as user_id, max(create_time) as last_message_time from " + SCHEMA_NAME + ".message where sender_id=? and sender_public_key_id=? group by receiver_id) "
                    + " ) as temp left join " + SCHEMA_NAME + ".\"user\" as us on us.id = user_id group by us.username, user_id order by last_message_time desc;";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                ps.setLong(2, publicKeyId);
                ps.setLong(3, userId);
                ps.setLong(4, publicKeyId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<User> list = new ArrayList<>();
                    while (rs.next()) {
                        User us = new User();
                        us.setId(rs.getLong("user_id"));
                        us.setUsername(rs.getString("username"));
                        list.add(us);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    /**
     * It fetches all messages. Costly... No need.
     *
     * @param username
     * @param userId
     * @param publicKeyId
     * @return
     * @throws SQLException
     */
    private List<SimpleMessage> getSimpleMessages(String username, long userId, long publicKeyId) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "select * from ( "
                    + "	(select message.id, sender_id, \"user\".username as sender_username, receiver_id, ? as receiver_username, receiver_read, receiver_body as body, "
                    + "   receiver_public_key_id as public_key_id, receiver_deleted as deleted, message.create_time "
                    + "   from " + SCHEMA_NAME + ".message left join " + SCHEMA_NAME + ".\"user\" on \"user\".id=\"message\".sender_id where receiver_id=? and receiver_public_key_id=?) "
                    + "	union all "
                    + "	(select message.id, sender_id, ? as sender_username, receiver_id, \"user\".username as receiver_username, receiver_read, sender_body as body, "
                    + "   sender_public_key_id as public_key_id, sender_deleted as deleted, message.create_time "
                    + "   from " + SCHEMA_NAME + ".message left join " + SCHEMA_NAME + ".\"user\" on \"user\".id=\"message\".receiver_id where sender_id=? and sender_public_key_id=?) "
                    + " ) temp order by create_time desc";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setLong(2, userId);
                ps.setLong(3, publicKeyId);
                ps.setString(4, username);
                ps.setLong(5, userId);
                ps.setLong(6, publicKeyId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<SimpleMessage> list = new ArrayList<>();
                    while (rs.next()) {
                        SimpleMessage sm = getSimpleMessageFromResultSet(rs);
                        list.add(sm);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    private Message checkAccess(long messageId, long userId) throws SQLException {
        try (Connection conn = conn()) {
            String sql = "select id, sender_id, receiver_id, create_time from __SCHEMANAME__.message where id =? and (sender_id=? or receiver_id=?) limit 1 ".replaceFirst(SCHEMA_NAME_PATTERN, SCHEMA_NAME);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, messageId);
                ps.setLong(2, userId);
                ps.setLong(3, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return getMessageFromResultSet(rs);
                    } else {
                        throw new SQLException("User does not have access to the message.");
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(long messageId, long userId) throws SQLException {

        Message msg = checkAccess(messageId, userId);

        try (Connection conn = conn()) {
            String sql;

            if (userId == msg.getReceiverId()) {
                sql = "update " + SCHEMA_NAME + ".message set receiver_deleted='t' where id=? limit 1";
            } else if (userId == msg.getSenderId()) {
                sql = "update " + SCHEMA_NAME + ".message set sender_deleted='t' where id=? limit 1";
            } else {
                throw new SQLException("User does not have access to the message.");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, messageId);
                ps.setLong(2, userId);
                int row = ps.executeUpdate();
                if (row != 1) {
                    throw new SQLException("Error during delete. Updated row != 1.");
                }
            }
        } catch (Exception e) {
            logger.log(e);
            throw new SQLException(e);
        }
    }

    private Message getMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getLong("id"));
        message.setSenderId(rs.getLong("sender_id"));
        message.setReceiverId(rs.getLong("receiver_id"));
        message.setReceiverRead(rs.getBoolean("receiver_read"));
        message.setSenderBody(rs.getString("sender_body"));
        message.setSenderPublicKeyId(rs.getLong("sender_public_key_id"));
        message.setReceiverBody(rs.getString("receiver_body"));
        message.setReceiverPublicKeyId(rs.getLong("receiver_public_key_id"));
        message.setSenderDeleted(rs.getBoolean("sender_deleted"));
        message.setReceiverDeleted(rs.getBoolean("receiver_deleted"));
        message.setCreateTime(new java.util.Date(rs.getTimestamp("create_time").getTime()));
        return message;
    }

    private SimpleMessage getSimpleMessageFromResultSet(ResultSet rs) throws SQLException {
        SimpleMessage sm = new SimpleMessage();
        sm.setId(rs.getLong("id"));
        sm.setSenderId(rs.getLong("sender_id"));
        sm.setSenderUsername(rs.getString("sender_username"));
        sm.setReceiverId(rs.getLong("receiver_id"));
        sm.setReceiverUsername(rs.getString("receiver_username"));
        sm.setReceiverRead(rs.getBoolean("receiver_read"));
        sm.setBody(rs.getString("body")); // TODO maybe add <wbr /> to save mankind?
        sm.setPublicKeyId(rs.getLong("public_key_id"));
        sm.setDeleted(rs.getBoolean("deleted"));
        sm.setCreateTime(new java.util.Date(rs.getTimestamp("create_time").getTime()));
        return sm;
    }

}
