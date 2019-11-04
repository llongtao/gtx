package com.gtx.spring.filter;

import com.gtx.common.utils.CollectionUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
@Order(9999)
@Component("xidHolder")
public class XidHolder extends OncePerRequestFilter implements RequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(XidHolder.class);

    private static final String XID_KEY = "__gtx_xid";

    private static final ThreadLocal<XID> XID_THREAD_LOCAL = new InheritableThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String xid = httpServletRequest.getHeader(XID_KEY);
        boolean isNew = false;
        if (xid == null) {
            xid = UUID.randomUUID().toString();
            isNew = true;
        }
        XID_THREAD_LOCAL.set(new XID(xid, isNew));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        XID_THREAD_LOCAL.remove();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, Collection<String>> headers = requestTemplate.headers();
        Collection<String> values = headers.get(XidHolder.XID_KEY);
        String xid = null;
        if (CollectionUtils.isEmpty(values)) {
            xid = getXid();
            if (xid != null) {
                requestTemplate.header(XidHolder.XID_KEY, xid);
            }
        } else {
            for (String value : values) {
                xid = value;
            }
        }


        LOGGER.info(" >>>> feign xid: {}", xid);

    }

    public static String getXid() {
        XID xid = XID_THREAD_LOCAL.get();
        if (xid == null) {
            return null;
        }
        return xid.xid;
    }

    public static Boolean getIsNew() {
        XID xid = XID_THREAD_LOCAL.get();
        if (xid == null) {
            return null;
        }
        return xid.isNew;
    }

    public static class XID {
        private String xid;
        private boolean isNew;

        XID(String xid, boolean isNew) {
            this.xid = xid;
            this.isNew = isNew;
        }
    }
}
