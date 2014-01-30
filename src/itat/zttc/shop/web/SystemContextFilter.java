package itat.zttc.shop.web;

import itat.zttc.shop.model.SystemContext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SystemContextFilter implements Filter {
	
	private int pageSize;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			int pageOffset = 0;
			int pageSize = 15;
			String sort = req.getParameter("sort");
			String order = req.getParameter("order");
			try {
				pageOffset = Integer.parseInt(req.getParameter("pager.offset"));
			} catch (NumberFormatException e) {
			}
			SystemContext.setOrder(order);
			SystemContext.setSort(sort);
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePageOffset();
			SystemContext.removeOrder();
			SystemContext.removeSort();
		}
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		pageSize = Integer.parseInt(conf.getInitParameter("pageSize"));
		if (pageSize == 0) pageSize = 15;
	}

}
