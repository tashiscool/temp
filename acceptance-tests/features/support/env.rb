# common libraries for all step definitions
require 'savon'
require 'rspec/expectations'
require 'uuid'
require 'organization_service_client'

UUID.state_file = false

Savon.configure do |config|
  # set to use SOAP version 1.2
  config.soap_version = 2
  # disable feature to raise errors for SOAPFaults and HTTP errors (we handle those separately)
  config.raise_errors = false
  config.log_level = :info
end


entity_endpoints_url = "rumba.test.pearsoncmg.com"
composite_endpoint_url = "dev.osb.rumba.pearsoncmg.com"

# initialize service client globals
$service_clients = {}
$service_clients[:OrganizationLifeCycle] = OrganizationServiceClient.new entity_endpoints_url
$service_clients[:ProductLifeCycleV1] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/ProductLifeCycle/product/services/ProductLifeCycle.wsdl"}
$service_clients[:ProductLifeCycleV2] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/ProductLifeCycle/product/services/V2/ProductLifeCycleV2.wsdl"}
$service_clients[:LicensePoolLifeCycle] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/LicensedProduct/services/ResourceLifeCycle.wsdl"}
#$grandparentOrgId = nil
$parentOrgId = nil
$child1OrgId = nil
$child2OrgId = nil
$productId = nil
$productEntityId = nil


Before do

@qualifyingLicensedPool =nil
 @getLicensedProductResponse = nil

end

