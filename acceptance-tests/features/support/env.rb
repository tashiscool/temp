# common libraries for all step definitions
require 'soap_client'
require 'rspec/expectations'
require 'uuid'
require 'Organization_2009_07_01_mapper'
require 'UserLifeCycleV3_mapper'
require 'ProductLifeCycleV2_mapper'
require 'Resource_V2_mapper'
require 'OrderProcessing_mapper'
require 'LicensedProductV2_mapper'

UUID.state_file = false

entity_endpoints_url = "idpdev.pearsoncmg.com"
composite_endpoint_url = "dev.osb.rumba.pearsoncmg.com"

# initialize service client globals
$service_clients = {}
$service_clients[:OrganizationLifeCycle] = SoapClient.new "http://#{entity_endpoints_url}/OrganizationLifeCycle/services/2009/07/01/OrganizationLifeCycle_2009_07_01.wsdl"
$service_clients[:UserLifeCycleV3] = SoapClient.new "http://#{entity_endpoints_url}/UserLifeCycle/services/V3/UserLifeCycleV3.wsdl"
$service_clients[:ProductLifeCycleV2] = SoapClient.new "http://#{entity_endpoints_url}/ProductLifeCycle/product/services/V2/ProductLifeCycleV2.wsdl"
$service_clients[:ResourceLifeCycleV2] = SoapClient.new "http://#{entity_endpoints_url}/ProductLifeCycle/resource/services/V2/ResourceLifeCycle_V2.wsdl"
$service_clients[:OrderProcessing] = SoapClient.new "http://#{composite_endpoint_url}/ProcessOrderService/ProcessOrderService?wsdl", "OrderProcessing"
$service_clients[:GetLicensedProductV2] = SoapClient.new "http://#{entity_endpoints_url}/LicensePoolLifeCycle/licensedproduct/services/V2/LicensedProductV2.wsdl"

$service_clients[:OrderProcessing].savon_client.wsdl.endpoint = "http://#{composite_endpoint_url}/ProcessOrderService"

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
