## employee list 
Test project with database of employee list. It has 6 modes. So I made two versions with Hibernate and with driver only.
Modes:
1. Create Employees Table.
2. Create record in table.
3. Get all records from table and print it (FIO YEAR_OF_BIRTH Gender AGE).
4. Autogenerate 1 000 000 employees and add it to table (make with Batch (bulk insert)).
5. Make filter to get from table only employees Gender male and Name starts with F.

And next task is to optimize database search. For this I make indexes by column Gender and full_name:

``CREATE INDEX employees_last_name_index ON employees (gender,full_name)``

Without optimization it works 56 ms
With optimization it works 54 ms
Accelerated by 3%.