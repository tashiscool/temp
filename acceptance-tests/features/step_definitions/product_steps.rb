Given /^I have created a valid '([a-zA-Z]*)' enabled product$/ do |business_model|
  if $dummy_user_id.nil?
    user_client = UserServiceClientV3.new $service_clients[:UserLifeCycleV3]
    user_client.gen_skeleton_request :create
    response = user_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $dummy_user_id = response[:create_user_response][:service_response_type][:return_value]
  end

  if $dummy_org_id_for_product.nil?
    org_client = OrganizationServiceClient.new $service_clients[:OrganizationLifeCycle]
    org_client.gen_skeleton_request :create
    org_client.set_org_name "#{$active_us} AT Org for Dummy Product " + UUID.generate
    org_client.set_org_type "Unknown"
    org_client.add_org_display_group "Pearson Business Unit"
    org_client.add_identifier "Pegasus Id", "#{$active_us}_Parent_PegasusId"
    response = org_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $dummy_org_id_for_product = response[:create_organization_response][:service_response_type][:return_value]

  end

  product_client = ProductServiceClientV2.new $service_clients[:ProductLifeCycleV2]
  if $product_entity_id.nil?
    product_client.gen_skeleton_request :create
    product_client.add_identifier
    product_client.add_display_info
    product_client.set_business_model business_model
    product_client.add_contact $dummy_user_id
    product_client.set_org_id $dummy_org_id_for_product
    response = product_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $product_id = response[:product_cud_response][:service_response_type][:return_value]
    product_client.gen_skeleton_request :get_details
    product_client.set_product_id $product_id
    response = product_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $product_entity_id = response[:get_product_details_response][:product][:product_entity_id]
  end
end

Given /^I added a resource to the '([a-zA-Z]*)' enabled product$/ do |business_model|
  unless $already_added
    product_client = ProductServiceClientV2.new $service_clients[:ProductLifeCycleV2]
    product_client.gen_skeleton_request :add_resource
    product_client.set_product_id $product_id
    product_client.add_resource $resource_id
    response = product_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $already_added = true
  end
end
