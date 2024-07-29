package com.shotty.shotty.Domain;

public enum UserRole {
    ADVERTISER((short)0),
    INFLUENCER((short)1);

    private final short roleNum;

    UserRole(short roleNum) {
        this.roleNum = roleNum;
    }

    public short getRoleNum() {
        return roleNum;
    }

    public static UserRole getUserRoleByRoleNum(short roleNum) {
        for (UserRole userRole : values()) {
            if (userRole.getRoleNum() == roleNum) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid roleNum: " + roleNum);
    }
}
