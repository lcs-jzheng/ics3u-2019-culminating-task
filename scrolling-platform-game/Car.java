import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class CAR here.
 * 
 * @author (your name) 
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

    // Constants to track vertical direction
    private static final String FACING_UP = "up";
    private static final String FACING_DOWN = "down";
    private String verticalDirection;

    private int currentScrollableWorldYPosition;
    private int Life = 3;
    /**
     * Act - do whatever the CAR wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        if (Greenfoot.isKeyDown("w"))
        {
            // MOVE THE ACTOR UP
            moveUp();
        }
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

        if(isTouching(AI1.class) == true)
        {
            Life = Life - 1;
        }
        if(isTouching(AI2.class) == true)
        {
            Life = Life - 1;
        }
        if(isTouching(AI3.class) == true)
        {
            Life = Life - 1;
        }
        if(Life == 0)
        {
            // Get object reference to world
            SideScrollingWorld world = (SideScrollingWorld) getWorld(); 
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

        // Set image 

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldYPosition - deltaY < world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME LOWER PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Don't let hero go off bottom of scrollable world 
            // (Allow movement only when not at bottom edge)
            if (currentScrollableWorldYPosition < world.VISIBLE_HEIGHT)
            {
                // Move up in visible world
                int newVisibleWorldYPosition = getY() - deltaY;
                setLocation(getX(), newVisibleWorldYPosition);

                // Track position in wider scrolling world
                currentScrollableWorldYPosition = getY();
            }            
        }
        else if (currentScrollableWorldYPosition + deltaY * 2 > world.SCROLLABLE_HEIGHT - world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME UPPER PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move up in visible world
            int newVisibleWorldYPosition = getY() - deltaY;
            setLocation(getX(), newVisibleWorldYPosition);

            // Track position in wider scrolling world
            currentScrollableWorldYPosition -= deltaX;
        }        
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving

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
