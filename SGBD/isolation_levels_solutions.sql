use HavEATreat
go
--deadlock transaction 2
--datorita setarii deadlock_priority=high datele modificate in tranzactia aceasta vor persista
set transaction isolation level serializable
set deadlock_priority high
begin tran
update Barmani set Varsta=30 where Nume='Tudor'
waitfor delay '00:00:05'
update Bucatari set Specialitate='Paste' where Nume='Larisa'
commit tran

--DEARTY READS (with problem)
--din cauza setarii cu read uncommited select-ul va citi datele necomise (adica pana sa se faca rollback)
set transaction isolation level read uncommitted
begin tran
select * from Barmani
waitfor delay '00:00:15'
select * from Barmani
commit tran

--DEARTY READS (solution)
--folosind read committed se pastraza consistenta datelor
set transaction isolation level read committed
begin tran
select * from Barmani
waitfor delay '00:00:15'
select * from Barmani
commit tran

select * from Barmani

--NON-REPEATABLE READS(with problem)
--din cauza lui read committed pentru primul select vom obtine datele inainte de update
--iar pentru al doilea select vom obtine datele de dupa update generand doua rezultate diferite in aceeasi tranzactie
set transaction isolation level read committed
begin tran
select * from Bucatari
waitfor delay '00:00:15'
select * from Bucatari
commit tran
--NON-REPEATABLE READS(solution)
--cu repeatable read ambele afisari vor contine aceleasi date
set transaction isolation level repeatable read
begin tran
select * from Bucatari
waitfor delay '00:00:15'
select * from Bucatari
commit tran

--PHANTOM READS(with problem)
--repeatable read face ca cele doua fisari sa difere datorita delay-ului si a adaugarii din prima tranzactie
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
SELECT * FROM Bucatari
WAITFOR DELAY '00:00:05'
SELECT * FROM Bucatari
commit tran
--PHANTOM READS(solution)
--serializable va face ca datele din ambele afisari sa coincida
SET TRANSACTION ISOLATION LEVEL Serializable
BEGIN TRAN
SELECT * FROM Bucatari
WAITFOR DELAY '00:00:05'
SELECT * FROM Bucatari
commit tran

select * from Bucatari
select * from Barmani

--stored procedures for C# app
alter procedure spTrans1
as begin
set transaction isolation level serializable
set deadlock_priority high
begin tran
update Barmani set Varsta=20 where Nume='Tudor'
waitfor delay '00:00:05'
update Bucatari set Specialitate='Desert' where Nume='Larisa'
commit tran
end

alter procedure spTrans2
as begin
set transaction isolation level serializable
begin tran 
update Bucatari set Specialitate='Paste' where Nume='Larisa'
waitfor delay '00:00:05'
update Barmani set Varsta=30 where Nume='Tudor'
commit tran
end