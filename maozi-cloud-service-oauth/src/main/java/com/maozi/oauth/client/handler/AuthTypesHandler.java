package com.maozi.oauth.client.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import com.maozi.base.BaseEnum;
import com.maozi.common.BaseCommon;
import com.maozi.db.handler.ListTypeHandler;
import com.maozi.oauth.client.enums.AuthType;
import com.maozi.utils.MapperUtils;
import java.util.Set;

public class AuthTypesHandler extends ListTypeHandler<Set<AuthType>>{
	
	private TypeReference typeReference = new TypeReference<Set<AuthType>>() {};
	
	@Override
    protected Object parse(String json) {
		
        try {return MapperUtils.getObjectMapper().readValue(json, typeReference);} catch (Exception e) {
        	
            BaseCommon.throwSystemError(e);
            
            return null;
            
        }
        
    }
	
	@Override
    protected String toJson(Object obj) {
    	
		Set<BaseEnum> iEnums =  (Set<BaseEnum>) obj;
		
		Set<Integer> datas = Sets.newHashSet();
		
		for(BaseEnum iEnum : iEnums) {
			datas.add(iEnum.getValue());
		}
		
		try {return MapperUtils.getObjectMapper().writeValueAsString(datas);} catch (Exception e) {
			 
			BaseCommon.throwSystemError(e);
			
	        return null;
	        
        }
		
    }

}
