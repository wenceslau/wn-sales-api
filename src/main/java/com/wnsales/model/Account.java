package com.wnsales.model;

import com.wnsales.model.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "IBAN")
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Account) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private Account(AccountDTO dto) {
        BeanUtils.copyProperties(dto, this);
        if (dto.getUserId() != null){
            this.user = new User();
            this.user.setId(dto.getUserId());
        }
    }

    public static Account of(AccountDTO source){
        if (source == null)
            return null;
        return new Account(source);
    }

    public static Set<Account> of(Set<AccountDTO> source){
        return source.stream().map(Account::new).collect(Collectors.toSet());
    }
}
