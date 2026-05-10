package com.ticket.booking.voucher;

import com.ticket.booking.common.constants.ApiMessage;
import com.ticket.booking.voucher.dto.VoucherRequest;
import com.ticket.booking.voucher.dto.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ticket.booking.common.constants.ApiMessage.VOUCHER_INVALID;
import static com.ticket.booking.common.constants.ErrorMessage.VOUCHER_EXISTED;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    public VoucherResponse create(VoucherRequest request) {
        voucherRepository.findByCode(request.code())
                .ifPresent(v -> {
                    throw new RuntimeException(VOUCHER_EXISTED);
                });

        VoucherEntity newVoucher = voucherMapper.toVoucher(request);

        VoucherEntity savedVoucher = voucherRepository.save(newVoucher);

        return voucherMapper.toResponse(savedVoucher);
    }

    public List<VoucherResponse> getAllVoucher() {
        List<VoucherEntity> voucherEntityList = voucherRepository.findAll();

        return voucherMapper.toVoucherResponseList(voucherEntityList);
    }

    public VoucherResponse getById(UUID id) {
        VoucherEntity voucher = voucherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(VOUCHER_INVALID));

        return voucherMapper.toResponse(voucher);
    }

    public VoucherEntity validateVoucher(String code) {

        VoucherEntity voucher = voucherRepository
                .findWithLockByCode(code)
                .orElseThrow(() ->
                        new RuntimeException(
                                ApiMessage.VOUCHER_INVALID
                        )
                );

        if (voucher.getExpiredAt()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    ApiMessage.VOUCHER_EXPIRED
            );
        }

        if (voucher.getUsedCount() >=
                voucher.getMaxUsage()) {

            throw new RuntimeException(
                    ApiMessage.VOUCHER_USAGE_EXCEEDED
            );
        }

        return voucher;
    }
}
