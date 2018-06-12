package com.logon.pet.petlogoncore.Repository;

import com.logon.pet.petlogoncore.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    @Query("select a from account a where a.accountId = :accountId")
    Account findByUserId(@Param("accountId") String accountId);

}
