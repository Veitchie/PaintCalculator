import java.util.ArrayList;

public class Wall {
    public float width;
    public float height;
    public int layers;
    public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    public float obstacleArea = 0f;

    public Wall(float width, float height, int layers) {
        this.width = width;
        this.height = height;
        this.layers = layers;
    }

    public float getArea(){
        return width * height;
    }

    private int getLayers(){
        return layers;
    }

    public void addObstacle(float w, float h){
        obstacles.add(new Obstacle(w,h));
        obstacleArea += w * h;
    }

    public float getObstaclesArea(){
        return obstacleArea;
    }

    public float getAreaToPaint(){
        return (getArea() - getObstaclesArea()) * getLayers();
    }
}
