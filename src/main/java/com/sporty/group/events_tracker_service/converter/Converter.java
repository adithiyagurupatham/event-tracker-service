package com.sporty.group.events_tracker_service.converter;

/**
 *
 * Generic converter to convert from one object to another using converter design pattern
 *
 * @param <I> input
 * @param <O> output
 * @param <E> existing
 *
 */
public interface Converter<I,O,E> {
    O convert(I input,E existing);
}