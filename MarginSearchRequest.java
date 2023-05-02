package com.example.demo.csv;

public class MarginSearchRequest {
    private String instruction;
    private String baseCcy;
    private String termCcy;
    private Integer traderTier;
    private Integer amount;
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getBaseCcy() {
		return baseCcy;
	}
	public void setBaseCcy(String baseCcy) {
		this.baseCcy = baseCcy;
	}
	public String getTermCcy() {
		return termCcy;
	}
	public void setTermCcy(String termCcy) {
		this.termCcy = termCcy;
	}
	public Integer getTraderTier() {
		return traderTier;
	}
	public void setTraderTier(Integer traderTier) {
		this.traderTier = traderTier;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}

