package com.pearson.ed.lplc.ws;

import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.exception.ComponentCardinalityException;
import com.pearson.ed.lplc.exception.ComponentValidationException;
import com.pearson.ed.lplc.exception.LPLCBaseException;
import com.pearson.ed.lplc.exception.LicensePoolException;
import com.pearson.ed.lplc.exception.LicensePoolExceptionFactory;
import com.pearson.ed.lplc.services.api.LicensePoolServiceEndPoint;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.warning.LicensePoolWarningFactory;
import com.pearson.rws.licensepool.doc._2009._04._01.CancelLicensePoolRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.CancelLicensePoolResponse;
import com.pearson.rws.licensepool.doc._2009._04._01.CreateLicensePool;
import com.pearson.rws.licensepool.doc._2009._04._01.CreateLicensePoolRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.CreateLicensePoolResponse;
import com.pearson.rws.licensepool.doc._2009._04._01.DenyNewSubscriptionsRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.DenyNewSubscriptionsResponse;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolByOrganizationIdRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolToSubscribeRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolDetails;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolToSubscribe;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensepoolsByOrganizationId;
import com.pearson.rws.common.doc._2008._06._01.ServiceResponseType;
import com.pearson.rws.common.doc._2008._06._01.StatusCodeType;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePool;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePoolRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePoolResponse;

/**
 * A LicensePool Life Cycle endpoint that processes marshaled messages.
 * 
 * @author Dipali Trivedi
 */

@Endpoint
public class MarshallingLicensePoolEndpoint implements LicensePoolWebServiceConstants {
	private static final Logger logger = Logger.getLogger(MarshallingLicensePoolEndpoint.class);
	/**
	 * LicensePool Life Cycle service
	 */
	private LicensePoolServiceEndPoint licensePoolServiceEndPoint;
	private LicensePoolExceptionFactory exceptionFactory;
	private LicensePoolWarningFactory warningFactory;
	private LicensePoolConverter licensePoolConverter;

	/**
	 * @return the licensepoolServiceEndPoint
	 */
	public LicensePoolServiceEndPoint getLicensePoolServiceEndPoint() {
		return licensePoolServiceEndPoint;
	}

	/**
	 * @param licensePoolServiceEndPoint
	 *            the licensePoolServiceEndPoint to set
	 */
	public void setLicensePoolServiceEndPoint(LicensePoolServiceEndPoint licensePoolServiceEndPoint) {
		this.licensePoolServiceEndPoint = licensePoolServiceEndPoint;
	}

	/**
	 * @param licensepoolServiceEndPoint
	 *            the licensepoolServiceEndPoint to set
	 */
	public void setLicensepoolServiceEndPoint(LicensePoolServiceEndPoint licensePoolServiceEndPoint) {
		this.licensePoolServiceEndPoint = licensePoolServiceEndPoint;
	}

	/**
	 * @return the exceptionFactory
	 */
	public LicensePoolExceptionFactory getExceptionFactory() {
		return exceptionFactory;
	}

