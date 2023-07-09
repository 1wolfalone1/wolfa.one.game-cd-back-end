package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {

//    @Query(value = "select g.id, g.description, g.\"name\", g.price, g.quantity from tbl_game g " +
//            "join tbl_category_game cg on g.id = cg.game_id WHERE g.name LIKE %?1% AND g.price " +
//            "BETWEEN ?2 AND ?3 and (1 = ?4 or cg.category_id = ?5)",
//            countQuery = "select count(g.id) from tbl_game g join tbl_category_game cg on g.id = " +
//                    "cg" +
//                    ".game_id WHERE g.name LIKE '%a%' AND g.price BETWEEN ?2 AND ?3 and (1 = ?4" +
//                    " " +
//                    "or cg.category_id = ?5)",
//            nativeQuery = true)
//    Page<Game> filterByNameAndPriceRange(String name, int fromPrice, int toPrice,
//                                         int hasCategoryId, int categoryId, Pageable pageable);
    @Query(value = "select g.id, g.description, g.\"name\", g.price, g.quantity, g.is_deleted " +
            "from " +
            "tbl_game " +
            "g " +
            "join tbl_category_game cg on g.id = cg.game_id WHERE g.name LIKE %?1% AND g.price " +
            "BETWEEN ?2 AND ?3 and (0 = ?4 or cg.category_id = ?5) and g.is_deleted = false group by g.id, g.description," +
            " " +
            "g.name, g.price, g.quantity , g.is_deleted",
            countQuery = "select count(g.id) from tbl_game g join tbl_category_game cg on g.id = " +
                    "cg" +
                    ".game_id WHERE g.name LIKE %?1% AND g.price BETWEEN ?2 AND ?3 and (0 = ?4" +
                    " " +
                    " or cg.category_id = ?5) and g.is_deleted = false group by g.id, g" +
                    ".description, g.name," +
                    " g.price, " +
                    "g" +
                    ".quantity , g.is_deleted",
            nativeQuery = true)
    Page<Game> filterByNameAndPriceRange(String name, int fromPrice, int toPrice,
                                         int hasCategoryId, int categoryId, Pageable pageable);

    Page<Game> findAllByIsDeletedFalse(Pageable pageable);
}
