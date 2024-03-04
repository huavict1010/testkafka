package com.example.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Message {
	@JsonProperty(value = "tenant_id")
	private String tenantId;
	private List<DataItem> data;
	@JsonProperty(value = "agent_id")
	private String agentId;
	@JsonProperty(value = "sub_tenant_id")
	private String subTenantId;
	@JsonProperty(value = "app_id")
	private String appId;
}
