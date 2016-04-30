/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.model;

/**
 *
 * @author PETI
 */

public enum Role{
   
    ADMIN("admin"),
    USER("user");
    
    private final String role;

    private Role(String role) {
        this.role = role;
    }
    
    public String getRole(){
        return role;
    }    
}
