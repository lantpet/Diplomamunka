/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.sessionmanager;

import javax.servlet.http.HttpSession;

public class SessionManager {
   
    public static boolean redirectToHomePage(HttpSession session){
		if (session.getAttribute("userEmail") == null || session.getAttribute("userEmail").equals("")){
			return true;
		}
		return false;
    }
}
