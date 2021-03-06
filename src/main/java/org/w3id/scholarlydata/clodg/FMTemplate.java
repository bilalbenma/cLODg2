package org.w3id.scholarlydata.clodg;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FMTemplate {

	private final int majorVersion = 2;
	private final int minorVersion = 3;
	private final int microVersion = 23;
	
	private String templateName; 
	private static final String TEMPLATE_LOCATION = "templates/easychair";
	private static final String ABSOLUTE_TEMPLATE_LOCATION = "/" + "templates/easychair";
	//private final String templateName = "d2rq_mapping_hsqldb.ftl";
	
	private Properties configuration;
	
	public FMTemplate(String templateName, Properties configuration) {
		this.templateName = templateName;
		this.configuration = configuration;
	}
	
	public Model generateMapping(){
		Model model = ModelFactory.createDefaultModel();
		
		Configuration cfg = new Configuration(new Version(majorVersion, minorVersion, microVersion));
		
		TemplateLoader loader = new ClassTemplateLoader(this.getClass(), ABSOLUTE_TEMPLATE_LOCATION);
		cfg.setTemplateLoader(loader);
	    cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
		
		try {
			Template template = cfg.getTemplate(templateName);
			
			Writer writer = new StringWriter();
			template.process(configuration, writer);
			Reader reader = new StringReader(writer.toString());
			
			model.read(reader, null, "TURTLE");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return model;
	}
	
	public static Collection<String> getTemplateNames() {
		Collection<String> templatesNames = Collections.emptyList();
		
		
		URL url = FMTemplate.class.getClassLoader().getResource(TEMPLATE_LOCATION);
		
		URI uri;
		try {
			uri = url.toURI();
			
			Path myPath;
		    if (uri.getScheme().equals("jar")) {
		        FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
		        myPath = fileSystem.getPath(TEMPLATE_LOCATION);
		    } else {
		        myPath = Paths.get(uri);
		    }
		    System.out.println(myPath);
		    templatesNames = 
		    		Files.walk(myPath, 1)
				    	.filter(path -> {
					    	Path fileName = path.getFileName();
					    	if(fileName.endsWith(".ftl")) return true;
					    	else return false;
					    })
				    	.map(path -> {
				    		return path.toString();
				    	})
				    	.collect(Collectors.toList());
		    
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return templatesNames;
	}
	
	public static void main(String[] args) {
		Collection<String> templates = FMTemplate.getTemplateNames();
		System.out.println(templates.size());
		for(String template : templates)
			System.out.println(template);
	}
}
