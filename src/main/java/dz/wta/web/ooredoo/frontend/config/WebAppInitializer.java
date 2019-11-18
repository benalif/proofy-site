package dz.wta.web.ooredoo.frontend.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;



public class WebAppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);

	//	servletContext.addFilter("AppFilter", AppFilter.class).addMappingForUrlPatterns(null, false, "/api/*");

		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("spring.profiles.active", "uat");
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
