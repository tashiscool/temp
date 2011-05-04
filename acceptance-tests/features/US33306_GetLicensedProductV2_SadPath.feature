@US33306 @sad-path
Feature: GetLicensedProductV2 Sad Path
  As a CG administrator
  In order to see how many products are licensed by an organization
  I must receive a meaningful error if I try to request licensed products by organization id if invalid data is given
    or there is a problem with the data or services

  Background:
    Given I am evaluating US33306
      And I have an organization hierarchy with a grandparent, a parent and 2 child organizations without licensed products

  Scenario Outline: GetLicensedProductV2 with an invalid Organization Id
    Given I have an Id to an Organization that does not exist
    When I request all licensed products for the invalid organization with the qualifier '<QualifyingLicensePool>'
    Then I will get an error saying Invalid Organization Id

    Examples:
      |QualifyingLicensePool|
      |AllInHierarchy       |
      |RootOnly             |
      |RootAndParents       |

  Scenario Outline: GetLicensedProductV2 with an Organization with No License Pools
    When I request all licensed products for the parent organization with the qualifier '<QualifyingLicensePool>'
    Then I will get 0 licensed products

    Examples:
      |QualifyingLicensePool|
      |AllInHierarchy       |
      |RootOnly             |
      |RootAndParents       |
