package com.wnsales.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkDTO {
    private String link;

    private String status;

    public LinkDTO(String link) {
        this.link = link;
    }
}
