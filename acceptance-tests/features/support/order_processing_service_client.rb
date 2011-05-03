# wrapper class around OrderProcessing web service client which encapsulates
# hash request generation and request execution/response validation
# class holds reference to request hash being generated but returns the response
# only one request is generated/handled at a time
class OrderProcessingServiceClient
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
      # special case since this service is not a CRUD service per-se
      when :process_order
        @request = {
          :order => {
            :request_type => "New",
            :order_type => "institutional license",
            :order_source_system => "sap-cg",
            :order_org_id => nil,
            "ISOCurrencyCode" => "USD",
            :order_total => 1,
            :notes => "#{$active_us}_AT_Order_" + UUID.generate,
            :ext_order_date => Time.now,
            :order_status => "created",
            :order_line_items => {
                :order_line => []
            }
          }
        }
#      when :update
#      when :delete
#      when :get
    end
  end

  def set_order_org_id(org_id)
    case @request_type
      when :process_order
        @request[:order][:order_org_id] = org_id
    end
  end


#	<xsd:complexType name="OrderLineType">
#		<xsd:sequence>
#			<xsd:element maxOccurs="1" minOccurs="1" name="ProductEntityID" type="ord:ProductEntityIdType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="OrderedISBN" type="ord:OrderedISBNType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="ReferenceOrderLineId" type="ord:OrderIdentifierType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="ReturnQuantity" type="xsd:int"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="ExtOrderLineId" type="ord:ExtOrderIdentifierType"/>
#			<xsd:element maxOccurs="1" minOccurs="1" name="Quantity" type="xsd:int"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="LineItemTotal" type="xsd:float"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="Notes" type="ord:NotesType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="LineItemStatus" type="ord:OrderStatusCodeType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="StatusFeedback" type="ord:FeedbackStatusCodeType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="LicensePoolInfo" type="ord:LicensePoolInfoType"/>
#		</xsd:sequence>
#	</xsd:complexType>
#
#	<xsd:complexType name="LicensePoolInfoType">
#		<xsd:sequence>
#			<xsd:element maxOccurs="1" minOccurs="1" name="Type" type="ord:LicenseType"/>
#			<xsd:element maxOccurs="1" minOccurs="1" name="StartDate" type="xsd:dateTime"/>
#			<xsd:element maxOccurs="1" minOccurs="1" name="EndDate" type="xsd:dateTime"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="DenyNewSubscription" type="ord:DenyNewSubscriptionType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="LicensePoolId" type="ord:LicensePoolIdType"/>
#			<xsd:element maxOccurs="1" minOccurs="0" name="OrderLineItemId" type="ord:OrderLineItemIdType"/>
#		</xsd:sequence>
#	</xsd:complexType>
  def add_order_line_item(product_entity_id, isbn = "0123456789123", return_quantity = 100, quantity = 100, line_item_total = 100, license_pool_hash = {})
    case @request_type
      when :process_order
        @request[:order][:order_line_items][:order_line] << {
          "ProductEntityID" => product_entity_id,
          "OrderedISBN" => isbn,
          :return_quantity => return_quantity,
          :quantity => quantity,
          :line_item_total => line_item_total,
          :notes => "#{$active_us}_AT_Order_Line_Item_for_#{product_entity_id}",
          :line_item_status => "created",
          :license_pool_info => {
            :type => ((license_pool_hash.has_key? :type) ? license_pool_hash[:type] : "Student seat based licensing"),
            :start_date => ((license_pool_hash.has_key? :start_date) ? license_pool_hash[:start_date] : Time.now),
            :end_date => ((license_pool_hash.has_key? :end_date) ? license_pool_hash[:end_date] : Time.now + 60*60*24*2000),
            :deny_new_subscription => ((license_pool_hash.has_key? :deny_new_subscription) ? license_pool_hash[:deny_new_subscription] : 0)
          }
        }
    end
  end

  # execute the request and return the response hash
  def exec
    service_function = nil
    case @request_type
      when :process_order
        service_function = :process_order_request
    end

    request = @request

    case @request_type
      when :process_order
#        ProcessOrderRequest SubmittedBy="RUMBA_Internal_${=(System.currentTimeMillis())}"
        #SourceSystem="http://idpdev.pearsoncmg.com/synapse"
        @client.request service_function, "SubmittedBy" => "Acceptance Tests", "SourceSystem" => "http://idpdev.pearsoncmg.com/synapse" do
          soap.body = request
          soap.namespaces["xmlns"] = "http://order.rws.pearson.com/doc/2009/02/09/"
        end
      else
        @client.request service_function do
          soap.body = request
          soap.namespaces["xmlns"] = "http://order.rws.pearson.com/doc/2009/02/09/"
        end
    end
  end

end
