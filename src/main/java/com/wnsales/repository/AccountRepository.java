package com.wnsales.repository;

import com.wnsales.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsById(Long id);

}
