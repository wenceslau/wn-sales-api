package com.wnsales.service;

import com.wnsales.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<Account> findById(Long accountId);

    Page<Account> findAll(Pageable pageable);

    void delete(Long accountId);

    Account save(Account account);

    Account edit(Long accountId, Account account);

    Account partialEdit(Long accountId, Account account);
}
