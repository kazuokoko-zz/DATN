package com.poly.datn.dao;

import com.poly.datn.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface SaleDAO extends JpaRepository<Sale, Integer> {

    Sale getOneById(Integer id);

    @Query(nativeQuery = true, value = "select * from sale s where :currtime between s.start_time and s.end_time")
    List<Sale> findSalesAt(@Param("currtime") Timestamp time);

    @Query(nativeQuery = true, value = "select * from sale s where :currtime < s.start_time")
    List<Sale> findSalesAboutStart(@Param("currtime") Timestamp time);

    @Query(nativeQuery = true, value = "select * from sale s where :currtime > s.end_time")
    List<Sale> findSalesEnd(@Param("currtime") Timestamp time);

    @Query(nativeQuery = true, value = "SELECT start_time FROM sale as s " +
            "INNER JOIN (select id ,TIME_TO_SEC(TIMEDIFF( start_time,NOW())) as mini" +
            " FROM sale having mini>0 ORDER BY mini asc LIMIT 1) as e on s.id=e.id")
    Timestamp getNearestTimeStartSale();

    @Query(nativeQuery = true, value = "SELECT end_time FROM sale as s " +
            "INNER JOIN (select id ,TIME_TO_SEC(TIMEDIFF(end_time,NOW())) as mini" +
            " FROM sale having mini > 0 ORDER BY mini asc LIMIT 1) as e on s.id=e.id")
    Timestamp getNearestTimeEndSale();

    @Query(nativeQuery = true, value = "select * from sale as s where s.end_time < NOW() and ( s.status = 'Đang diễn ra' or s.status = 'Đã dừng' )")
    List<Sale> getExpSaleButNotChangeStatus();

    @Query(nativeQuery = true, value = "select * from sale as s where ( NOW() between s.start_time and s.end_time )  and s.status = 'Sắp diễn ra'")
    List<Sale> getStartedSaleButNotChangeStatus();


    List<Sale> getAllByStartTimeEquals(Timestamp startTime);

    List<Sale> getAllByEndTimeEquals(Timestamp endTime);
//    @Query(value = "")
//    Integer countAllByStartTime_DayAndStartTime_MonthAndEndTime_DayAndEndTime_Month(Integer StartTime_Day, Integer StartTime_Month, Integer EndTime_Day, Integer EndTime_Month);
}
