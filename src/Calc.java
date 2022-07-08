import java.util.ArrayList;
import java.util.Scanner;

public class Calc {

    public static void main(String[] args) {
        betterCalculator();
        //test();
    }

    public static void printGUI(ArrayList<Wall> walls){
        float maxHeight = 0, maxWidth = 0;

        for (Wall wall: walls){
            maxHeight = Math.max(maxHeight, wall.height);
            maxWidth += wall.width;
        }

        int maxHeightInt = (int) (maxHeight * 2);
        int maxWidthInt = (int) (maxWidth * 2);

        String[][] gui = new String[maxHeightInt][maxWidthInt];

        int xPos = 0;
        for (Wall wall: walls){
            for (int i = 0; i < maxHeightInt; i++){
                for (int j = 0; j < xPos + maxWidthInt; j ++){
                    System.out.print("##");
                }
                System.out.println();
            }
            xPos = (int) (wall.width * 2);
        }

    }

    public static void test(){
        Scanner in = new Scanner(System.in);

        Paint paint = new Paint("Red", 5, 12, 20);

        paint.printData();

    }

    public static float[] getFloatDimensions(Scanner scan, boolean allowSingleInput){
        boolean validInputs = false;
        float[] tempArray = {0,0};

        while (!validInputs){
            System.out.print(">>>");
            String[] tempDimension = scan.nextLine().split(",");

            if (tempDimension.length == 2){
                try {
                    tempArray[0] = Float.parseFloat(tempDimension[0]);
                    tempArray[1] = Float.parseFloat(tempDimension[1]);
                    validInputs = true;
                }catch(NumberFormatException e){
                    System.out.println("Not floats");
                }
            } else if (allowSingleInput){
                try {
                    tempArray[0] = Float.parseFloat(tempDimension[0]);
                    validInputs = true;
                }catch(NumberFormatException e){
                    System.out.println("Not floats");
                }
            } else{
                System.out.println("Enter two values separated by a comma (,)");
            }

        }
        return tempArray;
    }

