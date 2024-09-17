/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.maozi.oauth.client.domain;

import com.alibaba.nacos.shaded.com.google.common.collect.Sets;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.maozi.base.AbstractBaseNameDomain;
import com.maozi.common.BaseCommon;
import com.maozi.oauth.client.enums.AuthType;
import com.maozi.oauth.client.handler.AuthTypesHandler;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * 客户端
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@TableName(value = "oauth_client_details",autoResultMap = true)
public class ClientDo extends AbstractBaseNameDomain implements ClientDetails {

	/**
	 * 客户端Id
	 */
	private String clientId;

	@TableField(typeHandler = AuthTypesHandler.class)
	private Set<AuthType> authTypes;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(typeHandler = JacksonTypeHandler.class)
	private Set<String> resourceIds;

	/**
	 * 客户端密钥
	 */
	private String clientSecret;

	/**
	 * 授权范围
	 */
	@TableField(typeHandler = JacksonTypeHandler.class)
	private Set<String> scope;

	/**
	 * 重定向地址
	 */
	@TableField(typeHandler = JacksonTypeHandler.class)
	private Set<String> registeredRedirectUri;

	/**
	 * 权限标识
	 */
	@TableField(typeHandler = JacksonTypeHandler.class)
	private List<GrantedAuthority> authorities;

	/**
	 * 授权令牌有效期 秒
	 */
	private Integer accessTokenValiditySeconds;

	/**
	 * 刷新令牌有效期 秒
	 */
	private Integer refreshTokenValiditySeconds;

	@TableField(typeHandler = JacksonTypeHandler.class)
	private Map<String, Object> additionalInformation;

	@TableField(typeHandler = JacksonTypeHandler.class)
	private Set<String> autoapprove;

	@Override
	public boolean isSecretRequired() {
		return this.clientSecret != null;
	}

	@Override
	public boolean isScoped() {
		return this.scope != null && !this.scope.isEmpty();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		
		if (autoapprove == null) {
			return false;
		}
		
		for (String auto : autoapprove) {
			if ("true".equals(auto) || scope.matches(auto)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public Set<String> getAuthorizedGrantTypes(){
		
		if(BaseCommon.collectionIsEmpty(authTypes)) {
			return null;
		}
		
		Set<String> datas = Sets.newHashSet();
		
		for(AuthType authType : authTypes) {
			datas.add(authType.getDesc());
		}
		
		return datas;
				
	}
	
}
