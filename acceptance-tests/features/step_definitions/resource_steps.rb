Given /^I have created a valid resource$/ do
  if $resource_id.nil?
    create_resource = Resource_V2::CreateResourceRequest.new
    create_resource.xmlattr_CreatedBy = "Acceptance Tests"
    create_resource.resource = Resource_V2::CreateResourceType.new
    create_resource.resource.name = "#{$active_us} AT Resource V2 " + UUID.generate
    create_resource.resource.description = "#{$active_us} AT Resource V2"
    create_resource.resource.authContextEntityId = 1

    response = $service_clients[:ResourceLifeCycleV2].request create_resource
    response.should_not be_a(Savon::SOAP::Fault)
    $resource_id = response.serviceResponseType.returnValue
  end
end
