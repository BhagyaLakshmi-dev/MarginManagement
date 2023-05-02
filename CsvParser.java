package com.example.demo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.example.demo.models.Margin;

public class CsvParser {
	public static List<Margin> parseCsv(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

		List<Margin> margins = new ArrayList<>();
		for (CSVRecord csvRecord : csvParser) {
			Margin margin = new Margin();
			margin.setInstruction(csvRecord.get("Instruction"));
			margin.setBaseCcy(csvRecord.get("Base Ccy"));
			margin.setTermCcy(csvRecord.get("Term Ccy"));
			margin.setTraderTier(parsePositiveInt(csvRecord.get("Trader Tier")));
			margin.setFromAmount(parsePositiveInt(csvRecord.get("From Amount")));
			margin.setToAmount(parsePositiveInt(csvRecord.get("To Amount")));
			margin.setBidOperator(csvRecord.get("Bid Operator"));
			margin.setBidModifier(Double.parseDouble(csvRecord.get("Bid Modifier")));
			margin.setAskOperator(csvRecord.get("Ask Operator"));
			margin.setAskModifier(Double.parseDouble(csvRecord.get("Ask Modifier")));
			margin.setRemarks(csvRecord.get("Remarks"));
			margin.setMarginOrder(0); // Default value, will be set later based on row index
			margins.add(margin);
		}

		csvParser.close();
		return margins;
	}

	public static String convertToCsv(List<Margin> margins) {
		StringWriter writer = new StringWriter();

		try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(MARGIN_HEADERS))) {
			for (Margin margin : margins) {
				csvPrinter.printRecord(
						margin.getInstruction(),
						margin.getBaseCcy(),
						margin.getTermCcy(),
						margin.getTraderTier(),
						margin.getFromAmount(),
						margin.getToAmount(),
						margin.getBidOperator(),
						margin.getBidModifier(),
						margin.getAskOperator(),
						margin.getAskModifier(),
						margin.getRemarks()
						);
			}
		} catch (IOException e) {
			
		}

		return writer.toString();
	}

	private static final String[] MARGIN_HEADERS = new String[] {
			"Comment",
			"Instruction",
			"Base Ccy",
			"Term Ccy",
			"Trader Tier",
			"From Amount",
			"To Amount",
			"Bid Operator",
			"Bid Modifier",
			"Ask Operator",
			"Ask Modifier",
			"Remarks"
	};

	private static Integer parsePositiveInt(String str) {
		String numericStr = str.replaceAll("\\D+", "");
		return numericStr.isEmpty() ? 0 : Integer.parseInt(numericStr);
	}

}

