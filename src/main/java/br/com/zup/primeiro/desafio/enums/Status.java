package br.com.zup.primeiro.desafio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    OPEN("open"),
    CLOSED("closed");

    private final String status;
}
