package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Response of the operation has error."
)
public class ErrorResponseDto {

    @Schema(
            description = "apiPath."
    )
    private String apiPath;

    @Schema(
            description = "Response error code."
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message description."
    )
    private String errorMsg;

    @Schema(
            description = "Time of error."
    )
    private LocalDateTime errorTime;
}
