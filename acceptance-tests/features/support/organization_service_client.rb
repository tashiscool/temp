# wrapper class around OrganizationLifeCycle web service client which encapsulates
# hash request generation and request execution/response validation
class OrganizationServiceClient
  attr_writer :request, :response, :client

  def initialize(wsdl_url)
    @client = Savon::Client.new {wsdl.document = wsdl_url}
  end

  def gen_skeleton_request(type)
    case type
      when :create
#      when :update
#      when :delete
#      when :get
    end
  end
end