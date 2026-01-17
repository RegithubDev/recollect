package com.resustainability.recollect.dto.response;

import java.util.List;

public record OrderInvoiceDetailedResponse(
		IOrderHistoryResponse details,
		List<IOrderCartItemResponse> cart,
		InvoiceResponse invoice
) {}
