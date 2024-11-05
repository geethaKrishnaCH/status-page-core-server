package com.easylearnz.status_page.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultRole {
    ADMINISTRATOR("ADMINISTRATOR", "rol_yiL30qDE9a0U8JK2"),
    SERVICE_MANAGER("SERVICE_MANAGER", "rol_wZGUGC70dxPErPy9"),
    VIEWER("VIEWER", "rol_p4GRjOq2CvxH12X8");

    private final String name;
    private final String roleId;
}