	/**
	 * @param exceptionFactory
	 *            the exceptionFactory to set
	 */
	public void setExceptionFactory(LicensePoolExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	/**
	 * @return the warningFactory
	 */
	public LicensePoolWarningFactory getWarningFactory() {
		return warningFactory;
	}

	/**
	 * @param warningFactory
	 *            the warningFactory to set
	 */
	public void setWarningFactory(LicensePoolWarningFactory warningFactory) {
		this.warningFactory = warningFactory;
	}

	/**
	 * @return the licensePoolConverter
	 */
	public LicensePoolConverter getLicensePoolConverter() {
		return licensePoolConverter;
	}

	/**
	 * @param licensePoolConverter
	 *            the licensePoolConverter to set
	 */
	public void setLicensePoolConverter(LicensePoolConverter licensePoolConverter) {
		this.licensePoolConverter = licensePoolConverter;
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;CreatelicensepoolRequestElement&gt;</code> payload.
	 * 
	 * @param licensepoolRequest
	 *            the create licensepool request.
	 */
	@PayloadRoot(localPart = CREATE_LICENSEPOOL_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public CreateLicensePoolResponse createLicensePool(CreateLicensePoolRequest licensepoolRequest) {
		ServiceResponseType serviceResponseType = new ServiceResponseType();

		try {
			CreateLicensePool createLicensePoolSchemaObj = licensepoolRequest.getCreateLicensePool();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + CREATE_LICENSEPOOL_REQUEST_ELEMENT + ":"
						+ createLicensePoolSchemaObj.toString());
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			if (createLicensePoolSchemaObj.getProductId().size() > 1)
				throw new ComponentCardinalityException("More than 1 product association is not supported.");
			if (LPLCConstants.LICENSEPOOLTYPE.equalsIgnoreCase(createLicensePoolSchemaObj.getType()))
				throw new ComponentValidationException("License Type is not Valid.");

			logger.info("Invoking Licensepool Service CreateLicensePool method");
			String licensepoolId = licensePoolServiceEndPoint.createLicensePool(createLicensePoolSchemaObj);
			serviceResponseType.setReturnValue(licensepoolId);
			serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
			serviceResponseType.setStatusCode(StatusCodeType.SUCCESS);
			serviceResponseType.getStatusMessage().add("LicensePool created successfully");

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			if (e instanceof LPLCBaseException) {
				serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
				serviceResponseType.setReturnValue(LPLCConstants.SERVICE_RESPONSE_RETURN_FAILURE);
				serviceResponseType.setStatusCode(StatusCodeType.FAILURE);
				serviceResponseType.getStatusMessage().add(licensepoolException.getCause().toString());
			} else {
				throw licensepoolException;
			}
		}
		CreateLicensePoolResponse createResponse = new CreateLicensePoolResponse();
		createResponse.setServiceResponseType(serviceResponseType);
		return createResponse;
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;CreatelicensepoolRequestElement&gt;</code> payload.
	 * 
	 * @param licensepoolRequest
	 *            the update licensepool request.
	 */
	@PayloadRoot(localPart = UPDATE_LICENSEPOOL_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public UpdateLicensePoolResponse createLicensePool(UpdateLicensePoolRequest licensepoolRequest) {
		ServiceResponseType serviceResponseType = new ServiceResponseType();

		try {
			UpdateLicensePool updateLicensePoolSchemaObj = licensepoolRequest.getUpdateLicensePool();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + UPDATE_LICENSEPOOL_REQUEST_ELEMENT + ":"
						+ updateLicensePoolSchemaObj.toString());
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			logger.info("Invoking Licensepool Service UpdateLicensePool method");
			String licensepoolId = licensePoolServiceEndPoint.updateLicensePool(updateLicensePoolSchemaObj);
			serviceResponseType.setReturnValue(licensepoolId);
			serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
			serviceResponseType.setStatusCode(StatusCodeType.SUCCESS);
			serviceResponseType.getStatusMessage().add("LicensePool updated successfully");

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			if (e instanceof LPLCBaseException) {
				serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
				serviceResponseType.setReturnValue(LPLCConstants.SERVICE_RESPONSE_RETURN_FAILURE);
				serviceResponseType.setStatusCode(StatusCodeType.FAILURE);
				serviceResponseType.getStatusMessage().add(licensepoolException.getCause().toString());
			} else {
				throw licensepoolException;
			}
		}
		UpdateLicensePoolResponse updateResponse = new UpdateLicensePoolResponse();
		updateResponse.setServiceResponseType(serviceResponseType);
		return updateResponse;
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;CreatelicensepoolRequestElement&gt;</code> payload.
	 * 
	 * @param licensepoolRequest
	 *            the update licensepool request.
	 */
	@PayloadRoot(localPart = GET_LICENSEPOOL_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public LicensepoolsByOrganizationId getLicensepoolByOrgIdLicensePool(
			GetLicensePoolByOrganizationIdRequest licensepoolRequest) {

		try {
			String organizationId = licensepoolRequest.getGetLicensePoolByOrganizationIdRequestType()
					.getOrganizationId();
			String qualifyingOrgs = licensepoolRequest.getGetLicensePoolByOrganizationIdRequestType()
					.getQualifyingLicensePool().toString();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + GET_LICENSEPOOL_REQUEST_ELEMENT + ":" + organizationId + " and "
						+ qualifyingOrgs);
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			logger.info("Invoking Licensepool Service GetLicensePool method");
			return licensePoolServiceEndPoint.getLicensePoolByOrganizationId(organizationId, qualifyingOrgs);

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			throw licensepoolException;

		}
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;CreatelicensepoolRequestElement&gt;</code> payload.
	 * 
	 * @param licensepoolRequest
	 *            the update licensepool request.
	 */
	@PayloadRoot(localPart = GET_LICENSEPOOL_TO_SUBSCRIBE_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public LicensePoolToSubscribe getLicensepoolToSubscribe(GetLicensePoolToSubscribeRequest licensepoolRequest) {

		try {
			String organizationId = licensepoolRequest.getGetLicensePoolToSubscribeRequestType().getOrganizationId();
			String productId = licensepoolRequest.getGetLicensePoolToSubscribeRequestType().getProductId();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + GET_LICENSEPOOL_TO_SUBSCRIBE_REQUEST_ELEMENT + ":" + organizationId
						+ " and " + productId);
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			logger.info("Invoking Licensepool Service GetLicensePoolToSubscribe method");
			return licensePoolServiceEndPoint.getLicensePoolToSubscribe(organizationId, productId);

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			throw licensepoolException;

		}
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;GetLicensePoolDetailsByIdRequest&gt;</code> payload.
	 * 
	 * @param getLicensePoolDetailsByIdRequest
	 *            GetLicensePoolDetailsByIdRequest element.
	 * @return GetLicensePoolDetailsByIdResponse
	 */
	@PayloadRoot(localPart = GET_LICENCEPOOL_DETAILS_BY_ID_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public GetLicensePoolDetailsByIdResponse getLicensepoolDetailsByIdrequest(
			GetLicensePoolDetailsByIdRequest getLicensePoolDetailsByIdRequest) {

		try {
			String licensePoolId = getLicensePoolDetailsByIdRequest.getLicensePoolId();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + GET_LICENCEPOOL_DETAILS_BY_ID_REQUEST_ELEMENT + ":" + licensePoolId);
			}

			logger.info("Invoking license pool service getLicensePoolDetailsById method");

			LicensePoolDetails licensePoolSummary = licensePoolServiceEndPoint.getLicensePoolDetailsById(licensePoolId);

			GetLicensePoolDetailsByIdResponse response = new GetLicensePoolDetailsByIdResponse();
			response.setLicensePool(licensePoolSummary);
			return response;

		} catch (Exception exception) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(exception);
			throw licensepoolException;

		}
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;DenyNewSubscriptionsRequest&gt;</code> payload.
	 * 
	 * @param denyNewSubscriptionsRequest
	 *            DenyNewSubscriptionsRequest element.
	 * @return DenyNewSubscriptionsResponse
	 */
	@PayloadRoot(localPart = DENY_NEW_SUBSCRIPTIONS_REQUEST, namespace = LICENSEPOOL_NAMESPACE)
	public DenyNewSubscriptionsResponse denyNewSubscriptions(DenyNewSubscriptionsRequest denyNewSubscriptionsRequest) {
		ServiceResponseType serviceResponseType = new ServiceResponseType();

		try {
			String requestLicensePoolId = denyNewSubscriptionsRequest.getLicensePoolId();
			String requestCreatedBy = denyNewSubscriptionsRequest.getCreatedBy();
			boolean requestIsDenied = denyNewSubscriptionsRequest.isIsDenied();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + DENY_NEW_SUBSCRIPTIONS_REQUEST + ":" + requestLicensePoolId + " : "
						+ requestCreatedBy + " : " + requestIsDenied);
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			logger.info("Invoking Licensepool Service DenyNewSubscriptions method");
			String licensepoolId = licensePoolServiceEndPoint.denyNewSubscriptions(requestLicensePoolId,
					requestIsDenied, requestCreatedBy);
			serviceResponseType.setReturnValue(licensepoolId);
			serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
			serviceResponseType.setStatusCode(StatusCodeType.SUCCESS);
			serviceResponseType.getStatusMessage().add("Deny Subscriptions Updated successfully");

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			if (e instanceof LPLCBaseException) {
				serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
				serviceResponseType.setReturnValue(LPLCConstants.SERVICE_RESPONSE_RETURN_FAILURE);
				serviceResponseType.setStatusCode(StatusCodeType.FAILURE);
				serviceResponseType.getStatusMessage().add(licensepoolException.getCause().toString());
			} else {
				throw licensepoolException;
			}
		}
		DenyNewSubscriptionsResponse denyNewSubscriptionsResponse = new DenyNewSubscriptionsResponse();
		denyNewSubscriptionsResponse.setServiceResponseType(serviceResponseType);
		return denyNewSubscriptionsResponse;
	}

	/**
	 * This endpoint method uses marshalling to handle message with a
	 * <code>&lt;CreatelicensepoolRequestElement&gt;</code> payload.
	 * 
	 * @param cancelLicensePoolRequest
	 *            the cancel licensepool request.
	 * @return CancelLicensePoolResponse
	 */
	@PayloadRoot(localPart = CANCEL_LICENSEPOOL_REQUEST_ELEMENT, namespace = LICENSEPOOL_NAMESPACE)
	public CancelLicensePoolResponse cancelLicensePool(CancelLicensePoolRequest cancelLicensePoolRequest) {
		ServiceResponseType serviceResponseType = new ServiceResponseType();

		try {
			String licensePoolId = cancelLicensePoolRequest.getLicensePoolId();
			String createdBy = cancelLicensePoolRequest.getCreatedBy();
			boolean isCancel = cancelLicensePoolRequest.isIsCancelled();
			if (logger.isDebugEnabled()) {
				logger.debug("Received " + CANCEL_LICENSEPOOL_REQUEST_ELEMENT + ":"
						+ cancelLicensePoolRequest.toString());
			}

			String transactionId = licensePoolServiceEndPoint.generateTransactionId();
			licensePoolServiceEndPoint.setTransactionId(transactionId);

			logger.info("Invoking Licensepool Service cancelLicensePool method");
			String licensepoolId = licensePoolServiceEndPoint.cancel(licensePoolId, createdBy, isCancel);
			serviceResponseType.setReturnValue(licensepoolId);
			serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
			serviceResponseType.setStatusCode(StatusCodeType.SUCCESS);
			serviceResponseType.getStatusMessage().add("Cancel LicensePool updated successfully");

		} catch (Exception e) {
			LicensePoolException licensepoolException = exceptionFactory.getLicensePoolException(e);
			if (e instanceof LPLCBaseException) {
				serviceResponseType.setTransactionId(licensePoolServiceEndPoint.getTransactionId());
				serviceResponseType.setReturnValue(LPLCConstants.SERVICE_RESPONSE_RETURN_FAILURE);
				serviceResponseType.setStatusCode(StatusCodeType.FAILURE);
				serviceResponseType.getStatusMessage().add(licensepoolException.getCause().toString());
			} else {
				throw licensepoolException;
			}
		}
		CancelLicensePoolResponse cancelLicensePoolResponse = new CancelLicensePoolResponse();
		cancelLicensePoolResponse.setServiceResponseType(serviceResponseType);
		return cancelLicensePoolResponse;
	}
}
