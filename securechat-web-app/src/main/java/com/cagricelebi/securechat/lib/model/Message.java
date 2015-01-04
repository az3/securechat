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
package com.cagricelebi.securechat.lib.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author azureel
 */
public class Message implements Serializable {

    private long id;
    private long senderId;
    private long receiverId;
    private boolean receiverRead = false;
    private String senderBody;
    private long senderPublicKeyId;
    private String receiverBody;
    private long receiverPublicKeyId;
    private boolean senderDeleted = false;
    private boolean receiverDeleted = false;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isReceiverRead() {
        return receiverRead;
    }

    public void setReceiverRead(boolean receiverRead) {
        this.receiverRead = receiverRead;
    }

    public String getSenderBody() {
        return senderBody;
    }

    public void setSenderBody(String senderBody) {
        this.senderBody = senderBody;
    }

    public long getSenderPublicKeyId() {
        return senderPublicKeyId;
    }

    public void setSenderPublicKeyId(long senderPublicKeyId) {
        this.senderPublicKeyId = senderPublicKeyId;
    }

    public String getReceiverBody() {
        return receiverBody;
    }

    public void setReceiverBody(String receiverBody) {
        this.receiverBody = receiverBody;
    }

    public long getReceiverPublicKeyId() {
        return receiverPublicKeyId;
    }

    public void setReceiverPublicKeyId(long receiverPublicKeyId) {
        this.receiverPublicKeyId = receiverPublicKeyId;
    }

    public boolean isSenderDeleted() {
        return senderDeleted;
    }

    public void setSenderDeleted(boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public boolean isReceiverDeleted() {
        return receiverDeleted;
    }

    public void setReceiverDeleted(boolean receiverDeleted) {
        this.receiverDeleted = receiverDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return toJson();
    }

    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public static Message fromJson(String source) {
        return new Gson().fromJson(source, Message.class);
    }
}
