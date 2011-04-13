package com.pearson.ed.lp.aggregator;

import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.TRANSACTION_ID;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;

import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;

public class LicensedProductsResponseAggregator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductsResponseAggregator.class);
	
	@Aggregator
	public GetLicensedProductResponseElement aggregateResponse(List<Object> responseMessages) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received response messages to aggregate, message count: %d", 
					responseMessages.size()));
			LOGGER.debug(responseMessages.toString());
		}
		// TODO implementation
		return new GetLicensedProductResponseElement();
	}
	
	/**
	 * Release checker function that verifies the presence of all of the following
	 * response object types:
	 * <ul>
	 * 	<li>{@link ProductEntityIdsResponse}</li>
	 * 	<li>{@link OrderLineItemsResponse}</li>
	 * 	<li>{@link OrganizationDisplayNamesResponse}</li>
	 * 	<li>a Collection of {@link OrganizationLPMapping}</li>
	 * </ul>
	 * @param messages
	 * @return
	 */
	@ReleaseStrategy 
	public boolean releaseChecker(List<Message<Object>> messages) {
		boolean haveProductEntityIds = false;
		boolean haveOrderLineItems = false;
		boolean haveOrganizationDisplayNames = false;
		boolean haveLicensePoolDetails = false;
		Object transactionId = null;
		
		for(Message<Object> message : messages) {
//			if(transactionId == null) {
//				transactionId = message.getHeaders().get(TRANSACTION_ID);
//			} else {
//				if(!transactionId.equals(message.getHeaders().get(TRANSACTION_ID))) {
//					return false;
//				}
//			}
			
			if(message.getPayload() instanceof ProductEntityIdsResponse) {
				haveProductEntityIds = true;
			} else if(message.getPayload() instanceof OrderLineItemsResponse) {
				haveOrderLineItems = true;
			} else if(message.getPayload() instanceof OrganizationDisplayNamesResponse) {
				haveOrganizationDisplayNames = true;
			} else if(message.getPayload() instanceof Collection) {
				haveLicensePoolDetails = true;
			}
		}
		
		boolean success = (haveProductEntityIds && haveOrderLineItems 
				&& haveOrganizationDisplayNames && haveLicensePoolDetails);
		
		return (haveProductEntityIds && haveOrderLineItems 
				&& haveOrganizationDisplayNames && haveLicensePoolDetails);
	}

	@CorrelationStrategy 
	public Object correlateBy(Message<Object> message) {
		LOGGER.debug("TransactionId: " + message.getHeaders().get(TRANSACTION_ID).toString());
		LOGGER.debug(message.getPayload().getClass().toString());
		return message.getHeaders().get(TRANSACTION_ID);
	}

}
