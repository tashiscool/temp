# wrapper class around GetLicensedProductV2 web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class LicensedProductServiceClientV2
  attr_writer :request, :request_type, :client

  def initialize(savon_client)
    @client = savon_client
  end

  # type is a symbol short hand for the actual request type to generate
  # ex. :create for a Create*Request hash, :update for an Update*Request hash
  # this function generates the skeleton of a request, but note that not all required
  # data will be populated so check the xml schema and matching setters to fill in remaining data
  def gen_skeleton_request(type)
    @request = {}
    @request_type = type
    case @request_type
      when :get
        @request = {
          :get_licensed_product => {
            :organization_id => nil,
            :qualifying_license_pool => nil
          }
        }
    end
  end

  def set_org_id(org_id)
    case @request_type
      when :get
        @request[:get_licensed_product][:organization_id] = org_id
    end
  end

# <simpleType name="QualifyingLicensePool">
#		<restriction base="xsd:string">
#			<enumeration value="RootOnly"/>
#			<enumeration value="RootAndParents"/>
#			<enumeration value="AllInHierarchy" />
#		</restriction>
#	</simpleType>
  def set_qualifying_license_pool(qualifying_license_pool = "AllInHierarchy")
    case @request_type
      when :get
        @request[:get_licensed_product][:qualifying_license_pool] = qualifying_license_pool
    end
  end

  # execute the request and return the response hash
  def exec
    service_function = nil
    case @request_type
      when :get
        service_function = :get_licensed_product_request_element
    end

    request = @request

    @client.request service_function do
      soap.body = request
      soap.namespaces["xmlns"] = "http://licensedproduct.rws.pearson.com/doc/V2/"
    end
  end

end
