package br.com.codart.application.usecase;

public interface OutputMapper<IN, OUT> {
    OUT toOutput(IN domain);
}
