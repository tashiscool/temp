# wrapper class around OrganizationLifeCycle web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class ProductServiceClientV2
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
          :product => {
            :internal_name => "#{$active_us} AT Product V2 " + UUID.generate,
            :internal_description => "#{$active_us} AT Product V2",
            :notes => "#{$active_us} AT Product - This is a dummy product created for an acceptance test",
            :approval_status_code => "Approved",
            :target_live_date => Time.now,  # today!
            :target_retirement_date => Time.now + 60*60*24*2000,  # some date in the future
            :organization_id => nil,
            :status_code => "Active",
            :login_url => "http://login.pst.com/login.do?u=v",
            :image_url => "http://image.pst.com/image.do?i=image",
            :identifiers => {
              :product_identifier => []
            },
            :display_information => {
              :display_info => []
            },
            :business_model => nil,
            :contact => []
          }
        }
#      when :update
#      when :delete
      when :get_details
        @request = {
            :product_id => nil
        }
      when :add_resource
        @request = {
            :product_id => nil,
            :resources => {
                :resource => []
            }
        }
    end
  end

  def add_identifier(id_type = "ISBN13", id_master_type = "ThreeSixty", id_value = "0123456789123")
    case @request_type
      when :create
        @request[:product][:identifiers][:product_identifier] << {
          :identifier_type => id_type,
          :product_master_type => id_master_type,
          :identifier_value => id_value
        }
    end
  end

  def add_display_info(name = "#{$active_us} AT Product", is_default = true, locale = "en_US")
    case @request_type
      when :create
        @request[:product][:display_information][:display_info] << {
          :name => name,
          :is_default=> is_default,
          :locale => locale
        }
    end
  end

  def set_business_model(business_model)
    case @request_type
      when :create
        @request[:product][:business_model] = business_model
    end
  end

  def add_contact(contact_user_id, contact_type = "Owner")
    case @request_type
      when :create
        @request[:product][:contact] << {
          :type => contact_type,
          :user_id => contact_user_id
        }
    end
  end

  def set_org_id(org_id)
    case @request_type
      when :create
        @request[:product][:organization_id] = org_id
    end
  end

  def set_product_id(product_id)
    case @request_type
      when :get_details, :add_resource
        @request[:product_id] = product_id
    end
  end

  def add_resource(resource_id)
    case @request_type
      when :add_resource
        @request[:resources][:resource] << {
            :resource_id => resource_id
        }
    end
  end

  # execute the request and return the response hash
  def exec
    service_function = nil
    case @request_type
      when :create
        service_function = :create_product_request
      when :get_details
        service_function = :get_product_details_request
      when :add_resource
        service_function = :add_resources_to_a_product_request
    end

    request = @request

    case @request_type
      when :create, :add_resource
        @client.request service_function, "CreatedBy" => "Acceptance Tests" do
          soap.body = request
          soap.namespaces["xmlns"] = "http://product.rws.pearson.com/doc/V2/"
        end
      else
        @client.request service_function do
          soap.body = request
          soap.namespaces["xmlns"] = "http://product.rws.pearson.com/doc/V2/"
        end
    end
  end

end
