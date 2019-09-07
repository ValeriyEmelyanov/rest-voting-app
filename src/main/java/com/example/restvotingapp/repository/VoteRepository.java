package com.example.restvotingapp.repository;

import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    @Query(value = "select " +
            "new com.example.restvotingapp.dto.VotePlainDto(v.id, u.id, u.name, m.id, r.id, r.name, v.date) " +
            "from Vote v join v.user u join v.menu m join v.restaraunt r where v.date=:date",
            countQuery = "select count(*) from Vote v where v.date=:date")
    Page<VotePlainDto> findAllByDateQL(@Param("date") LocalDate date, Pageable pageableRequest);

    int countAllByDate(LocalDate date);

    int countAllByDateAndRestaraunt(LocalDate date, Restaraunt restaraunt);
    
    @Query(value = "select v.id from Vote v where v.date=:date and v.user=:user")
    Long findByDateAndUserQL(@Param("date") LocalDate date, @Param("user") User user);

    void deleteById(Long id);
}
