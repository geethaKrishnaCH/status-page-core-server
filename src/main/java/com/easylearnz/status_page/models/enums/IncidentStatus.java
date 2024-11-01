package com.easylearnz.status_page.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IncidentStatus {
    INVESTIGATING,
    IDENTIFIED,
    MONITORING,
    RESOLVED;

    public static IncidentStatus findStatusCode(String status) {
        return Arrays.stream(IncidentStatus.values())
                .filter(s -> s.name().equals(status))
                .findFirst().get();
    }
}
