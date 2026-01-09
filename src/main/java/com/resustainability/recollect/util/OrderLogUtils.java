package com.resustainability.recollect.util;

import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.tag.Role;

public final class OrderLogUtils {

    private OrderLogUtils() {}

   
    public static String resolveDoneBy(IUserContext user) {

        
        if (user == null) {
            return "Server";
        }

        String roleAbbreviation = Role.fromUserContext(user);

        
        if (roleAbbreviation == null) {
            return "Server";
        }

        Role role = Role.fromAbbreviation(roleAbbreviation);

        
        if (role == Role.ADMIN) {
            return "Server (Admin)";
        }

        
        String name = safe(user.getFullName());
        String phone = safe(user.getPhoneNumber());
        String roleLabel = role.getAbbreviation(); 

        if (!name.isBlank() && !phone.isBlank()) {
            return name + "/" + phone + " - (" + roleLabel + ")";
        }

        if (!name.isBlank()) {
            return name + " - (" + roleLabel + ")";
        }

        if (!phone.isBlank()) {
            return phone + " - (" + roleLabel + ")";
        }

       
        return "Unknown - (" + roleLabel + ")";
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
