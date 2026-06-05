package br.org.kinflasy.apis.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FgaCheckRequest {

    private String objectType;
    private String objectId;
    private String relation;
    private String userType;
    private String userId;
    private String userGender;
    private Integer userAge;

    @Data
    @Builder
    public static class Simple {
        private String objectType;
        private String objectId;
        private String relation;
    }

}
