		Bank API Management System


1- Create New Bank Account

URL: /new-account
Method: POST
Request Headers
Header
Value
Description
Content-Type
application/json
The format of the request body should be JSON.


Request Body
The request body should contain a JSON object representing the BankAccount to be created. The required fields are:
customerId (string): The customer ID associated with the account.
initialDeposit (Long): The initial deposit amount for the account.
Response
Success Response
Status Code: 200 OK
If the bank account is successfully created, the API will respond with a JSON object containing the result. The response body will have the following format:
{
	“success” : true,
	“account “ : JSON
}
If the customer hasn’t registered yet, the response would be :
{
“Success” : false,
“errorMsg” : “customer hasn’t registered yet”
}

Error Response
Status Code: 400 Bad Request
If there is an issue during the account creation process, the API will respond with a JSON object indicating the error. The response body will have the following format:
{
“success” : false,
“error” : “Error Message describing the issue “
} 



2- Transfer Amount between accounts

URL: /transfer
Method: POST
Request Headers
Header
Value
Description
Content-Type
application/json
The format of the request body should be JSON.


Request Body
The request body should contain a JSON object with the following parameters:
senderAccountId (string): The ID of the sender's bank account.
receiverAccountId (string): The ID of the receiver's bank account.
amount (Long): The amount of money to be transferred.
Success Response
Status Code: 200 OK
If the money transfer is successful, the API will respond with a JSON object containing the result. The response body will have the following format:
{
“success” : true,
“transactionHistory” : JSON
}
If the user don’t have enough balance in account
{
	“success” : false,
	“errorMsg” : “You do not have enough credit in account”
}
If there is some other issue like accounts are same
{
	“success” : false,
	“errorMsg” : “Transaction could not go through”
}

Error Response
Status Code: 400 Bad Request
If there is an issue during the account creation process, the API will respond with a JSON object indicating the error. The response body will have the following format:
{
“success” : false,
“error” : “Error Message describing the issue “
} 


3- Get Account Balance
URL: /account/balance/{accountId}/{customerId}
Method: GET
Request
Path Parameters
Parameter
Description
accountId
The unique ID of the bank account to retrieve the balance for.
customerId
The unique ID of the customer associated with the bank account.

Response
Success Response
Status Code: 200 OK
If the retrieval of the account balance is successful, the API responds with a JSON object containing the result. The response body will have the following format:
{
“success” : true,
“balance” : Long
}

If account belongs to some other customer 
{
“success” : false,
“errorMsg” : “This account belongs to someone else”
}

Error Response
Status Code: 400 Bad Request
If there is an issue during the account creation process, the API will respond with a JSON object indicating the error. The response body will have the following format:
{
“success” : false,
“error” : “Error Message describing the issue “
} 


4- Get Transactions History

URL: /account/history/{accountId}
Method: GET
Request
Path Parameter
Parameter
Description
accountId
The unique ID of the bank account to retrieve the transaction history for.

Response
Success Response
Status Code: 200 OK
If the retrieval of the transaction history is successful, the API responds with a JSON object containing the result. The response body will have the following format:



{
“success” : true,
“history” : [ {} ]

}

Error Response
Status Code: 400 Bad Request
If there is an issue during the account creation process, the API will respond with a JSON object indicating the error. The response body will have the following format:
{
“success” : false,
“error” : “Error Message describing the issue “
} 
