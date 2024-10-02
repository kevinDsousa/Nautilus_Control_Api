package com.nautilus_control_api.domain.model.dto.request;

import com.nautilus_control_api.domain.utils.constants.FrasesFeedback;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestJWT {
    @NotNull(message = FrasesFeedback.TOKEN_NULL)
    String token;
}
