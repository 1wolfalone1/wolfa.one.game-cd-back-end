//package com.wolfalone.gamecdbackend.repository;
//
//import com.wolfalone.gamecdbackend.config.constant.PagingConstant;
//import com.wolfalone.gamecdbackend.entity.Game;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class GameRepoTest {
//
//    @Autowired
//    private GameRepo gameRepo;
//    @Test
//    @Transactional
//    void testFilter() {
//        PageRequest pageRequest = PageRequest.of(1 - 1,
//                PagingConstant.PAGE_NUMBER);
//        Page<Game> pageAble = gameRepo.filterByNameAndPriceRange("a", 0, 10, 1, 2, pageRequest);
//        List<Game> games = pageAble.getContent();
//        long page = pageAble.getTotalPages();
//        long totaElement = pageAble.getTotalElements();
//        System.out.println(games + " - " + page + " -" + totaElement);
//    }
//}