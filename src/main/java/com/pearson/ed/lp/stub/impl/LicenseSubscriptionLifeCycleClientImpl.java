package com.pearson.ed.lp.stub.impl;

import com.pearson.rws.subscriptionevent.doc._2009._06._01.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.ltg.rumba.webservices.exception.SubscribeUserB2CResponseElementException;
import com.pearson.ed.ltg.rumba.webservices.exception.SubscriptionExceptionService;
import com.pearson.ed.ltg.rumba.webservices.service.validator.ValidatorConstants;
import com.pearson.ed.ltg.rumba.webservices.stub.api.LicenseSubscriptionLifeCycleClient;
import com.pearson.ed.ltg.rumba.webservices.stub.api.SubscriptionLifeCycleClient;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePool;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePoolRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateUsedLicenses;

import java.util.ArrayList;
import java.util.List;

/**
 * Web Service Client stub implementation of the {@link SubscriptionLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class LicenseSubscriptionLifeCycleClientImpl implements LicenseSubscriptionLifeCycleClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseSubscriptionLifeCycleClientImpl.class);
    
    @Autowired
    private WebServiceTemplate subscriptionWebServiceClient;

    @Autowired
    private WebServiceTemplate serviceClientLicense;
    
    public WebServiceTemplate getServiceClientLicense() {
		return serviceClientLicense;
	}

	public void setServiceClientLicense(WebServiceTemplate serviceClientLicense) {
		this.serviceClientLicense = serviceClientLicense;
	}

	@Autowired
    private SubscriptionExceptionService excSvc;
    
    public WebServiceTemplate getSubscriptionWebServiceClient() {
        return subscriptionWebServiceClient;
    }

    public void setSubscriptionWebServiceClient(WebServiceTemplate serviceClient) {
        this.subscriptionWebServiceClient = serviceClient;
    }
    
	@Override
	public SubscribeUserResponseElement subscribeUser(
			SubscribeUserRequestElement subscribeUserRequestElement) {
		SubscribeUserResponseElement response = null;
		try {
			LOGGER.debug("subscriptionWebServiceClient sent" + subscribeUserRequestElement.toString() + "\n");
			response = (SubscribeUserResponseElement) subscriptionWebServiceClient
					.marshalSendAndReceive(subscribeUserRequestElement);
			LOGGER.debug("subscriptionWebServiceClient recieved" + response.toString() + "\n");

			if (ValidatorConstants.STUDENT.equals(subscribeUserRequestElement
					.getSubscribeUserRequestType().getLearningContextRole())) {
				UpdateLicensePoolRequest updateRequest = new UpdateLicensePoolRequest();
				UpdateLicensePool updateValues = new UpdateLicensePool();

				updateValues.setLicensePoolId(subscribeUserRequestElement
						.getSubscribeUserRequestType().getLicensePoolId());
				updateValues.setEndDate(subscribeUserRequestElement
						.getSubscribeUserRequestType().getEndDate());
				updateValues.setStartDate(subscribeUserRequestElement
						.getSubscribeUserRequestType().getStartDate());
				UpdateUsedLicenses updatedLicense = new UpdateUsedLicenses();
				updatedLicense.setOrganizationId(subscribeUserRequestElement
						.getSubscribeUserRequestType().getOrganizationId());
				updatedLicense.setUsedLicenses(1);
				updateValues.setUsedLicenses(updatedLicense);

				updateRequest.setUpdateLicensePool(updateValues);
				LOGGER.debug("serviceClientLicense sent" + updateRequest.toString() + "\n");
				Object response2 = serviceClientLicense.marshalSendAndReceive(updateRequest);
				if (response2 != null)
				{
					LOGGER.debug("serviceClientLicense recieved" + response2.toString() + "\n");
				}
			}
		} catch (SoapFaultClientException e) {
			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
			throw new SubscribeUserB2CResponseElementException(
					excSvc.getSoapFaultMessage(e), null, e);
		} catch (Exception exception) {
			LOGGER.error("licenseRequest SoapFaultClientException" + exception.getMessage() + "\n");
			throw new SubscribeUserB2CResponseElementException(
					exception.getMessage(), null, exception);
		}
		return response;
	}
	
    @Override
   	public UnSubscribeUserResponseElement unsubscribeUser(
      UnSubscribeUserRequestElement subscribeUserRequestElement) {
        UnSubscribeUserResponseElement response = null;
        try {
            LOGGER.debug("subscriptionWebServiceClient sent" + subscribeUserRequestElement.toString() + "\n");
            response = (UnSubscribeUserResponseElement) subscriptionWebServiceClient
              .marshalSendAndReceive(subscribeUserRequestElement);
            LOGGER.debug("subscriptionWebServiceClient recieved" + response.toString() + "\n");
            String licencePoolId = response.getServiceResponseType().getReturnValue();
            if (ValidatorConstants.STUDENT.equals(subscribeUserRequestElement
              .getUnSubscribeUserRequestType().getLearningContextRole().getValue())) {
                UpdateLicensePoolRequest updateRequest = new UpdateLicensePoolRequest();
                UpdateLicensePool updateValues = new UpdateLicensePool();

                updateValues.setLicensePoolId(licencePoolId);
                UpdateUsedLicenses updatedLicense = new UpdateUsedLicenses();
                updatedLicense.setOrganizationId(subscribeUserRequestElement
                  .getUnSubscribeUserRequestType().getOrganizationId());
                updatedLicense.setUsedLicenses(-1);
                updateValues.setUsedLicenses(updatedLicense);

                updateRequest.setUpdateLicensePool(updateValues);
                LOGGER.debug("serviceClientLicense sent" + updateRequest.toString() + "\n");
                Object response2 = serviceClientLicense.marshalSendAndReceive(updateRequest);
                LOGGER.debug("serviceClientLicense recieved" + response2.toString() + "\n");
            }
   		} catch (SoapFaultClientException e) {
   			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
   			throw new SubscribeUserB2CResponseElementException(
   					excSvc.getSoapFaultMessage(e), null, e);
   		} catch (Exception exception) {
   			LOGGER.error("licenseRequest SoapFaultClientException" + exception.getMessage() + "\n");
   			throw new SubscribeUserB2CResponseElementException(
   					exception.getMessage(), null, exception);
   		}
   		return response;
   	}

    @Override
    public UnSubscribeUserByRequestIdResponseElement unsubscribeUserByRequestId(
      UnSubscribeUserByRequestIdRequestElement sure) {
        UnSubscribeUserByRequestIdResponseElement response = null;
        try {
            List<UnSubscribeUserByRequestId> ids =
              sure.getSubscriptionRequestId();
            List<String> reqIds = convertIds(ids);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(
                  "calling subscriptionWebServiceClient sent: " + sure.toString() +
                    "\n");
            }

            response =
              (UnSubscribeUserByRequestIdResponseElement) subscriptionWebServiceClient
                .marshalSendAndReceive(sure);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subscriptionWebServiceClient received" +
                  response.toString() + "\n");
            }
            List<UnSubscribeUserByRequestId> failures = response.getFailedRequestId();
            if (failures != null && failures.size() > 0) {
                for (UnSubscribeUserByRequestId unsub : failures) {
                    reqIds.remove(unsub.getRequestId());
                    //
                    // also handle the return value?
                }
            }

            List<SubscriptionRequestType> ofSubs = getSubscriptions(reqIds);
            if (ofSubs != null) {
                for (SubscriptionRequestType curr : ofSubs) {
                    updateLicensePool(curr);
                }
            }

   			//}
   		} catch (SoapFaultClientException e) {
   			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
   			throw new SubscribeUserB2CResponseElementException(
   					excSvc.getSoapFaultMessage(e), null, e);
   		} catch (Exception exception) {
   			LOGGER.error("licenseRequest SoapFaultClientException" + exception.getMessage() + "\n");
   			throw new SubscribeUserB2CResponseElementException(
   					exception.getMessage(), null, exception);
   		}
   		return response;
   	}

    private List<SubscriptionRequestType> getSubscriptions(List<String> reqIds) {
        if (reqIds == null || reqIds.size() == 0) {
            return null;
        }
        GetSubscriptionsRequestBySubscriptionRequestIdsRequest req =
          new GetSubscriptionsRequestBySubscriptionRequestIdsRequest();
        List<Long> ids = req.getSubscriptionRequestIds();
        for (String id : reqIds) {
            ids.add(Long.parseLong(id));
        }

        GetSubscriptionsRequestBySubscriptionRequestIdsResponse response =
          (GetSubscriptionsRequestBySubscriptionRequestIdsResponse)
            subscriptionWebServiceClient.marshalSendAndReceive(req);

        return response.getSubscriptionInfo();
    }

    private void updateLicensePool(SubscriptionRequestType curr) {

        //String licencePoolId = response.getServiceResponseType().getReturnValue();
        if (ValidatorConstants.STUDENT.equals(curr.getLearningContextRole())) {
            String licencePoolId = curr.getPaymentEventId();

            UpdateLicensePoolRequest updateRequest = new UpdateLicensePoolRequest();
            UpdateLicensePool updateValues = new UpdateLicensePool();

            updateValues.setLicensePoolId(licencePoolId);

            UpdateUsedLicenses updatedLicense = new UpdateUsedLicenses();
            updatedLicense.setOrganizationId(curr.getOrganizationId());
            updatedLicense.setUsedLicenses(-1);
            updateValues.setUsedLicenses(updatedLicense);
            updateRequest.setUpdateLicensePool(updateValues);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("serviceClientLicense sent" + updateRequest.toString() +
                  "\n");
            }
            Object response2 = serviceClientLicense.marshalSendAndReceive(updateRequest);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("serviceClientLicense received" + response2.toString() +
                  "\n");
            }
        }
    }

    private List<String> convertIds(List<UnSubscribeUserByRequestId> ids) {
        List<String> retVal = new ArrayList<String>();
        if (ids == null || ids.size() == 0) {
            return retVal;
        }

        for (UnSubscribeUserByRequestId curr : ids) {
            retVal.add(curr.getRequestId());
        }
        return retVal;
    }
}
