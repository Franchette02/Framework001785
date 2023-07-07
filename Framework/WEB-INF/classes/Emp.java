package etu1785.framework;
import etu1785.framework.*;
import etu1785.framework.servlet.*;
import java.util.*;
public class Emp {
    
    @UrlMapping(url = "/emp/list")
    public void getList() {
        System.out.println("mety");
    }
    
    @UrlMapping(url = "/emp/details")
    public void getDetails() {
        System.out.println("mety");
    }
    
    @UrlMapping(url = "/emp/create")
    public void createEmp() {
        System.out.println("mety");
    }
    
     public static void main(String args[]){
        FrontServlet frontServlet = new FrontServlet();
        try{
            frontServlet.init();
        }
        catch(Exception e){

        }
        

        HashMap<String, Mapping> mappingUrls = frontServlet.getMappingUrls();
        for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
            String url = entry.getKey();
            Mapping mapping = entry.getValue();
            System.out.println("URL: " + url);
            System.out.println("classe: " + mapping.getClassName());
            System.out.println("Method: " + mapping.getMethod());
            System.out.println();
        }
    
    }
}