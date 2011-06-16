Given /^I have an organization hierarchy with (a grandparent,|) (a parent and|) ([1-9]) child organizations?( without licensed products|)$/ do |grandparent, parent, child_count, without_orders|

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
      create_org = Organization_2009_07_01::CreateOrganizationRequest.new
      create_org.createOrganizationType = Organization_2009_07_01::CreateOrganizationType.new
      create_org.createOrganizationType.name = "#{$active_us} AT Child Org \##{i} " + UUID.generate
      create_org.createOrganizationType.organizationType = Organization_2009_07_01::OrganizationTypeType::School
      create_org.createOrganizationType.organizationDisplayGroupInfo = Organization_2009_07_01::DisplayGroupInfoType.new
      create_org.createOrganizationType.organizationDisplayGroupInfo.groupType = Organization_2009_07_01::DisplayGroupType::K12
      create_org.createOrganizationType.identifiers = Organization_2009_07_01::IdentifiersType.new
      create_org.createOrganizationType.identifiers << Organization_2009_07_01::IdentifierType.new(Organization_2009_07_01::IdTypeType::PegasusId, "#{$active_us}_Child_#{i}_PegasusId")
      create_org.createOrganizationType.sourceSystem = "https://idpdev.pearsoncmg.com/synapse"

      response = $service_clients[:OrganizationLifeCycle].request create_org
      response.should_not be_a(Savon::SOAP::Fault)
      $child_org_ids << response.serviceResponseType.returnValue
    end
  end

  if not parent.nil? and $parent_org_id.nil?
    create_org = Organization_2009_07_01::CreateOrganizationRequest.new
    create_org.createOrganizationType = Organization_2009_07_01::CreateOrganizationType.new
    create_org.createOrganizationType.name = "#{$active_us} AT Parent Org " + UUID.generate
    create_org.createOrganizationType.organizationType = Organization_2009_07_01::OrganizationTypeType::School
    create_org.createOrganizationType.organizationDisplayGroupInfo = Organization_2009_07_01::DisplayGroupInfoType.new
    create_org.createOrganizationType.organizationDisplayGroupInfo.groupType = Organization_2009_07_01::DisplayGroupType::K12
    create_org.createOrganizationType.identifiers = Organization_2009_07_01::IdentifiersType.new
    create_org.createOrganizationType.identifiers << Organization_2009_07_01::IdentifierType.new(Organization_2009_07_01::IdTypeType::PegasusId, "#{$active_us}_Parent_PegasusId")
      create_org.createOrganizationType.sourceSystem = "https://idpdev.pearsoncmg.com/synapse"

    response = $service_clients[:OrganizationLifeCycle].request create_org
    response.should_not be_a(Savon::SOAP::Fault)
    
    $parent_org_id = response.serviceResponseType.returnValue

  # add multiple child orgs... since we use Hash objects right now we have to do this one at a time due to
  # the contract's use of attributes
    $child_org_ids.each do |child_org_id|
      create_org_relation = Organization_2009_07_01::CreateUpdateOrganizationRelationRequest.new
      relation = Organization_2009_07_01::OrganizationRelation.new
      relation.xmlattr_OrganizationId = $parent_org_id
      relation.xmlattr_RelatedOrganizationId = child_org_id
      relation.xmlattr_RelationTypeCode = Organization_2009_07_01::RelationshipTypeCodeType::C
      create_org_relation << relation

      response = $service_clients[:OrganizationLifeCycle].request create_org_relation
      response.should_not be_a(Savon::SOAP::Fault)
    end
  end

  if not grandparent.nil? and $grandparent_org_id.nil?
    create_org = Organization_2009_07_01::CreateOrganizationRequest.new
    create_org.createOrganizationType = Organization_2009_07_01::CreateOrganizationType.new
    create_org.createOrganizationType.name = "#{$active_us} AT Grandparent Org " + UUID.generate
    create_org.createOrganizationType.organizationType = Organization_2009_07_01::OrganizationTypeType::School
    create_org.createOrganizationType.organizationDisplayGroupInfo = Organization_2009_07_01::DisplayGroupInfoType.new
    create_org.createOrganizationType.organizationDisplayGroupInfo.groupType = Organization_2009_07_01::DisplayGroupType::K12
    create_org.createOrganizationType.identifiers = Organization_2009_07_01::IdentifiersType.new
    create_org.createOrganizationType.identifiers << Organization_2009_07_01::IdentifierType.new(Organization_2009_07_01::IdTypeType::PegasusId, "#{$active_us}_Grandparent_PegasusId")
    create_org.createOrganizationType.relatedOrganization = Organization_2009_07_01::OrganizationRelationType.new
    create_org.createOrganizationType.relatedOrganization.xmlattr_RelatedOrganizationId = $parent_org_id
    create_org.createOrganizationType.relatedOrganization.xmlattr_RelationType = Organization_2009_07_01::RelationshipTypeCodeType::C
    create_org.createOrganizationType.sourceSystem = "https://idpdev.pearsoncmg.com/synapse"

    response = $service_clients[:OrganizationLifeCycle].request create_org
    response.should_not be_a(Savon::SOAP::Fault)

    $grandparent_org_id = response.serviceResponseType.returnValue
  end
end

Given /^I have an Id to an Organization that does not exist$/ do
  $invalid_org_id = "nosuchorg"
end
