package com.pearson.ed.lplc.event;


import java.util.List;

import javax.jms.JMSException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.jms.util.LicensepoolJMSUtils;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.ws.schema.EventTypeType;

public class LicensepoolExpirationEvent {
	
	private static final Logger logger = Logger.getLogger(LicensepoolExpirationEvent.class);
	private LicensePoolService licensepoolService;
	private LicensepoolJMSUtils jmsUtils;
	
	/**
	 * @return the jmsUtils
	 */
	public LicensepoolJMSUtils getJmsUtils() {
		return jmsUtils;
	}

	/**
	 * @param jmsUtils the jmsUtils to set
	 */
	public void setJmsUtils(LicensepoolJMSUtils jmsUtils) {
		this.jmsUtils = jmsUtils;
	}

	/**
	 * @return the licensepoolService
	 */
	public LicensePoolService getLicensepoolService() {
		return licensepoolService;
	}

	/**
	 * @param licensepoolService the licensepoolService to set
	 */
	public void setLicensepoolService(LicensePoolService licensepoolService) {
		this.licensepoolService = licensepoolService;
	}
	/**
	 * This method will call license pool service and send message.
	 */
	public void checkForExpiration()
			throws JobExecutionException {
		List<String> findExpiredLicensePool = licensepoolService.findExpiredLicensePool();
		LicensePoolDTO lplcDTO = null;
		if (findExpiredLicensePool!= null && findExpiredLicensePool.size()>0){
			for (String lplcId : findExpiredLicensePool) {
				System.out.println("########################"+lplcId+"###################");
				lplcDTO = new LicensePoolDTO();
				lplcDTO.setLicensepoolId(lplcId);
				try {
					jmsUtils.publish(lplcDTO, EventTypeType.LP_EXPIRE);
				} catch (Exception e) {
					logger.log(Level.ERROR,
							"Exception while sending Licensepool expiration message with ID :"
									+ lplcId +"  "+e.getStackTrace());	
				}
			}
		}
	}

}
