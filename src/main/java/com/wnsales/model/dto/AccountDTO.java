package com.wnsales.model.dto;

import com.wnsales.model.Account;
import com.wnsales.util.validations.IbanConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;

    @NotBlank
    private String accountName;

    @IbanConstraint
    private String iban;

    @NotNull
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((AccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private AccountDTO(Account model) {
        BeanUtils.copyProperties(model, this);
        this.userId = model.getId();
    }

    public static AccountDTO of(Account source){
        if (source == null)
            return null;
        return new AccountDTO(source);
    }

    public static Set<AccountDTO> of(Set<Account> source){
        return source.stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public static Page<AccountDTO> of(Page<Account> source){
        return source.map(AccountDTO::new);
    }

}
