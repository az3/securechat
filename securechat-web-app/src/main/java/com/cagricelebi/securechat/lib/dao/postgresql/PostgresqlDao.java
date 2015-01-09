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

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author azureel
 */
public class PostgresqlDao {

    protected static final String SCHEMA_NAME_PATTERN = "__SCHEMANAME__";
    protected static final String SCHEMA_NAME = "\"chat\"";

    protected Connection conn() throws NamingException, SQLException {
        return ((DataSource) new InitialContext().lookup("jdbc/chat")).getConnection();
    }
    /*
     -- tested with postgresql 9.4
   
     CREATE ROLE "chat" login PASSWORD '****' NOINHERIT;
     create schema if not exists "chat";
     alter schema "chat" owner to "chat";
     -- jdbc:postgresql://localhost:5432/postgres
     -- psql --host=localhost --port=5432 --username=chat -W postgres
    
     create table if not exists "chat"."user"(
     "id" bigserial PRIMARY KEY,
     "username" character varying(100),
     "password_hash" character varying(2000),
     "public_key_id" bigint,
     "create_time" timestamp with time zone DEFAULT now()
     ) WITH (OIDS = FALSE);
     ALTER TABLE "chat"."user" OWNER TO "chat";

     create table if not exists "chat"."public_key" (
     "id" bigserial PRIMARY KEY,
     "bit_length" int2 default 512,
     "public_key" character varying(2000),
     "create_time" timestamp with time zone DEFAULT now()
     ) WITH (OIDS = FALSE);
     ALTER TABLE "chat"."public_key" OWNER TO "chat";

     CREATE TABLE if not exists "chat"."message" (
     "id" bigserial PRIMARY KEY,
     "sender_id" bigint, 
     "receiver_id" bigint, 
     "receiver_read" bool default false,
     "sender_body" text,
     "sender_public_key_id" bigint,
     "receiver_body" text,
     "receiver_public_key_id" bigint,
     "sender_deleted" bool default false,
     "receiver_deleted" bool default false,
     "create_time" timestamptz default now()
     ) WITH (OIDS = FALSE);
     ALTER TABLE "chat"."message" OWNER TO "chat";
     */
}
