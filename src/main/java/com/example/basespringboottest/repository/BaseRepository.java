package com.example.basespringboottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A base repository interface extending JpaRepository.
 * @param <T> The type of the entity.
 */
public interface BaseRepository<T> extends JpaRepository<T,String> {
}
