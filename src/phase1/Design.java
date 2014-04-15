package phase1;

public class Design {
	
	/*
	 * Need designs for:
	 * bumpers
	 * balls
	 * boards
	 * simulator
	 * client 
	 * server
	 * parsers for wire protocol
	 * user commands
	 * file format
	 */

	/*
	 * Ball should be similar to the object created in the warmup exercise
	 * It should have a move method which updates its x and y coordinate
	 * It will also have an orientation vector.
	 * Also a velocity vector which is altered when you a bumper
	 */
	
	/*
	 * Board will have a 2D array corresponding to the walls, the position of the
	 * flippers and the ball.
	 * It will have a toString like method which prints the status quo of the board
	 * which will be printed for each frame.
	 * It will have an additional gravity methods which alters the ball
	 * velocit depending on the ball's orientation vector.
	 * Also, has a constructor which takes in a file, this is similar 
	 * to pset 3 where we use a buffer eader to parse
	 * the file and then set up the board.
	 * Board display is simply printing the board.toString method.
	 * Trigger action are also effected through methods of the ball
	 * Overall, we use the board object to house all the obstacles and
	 * barriers then the ball is an independent object which outputs
	 * a trace which responds to the outline of the board
	 */
	
	/*
	 * Clients will be initiated in a serve method with their unique 
	 * sockets. Serve will also take in a string representing
	 * the client's name.
	 * So we will have a handleConnection method which iniates a
	 * new board and puts it into a global wrapper like a hashMap
	 * and the tag on that board will be the client which initiated
	 * the board
	 * this is done if the client chooses to play in client
	 * server mode.
	 * Since the board associated with a client must be final,
	 * when we merge two boards we have a merge method which
	 * sets the boards of the two clients to the merged version
	 * of the two boards.
	 * Board will be threadsafe due to locks and locks are acquired
	 * in order based on the alphabetical order of names of users
	 * accessing the board.
	 * 
	 */
	
	/*
	 * Bumper will just a swinging sideway line segment
	 * So before updating the position of the ball, we check
	 * if it has collided with a wall of the board
	 * or a line segment
	 */
	
    /*
     *     //inside the board
    public void ballsMove() {
        for (Ball b: balls) {
            ballMove(b,1.0); //time to go is 1
        }
    }
    
    //inside the board class, updates what's in the list of balls (representing
    //what balls are in the board at the start of the time step)
    public void (List<Ball> l) {
        this.balls=l;
    }
    
    //need to figure out where the ball will end up in a board after it passes
    //through the wall
    //inside the simulate method, just need to call neighbourBoard.ballMove(ball,timetoGo)
    
    //need to make the collide method of wall s.t handles simulation
    //i.e if it leaves the board, needs to call simulate method inside the server
    //to figure out where the ball will end up
    
    //just applies to colliding with gadget
    public void ballMove(Ball b, double timetoGo) {
        Gadget minGadget=null;
        time=timetoGo;
        for (Gadget gad:listofGadgets) {
            if (time_until_collide_with_gad<time) {
                minGadget=gad;
                time=time_until_collide_with_gad;
            }
        }
        if (!(time==timetoGo)) {
            minGadget.collide(b,timetoGo);
            //inside the collide method should:
            //ball.move(time_until_collide)
            //change the velocity of the ball (the "after" velocity)
            //call board.ballMove(b,timetoGo-time_until_collide)
        }
        //handles the case when you don't collide with any gadgets whatsoever
    }
    
    //timetoGo is passed around
    
    //colide method of the wall (is a gadget)
}
//if a client sends a message to server "a ball is leaving "
     */
	
}
