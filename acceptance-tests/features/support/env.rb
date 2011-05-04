# common libraries for all step definitions
require 'savon'
require 'rspec/expectations'
require 'uuid'

UUID.state_file = false

Savon.configure do |config|
  # set to use SOAP version 1.2
  config.soap_version = 2
  # disable feature to raise errors for SOAPFaults and HTTP errors (we handle those separately)
  config.raise_errors = false
  config.log_level = :info
end

# :get_something_request -> <GetSomethingRequest>
Gyoku.convert_symbols_to :camelcase

entity_endpoints_url = "idpdev.pearsoncmg.com"
composite_endpoint_url = "dev.osb.rumba.pearsoncmg.com"

# initialize service client globals
$service_clients = {}
$service_clients[:OrganizationLifeCycle] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/OrganizationLifeCycle/services/2009/07/01/OrganizationLifeCycle_2009_07_01.wsdl"}
$service_clients[:UserLifeCycleV3] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/UserLifeCycle/services/V3/UserLifeCycleV3.wsdl"}
$service_clients[:ProductLifeCycleV2] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/ProductLifeCycle/product/services/V2/ProductLifeCycleV2.wsdl"}
$service_clients[:ResourceLifeCycleV2] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/ProductLifeCycle/resource/services/V2/ResourceLifeCycle_V2.wsdl"}
$service_clients[:OrderProcessing] = Savon::Client.new {wsdl.document = "http://#{composite_endpoint_url}/ProcessOrderService/ProcessOrderService?wsdl"}
$service_clients[:GetLicensedProductV2] = Savon::Client.new {wsdl.document = "http://#{entity_endpoints_url}/LicensePoolLifeCycle/licensedproduct/services/V2/LicensedProduct.wsdl"}

$service_clients[:OrderProcessing].wsdl.endpoint = "http://#{composite_endpoint_url}/ProcessOrderService"

$dummy_org_id_for_product = nil
$product_id = nil
$product_entity_id = nil
$resource_id = nil
$grandparent_org_id = nil
$parent_org_id = nil
$invalid_org_id = nil
$child_org_ids = []
$dummy_user_id = nil
$active_us = nil
$already_added = false
$already_ordered = false
$already_created_orderless_orgs = false


Before do
  @result_response = nil
end

