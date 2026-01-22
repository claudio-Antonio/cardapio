package com.example.cardapio.domain.enums;

public enum StatusRequest {
    PENDENTE("pendente"),
    EM_PREPARO("em_preparo"),
    PRONTO("pronto"),
    ENTREGUE("entregue"),
    CANCELADO("cancelado");

    private String value;

    StatusRequest(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
