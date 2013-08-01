package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.CG_PROGRAM_ATTR_KEY;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.GRADE_LEVEL_ATTR_KEY;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.exception.ProductNotFoundException;
import com.pearson.ed.lp.exception.RequiredObjectNotFoundException;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;
import com.pearson.ed.lplc.stub.impl.Exception;
import com.pearson.ed.lplc.stub.impl.GetLicensePoolToSubscribeRequest;
import com.pearson.ed.lplc.stub.impl.GetProductDetailsRequestWithOrg;
import com.pearson.ed.lplc.stub.impl.GetProductDetailsResponse;
import com.pearson.ed.lplc.stub.impl.GetResourcesByProductIdRequest;
import com.pearson.ed.lplc.stub.impl.GetResourcesByProductIdResponse;
import com.pearson.ed.lplc.stub.impl.GetResourcesByProductIdResponseException;
import com.pearson.ed.lplc.stub.impl.LicensePoolToSubscribe;
import com.pearson.ed.lplc.stub.impl.LicensePoolToSubscribeException;
import com.pearson.ed.lplc.stub.impl.Override;
import com.pearson.ed.lplc.stub.impl.ProductLicenseWrapper;
import com.pearson.rws.product.doc.v2.AttributeType;
import com.pearson.rws.product.doc.v2.DisplayInfoType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author VALAGNA
 * 
 */
