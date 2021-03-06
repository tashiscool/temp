package com.pearson.ed.lp.aggregator;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;

import com.pearson.ed.lplc.stub.dto.OrganizationDTO;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProduct;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;
import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdResponse;
import com.pearson.rws.subscriptionevent.doc.v2.ObjectFactory;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserRequestElement;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserRequestType;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationResponse;

/**
 * Final response message aggregator for the GetLicensedProductV2 service.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductResponseAggregator {
	ObjectFactory ob = new ObjectFactory();
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
		GetLicensePoolDetailsByIdResponse details = typeGet(GetLicensePoolDetailsByIdResponse.class, responseMessages);
		GetProductDetailsResponse prod = typeGet(GetProductDetailsResponse.class, responseMessages);
		GetResourcesByProductIdResponse resc = typeGet(GetResourcesByProductIdResponse.class, responseMessages);
		GetUsersByAffiliationResponse afilliation = typeGet(GetUsersByAffiliationResponse.class, responseMessages);
		ArrayList childlist = typeGet(ArrayList.class, responseMessages);
		LinkedList parentlist = typeGet(LinkedList.class, responseMessages);

		childlist.addAll(parentlist);
		
		for (String userid : afilliation.getUsers().getUserId())
		{
			for (Object orgIds : childlist)
			{
				SubscribeUserRequestElement subscribeUser = new SubscribeUserRequestElement();
				subscribeUser.setEventReason("LP_CREATE");
				SubscribeUserRequestType value = new SubscribeUserRequestType();
				
				value.setSubscriptionType("Institutional Licensing");
				value.setLicensePoolId(details.getLicensePool().getLicensePoolId());
				value.setStartDate(details.getLicensePool().getStartDate());
				value.setEndDate(details.getLicensePool().getEndDate());
				value.setLearningContextRole("INSTRUCTOR");
				value.setUserId(userid);
				value.setCreatedBy(ob.createSubscribeUserRequestTypeCreatedBy("System"));
				value.setSourceSystem("https://idpdev.pearsoncmg.com/synapse");
				if (orgIds instanceof OrganizationDTO)
					value.setOrganizationId(((OrganizationDTO) orgIds).getOrgId());
				value.setProductId(String.valueOf(prod.getProduct().getProductEntityId()));
				
				subscribeUser.setSubscribeUserRequestType(value);
				request.add(subscribeUser);
			}			
		}
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
