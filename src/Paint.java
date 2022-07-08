public class Paint {
    public String brand;
    public String colour;
    public float tubSize;
    public float coverage;
    public float price;

    public Paint(String brand, String colour, float tubSize, float coverage, float price){
        this.brand = brand;
        this.colour = colour;
        this.tubSize = tubSize;
        this.coverage = coverage;
        this.price = price;
    }

    public Paint(String colour, float tubSize, float coverage, float price){
        this("Generic", colour, tubSize, coverage, price);
    }

    public void printData(){
        System.out.println("Brand:    " + brand);
        System.out.println("Colour:   " + colour);
        System.out.println("Size:     " + tubSize + "L");
        System.out.println("Coverage: " + coverage);
        System.out.println("Price:    " + price);
    }
}
