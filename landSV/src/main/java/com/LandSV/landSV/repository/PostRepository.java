package com.LandSV.landSV.repository;

import com.LandSV.landSV.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("select p from Post p where p.location like :location and p.price < :maxValue")
    List<Post> findByLocationAndMaxValue(@Param("location") String location, @Param("maxValue") Double maxValue);

    List<Post> findByUserIdOrderByCreatedAtDesc (UUID id);
}
