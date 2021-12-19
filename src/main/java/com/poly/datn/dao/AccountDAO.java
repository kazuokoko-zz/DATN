package com.poly.datn.dao;

import com.poly.datn.entity.Account;
import com.poly.datn.jwt.dto.InfoAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AccountDAO extends JpaRepository<Account, Integer> {
//START OF MA CODE

    @Query("select a from Account   a where  a.username =:username")
    Account findAccountByUsername(String username);

    Optional<Account> findByUsername(String username);

    //END OF MA CODE
//   @Query("select a from Account a where  a.passwordresetKey =:token")
//    Account findByToken(String token);
    //dong cua sql o duoi
//    passwordresetkey

    Account findOneById(Integer Id);
    @Query(value = "select a from Account a where a.email=:email")
    Account findOneByEmail(@Param("email") String email);

    @Query(value = "select a.email, a.fullname from Account a")
    List<Account> findAllEmail();
//    @Query(nativeQuery = true, value = "update account a set a.password=:password where a.email=:email")
//    void changePass(@Param("password") String password, @Param("email") String email);
}
