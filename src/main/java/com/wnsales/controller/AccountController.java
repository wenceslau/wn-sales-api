package com.wnsales.controller;

import com.wnsales.model.Account;
import com.wnsales.model.dto.AccountDTO;
import com.wnsales.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/account")
@Tag(name = "Account API - List/Create/Update")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/template")
    @Operation(summary = "Template JSON for resource create")
    public AccountDTO template(){
        return new AccountDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find User resource by ID")
    public AccountDTO findById(@PathVariable Long id) {
        return AccountDTO.of(accountService.findById(id).get());
    }

    @GetMapping()
    @Operation(summary = "User list pageable")
    public Page<AccountDTO> list(@PageableDefault Pageable pageable) {
        return AccountDTO.of(accountService.findAll(pageable));
    }

    @PostMapping
    @Operation(summary = "Save an User resource")
    public ResponseEntity<AccountDTO> save(@RequestBody @Validated AccountDTO dto) {
        Account account = accountService.save(Account.of(dto));

        return new ResponseEntity<>(AccountDTO.of(account), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit an User resource")
    public ResponseEntity<AccountDTO> edit(@PathVariable Long id, @RequestBody @Validated AccountDTO dto) {
        Account account = accountService.edit(id, Account.of(dto));

        return new ResponseEntity<>(AccountDTO.of(account), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Edit partially Account resource")
    public ResponseEntity<AccountDTO> partialEdit(@PathVariable Long id, @RequestBody AccountDTO dto) {
        Account account = accountService.partialEdit(id, Account.of(dto));

        return new ResponseEntity<>(AccountDTO.of(account), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Account resource")
    public void deleteByCode(@PathVariable Long id) {
        accountService.delete(id);
    }
}
