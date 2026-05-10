package com.ticket.booking.voucher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.ticket.booking.common.constants.Constant.MIN_USAGE;

import static com.ticket.booking.common.constants.ValidationMessage.DISCOUNT_PERCENT_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.VOUCHER_CODE_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.DISCOUNT_PERCENT_MIN;
import static com.ticket.booking.common.constants.ValidationMessage.DISCOUNT_PERCENT_MAX;
import static com.ticket.booking.common.constants.ValidationMessage.MAX_USAGE_REQUIRED;
import static com.ticket.booking.common.constants.ValidationMessage.MAX_USAGE_MIN;
import static com.ticket.booking.common.constants.ValidationMessage.EXPIRED_AT_REQUIRED;

@Schema(description = "Request body for creating a new discount voucher")
public record VoucherRequest(

        @Schema(description = "Unique promotional code used at checkout", example = "VOUCHER10")
        @NotBlank(message = VOUCHER_CODE_REQUIRED)
        String code,

        @Schema(description = "Percentage of discount to be applied", example = "15.5")
        @NotNull(message = DISCOUNT_PERCENT_REQUIRED)
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = DISCOUNT_PERCENT_MIN
        )
        @DecimalMax(
                value = "100.0",
                inclusive = true,
                message = DISCOUNT_PERCENT_MAX
        )
        BigDecimal discountPercent,

        @Schema(description = "Maximum number of times this voucher can be used", example = "100")
        @NotNull(message = MAX_USAGE_REQUIRED)
        @Min(
                value = 1,
                message = MAX_USAGE_MIN
        )
        Integer maxUsage,

        @Schema(description = "The expiration date and time of the voucher", example = "2024-12-31T23:59:59")
        @NotNull(message = EXPIRED_AT_REQUIRED)
        LocalDateTime expiredAt
) {
}
