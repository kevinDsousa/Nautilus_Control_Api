package com.nautilus_control_api.domain.model.dto.request;

import com.nautilus_control_api.domain.utils.constants.FrasesFeedback;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FirebaseRequestUID {
    @NotNull(message = FrasesFeedback.FIREBASE_UID_NULL)
    String uid;
}
