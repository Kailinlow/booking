package com.ticket.booking.voucher.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response object containing voucher details")
public record VoucherResponse(

        @Schema(description = "Unique identifier of the voucher", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ab")
        UUID id,

        @Schema(description = "Unique promotional code used at checkout", example = "VOUCHER10")
        String code,

        @Schema(description = "Percentage of discount to be applied", example = "15.5")
        BigDecimal discountPercent,

        @Schema(description = "Maximum number of times this voucher can be used", example = "100")
        Integer maxUsage,

        @Schema(description = "Number of times this voucher has already been redeemed", example = "24")
        Integer usedCount,

        @Schema(description = "The expiration date and time of the voucher", example = "2024-12-31T23:59:59")
        LocalDateTime expiredAt
) {
}
