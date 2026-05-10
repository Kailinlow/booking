package com.ticket.booking.voucher;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository extends JpaRepository<VoucherEntity, UUID> {

    Optional<VoucherEntity> findByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<VoucherEntity> findWithLockByCode(String code);

}
