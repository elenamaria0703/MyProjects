use HavEATreat
go


--deadlock transaction 1 (victim)
--tranzactia curenta blocheaza resursa din tabelul Bucatari si va incerca sa acceseze resursa de la Barmani
--in fisierul Lab4_SGBD1.sql tranzactia blocheaza resursa de la Barmani si incearca sa acceseze resursa de la Bucatari
--de aici rezulta deadlockul
select * from Bucatari
select * from Barmani
set transaction isolation level serializable
begin tran 
update Bucatari set Specialitate='Desert' where Nume='Larisa'
waitfor delay '00:00:05'
update Barmani set Varsta=20 where Nume='Tudor'
commit tran


-- DEARTY READS
--in fisierul Lab4_SGBD1.sql puteti veadea tranzactia care afiseaza datele din Barmani
select * from Barmani
begin tran
update Barmani set Varsta=21 where Nume='Alex'
waitfor delay '00:00:10'
rollback tran
select * from Barmani

--NON-REPEATABLE READS
--in fisierul Lab4_SGBD1.sql puteti veadea tranzactia carea fiseaza datele din Bucatari
select * from Bucatari
begin tran
waitfor delay '00:00:10'
update Bucatari set Specialitate='Desert' where Nume='Barbu'
commit tran

select * from Bucatari

--PHANTOM READS
--in fisierul Lab4_SGBD1.sql puteti veadea tranzactia care afiseaza datele din Bucatari
begin tran
waitfor delay '00:00:04'
insert into Bucatari values('Marga','Desert')
commit tran
select * from Bucatari



