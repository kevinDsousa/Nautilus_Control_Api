package com.nautilus_control_api.domain.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseJWT {
    String token;
}
