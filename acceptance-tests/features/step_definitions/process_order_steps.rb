# hardcoded string, not sure if there is a reasonable way to genericize this step
Given /^I have an ordered license pool for each organization for the 'InstitutionalLicensing' product$/ do
  unless $already_ordered
    orgs = []
    orgs << $grandparent_org_id << $parent_org_id
    orgs += $child_org_ids

    orgs.each do |org_id|
      order = OrderProcessing::ProcessOrderRequest.new
      order.xmlattr_SourceSystem = "http://idpdev.pearsoncmg.com/synapse"
      order.xmlattr_SubmittedBy = "Acceptance Tests"
      order.order = OrderProcessing::OrderType.new
      order.order.requestType = OrderProcessing::RequestTypeType::New
      order.order.orderType = OrderProcessing::OrderTypeType::InstitutionalLicense
      order.order.orderSourceSystem = OrderProcessing::OrderSourceSystemType::SapCg
      order.order.orderOrgId = org_id
      order.order.orderTotal = 1
      order.order.iSOCurrencyCode = OrderProcessing::ISOCurrencyCodeType::USD
      order.order.extOrderDate = Time.now
      order.order.orderLineItems = OrderProcessing::ProcessOrderLineItemsType.new
      order.order.orderStatus = OrderProcessing::OrderStatusCodeType::Created

      order_item = OrderProcessing::ProcessOrderLineType.new
      order_item.productEntityID = $product_entity_id
      order_item.orderedISBN = "0123456789123"
      order_item.quantity = 100
      order_item.returnQuantity = 100
      order_item.lineItemTotal = 100
      order_item.notes = "#{$active_us}_AT_Order_Line_Item_for_#{$product_entity_id}"
      order_item.lineItemStatus = OrderProcessing::OrderStatusCodeType::Created
      order_item.licensePoolInfo = OrderProcessing::LicensePoolInfoType.new
      order_item.licensePoolInfo.type = "Student seat based licensing"
      order_item.licensePoolInfo.startDate = Time.now
      order_item.licensePoolInfo.endDate = Time.now + 60*60*24*2000
      order_item.licensePoolInfo.denyNewSubscription = OrderProcessing::DenyNewSubscriptionType::C_0

      order.order.orderLineItems << order_item

      response = $service_clients[:OrderProcessing].request order
      response.should_not be_a(Savon::SOAP::Fault)
      response.serviceResponseType.statusCode.should == OrderProcessing::StatusCodeType::SUCCESS
    end
    $already_ordered = true
  end
end
