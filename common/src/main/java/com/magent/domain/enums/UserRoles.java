package com.magent.domain.enums;

import javassist.NotFoundException;

/**
 * Created  on 06.06.2016.
 */

public enum UserRoles {
    ADMIN(1L),
    BACK_OFFICE_EMPLOYEE(2L),
    REMOTE_SELLER_STAFFER(3L),
    SALES_AGENT_FREELANCER(4L),
    SALES_AGENT_FREELANCER_LEAD_GEN(5L);

    private Long roleId;

    UserRoles(Long l) {
        this.roleId = l;
    }

    public Long getRoleId() {
        return roleId;
    }

    public static UserRoles getById(Long roleId) throws NotFoundException {
        for (UserRoles roles : UserRoles.values()) {
            if (roleId.equals(roles.getRoleId())) {
                return roles;
            }
        }
        throw new NotFoundException("role not present");
    }

    public static UserRoles getByString(String role) throws NotFoundException {
        for (UserRoles presentRole : UserRoles.values()) {
            if (role.equalsIgnoreCase(presentRole.toString())) return presentRole;
        }
        throw new NotFoundException("user with current role not found");
    }

}
