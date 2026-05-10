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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vouchers")
@Tag(name = "Voucher Management", description = "Endpoint for retrieving and managing voucher information.")
public class VoucherController {

    private final VoucherService voucherService;

    @Operation(
            summary = "Retrieving all the voucher",
            description = "Fetch a list of all concerts in the system"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<VoucherResponse>>> getAll() {
        List<VoucherResponse> responseList = voucherService.getAllVoucher();

        return ResponseEntity.ok()
                .body(ApiResponse.success(responseList));
    }
}
