package com.bb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EncodingFilter implements Filter {

	private String encoding = null;
	private Logger log = LoggerFactory.getLogger(EncodingFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		log.info("encoding filter 실행");
		
		this.encoding = filterConfig.getInitParameter("utf-8");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if(this.encoding != null) {
			if(request.getCharacterEncoding() == null) {
				response.setCharacterEncoding(encoding);
				request.setCharacterEncoding(encoding);			
			}			
		}
		
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
