package az.company.elibrary.models.request;

import az.company.elibrary.models.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationStatusUpdateRequest {

    @NotNull
    private ReservationStatus status;

}