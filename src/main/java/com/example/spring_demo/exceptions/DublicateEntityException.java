package com.example.spring_demo.exceptions;

public class DublicateEntityException extends RuntimeException {
    public DublicateEntityException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }
}
