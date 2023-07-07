package model;
import etu1785.framework.ModelView;
import etu1785.framework.UrlMapping;
import java.util.HashMap;
import java.util.Vector;

public class Emp {
    int id;
    String name;
    String firstname;
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    

    @UrlMapping(url = "get-name.do")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Emp(int id, String name, String firstname) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
    }

    public Emp(String name) {
        this.name = name;
    }
    
    @UrlMapping(url="emp-all.do")
    public ModelView findAll(){
        ModelView mv = new ModelView();
        mv.setView("/emp-list.jsp");
            
        Vector<Emp> emp = new Vector<>();
        emp.add(new Emp("Rakoto"));    
        emp.add(new Emp("Rabe"));
        emp.add(new Emp("Rasoa"));

        mv.addItem("list", emp);        
        mv.addItem("liste", new Emp("Rasoa"));
        
        return mv;
    }
    public Emp() {
    }
}