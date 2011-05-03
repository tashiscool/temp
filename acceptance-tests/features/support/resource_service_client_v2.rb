# wrapper class around ResourceLifeCycle web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class ResourceServiceClientV2
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
      when :create
        @request = {
          :resource => {
            :name => "#{$active_us} AT Resource V2 " + UUID.generate,
            :description => "#{$active_us} AT Resource V2",
            :auth_context_entity_id => 1
          }
        }
#      when :update
#      when :delete
#      when :get
    end
  end

  # execute the request and return the response hash
  def exec
    service_function = nil
    case @request_type
      when :create
        service_function = :create_resource_request
    end

    request = @request

    case @request_type
      when :create
        @client.request service_function, "CreatedBy" => "Acceptance Tests" do
          soap.body = request
          soap.namespaces["xmlns"] = "http://resource.rws.pearson.com/doc/V2/"
        end
      else
        @client.request service_function do
          soap.body = request
          soap.namespaces["xmlns"] = "http://resource.rws.pearson.com/doc/V2/"
        end
    end
  end

end
