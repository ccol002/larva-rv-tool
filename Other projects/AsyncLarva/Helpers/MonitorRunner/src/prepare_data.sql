DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    PROCEDURE `new1000`.`prepare_data`(IN stime bigint(20), IN etime bigint(20))
    /*LANGUAGE SQL
    | [NOT] DETERMINISTIC
    | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
    | SQL SECURITY { DEFINER | INVOKER }
    | COMMENT 'string'*/
    BEGIN


create table bbbb (
              timestamp		      bigint(20),
	      transactionEntryId      varchar(255), 
	      allocationId            varchar(255), 
              balanceAdjustmentAmount decimal(12,2),
	      balanceAfterAmount      decimal(12,2),
	      balanceType             varchar(255)
		  );

/*vcc (leaving out entry concerning system cards)*/

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, d.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "ACTUAL"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join vccbalances d 
       on c.balanceId = d.vccBalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, d.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "AVAILABLE"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join vccbalances d 
       on c.balanceId = d.base24BalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, d.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "FROZEN_ACTUAL"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join vccbalances d 
       on c.balanceId = d.frozenVCCBalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, d.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "FROZEN_AVAILABLE"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join vccbalances d 
       on c.balanceId = d.frozenBASE24BalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;




/*bankcard*/


insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, e.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "ACTUAL"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join bankcardbalances d 
       on c.balanceId = d.actualBalanceId
     join bankcards e 
       on d.bankCardInstrumentId = e.id
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, e.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "AVAILABLE"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join bankcardbalances d 
       on c.balanceId = d.availableBalanceId      
     join bankcards e 
       on d.bankCardInstrumentId = e.id
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

                
/*named balance*/


insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, b.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "ACTUAL"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join namedbalances d 
       on c.balanceId = d.actualBalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;

insert into bbbb 
 select a.creationTimestamp, a.transactionEntryId, b.allocationId, c.balanceAfterAmount - c.balanceBeforeAmount as 'balanceAdjustmentAmount', c.balanceAfterAmount, "AVAILABLE"
   from tl_transactionentry a 
     join users_trans_ie b 
       on a.transactionEntryId = b.transactionEntryId
     join users_trans_be c 
       on b.instrumentEntryId  = c.instrumentEntryId
     join namedbalances d 
       on c.balanceId = d.availableBalanceId
where not c.userId is NULL
and a.creationTimestamp >= stime and a.creationTimestamp < etime;




    END$$

DELIMITER ;