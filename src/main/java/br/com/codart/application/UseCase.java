package br.com.codart.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN input);

}
