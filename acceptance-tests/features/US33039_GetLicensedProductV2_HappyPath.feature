@US33039 @happy-path @wip
Feature: GetLicensedProductV2 Happy Path
  As a CG administrator
  In order to see how many products are licensed by an organization
  I must be able to successfully request all licensed products with a valid organization id

  Background:
    Given I am evaluating US33039
      And I have an organization hierarchy with a grandparent, a parent and 2 child organizations
      And I have created a valid 'InstitutionalLicensing' enabled product
      And I have created a valid resource
      And I added a resource to the 'InstitutionalLicensing' enabled product
      And I have an ordered license pool for each organization for the 'InstitutionalLicensing' product

  Scenario Outline: GetLicensedProduct successfully with different QualifyingLicensePools
    When I request all licensed products for the parent organization with the qualifier '<QualifyingLicensePool>'
    Then I will get '<Count>' licensed products
      And each licensed product will be the 'InstitutionalLicensing' product
      And and the oppropriate organizations for '<QualifyingLicensePool>' will be referenced

  Examples:
      |QualifyingLicensePool     |  Count    |
      |AllInHierarchy            |   4       |
      |RootOnly                  |   1       |
      |RootAndParents            |   2       |
