/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.base64password;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author PETI
 */
public class CreateAndValidatePassword {
 
    public static String base64Decode(String string){
        byte[] tmp = new org.apache.commons.codec.binary.Base64().decode(string);
        String newString = new String(tmp, StandardCharsets.UTF_8); 
        return newString; 
    }
    
    public static String base64Encode(String string){
       byte[] tmp = string.getBytes(Charset.forName("UTF-8"));
       String newString = new org.apache.commons.codec.binary.Base64().encodeAsString(tmp);
       return newString;
    }
    
    public static boolean validatePassword(String pw, String password){
        if(pw.equals(password)){
            return true;
        }        
        return false;
    }
    
}
