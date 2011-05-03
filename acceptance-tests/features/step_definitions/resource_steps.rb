Given /^I have created a valid resource$/ do
  if $resource_id.nil?
    resource_client = ResourceServiceClientV2.new $service_clients[:ResourceLifeCycleV2]
    resource_client.gen_skeleton_request :create
    response = resource_client.exec
    response.http_error?.should be_false, response.http_error
    response.soap_fault?.should be_false, response.soap_fault
    $resource_id = response[:create_resource_response][:service_response_type][:return_value]
  end
end
