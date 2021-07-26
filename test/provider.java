package test;

public class provider
{
    int id;
    String name;
    String img;
    
    public provider(final int i, final String n, final String im) {
        this.id = i;
        this.name = n;
        this.img = im;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int i) {
        this.id = i;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String n) {
        this.name = n;
    }
    
    public String getImg() {
        return this.img;
    }
    
    public void setImg(final String im) {
        this.img = im;
    }
    
    public void display() {
        System.out.println("Id = " + this.id);
        System.out.println("Name = " + this.name);
        System.out.println("img = " + this.img);
    }
}
