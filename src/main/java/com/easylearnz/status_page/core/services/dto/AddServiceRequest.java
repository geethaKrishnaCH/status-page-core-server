package com.easylearnz.status_page.core.services.dto;

import lombok.Data;

@Data
public class AddServiceRequest {
    private String name;
    private String description;
    private String category;
    private String url;
    private String currentStatus;
}
