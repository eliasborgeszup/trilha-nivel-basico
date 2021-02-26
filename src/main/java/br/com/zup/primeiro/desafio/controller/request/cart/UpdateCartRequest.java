package br.com.zup.primeiro.desafio.controller.request.cart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateCartRequest {
    @NotBlank(message = "{validation.blank}")
    private String idComics;

    @Min(value = 1, message = "{validation.size}")
    private Integer quantity;
}