public class ProductLifeCycleClientImpl implements ProductLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductLifeCycleClientImpl.class);

	private WebServiceTemplate serviceClient;

	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}
	
	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}
	
	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	/**
	 * Populate ProductData pojos associated with the given product entity ids by calling the ProductLifeCycle service.
	 * Implements {@link ProductLifeCycleClient#getProductDataByProductEntityIds(ProductEntityIdsRequest)}.
	 * 
	 * @param request
	 *            ProductEntityIdsRequest wrapping a list of product entity ids
	 * @return ProductEntityIdsResponse mapping Display Name strings to associated product entity ids
	 * @throws AbstractRumbaException on service error
	 */
	public ProductEntityIdsResponse getProductDataByProductEntityIds(ProductEntityIdsRequest request)
			throws AbstractRumbaException {

		ProductEntityIdsResponse response = new ProductEntityIdsResponse();
		Map<Long, ProductData> responsePayload = response.getProductDataByEntityIds();

		GetProductsByProductEntityIdsRequest productEntityIdRequest = new GetProductsByProductEntityIdsRequest();
		GetProductsByProductEntityIdsResponse productEntityIdResponse = null;

		// pass through empty set if no entity ids exist to request, valid scenario
		if(request.getProductEntityIds().isEmpty()) {
			return response;
		}
		
		productEntityIdRequest.getProductEntityId().addAll(request.getProductEntityIds());

		try {
			productEntityIdResponse = (GetProductsByProductEntityIdsResponse) serviceClient
					.marshalSendAndReceive(productEntityIdRequest);
		} catch (SoapFaultClientException exception) {
			String faultMessage = getFaultMessage(exception.getWebServiceMessage());
			if(faultMessage.contains("Required object not found")) {
				throw new ProductNotFoundException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0003), 
								request.getProductEntityIds().toArray(), exception);
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if (productEntityIdResponse != null) {

			for (GetProductsByProductEntityIdsResponseType responseType : productEntityIdResponse.getProduct()) {
				ProductData productData = new ProductData();
				responsePayload.put(responseType.getProductEntityId(), productData);

				productData.setProductId(responseType.getProductId());

				// quirk of the contract allows each possibility for empty display information
				if ((responseType.getDisplayInformation() == null)
						|| (responseType.getDisplayInformation().getDisplayInfo().isEmpty())) {
					LOGGER.error(String.format("No display information for product with entity id %d", 
							responseType.getProductEntityId()));
					throw new RequiredObjectNotFoundException(
							exceptionFactory.findExceptionMessage(
									LicensedProductExceptionMessageCode.LP_EXC_0005), 
									new Object[]{responseType.getProductEntityId()});
				}

				DisplayInfoType firstDisplayInfo = responseType.getDisplayInformation().getDisplayInfo().iterator()
						.next();

				// get the first display info name, just like the legacy service
				productData.setDisplayName(firstDisplayInfo.getName());
				productData.setShortDescription(firstDisplayInfo.getShortDescription());
				productData.setLongDescription(firstDisplayInfo.getLongDescription());

				// iterate through the attributes for the ones we want
				if (responseType.getAttributes() != null) {
					for (AttributeType attribute : responseType.getAttributes().getAttribute()) {
						if (attribute.getAttributeKey().equals(CG_PROGRAM_ATTR_KEY)) {
							if(productData.getCgProgram() == null) {
								productData.setCgProgram(attribute.getAttributeValue());
							} else {
								productData.setCgProgram(
										productData.getCgProgram() + " " + attribute.getAttributeValue());
							}
						} else if (attribute.getAttributeKey().equals(GRADE_LEVEL_ATTR_KEY)) {
							productData.addGradeLevel(attribute.getAttributeValue());
						}
					}
				}
			}
		}
		
		 @Override
		    public Object getResourcesByProductId(
		            GetResourcesByProductIdRequest request) {
		        GetResourcesByProductIdResponse response = null;
		        try {
					LOGGER.debug("serviceClient sent" + request.toString() + "\n");
		            response = (GetResourcesByProductIdResponse) serviceClient
		                    .marshalSendAndReceive(request);
					LOGGER.debug("serviceClient recieved" + response + "\n");
		        } catch (SoapFaultClientException e) {
					LOGGER.error("serviceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
		            return new GetResourcesByProductIdResponseException(
		                    excSvc.getSoapFaultMessage(e).replace("{}", request.getProductId()), null,
		                    e);
		        } catch (Exception exception) {
					LOGGER.error("serviceClient SoapFaultClientException" + exception.getMessage() + "\n");
		            return new GetResourcesByProductIdResponseException(
		                    exception.getMessage(), null, exception);
		        }
		        return response;
		    }

			@Override
			public Object getProductAndLicenseDetailsById(
					GetProductDetailsRequestWithOrg request) {
				ProductLicenseWrapper wrapper = new ProductLicenseWrapper();
				try {
					Object prodObj = getProductDetailsById(request.getProductRequest());
					if (prodObj instanceof GetProductDetailsResponse)
					{
						GetProductDetailsResponse product = (GetProductDetailsResponse) prodObj;
						wrapper.setProductRepsonse(product);
						request.getLicensePoolRequest().getGetLicensePoolToSubscribeRequestType()
							.setProductId(String.valueOf(product.getProduct().getProductEntityId()));
			            Object responObject = getLicensePoolToSubscribe(request.getLicensePoolRequest());
			            if (responObject instanceof LicensePoolToSubscribe)
						{
			            	LicensePoolToSubscribe response =(LicensePoolToSubscribe) responObject;
			            	wrapper.setLicenseResponse(response);
						}
			            else
			            {
			            	return responObject;
			            }
					}
					else 
					{
						return prodObj;
					}
					
		        }  catch (SoapFaultClientException e) {
					LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
		            return new LicensePoolToSubscribeException(
		                    excSvc.getSoapFaultMessage(e), null,
		                    e);
		        }
		        catch (Exception exception) {
		            return new LicensePoolToSubscribeException(exception.getMessage(), null, exception);
		        }
				return wrapper;
			}

			public Object getLicensePoolToSubscribe(
					GetLicensePoolToSubscribeRequest licenseRequest) {
				LicensePoolToSubscribe response = null;
				try {
					LOGGER.debug("licenseRequest sent" + licenseRequest.toString() + "\n");
		            response = (LicensePoolToSubscribe) serviceClientLicense
		                    .marshalSendAndReceive(licenseRequest);
					LOGGER.debug("licenseRequest recieved" + response + "\n");
		        }  catch (SoapFaultClientException e) {
					LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
		            return new LicensePoolToSubscribeException(
		                    excSvc.getSoapFaultMessage(e), null,
		                    e);
		        }
		        catch (Exception exception) {
		            return new LicensePoolToSubscribeException(exception.getMessage(), null, exception);
		        }
		        return response;
			}

		return response;
	}
}
