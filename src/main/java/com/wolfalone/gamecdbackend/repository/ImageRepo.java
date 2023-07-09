package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer> {

    List<Image> findByGameId(int gameId);

    @Query(nativeQuery = true, value = "delete from tbl_image where id in (:ids)")
    void deleteAllInListImageId(@Param("ids") List<Integer> ids);
}
