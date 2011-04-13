package com.pearson.ed.lp.aggregator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Aggregator that merges multiple OrganizationDisplayNamesResponse objects
 * into a single OrganizationDisplayNamesResponse object.
 * 
 * @author ULLOYNI
 *
 */
public class OrganizationDisplayNamesResponseAggregator {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(OrganizationDisplayNamesResponseAggregator.class);

	public OrganizationDisplayNamesResponse aggregateResponse(List<OrganizationDisplayNamesResponse> responses) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received messages to aggregate, count: %d", responses.size()));
		}
		// TODO implementation
		
		return new OrganizationDisplayNamesResponse();
	}
	
}
