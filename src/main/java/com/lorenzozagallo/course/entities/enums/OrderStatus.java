package com.lorenzozagallo.course.entities.enums;

public enum OrderStatus {

    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

//    codigo do tipo enumerado
    private final int code;

//    construtor do tipo enumerado
    private OrderStatus(int code) {
        this.code = code;
    }

//    para o 'private int code' ficar acessivel para o resto, criaremos um metodo publico para acessar ele
    public int getCode() {
        return code;
    }

//    metodo estatico para converter um valor numerico para o tipo enumerado
//    ele é static porque esse metodo vai funcionar sem precisar instanciar
    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}
