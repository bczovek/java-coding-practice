package com.happyflights.search.converter;

/**
 * A generic interface for converting objects of type {@code T} to objects of type {@code Z}.
 *
 * @param <T> The type of the input object to be converted.
 * @param <Z> The type of the output object after conversion.
 */
public interface Converter<T, Z> {
    /**
     * Converts an object of type {@code T} to an object of type {@code Z}.
     *
     * @param input The input object of type {@code T} to be converted.
     * @return The output object of type {@code Z} after the conversion.
     */
    Z convert(T input);
}