    public static void betterCalculator() {
        ArrayList<Paint> defaultPaints = new ArrayList<Paint>();
        defaultPaints.add(new Paint("Paint Co.", "Red", 2.5f, 10, 25));
        defaultPaints.add(new Paint("Paint Co.", "Green", 2.5f, 10, 25));
        defaultPaints.add(new Paint("Paint Co.", "Blue", 3, 12, 25));
        defaultPaints.add(new Paint("Paint Co.", "White", 3, 12, 20));

        Scanner in = new Scanner(System.in);
        float areaToPaint = 0f;
        String paintColour;
        float paintCoverage = 12f;  //how many metres^2 1 litre of paint will cover
        float paintInTub = 2.5f;    //how much paint is in a tub (litres)
        float costPerTub = 20;      //Price per tub of paint
        boolean paintResponseValid = false;
        Paint paintUsed = defaultPaints.get(0);

        //Print and select paint
        System.out.println("\nDefault paints:");
        boolean paintDecided = false;
        while (!paintDecided){
            System.out.print("Available colours: ");
            for (Paint paint: defaultPaints){
                System.out.print(paint.colour + "\t");
            }
            System.out.print("\nEnter paint colour to get more information, or enter \"n\" to add new paint data\n>>>");
            String colourChosen = in.nextLine();
            colourChosen = colourChosen.substring(0, 1).toUpperCase() + colourChosen.substring(1);

            if (colourChosen.equals("N")){
                while (!paintResponseValid) {
                    try {
                        //Get new paint values
                        System.out.print("What colour is the paint?\n>>>");
                        paintColour = in.nextLine();
                        System.out.print("How much paint is in a tub?\n>>>");
                        paintInTub = Float.parseFloat(in.nextLine());
                        System.out.print("How much surface area (m^2) can 1 litre cover?\n>>>");
                        paintCoverage = Float.parseFloat(in.nextLine());
                        System.out.print("How much does this paint cost per tub?\n>>>");
                        costPerTub = Float.parseFloat(in.nextLine());

                        defaultPaints.add(new Paint(paintColour, paintInTub, paintCoverage, costPerTub));

                        paintResponseValid = true;
                    } catch (NumberFormatException e){
                        System.out.println("That was not a valid input.");
                    }
                }
            } else {
                boolean paintColourFound = false;
                for (Paint paint : defaultPaints) {
                    if (colourChosen.equals(paint.colour)) {
                        paint.printData();
                        paintColourFound = true;

                        System.out.print("\nSelect this paint? (type \"Y\"))\n>>>");
                        if (in.nextLine().toUpperCase().equals("Y")){
                            paintUsed = paint;
                            paintDecided = true;
                        }
                        break;
                    }
                }
                if (!paintColourFound){
                    System.out.println("That colour is not found.");
                }
            }
        }

        //Get the number of walls
        System.out.print("How many walls?\n>>>");
        int numWalls = Integer.parseInt(in.nextLine());
        ArrayList<Wall> walls = new ArrayList<Wall>();

        //Iterate through each wall
        for (int i = 0; i < numWalls; i++) {

            //Get dimensions of the current wall
            System.out.println("Enter dimensions of wall " + (i + 1) + " in the form <width,height>");
            float[] tempReturn = getFloatDimensions(in, false);
            float width = tempReturn[0];
            float height = tempReturn[1];

            //Get number of layers of paint to use on the current wall
            System.out.print("How many layers do you want on this wall?\n>>>");
            int numLayers = Integer.parseInt(in.nextLine());
            walls.add(new Wall(width, height, numLayers));

            //Get number of obstacles for the current wall
            System.out.print("How many obstacles on this wall?\n>>>");
            int numObstacles = Integer.parseInt(in.nextLine());

            switch (numObstacles) {
                case 0:
                    System.out.println("Natural light is important");
                    break;
                default:
                    //Iterate through each obstacle for the current wall
                    for (int j = 0; j < numObstacles; j++) {

                        //Get dimensions of the current obstacle
                        System.out.println("IF RECTANGULAR : Enter obstacle dimensions as <width,height>");
                        System.out.println("IF CIRCULAR    : Enter obstacle dimensions as <radius>");
                        tempReturn = getFloatDimensions(in, true);
                        float tempWidth = tempReturn[0];
                        float tempHeight = tempReturn[1];

                        //Make sure obstacle fits on the wall
                        if ( (tempHeight != 0 && (tempWidth > walls.get(i).width || tempHeight > walls.get(i).height)) ||
                                (tempHeight == 0 && (tempWidth * 2) > Math.min(walls.get(i).height, walls.get(i).width))){
                            System.out.println("You obstacle is larger than the wall");
                            j--;
                        } else {
                            walls.get(i).addObstacle(tempWidth, tempHeight);
                        }
                    }
            }

            //Increase the area to paint based on the area of the wall minus the combined area of the obstacles
            areaToPaint += walls.get(i).getAreaToPaint();
        }

        //Data printout
        System.out.println("\nData printout");
        System.out.println("========================================");
        System.out.println(numWalls + " walls");
        for (Wall wall: walls){
            System.out.println("\t-> " + wall.width + " x " + wall.height + ", " + wall.layers + " layers, " + wall.obstacles.size() + " obstacles");
            for (Obstacle obs: wall.obstacles){
                if (obs.isCircle){
                    System.out.println("\t\t-> r=" + obs.width + ", circular");
                } else {
                    System.out.println("\t\t-> " + obs.width + " x " + obs.height + ", rectangular");
                }
            }
        }
        paintUsed.printData();
        System.out.println("========================================");

        //Calculate the number of tubs of paint based on coverage and area to paint
        float amountOfPaint = (areaToPaint / paintUsed.coverage);
        int numTubs = (int) Math.ceil(amountOfPaint / paintUsed.tubSize);

        System.out.println("\nYou need " + numTubs + " tub(s) of paint (£" + (paintUsed.price * numTubs) + "), " + amountOfPaint + "L of paint in total, to paint all " + numWalls + " walls.");

        printGUI(walls);
    }
}