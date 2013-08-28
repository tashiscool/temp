package com.pearson.ed.lp.aggregator;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.stub.dto.OrganizationDTO;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProduct;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;
import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdResponse;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserRequestElement;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationResponse;

/**
 * Final response message aggregator for the GetLicensedProductV2 service.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductResponseAggregator {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductResponseAggregator.class);

	/**
	 * Aggregate a list of message objects into a single {@link GetLicensedProductResponseElement}.
	 * Expected response messages in provided list:
	 * <ul>
	 * <li>{@link ProductEntityIdsResponse}</li>
	 * <li>{@link OrderLineItemResponse}</li>
	 * <li>{@link LicensePoolResponse}</li>
	 * <li>{@link OrganizationDisplayNameResponse}</li>
	 * </ul>
	 * 
	 * @param responseMessages collection of messages to aggregate
	 * @return {@link GetLicensedProductResponseElement} instance
	 */
	@Aggregator
	public List<SubscribeUserRequestElement> aggregate(List<Object> responseMessages) {
		List<SubscribeUserRequestElement> request = new ArrayList<SubscribeUserRequestElement>();
		typeGet(GetLicensePoolDetailsByIdResponse.class, responseMessages);
		typeGet(GetProductDetailsResponse.class, responseMessages);
		typeGet(GetResourcesByProductIdResponse.class, responseMessages);
		GetUsersByAffiliationResponse afilliation = typeGet(GetUsersByAffiliationResponse.class, responseMessages);
		typeGet(ArrayList.class, responseMessages);
		
		
		return request;
	}

	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}
	
	private <T> T typeGet(Class<T> clazz, List<Object> responseMessages)
	{
		for (Object res : responseMessages)
			if (res.getClass() == clazz)
				return (T) res;
		return null;
		
	}

}
