package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Response of the operation is successful."
)
public class ResponseDto {

    @Schema(
            description = "Http status code."
    )
    private String statusCode;

    @Schema(
            description = "Response message."
    )
    private String statusMsg;
}
