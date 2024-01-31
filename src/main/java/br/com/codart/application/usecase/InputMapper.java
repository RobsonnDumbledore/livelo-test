package br.com.codart.application.usecase;

public interface InputMapper<IN, OUT> {
    OUT toDomain(IN input);
}
