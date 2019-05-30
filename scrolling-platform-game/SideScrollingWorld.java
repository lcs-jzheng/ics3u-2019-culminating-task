import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Template for a side-scrolling platform game.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class SideScrollingWorld extends World
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Tile size in pixels for world elements (blocks, clouds, etc)
    // TO STUDENTS: Modify if your game's tiles have different dimensions
    private static final int TILE_SIZE = 32;
    private static final int HALF_TILE_SIZE = TILE_SIZE / 2;

    // World size constants
    // TO STUDENTS: Modify only if you're sure
    //              Should be a resolution that's a multiple of TILE_SIZE
    public static final int VISIBLE_WIDTH = 480;
    public static final int VISIBLE_HEIGHT = 640;

    // Additional useful constants based on world size
    public static final int HALF_VISIBLE_WIDTH = VISIBLE_WIDTH / 2;
    public static final int HALF_VISIBLE_HEIGHT = VISIBLE_HEIGHT / 2;

    // Defining the boundaries of the scrollable world
    // TO STUDENTS: Modify SCROLLABLE_WIDTH if you wish to have a longer level
    public static final int SCROLLABLE_WIDTH = VISIBLE_WIDTH;
    public static final int SCROLLABLE_HEIGHT = VISIBLE_HEIGHT * 3;

    // Hero
    Hero theHero;
    Car theCAR;

    // Track whether game is on
    private boolean isGameOver;

    //Keep track of the time in the game
    private int frames = 0;

    //BGM for the game
    GreenfootSound backgroundMusic = new GreenfootSound("SH_BGM.mp3");

    // Beginning image for the game
    private GreenfootImage image1;
    private GreenfootImage image2;
    /**
     * Constructor for objects of class SideScrollingWorld.
     */
    public SideScrollingWorld()
    {    
        // Create a new world with 640x480 cells with a cell size of 1x1 pixels.
        // Final argument of 'false' means that actors in the world are not restricted to the world boundary.
        // See: https://www.greenfoot.org/files/javadoc/greenfoot/World.html#World-int-int-int-boolean-
        super(VISIBLE_WIDTH, VISIBLE_HEIGHT, 1, false);
        //There are two images to switch, one is the cover page and the other is the race track.
        image1 = new GreenfootImage("asphalt.png");
        image2 = new GreenfootImage("OPENING.png");
        setBackground(image2);
        if(getBackground() == image2)
        {frames = 0;

        }
        setup();
        // Game on
        isGameOver = false;

        backgroundMusic.playLoop();
    }

    /**
     * Set up the entire world.
     */
    private void setup()
    {
        // TO STUDENTS: Add, revise, or remove methods as needed to define your own game's world
        // addLeftGround();
        // addFences();
        // addMetalPlateSteps();
        // addClouds();
        // addRightGround();

        //Add some metal plate at left
        //for (int i = 0; i <= 4; i += 1)
        //{
        //int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
        //int y = 4 * TILE_SIZE + HALF_TILE_SIZE;

        //MetalPlate plate = new MetalPlate(x,y);
        //addObject(plate,x,y);

        //}

        ////Add some metal tiles to the right
        //for (int i = 0; i <= 10; i += 1)
        //{
        //int x =8 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
        //int y = 4 * TILE_SIZE + HALF_TILE_SIZE;

        //Ground someGround = new Ground(x,y);
        //addObject(someGround,x,y);

        //}

        //Add some sides on the left
        for (int i = 1; i <= 501; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + 1 * TILE_SIZE;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            roadL road1 = new roadL (x,y);
            roadL road2 = new roadL (x,y1);
            addObject(road1,x,y);
            addObject(road2,x,y1);

        }
        //Add some sides on the right
        for (int i = 1; i <= 501; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + 11 * TILE_SIZE;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            roadR road1 = new roadR (x,y);
            roadR road2 = new roadR (x,y1);
            addObject(road1,x,y);
            addObject(road2,x,y1);

        }
        //Add some grass on the right
        for (int i = 1; i <= 501; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + 12 * TILE_SIZE;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            grass pR1 = new grass (x,y);
            grass pR2 = new grass (x,y1);
            addObject(pR1,x,y);
            addObject(pR2,x,y1);

        }
        for (int i = 1; i <= 501; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + 13 * TILE_SIZE;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            grass pR3 = new grass (x,y);
            grass pR4 = new grass (x,y1);
            addObject(pR3,x,y);
            addObject(pR4,x,y1);

        }
        //Add some grass on the left
        for (int i = 1; i <= 501; i += 1)
        {
            int x = 1 * TILE_SIZE + HALF_TILE_SIZE;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            grass pL1 = new grass (x,y);
            grass pL2 = new grass (x,y1);
            addObject(pL1,x,y);
            addObject(pL2,x,y1);

        }
        for (int i = 1; i <= 501; i += 1)
        {
            int x = 1 * TILE_SIZE -HALF_TILE_SIZE ;
            int y = -i * TILE_SIZE + HALF_TILE_SIZE;
            int y1 = i * TILE_SIZE - HALF_TILE_SIZE;
            grass pL3 = new grass (x,y);
            grass pL4 = new grass (x,y1);
            addObject(pL3,x,y);
            addObject(pL4,x,y1);

        }

        //added in car, the main character into the game
        addCAR();
    }

    /**
     * Add blocks to create the ground to walk on at bottom-left of scrollable world.
     */
    private void addLeftGround()
    {
        // How many tiles will cover the bottom of the initial visible area of screen?
        final int tilesToCreate = getWidth() / TILE_SIZE;

        // Loop to create and add the tile objects
        for (int i = 0; i < tilesToCreate; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }
    }

    /**
     * Add some fences at left and right side.
     */
    private void addFences()
    {
        // Three fences on left side of world
        int x = HALF_TILE_SIZE + TILE_SIZE * 5;
        int y = VISIBLE_HEIGHT - HALF_TILE_SIZE - TILE_SIZE;
        Fence fence1 = new Fence(x, y);
        addObject(fence1, x, y);

        x = HALF_TILE_SIZE + TILE_SIZE * 6;
        y = VISIBLE_HEIGHT - HALF_TILE_SIZE - TILE_SIZE;        
        Fence fence2 = new Fence(x, y);
        addObject(fence2, x, y);

        x = HALF_TILE_SIZE + TILE_SIZE * 7;
        y = VISIBLE_HEIGHT - HALF_TILE_SIZE - TILE_SIZE;
        Fence fence3 = new Fence(x, y);
        addObject(fence3, x, y);

        // Two fences on right side of world
        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 3;
        y = VISIBLE_HEIGHT / 2;
        Fence fence4 = new Fence(x, y);
        addObject(fence4, x, y);

        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 4;
        y = VISIBLE_HEIGHT / 2;
        Fence fence5 = new Fence(x, y);
        addObject(fence5, x, y);
    }

    /**
     * Add steps made out of metal plates leading to end of world.
     */
    private void addMetalPlateSteps()
    {
        // How many plates total?
        final int COUNT_OF_METAL_PLATES = 20;
        final int PLATES_PER_GROUP = 4;

        // Add groups of plates
        for (int i = 0; i < COUNT_OF_METAL_PLATES / PLATES_PER_GROUP; i += 1)
        {
            // Group of four metal plates all at same y position
            int y = VISIBLE_HEIGHT - HALF_TILE_SIZE * 3 - i * TILE_SIZE;

            // Add the individual plates in a given group
            for (int j = 0; j < PLATES_PER_GROUP; j += 1)
            {
                int x = VISIBLE_WIDTH + TILE_SIZE * 2 + TILE_SIZE * (i + j) + TILE_SIZE * 5 * i;
                MetalPlate plate = new MetalPlate(x, y);
                addObject(plate, x, y);
            }
        }
    }

    /**
     * Add a few clouds for the opening scene.
     */
    private void addClouds()
    {
        Cloud cloud1 = new Cloud(170, 125);
        addObject(cloud1, 170, 125);
        Cloud cloud2 = new Cloud(450, 175);
        addObject(cloud2, 450, 175);
        Cloud cloud3 = new Cloud(775, 50);
        addObject(cloud3, 775, 50);
    }

    /**
     * Act
     * 
     * This method is called approximately 60 times per second.
     */
    public void act()
    {
        setBackground(image1);
        // Every 60 frames, update the time
        int x = 4 + Greenfoot.getRandomNumber(8);
        AI1 AI1 = new AI1();
        AI2 AI2 = new AI2();
        AI3 AI3 = new AI3();
        Tree Tree = new Tree();
        Tree Tree2 = new Tree();

        // Increment frame (roughly 60 frames per second)

            frames = frames + 1;

        if ((frames % 60) == 0)
        {
            String timeElapsed = Integer.toString(frames / 60);
            addObject(AI1,x * TILE_SIZE -HALF_TILE_SIZE,0);

        }
        if ((frames % 45) == 0)
        {
            String timeElapsed = Integer.toString(frames / 45);
            addObject(AI3,x * TILE_SIZE -HALF_TILE_SIZE,0);

        }
        if ((frames % 75) == 0)
        {
            String timeElapsed = Integer.toString(frames / 75);
            addObject(AI2,x * TILE_SIZE -HALF_TILE_SIZE,0);

        }
        if ((frames % 150) == 0)
        {
            String timeElapsed = Integer.toString(frames / 150);
            addObject(Tree,1 * TILE_SIZE,0);
        }
        if ((frames % 150) == 0)
        {
            String timeElapsed = Integer.toString(frames / 150);
            addObject(Tree2,14 * TILE_SIZE,0);
        }
    }

   

    /**
     * Add the CAR to the world.
     */
    private void addCAR()
    {
        // Initial horizontal position
        int initialX = TILE_SIZE * 8;
        int initialY = TILE_SIZE * 16;
        // Instantiate the hero object
        theCAR = new Car(initialY);

        // Add hero in bottom left corner of screen
        addObject(theCAR, initialX, initialY );
    }

    

    /**
     * Set game over
     */
    public void setGameOver()
    {
        //Let the game stop
        Greenfoot.stop();
    }
}

