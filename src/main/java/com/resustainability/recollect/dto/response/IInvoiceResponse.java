package com.resustainability.recollect.dto.response;

public interface IInvoiceResponse {
    Long getId();
    Double getWeight();
    Double getClientPrice();
    Double getSubTotal();
    Double getCgstAmount();
    Double getSgstAmount();
    Double getBagAmount();
    Double getTotalBill();
}
