package com.ifootball.app.framework.content;

import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.HasCollection;
import com.ifootball.app.webservice.ServiceException;

import java.io.IOException;

public abstract class CBCollectionResolver<T> {
	
	public abstract HasCollection<T> query() throws IOException, ServiceException, BizException;
	
}