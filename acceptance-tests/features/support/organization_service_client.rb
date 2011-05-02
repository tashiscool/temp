# wrapper class around OrganizationLifeCycle web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class OrganizationServiceClient
  attr_writer :request, :request_type, :client

  def initialize(host_and_port)
    @client = Savon::Client.new {wsdl.document = "http://#{host_and_port}/OrganizationLifeCycle/services/2009/07/01/OrganizationLifeCycle_2009_07_01.wsdl"}
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
          :create_organization_type => {
            :name => nil,
            :organization_type => nil,
            :organization_display_group_info => [],
            :identifiers => [],
#              :identifier => {
#                :identifier_type => "Mdr Id",
#                :identifier_value => "#{user_story_name}_AT_MdrId"
#              }
#            },
            :source_system => "https://idpdev.pearsoncmg.com/synapse"
          }
        }
#      when :update
#      when :delete
#      when :get
    end
  end

  def set_org_name(name)
    case @request_type
      when :create
        @request[:create_organization_type][:name] = name
    end
  end

  # type is "School" or "Unknown" for latest contract
  def set_org_type(type = "School")
    case @request_type
      when :create
        @request[:create_organization_type][:type] = type
    end
  end

#	<xsd:simpleType name="DisplayGroupType">
#		<xsd:restriction base="xsd:string">
#			<xsd:enumeration value="K-12" />
#			<xsd:enumeration value="Pearson Business Unit" />
#			<xsd:enumeration value="Internal Use Only" />
#			<!-- Added on 11/09/2010 -->
#			<xsd:enumeration value="HE"/>
#			<!-- Added on 11/09/2010 -->
#			<xsd:enumeration value="Not for Display"/>
#		</xsd:restriction>
#	</xsd:simpleType>
  def add_org_display_group(group_type = "K-12")
    case @request_type
      when :create
        @request[:create_organization_type][:organization_display_group_info] << {
            :group_type => group_type
        }
    end
  end

#	<xsd:simpleType name="IdTypeType">
#		<xsd:restriction base="xsd:string">
#			<xsd:enumeration value="Mdr Id" />
#			<xsd:enumeration value="Sap Customer Id" />
#			<xsd:enumeration value="Pegasus Id"/>
#			<xsd:enumeration value="Socrates Id"/>
#		</xsd:restriction>
#	</xsd:simpleType>
  def add_identifier(id_type, id_value)
    case @request_type
      when :create
        @request[:create_organization_type][:identifiers] << {
            :identifier_type => id_type,
            :identifier_value => id_value
        }
    end
  end

#	<xsd:simpleType name="RelationshipTypeCodeType">
#		<xsd:restriction base="xsd:string">
#			<!-- Child -->
#			<xsd:enumeration value="C" />
#		</xsd:restriction>
#	</xsd:simpleType>
  def add_related_org(related_org_id, relation_type = "C")
    case @request_type
      when :create
        # first move the identifiers, if we need to, for proper ordering in the xml
        unless @request[:create_organization_type].has_key? :related_organization
          identifiers = @request[:create_organization_type].delete :identifiers
          @request[:create_organization_type][:related_organization] = []
          @request[:create_organization_type][:identifiers] = identifiers
        end
        @request[:create_organization_type][:related_organization] << {
            :related_organization_id => related_org_id,
            :relation_type => relation_type
        }
    end
  end

  # execute the request and return the response hash
  def exec
    case @request_type
      when :create
        @client.request :create_organization_request do
          soap.body = request
          soap.namespaces["xmlns"] = "http://organization.rws.pearson.com/doc/2009/07/01/"
        end
    end
  end
end
