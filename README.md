# MarginManagement

Task: Build a RESTful API for a simple margin management

Requirements:
- Use Spring Framework to build the RESTful API
- Use in-memory H2 database
- Design and implement the RESTful API endpoints to perform the following tasks:
	1. Upload a margin CSV file (csv example attached). POST /upload
		a. Validate as below:
			For Comment: Ignore/Skip this column from uploading
			Instruction: String
			Base Ccy: String length = 3. I.E: USD
			Term Ccy: String length = 3. i.E: SGD
			Trader Tier: Integer >= 0
			From Amount: Integer >= 0
			To Amount: Integer >= 0
			Bid Operator: "-" or "+"
			Bid Modifier: 6 dps number
			Ask Operator: "-" or "+"
			Ask Modifier: 6 dps number
			Remarks: String
		b. Upload a new file will erase current data in database and replace it with the new file content.
		c. Row order should be maintained in database. (using margin_order column)
	2. Download current margins as CSV. GET /download
	3. Find margin endpoint. POST /find
		a. Using POST method with input payload as below:
			"instruction"
			"baseCcy"
			"termCcy"
			"traderTier"
			"amount"
		b. 	Return the FIRST matched column (by margin_order column) with the input value.			
			Special codition, If in Database:
			- instruction = "*" => matched to any input "instruction"
			- base_ccy = "*" => matched to any input "baseCcy"
			- term_ccy = "*" => matched to any input "termCcy"
			- trader_tier = 0 => matched to any input "traderTier"
			- "amount" must be within range of from_amount and to_amount, if from_amount = to_amount = 0 => matched to any input "amount"
			
- Give instruction using postman on how to use the API. (Bonus)

For uploading CSV 1. POST MAPPING :: http://localhost:8080/upload 
	> Under body tab. Select form-data. Give file as key and browse CSV from computer.

For downloading CSV 2. GET MAPPING :: http://localhost:8080/download

For finding the data from Margin 3. POST MAPPING :: http://localhost:8080/find
	payload:: {
    "instruction": "*",
    "baseCcy": "USD",
    "termCcy": "SGD",
    "traderTier": 170,
    "amount": 50000
}
      
