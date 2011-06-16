Given /^I have created a valid '([a-zA-Z]*)' enabled product$/ do |business_model|
  if $dummy_user_id.nil?
    create_user = UserLifeCycleV3::CreateUserRequest.new
    create_user.xmlattr_CreatedBy = "Self-reg"
    user = UserLifeCycleV3::CreateUser.new
    create_user.user = user
    user.firstName = "Test"
    user.lastName = "Testerson"
    user.displayName = "#{$active_us} AT User"
    user.gender = "Unspecified"
    user.encryptionType = UserLifeCycleV3::EncryptionTypeType::SHA
    user.authenticationType = UserLifeCycleV3::AuthenticationTypeType::SSO
    user.businessRuleSet = UserLifeCycleV3::BusinessRuleSet::HE
    user.emailInfo = UserLifeCycleV3::EmailInfoType.new nil, true, "fake.person@pearsoncmg.com"

    response = $service_clients[:UserLifeCycleV3].request create_user
    response.should_not be_a(Savon::SOAP::Fault)
    $dummy_user_id = response.serviceResponseType.returnValue
  end

  if $dummy_org_id_for_product.nil?
    create_org = Organization_2009_07_01::CreateOrganizationRequest.new
    create_org.createOrganizationType = Organization_2009_07_01::CreateOrganizationType.new
    create_org.createOrganizationType.name = "#{$active_us} AT Org for Dummy Product " + UUID.generate
    create_org.createOrganizationType.organizationType = Organization_2009_07_01::OrganizationTypeType::Unknown
    create_org.createOrganizationType.organizationDisplayGroupInfo = Organization_2009_07_01::DisplayGroupInfoType.new
    create_org.createOrganizationType.organizationDisplayGroupInfo.groupType = Organization_2009_07_01::DisplayGroupType::PearsonBusinessUnit
    create_org.createOrganizationType.identifiers = Organization_2009_07_01::IdentifiersType.new
    create_org.createOrganizationType.identifiers << Organization_2009_07_01::IdentifierType.new(Organization_2009_07_01::IdTypeType::PegasusId, "#{$active_us}_Dummy_PegasusId")
    create_org.createOrganizationType.sourceSystem = "https://idpdev.pearsoncmg.com/synapse"

    response = $service_clients[:OrganizationLifeCycle].request create_org
    response.should_not be_a(Savon::SOAP::Fault)

    $dummy_org_id_for_product = response.serviceResponseType.returnValue
  end

  if $product_entity_id.nil?
    create_product = ProductLifeCycleV2::CreateProductRequest.new
    create_product.xmlattr_CreatedBy = "Acceptance Tests"
    create_product.product = ProductLifeCycleV2::CreateProductType.new
    create_product.product.internalName = "#{$active_us} AT Product V2 " + UUID.generate
    create_product.product.internalDescription = "#{$active_us} AT Product V2"
    create_product.product.notes = "#{$active_us} AT Product - This is a dummy product created for an acceptance test"
    create_product.product.approvalStatusCode = ProductLifeCycleV2::ApprovalStatusCodeType::Approved
    create_product.product.targetLiveDate = Time.now  # today!
    create_product.product.targetRetirementDate = Time.now + 60*60*24*2000  # some date in the future
    create_product.product.organizationId = $dummy_org_id_for_product
    create_product.product.statusCode = ProductLifeCycleV2::ProductStatusCodeType::Active
    create_product.product.loginUrl = "http://login.pst.com/login.do?u=v"
    create_product.product.imageUrl = "http://image.pst.com/image.do?i=image"
    create_product.product.businessModel = eval "ProductLifeCycleV2::BusinessModelType::#{business_model}"
    
    create_product.product.identifiers = ProductLifeCycleV2::CreateProductIdentifiersType.new
    identifier = ProductLifeCycleV2::BaseProductIdentifierType.new
    identifier.identifierType = ProductLifeCycleV2::IdentifierTypeType::ISBN13
    identifier.productMasterType = ProductLifeCycleV2::ProductMasterType::ThreeSixty
    identifier.identifierValue = "0123456789123"
    create_product.product.identifiers << identifier

    create_product.product.displayInformation = ProductLifeCycleV2::CreateDisplayInfosType.new
    display_info = ProductLifeCycleV2::BaseDisplayInfoType.new "#{$active_us} AT Product", nil, nil, true, ProductLifeCycleV2::LocaleType::En_US
    create_product.product.displayInformation << display_info

    create_product.product.contact = ProductLifeCycleV2::BaseProductContactType.new "Owner", $dummy_user_id

    response = $service_clients[:ProductLifeCycleV2].request create_product
    response.should_not be_a(Savon::SOAP::Fault)

    $product_id = response.serviceResponseType.returnValue

    get_details = ProductLifeCycleV2::GetProductDetailsRequest.new $product_id
    response = $service_clients[:ProductLifeCycleV2].request get_details
    response.should_not be_a(Savon::SOAP::Fault)

    $product_entity_id = response.product.productEntityId
  end
end

Given /^I added a resource to the '([a-zA-Z]*)' enabled product$/ do |business_model|
  unless $already_added
    add_resource = ProductLifeCycleV2::AddResourcesToAProductRequest.new $product_id, ProductLifeCycleV2::ResourcesForProductType.new
    add_resource.xmlattr_CreatedBy = "Acceptance Tests"
    add_resource.resources << ProductLifeCycleV2::ResourceForProductType.new($resource_id)

    response = $service_clients[:ProductLifeCycleV2].request add_resource
    response.should_not be_a(Savon::SOAP::Fault)

    $already_added = true
  end
end
