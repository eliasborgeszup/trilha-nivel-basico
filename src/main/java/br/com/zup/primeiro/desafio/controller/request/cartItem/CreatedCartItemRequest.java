package br.com.zup.primeiro.desafio.controller.request.cartItem;

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
public class CreatedCartItemRequest {
    @Min(value = 1, message = "{validation.size}")
    private final Integer quantity;

    @NotBlank(message = "{validation.blank}")
    private final String customerId;
}
