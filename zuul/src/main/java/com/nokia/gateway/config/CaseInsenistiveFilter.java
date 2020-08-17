package com.nokia.gateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author ：yww
 * @date ：Created in 2020/04/05 9:45
 */

public class CaseInsenistiveFilter extends ZuulFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(CaseInsenistiveFilter.class);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        logger.info(requestURI);

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request){
            @Override
            public String getRequestURI() {
                if (requestURI.contains("/")) {
                    String[] urls = requestURI.split("/");
                    if (urls.length >= 1) {
                        String serverName = urls[1];
                        String lowCase = serverName.toLowerCase();
                        String url = requestURI.replace(serverName,lowCase);
                        logger.info("send url : [{}]", url);
                        return url;
                    }
                }
                return requestURI;
            }
        };

        ctx.setRequest(requestWrapper);

        return null;
    }
}