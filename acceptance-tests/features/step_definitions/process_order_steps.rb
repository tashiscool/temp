# hardcoded string, not sure if there is a reasonable way to genericize this step
Given /^I have an ordered license pool for each organization for the 'InstitutionalLicensing' product$/ do
  unless $already_ordered
    orgs = []
    orgs << $grandparent_org_id << $parent_org_id
    orgs += $child_org_ids

    order_client = OrderProcessingServiceClient.new $service_clients[:OrderProcessing]
    orgs.each do |org_id|
      order_client.gen_skeleton_request :process_order
      order_client.set_order_org_id org_id
      order_client.add_order_line_item $product_entity_id
      response = order_client.exec
      response.http_error?.should be_false, response.http_error
      response.soap_fault?.should be_false, response.soap_fault
      response[:process_order_response][:service_response_type][:status_code].should == "SUCCESS"
    end
    $already_ordered = true
  end
end
