package com.ticket.booking.voucher;

import com.ticket.booking.common.entities.ApiResponse;
import com.ticket.booking.voucher.dto.VoucherRequest;
import com.ticket.booking.voucher.dto.VoucherResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/vouchers")
@Tag(name = "Admin Voucher Management", description = "Administrative APIs for managing voucher.")
public class AdminVoucherController {

    private final VoucherService voucherService;

    @Operation(
            summary = "Create a new voucher",
            description = "Create a new voucher in the system"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<VoucherResponse>> create(
            @Valid @RequestBody VoucherRequest request
    ) {

        VoucherResponse voucher =
                voucherService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(voucher));
    }
}
