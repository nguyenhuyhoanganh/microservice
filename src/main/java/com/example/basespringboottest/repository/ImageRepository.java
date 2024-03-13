package com.example.basespringboottest.repository;

import com.example.basespringboottest.dto.response.image.ImageResponse;
import com.example.basespringboottest.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends BaseRepository<Image> {
  @Query("""
                SELECT new com.example.basespringboottest.dto.response.image.ImageResponse
                (i.id,i.url,i.contentType, i.name)
                FROM Image i
        """)
  Page<ImageResponse> findAllImage(Pageable pageable);
  @Query("""
          SELECT new com.example.basespringboottest.dto.response.image.ImageResponse
          (i.id,i.url,i.contentType, i.name)
          FROM Image i
          WHERE (:keyword is null or
                        lower(i.name) LIKE lower(concat('%', :keyword, '%')) or
                        lower(i.url) LIKE lower(concat('%', :keyword, '%')) or
                        lower(i.contentType) LIKE lower(concat('%', :keyword, '%')))
                        ORDER BY i.name
        """)
  Page<ImageResponse> search(Pageable pageable, String keyword);
}
