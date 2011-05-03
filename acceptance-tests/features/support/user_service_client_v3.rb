# wrapper class around UserLifeCycleV3 web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class UserServiceClientV3
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
          :user => {
            :first_name => "Test",
            :last_name => "Testerson",
            :display_name => "#{$active_us} AT User",
            :gender => "Unspecified",
            :encryption_type => "SHA",
            :authentication_type => "SSO",
            :business_rule_set => "HE",
            :email_info => {
              :is_primary => true,
              :email_address => "nicholas.lloyd@pearson.com"
            }
          }
        }
#      when :update
#      when :delete
#      when :get
    end
  end


  # execute the request and return the response hash
  def exec

    request = @request

    case @request_type
      when :create
        @client.request :create_user_request, "CreatedBy" => "Self-reg" do
          soap.body = request
          soap.namespaces["xmlns"] = "http://user.rws.pearson.com/doc/V3/"
        end
    end
  end

end
