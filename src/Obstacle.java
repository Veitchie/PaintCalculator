public class Obstacle {
    public float width;
    public float height;
    public boolean isCircle = false;

    public Obstacle(float width, float height){
        this.width = width;
        if (height == 0){
            this.isCircle = true;
        } else {
            this.height = height;
        }
    }

    public float getArea(){
        if (isCircle){
            return (float) (Math.PI * Math.pow((width / 2), 2));
        }
        return width * height;
    }
}
