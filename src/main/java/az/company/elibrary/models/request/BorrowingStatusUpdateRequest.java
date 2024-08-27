package az.company.elibrary.models.request;

import az.company.elibrary.models.enums.BorrowingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowingStatusUpdateRequest {

    @NotNull
    BorrowingStatus status;

}
