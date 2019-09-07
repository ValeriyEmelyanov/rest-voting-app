package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.web.response.VoteRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    @Query("select new com.example.restvotingapp.web.response.VoteRest(v.id, u.id, u.name, m.id, r.id, r.name, v.date) from Vote v join v.user u join v.menu m join v.restaraunt r where v.date=:date")
    List<VoteRest> findAllByDateNative(@Param("date") LocalDate date);
}
