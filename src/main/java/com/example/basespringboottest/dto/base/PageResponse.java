package com.example.basespringboottest.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Represents a paginated response with a list of content and an amount.
 *
 * @param <T> The type of the entity.
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PageResponse<T> {
    // List of content for the current page
    private List<T> content;
    // This property is the actual number of records has been found
    private int amount;

    /**
     * Creates an instance of PageResponse with the provided data and amount.
     *
     * @param data List of generic type
     * @param amount actual number of records
     * @param <T> generic type
     * @return a PageResponse with generic type
     */
    public static <T> PageResponse<T> of(List<T> data, Integer amount) {
        return new PageResponse<>(data, Objects.isNull(amount) ? 0 : amount.intValue());
    }
}
