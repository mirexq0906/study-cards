package com.example.StudyCards.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/")
				.resourceChain(true)
				.addResolver(new SpaFallbackResolver());
	}

	private static final class SpaFallbackResolver extends PathResourceResolver {

		@Override
		protected Resource resolveResourceInternal(HttpServletRequest request,
												   String requestPath,
												   List<? extends Resource> locations,
												   ResourceResolverChain chain) {
			Resource resource = super.resolveResourceInternal(request, requestPath, locations, chain);

			if (resource != null) {
				return resource;
			}

			if (isApiRequest(requestPath)) {
				return null;
			}

			try {
				return getResource("index.html", locations.getFirst());
			}
			catch (IOException ex) {
				return null;
			}
		}

		private boolean isApiRequest(String requestPath) {
			return requestPath.startsWith("api/") || requestPath.startsWith("h2-console");
		}

	}

}
