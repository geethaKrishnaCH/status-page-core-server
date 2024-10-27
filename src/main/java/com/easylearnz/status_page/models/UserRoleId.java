package com.easylearnz.status_page.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UserRoleId {
    private Integer userId;
    private Integer roleId;

}