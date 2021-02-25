package br.com.zup.primeiro.desafio.controller.request.cartItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@EqualsAndHashCode
@ToString
public class UpdateCartItemRequest {
    @Min(value = 1, message = "{validation.size}")
    private final Integer quantity;

    @JsonCreator
    public UpdateCartItemRequest(@JsonProperty ("quantity") Integer quantity) {
        this.quantity = quantity;
    }
}
