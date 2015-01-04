/* 
 * Author     : azureel
 * Licence    : The MIT License (MIT)
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

function Participant(id, username, public_key_id, bit_length, public_key) {
    this.id = id;
    this.username = username;
    this.public_key_id = public_key_id;
    this.bit_length = bit_length;
    this.public_key = public_key;
}

/*function Message(id, sender, receiver, open_message, sender_encrypted, receiver_encrypted) {
 this.id = id;
 this.sender = sender;
 this.receiver = receiver;
 this.open_message = open_message;
 this.sender_encrypted = sender_encrypted;
 this.receiver_encrypted = receiver_encrypted;
 }*/

///////////////// BEGIN SENDING functions used in messages and conversation pages /////////////////

function ajaxGetInfoAndSendMessage(receiver_name, sender_public_key, sender_decrypted_message, postOperations) {
    var url = "ajax?op=get_public_key";
    var receiver = new Participant(0, '', 0, 0, '');
    $.post(url,
            {
                receiverName: receiver_name
            },
    function (data, status) {
        //alert("Data: " + data + "\nStatus: " + status);
        var jsonObj = jQuery.parseJSON(data);
        receiver = new Participant(jsonObj.id, receiver_name, jsonObj.publicKey.id, 0, jsonObj.publicKey.publicKey);
        sendTo(receiver, sender_public_key, sender_decrypted_message, postOperations);
    });
    return receiver;
}

function sendTo(receiver, sender_public_key, sender_decrypted_message, postOperations) {
    var sender_decrypted_message_b64 = base64.encode(sender_decrypted_message);

    var receiver_encrypted_message = encryptWithPublicKey('receiver', sender_decrypted_message_b64, receiver.public_key);
    var sender_encrypted_message = encryptWithPublicKey('sender', sender_decrypted_message_b64, sender_public_key);

    ajaxSendMessage(0, receiver.id, sender_encrypted_message, 0, receiver_encrypted_message, receiver.public_key_id, postOperations);
}

function encryptWithPublicKey(participant, decrypted_message, publicKey) {
    if (publicKey === '') {
        alert("please insert " + participant + " public key to encrypt outgoing message.")
        return false;
    }
    var sender_encryption_result = cryptico.encrypt(decrypted_message, publicKey);
    var sender_encrypted_message = sender_encryption_result.cipher;
    return sender_encrypted_message;
}

function ajaxSendMessage(sender_id, receiver_id, sender_encrypted_message,
        sender_public_key_id, receiver_encrypted_message, receiver_public_key_id,
        postOperations) {
    var url = "ajax?op=send_message"
    $.post(url,
            {
                senderId: sender_id,
                receiverId: receiver_id,
                senderBody: sender_encrypted_message,
                senderPublicKeyId: sender_public_key_id,
                receiverBody: receiver_encrypted_message,
                receiverPublicKeyId: receiver_public_key_id,
            },
            function (data, status) {
                // alert("Data: " + data + "\nStatus: " + status);
                postOperations();
            });
}
///////////////// END SENDING functions used in messages and conversation pages /////////////////

///////////////// BEGIN PRIVATE-PUBLIC key function used in settings page /////////////////
function generatePublicKey(username, sender_passphrase, sender_rsa_bit_length, workPublicKey) {
    // var sender_passphrase = $('#sender_passphrase').val();
    if (sender_passphrase === '') {
        alert("please insert your passphrase to re-create your public key.");
        return false;
    }
    sender_passphrase = username + sender_passphrase;
    // var sender_rsa_bit_length = $('#sender_rsa_bit_length').val();
    var sender_rsa_key = cryptico.generateRSAKey(sender_passphrase, sender_rsa_bit_length);
    var sender_public_key = cryptico.publicKeyString(sender_rsa_key);
    // $('#sender_public_key').val(sender_public_key);
    workPublicKey(sender_public_key);
}
///////////////// END PRIVATE-PUBLIC key function used in settings page /////////////////

///////////////// BEGIN DECRYPTING function used in conversation pages /////////////////
function decryptConversation(username) {
    //alert("test_decryption clicked.");
    var sender_passphrase = $('#sender_passphrase').val();
    if (sender_passphrase === '') {
        alert("Please insert your passphrase to decrypt.");
        return false;
    }
    sender_passphrase = username + sender_passphrase;
    var bits = $('#sender_rsa_bit_length').val();
    //alert("sender_passphrase:" + sender_passphrase + " , bits:" + bits);
    var sender_rsa_key = cryptico.generateRSAKey(sender_passphrase, bits);
    //alert("sender_rsa_key" + sender_rsa_key);

    var encryptedFields = $("div[id^='enc_body_']");
    encryptedFields.each(function () {
        var internalId = $(this).attr('id');
        var decId = internalId.replace("enc", "dec");
        var sender_encrypted_message = $(this).text();
        //alert("sender_encrypted_message:" + sender_encrypted_message);
        var sender_decryption_result = cryptico.decrypt(sender_encrypted_message, sender_rsa_key);
        var sender_decrypted_message_b64 = sender_decryption_result.plaintext;
        var sender_decrypted_message_2 = base64.decode(sender_decrypted_message_b64);
        //alert(sender_decrypted_message_2); // TODO handle key errors...
        sender_decrypted_message_2 = escapeHtml(sender_decrypted_message_2);
        if (sender_decrypted_message_2 !== '') {
            $('#' + decId).text(sender_decrypted_message_2);
            $('#' + decId).attr('style', 'display: block;');
            $(this).attr('style', 'display: none;');
        } else {
            $('#' + decId).text("decryption error.");
            $('#' + decId).attr('style', 'display: block;');
        }
    });

    return false;
}
///////////////// END DECRYPTING function used in conversation pages /////////////////

function escapeHtml(unsafe) {
    return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
}



// this function is not used.
function ajaxGetReceiverInfo(receiver_name, postOps) {
    var url = "ajax?op=get_public_key";
    var receiver = new Participant(0, '', 0, '');
    $.post(url,
            {
                receiverName: receiver_name
            },
    function (data, status) {
        //alert("Data: " + data + "\nStatus: " + status);
        var jsonObj = jQuery.parseJSON(data);
        receiver = new Participant(jsonObj.id, receiver_name, jsonObj.publicKey.id, jsonObj.publicKey.publicKey);
        postOps(receiver);
    });
}
