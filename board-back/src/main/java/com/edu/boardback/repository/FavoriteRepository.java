package com.edu.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.edu.boardback.entity.FavoriteEntity;
import com.edu.boardback.entity.primaryKey.FavoritePk;
import com.edu.boardback.repository.resultSet.GetFavoriteListResultSet;

import jakarta.transaction.Transactional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>{
    
    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(
        value=
        "SELECT " +
        "u.email AS email, " +
        "u.nickname AS nickname, " +
        "u.profile_image as profileImage " +
        "FROM favorite AS f " +
        "INNER JOIN user AS u " +
        "ON f.user_email = u.email " +
        "WHERE f.board_number= ?1 ",
        nativeQuery=true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);
}
