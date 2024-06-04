package com.happyflights.search.converter;

public interface Converter<T, Z> {

    Z convert(T input);

}
