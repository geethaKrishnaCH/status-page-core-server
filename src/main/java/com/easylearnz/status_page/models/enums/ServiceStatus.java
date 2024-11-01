package com.easylearnz.status_page.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceStatus {
    OPERATIONAL,
    MAJOR_OUTAGE,
    DEGRADED,
    PARTIAL_OUTAGE,
    MAINTENANCE;

    public static ServiceStatus findStatusCode(String status) {
        return Arrays.stream(ServiceStatus.values())
                .filter(s -> s.name().equals(status))
                .findFirst().get();
    }
}
