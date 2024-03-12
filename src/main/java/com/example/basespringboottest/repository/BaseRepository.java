package com.example.basespringboottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * A base repository interface extending JpaRepository.
 * @param <T> The type of the entity.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T,String> {
}
