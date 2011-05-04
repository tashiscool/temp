Given /^I have an organization hierarchy with (a grandparent,|) (a parent and|) ([1-9]) child organizations?( without licensed products|)$/ do |grandparent, parent, child_count, without_orders|
  org_client = OrganizationServiceClient.new $service_clients[:OrganizationLifeCycle]

  unless without_orders.nil? or without_orders.empty?
    # generate fresh org heirarchy, and make sure to set the flag to ignore OrderProcessing service calls
    unless $already_created_orderless_orgs
      $already_ordered = true
      $child_org_ids.clear
      $parent_org_id = nil
      $grandparent_org_id = nil
      $already_created_orderless_orgs = true
    end
  end

  if child_count.to_i > 0 and $child_org_ids.empty?
    child_count.to_i.times do |i|
      org_client.gen_skeleton_request :create
      org_client.set_org_name "#{$active_us} AT Child Org \##{i} " + UUID.generate
      org_client.set_org_type "School"
      org_client.add_org_display_group "K-12"
      org_client.add_identifier "Pegasus Id", "#{$active_us}_Child_#{i}_PegasusId"
      response = org_client.exec
      response.http_error?.should be_false, response.http_error
      response.soap_fault?.should be_false, response.soap_fault
      $child_org_ids << response[:create_organization_response][:service_response_type][:return_value]
    end
  end

  if not parent.nil? and $parent_org_id.nil?
    org_client.gen_skeleton_request :create
    org_client.set_org_name "#{$active_us} AT Parent Org " + UUID.generate
    org_client.set_org_type "School"
    org_client.add_org_display_group "K-12"
    org_client.add_identifier "Pegasus Id", "#{$active_us}_Parent_PegasusId"
    response = org_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $parent_org_id = response[:create_organization_response][:service_response_type][:return_value]

  # add multiple child orgs... since we use Hash objects right now we have to do this one at a time due to
  # the contract's use of attributes
    $child_org_ids.each do |child_org_id|
      org_client.gen_skeleton_request :create_update_relation
      org_client.add_related_org child_org_id, "C", $parent_org_id
      response = org_client.exec
      response.http_error?.should be_false, response.http_error
      response.soap_fault?.should be_false, response.soap_fault
    end
  end

  if not grandparent.nil? and $grandparent_org_id.nil?
    org_client.gen_skeleton_request :create
    org_client.set_org_name "#{$active_us} AT Grandparent Org " + UUID.generate
    org_client.set_org_type "School"
    org_client.add_org_display_group "K-12"
    org_client.add_related_org $parent_org_id
    org_client.add_identifier "Pegasus Id", "#{$active_us}_Grandparent_PegasusId"
    response = org_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $grandparent_org_id = response[:create_organization_response][:service_response_type][:return_value]
  end
end

Given /^I have an Id to an Organization that does not exist$/ do
  $invalid_org_id = "nosuchorg"
end
