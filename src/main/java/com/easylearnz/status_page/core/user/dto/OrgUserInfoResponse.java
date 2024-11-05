package com.easylearnz.status_page.core.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgUserInfoResponse {
    private int id;
    private String name;
    private String email;
    private String userId;
    private String status;
    private List<String> roles;
}
