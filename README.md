<h1>Steps To Start </h1>

<h3>1: Create Table for Bank Transactions in Your Database </h3>

create table bank_transactions (
transaction_id int primery key ,
account_number int ,
account_holder_name varchar 255 , 
deposit_amount double , 
withdraw_amount double , 
to_account int,
from_account int,
account_password varchar 255 );



