package com.ticket.booking.voucher;

import com.ticket.booking.voucher.dto.VoucherRequest;
import com.ticket.booking.voucher.dto.VoucherResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherEntity toVoucher(VoucherRequest request);

    VoucherResponse toResponse(VoucherEntity voucher);

    List<VoucherResponse> toVoucherResponseList(List<VoucherEntity> voucherEntityList);
}
