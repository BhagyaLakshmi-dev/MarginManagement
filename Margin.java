package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "margin")
public class Margin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "instruction", nullable = false)
    private String instruction;
    
    @Column(name = "base_ccy", nullable = false, length = 3)
    private String baseCcy;
    
    @Column(name = "term_ccy", nullable = false, length = 3)
    private String termCcy;
    
    @Column(name = "trader_tier", nullable = false)
    private Integer traderTier;
    
    @Column(name = "from_amount", nullable = false)
    private Integer fromAmount;
    
    @Column(name = "to_amount", nullable = false)
    private Integer toAmount;
    
    @Column(name = "bid_operator", nullable = false)
    private String bidOperator;
    
    @Column(name = "bid_modifier", nullable = false)
    private Double bidModifier;
    
    @Column(name = "ask_operator", nullable = false)
    private String askOperator;
    
    @Column(name = "ask_modifier", nullable = false)
    private Double askModifier;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "margin_order")
    private Integer marginOrder;

    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

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

	public Integer getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Integer fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Integer getToAmount() {
		return toAmount;
	}

	public void setToAmount(Integer toAmount) {
		this.toAmount = toAmount;
	}

	public String getBidOperator() {
		return bidOperator;
	}

	public void setBidOperator(String bidOperator) {
		this.bidOperator = bidOperator;
	}

	public Double getBidModifier() {
		return bidModifier;
	}

	public void setBidModifier(Double bidModifier) {
		this.bidModifier = bidModifier;
	}

	public String getAskOperator() {
		return askOperator;
	}

	public void setAskOperator(String askOperator) {
		this.askOperator = askOperator;
	}

	public Double getAskModifier() {
		return askModifier;
	}

	public void setAskModifier(Double askModifier) {
		this.askModifier = askModifier;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMarginOrder() {
		return marginOrder;
	}

	public void setMarginOrder(Integer marginOrder) {
		this.marginOrder = marginOrder;
	}

	public boolean isValid() {
        // add your validation rules here
        if (baseCcy == null || baseCcy.length() != 3) {
            return false;
        }
        if (termCcy == null || termCcy.length() != 3) {
            return false;
        }
        if (traderTier == null || traderTier < 0) {
            return false;
        }
        if (fromAmount == null || fromAmount < 0) {
            return false;
        }
        if (toAmount == null || toAmount < 0) {
            return false;
        }
        if (!"-".equals(bidOperator) && !"+".equals(bidOperator)) {
            return false;
        }
        if (bidModifier == null || bidModifier < 0/* || bidModifier > 999999.999999*/) {
            return false;
        }
        if (!"-".equals(askOperator) && !"+".equals(askOperator)) {
            return false;
        }
        if (askModifier == null || askModifier < 0/* || askModifier > 999999.999999*/) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Margin [comment=" + comment + ", instruction=" + instruction + ", baseCcy=" + baseCcy + ", termCcy="
                + termCcy + ", traderTier=" + traderTier + ", fromAmount=" + fromAmount + ", toAmount=" + toAmount
                + ", bidOperator=" + bidOperator + ", bidModifier=" + bidModifier + ", askOperator=" + askOperator
                + ", askModifier=" + askModifier + ", remarks=" + remarks + "]";
    }
}
