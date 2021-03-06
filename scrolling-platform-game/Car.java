import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * This is a car that will move around the world and it will be passing throught road till the end.
 * 
 * Jeff
 * @version (a version number or a date)
 */
public class Car extends Actor
{

    // Vertical speed (change in vertical position, or delta Y)
    private int deltaY = 4;

    // Horizontal speed (change in horizontal position, or delta X)
    private int deltaX = 4;

    // Track whether game is over or not
    private boolean isGameOver;

    //Track whether enemy being touched
    private boolean isTouchingEnemy;

    // Constants to track vertical direction
    private static final String FACING_UP = "up";
    private static final String FACING_DOWN = "down";
    private String verticalDirection;
    // current Y position in the world to keep track 
    private int currentScrollableWorldYPosition;
    //Life the car got
    private int Life = 3;

    //The score player will get
    private int Score = 0;
    //sound it will play after hitting the cars
    GreenfootSound boom;
    GreenfootSound Success;
    /**
     * Constructor
     * 
     * This runs once when the Hero object is created.
     */
    Car(int initialY)
    {
        // Set where hero begins horizontally
        currentScrollableWorldYPosition = initialY;

    }

    /**
     * Act - do whatever the CAR wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Score added 60 each second
        Score = Score + 1;
        boom = new GreenfootSound("explosion.wav");
        Success = new GreenfootSound("RIGHTCAR.wav");
        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 
        //Show the actors life and Score status
        world.showText("Life :" + Life, 440, 40);
        world.showText("Score :" + Score, 60, 40);

        // Add your action code here.
       
        moveUp();
        if (Greenfoot.isKeyDown("a"))
        {
            //MOVE LEFT
            move(-3);
        }
        if (Greenfoot.isKeyDown("d"))
        {
            //MOVE RIGHT
            move(3);
        }

        if(isTouching(AI1.class) == true && !isTouchingEnemy)
        {
            AI1 enemy = (AI1)getOneIntersectingObject(AI1.class);
            getWorld().removeObject(enemy);

            Life = Life - 1;

            isTouchingEnemy = true;

            boom.play();
        }
        if(isTouching(AI2.class) == true && !isTouchingEnemy)
        {
            AI2 boss = (AI2)getOneIntersectingObject(AI2.class);
            getWorld().removeObject(boss);

            Score = Score +1000;

            isTouchingEnemy = true;

            Success.play();
        }
        if(isTouching(AI3.class) == true && !isTouchingEnemy)
        {
            AI3 enemy = (AI3)getOneIntersectingObject(AI3.class);
            getWorld().removeObject(enemy);

            Life = Life - 1;

            isTouchingEnemy = true;

            boom.play();
        }
        if(isTouching(Tree.class) == true && !isTouchingEnemy)
        {
 
            Life = Life - 3;

            isTouchingEnemy = true;

            boom.play();
        }
        if (isTouching(AI1.class) == false && (isTouching(AI2.class) == false && (isTouching(AI3.class) == false && isTouchingEnemy)))
        {
            isTouchingEnemy = false;
        }
        if(Life <= 0)
        {

            world.showText("GameOver", world.getWidth() / 2, world.getHeight() / 2);
            Greenfoot.stop();

        }
    }    

    /**
     * Move the hero to the up.
     */
    public void moveUp()
    {
        // Track direction
        verticalDirection = FACING_UP;

        // Speed chang
        if(isTouching(grass.class))
        {
            deltaY = 2;
        }
        if(isTouching(roadL.class))
        {
            deltaY = 4;
        }
        if(isTouching(roadR.class))
        {
            deltaY = 4;
        }
        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        System.out.println("I am here vertically: " + currentScrollableWorldYPosition);

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldYPosition - deltaY > world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME LOWER PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.
            System.out.println("extreme bottom");
            // Don't let hero go off bottom of scrollable world 
            // (Allow movement only when not at bottom edge)
            System.out.println("y position is " + currentScrollableWorldYPosition);
            System.out.println("world's height is " + world.VISIBLE_HEIGHT);

            if (currentScrollableWorldYPosition < world.VISIBLE_HEIGHT)
            {
                // Move up in visible world
                int newVisibleWorldYPosition = getY() - deltaY;
                setLocation(getX(), newVisibleWorldYPosition);
                System.out.println("in moving up part");

                // Track position in wider scrolling world
                currentScrollableWorldYPosition = getY();
            }            
        }
        else if (currentScrollableWorldYPosition + deltaY * 2 < (world.SCROLLABLE_HEIGHT - world.HALF_VISIBLE_HEIGHT) * -1)
        {

            // HERO IS WITHIN EXTREME UPPER PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.
            System.out.println("extreme top");

            // Allow movement only when not at edge of world
            if (currentScrollableWorldYPosition > world.VISIBLE_HEIGHT * 3 * -1)
            {
                // Move up in visible world
                int newVisibleWorldYPosition = getY() - deltaY;
                setLocation(getX(), newVisibleWorldYPosition);

                // Track position in taller scrolling world
                currentScrollableWorldYPosition -= deltaY;
            }
            else
            {
                isGameOver = true;
                world.setGameOver();

                // Tell the user game is over
                world.showText("LEVEL COMPLETE", world.getWidth() / 2, world.getHeight() / 2);
            }

        }        
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving
            System.out.println("in the middle");

            // Track position in wider scrolling world
            currentScrollableWorldYPosition -= deltaY;

            // Get a list of all platforms (objects that need to move
            // to make hero look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects at same speed as hero
            for (Platform platform : platforms)
            {
                // Platforms move right to make hero appear to move left
                platform.moveDown(deltaY);
            }

            // Get a list of all decorations (objects that need to move
            // to make hero look like they are moving)
            List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like hero is moving
            for (Decoration decoration: decorations)
            {
                // Platforms move right to make hero appear to move left
                decoration.moveDown(deltaY);
            }

            // Get a list of all items that are in the distance (far away items)
            List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the FarAwayItem objects at one quarter speed as hero to create depth illusion
            for (FarAwayItem farAwayItem : farAwayItems)
            {
                // FarAwayItems move right to make hero appear to move left
                farAwayItem.moveDown(deltaY / 4);
            }

        } 

    }

}
