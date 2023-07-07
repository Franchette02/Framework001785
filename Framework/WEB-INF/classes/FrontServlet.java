package etu1785.framework.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import etu1785.framework.ModelView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import etu1785.framework.Mapping;
import etu1785.framework.UrlMapping;
import etu1785.framework.Utilitaire;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> mappingUrls;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.mappingUrls = new HashMap<>();

            final String tomPath = "/WEB-INF/classes/";
            String path = getServletContext().getRealPath(tomPath);
            List<Class<?>> allClasses = Utilitaire.getAllClasses(path, tomPath);

            processAnnotations(allClasses);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    protected void processAnnotations(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            Method[] allMethods = clazz.getMethods();

            for (Method method : allMethods) {
                if (method.isAnnotationPresent(UrlMapping.class)) {
                    UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);
                    Mapping mapping = new Mapping();
                    mapping.setClassName(clazz.getName());
                    mapping.setMethod(method.getName());
                    mappingUrls.put(urlMapping.url(), mapping);
                }
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        String mappedUrl = Utilitaire.getUrl(url, contextPath);

        Mapping mapping = mappingUrls.get(mappedUrl);

        if (mapping == null) {
            throw new Exception("Not Found");
        }

        Class<?> clazz = Class.forName(mapping.getClassName());
        Object object = clazz.getDeclaredConstructor().newInstance();

        ModelView modelView = (ModelView) clazz.getMethod(mapping.getMethod()).invoke(object);

        addData(request, modelView);
        HashMap<String, Object> data = modelView.getData();


        // Mettre à jour les données dans la requête
        for (String key : data.keySet()) {
            request.setAttribute(key, data.get(key));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(modelView.getView());
        dispatcher.forward(request, response);
    }

    public void addData(HttpServletRequest req,ModelView mv){
        for (Map.Entry<String,Object> obj: mv.getData().entrySet()) {
            req.setAttribute(obj.getKey(), obj.getValue());
        }
        
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }
}
