When /^I request all licensed products for the ([a-z]+) organization with the qualifier '([a-zA-Z]+)'/ do |specific_org, qualifying_license_pool|
  get_licensed_products = LicensedProductV2::GetLicensedProductRequestElement.new
  get_licensed_products.getLicensedProduct = LicensedProductV2::GetLicensedProduct.new
  get_licensed_products.getLicensedProduct.organizationId = eval "$#{specific_org}_org_id"
  get_licensed_products.getLicensedProduct.qualifyingLicensePool = qualifying_license_pool

  begin
    @result_response = $service_clients[:GetLicensedProductV2].request get_licensed_products
  rescue Savon::SOAP::Fault => fault
    @result_response = fault
  end
end

Then /^I will get ([0-9]+) licensed products$/ do |expected_licensed_product_count|
  @result_response.should_not be_a(Savon::SOAP::Fault)

  @result_response.should be_a(Array)
  @result_response.should have(expected_licensed_product_count.to_i).items
end

Then /^each licensed product will be the 'InstitutionalLicensing' product$/ do
  @result_response.should_not be_a(Savon::SOAP::Fault)

  @result_response.should be_a(Array)
  @result_response.should have_at_least(1).items, "No licensed products found!"

  @result_response.each { |licensed_product| licensed_product.productId.should == $product_id }
end

Then /^and the oppropriate organizations for '([a-zA-Z]+)' will be referenced$/ do |qualifying_license_pool|
  @result_response.should_not be_a(Savon::SOAP::Fault)

  @result_response.should be_a(Array)
  @result_response.should have_at_least(1).items, "No licensed products found!"

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

  @result_response.each do |licensed_product|
    org_ids.should include(licensed_product.organizationId)
    org_ids.should include(licensed_product.licensedOrganizationId)
  end
end

Then /^I will get an error saying (.*)$/ do |error_msg|
  @result_response.should be_a(Savon::SOAP::Fault)

  fault_message = @result_response.to_s
  fault_message.should match(/#{error_msg}/)
end
