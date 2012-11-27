package com.pearson.ed.lp.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProduct;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;

/**
 * Transforms incoming unmarshalled JAXB2 GetLicensedProductsRequest objects into the internal
 * LicensePoolByOrganizationIdRequest message object consumed by the LicensePoolLifeCycleClient stub.
 * 
 * @author ULLOYNI
 * 
 */
public class GetLicensedProductRequestTransformer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetLicensedProductRequestTransformer.class);

	/**
	 * Transform the JAXB2 generated {@link GetLicensedProductRequestElement} into
	 * the internal {@link LicensePoolByOrganizationIdRequest}.
	 * 
	 * @param request {@link GetLicensedProductRequestElement}
	 * @return {@link LicensePoolByOrganizationIdRequest}
	 */
	@Transformer
	public LicensePoolByOrganizationIdRequest transform(GetLicensedProductRequestElement request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received request to transform");
		}
		GetLicensedProduct requestContent = request.getGetLicensedProduct();
		return new LicensePoolByOrganizationIdRequest(requestContent.getOrganizationId(), requestContent
				.getQualifyingLicensePool().toString());
	}

}
