drop table aaaa;



create table aaaa (
              timestamp		      bigint(20),
	      transactionEntryId      varchar(255), 
	      allocationId            varchar(255), 
              balanceAdjustmentAmount decimal(12,2),
	      balanceAfterAmount      decimal(12,2),
	      balanceType             varchar(255)
		  );


call prepare_data(@start,@end);