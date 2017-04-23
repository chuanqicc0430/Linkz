package cn.net.cvtt.http.container.core.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import cn.net.cvtt.http.container.core.filter.impl.BlackHostFilter;

public class NettyFilterFactory {
    
    public static FilterChain createFilter() throws ServletException{
        BlackHostFilter hostFilter = new BlackHostFilter();
        
        hostFilter.init(null);
        
        List<BaseNettyFilter> filters = new ArrayList<BaseNettyFilter>();
        filters.add(hostFilter);
        
        FilterChain filterChain = new NettyFilterChain(filters);
        return filterChain;
    }
}
