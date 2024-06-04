package com.happyflights;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExampleTest {

    @Test
    void should_succeed() {
        String actual = "the quick brown fox jumped over the lazy dog";
        assertThat(actual).contains("fox", "dog");
    }
}
