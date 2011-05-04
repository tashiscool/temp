When /^I request all licensed products for the ([a-z]+) organization with the qualifier '([a-zA-Z]+)'/ do |specific_org, qualifying_license_pool|
  lp_client = LicensedProductServiceClientV2.new $service_clients[:GetLicensedProductV2]
  lp_client.gen_skeleton_request :get
  lp_client.set_org_id eval "$#{specific_org}_org_id"
  lp_client.set_qualifying_license_pool qualifying_license_pool
  @result_response = lp_client.exec
end

Then /^I will get ([0-9]+) licensed products$/ do |expected_licensed_product_count|
  @result_response.should be
  @result_response.http_error?.should be_false, @result_response.http_error
  @result_response.soap_fault?.should be_false, @result_response.soap_fault

  licensed_products = @result_response[:get_licensed_product_response_element][:licensed_product]

  if expected_licensed_product_count.to_i > 0
    licensed_products =  [licensed_products] unless licensed_products.is_a? Array and not licensed_products.nil?
    licensed_products.should have(expected_licensed_product_count.to_i).items
  else
    licensed_products.should_not be
  end
end

Then /^each licensed product will be the 'InstitutionalLicensing' product$/ do
  @result_response.should be
  @result_response.http_error?.should be_false, @result_response.http_error
  @result_response.soap_fault?.should be_false, @result_response.soap_fault

  licensed_products = @result_response[:get_licensed_product_response_element][:licensed_product]
  licensed_products.should be, "No licensed products found!"

  licensed_products = [licensed_products] unless licensed_products.is_a? Array

  licensed_products.each { |licensed_product| licensed_product[:product_id].should == $product_id }
end

Then /^and the oppropriate organizations for '([a-zA-Z]+)' will be referenced$/ do |qualifying_license_pool|
  @result_response.should be
  @result_response.http_error?.should be_false, @result_response.http_error
  @result_response.soap_fault?.should be_false, @result_response.soap_fault

  licensed_products = @result_response[:get_licensed_product_response_element][:licensed_product]
  licensed_products.should be

  org_ids = []
  case qualifying_license_pool
    when "AllInHierarchy"
      org_ids << $grandparent_org_id << $parent_org_id
      org_ids += $child_org_ids
    when "RootAndParents"
      org_ids << $grandparent_org_id << $parent_org_id
    when "RootOnly"
      org_ids << $parent_org_id
  end

  licensed_products = [licensed_products] unless licensed_products.is_a? Array

  licensed_products.each do |licensed_product|
    org_ids.should include(licensed_product[:organization_id])
    org_ids.should include(licensed_product[:licensed_organization_id])
  end
end

Then /^I will get an error saying (.*)$/ do |error_msg|
  @result_response.should be
  @result_response.http_error?.should be_true
  @result_response.soap_fault?.should be_true

  fault_message = @result_response[:fault][:reason][:text]
  fault_message.should match(/#{error_msg}/)
end
