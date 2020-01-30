/*
Mwape Chintu 2020
 */

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Elevator extends Thread {

    private int id;
    private int currentElevatorPos;
    private int requestFromFloor;
    private int destination;
    private boolean available = true;
    private boolean isMoving = false;
    private String direction;
    private Passenger passenger;
    private Random random;
    private Controller controller;


    Elevator(int id, int currentElevatorPos, boolean available, boolean isMoving){      //constructor for calling out object
        this.id = id;
        this.currentElevatorPos = currentElevatorPos;
        this.available = available;
        this.isMoving = isMoving;
    }

    public boolean isMoving() {         //auto generated getters and setters for the attributes of the object.
        return isMoving;
    }

    private String getDirection() {
        return direction;
    }

    private void setDirection(String direction) {
        this.direction = direction;
    }

    int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setMoving(boolean moving) {
        isMoving = moving;
    }

    boolean isAvailable() {
        return available;
    }

    private void setAvailable(boolean available) {
        this.available = available;
    }


    int getcurrentElevatorPos() {
        return currentElevatorPos;
    }

    private void setcurrentElevatorPos(int currentElevatorPos) {
        this.currentElevatorPos = currentElevatorPos;
    }

    private int getDestination() {
        return destination;
    }

    void setDestination(int destination) {
        this.destination = destination;
    }

    private int getRequestFromFloor() {
        return requestFromFloor;
    }

    void setRequestFromFloor(int requestFromFloor) {
        this.requestFromFloor = requestFromFloor;
    }

    private synchronized void goToDestination(int currentElevatorPos, int requestFromFloor, int destination) throws InterruptedException {
        setMoving(true);    //if the elevator has been requested, it is now moving and no longer available for other requests.
        setAvailable(false);
        if(currentElevatorPos < destination) {  //if the current position of the elevator is less than the destination, then the direction is up.
            setDirection("UP");
            System.out.println("[Elevator: " + getID() + " called from current floor: " + getcurrentElevatorPos() + ", Requested at floor: " + getRequestFromFloor() + ", Destination Floor: " + getDestination() +", Direction: " + getDirection()+"]" );
        }
        else{
            setDirection("DOWN");   //else it's down.
            System.out.println("[Elevator: " + getID() + " called from current floor: " + getcurrentElevatorPos() + ", Requested at floor: " + getRequestFromFloor() + ", Destination Floor: " + getDestination() +", Direction: " + getDirection()+"]" );

        }
        while(currentElevatorPos < requestFromFloor){   //while elevator is not at the requested location and that location is above, increase the position of the elevator
            currentElevatorPos++;
            setcurrentElevatorPos(currentElevatorPos);
           // System.out.print("Elevator: "+ getID() +"->" + currentElevatorPos);
            TimeUnit.MILLISECONDS.sleep(300); //set a delay to simulate the elevator moving. (make the thread sleep)
        }
        while(currentElevatorPos > requestFromFloor){       //if it is below, decrease it
            currentElevatorPos--;
            setcurrentElevatorPos(currentElevatorPos);
           // System.out.print("Elevator: "+ getID() +"->" + currentElevatorPos);
            TimeUnit.MILLISECONDS.sleep(300);  //set a delay to simulate the elevator moving. (make the thread sleep)
        }
        System.out.println();
        System.out.print("Loading passengers "+ passenger.getPassengers() + " into Elevator: " + getID());  //loading the "passengers" at the requested floor.
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(200);
        TimeUnit.SECONDS.sleep(2);  //make the thread sleep while loading passengers
        System.out.println();
        System.out.print("Elevator: " + getID() + " ");
        while(currentElevatorPos < destination){
            currentElevatorPos++;
            setcurrentElevatorPos(currentElevatorPos);
          //  System.out.print("Elevator: "+ getID() + "->" + currentElevatorPos);
            System.out.print("->");
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.println();
        System.out.print("Elevator: " + getID() + " ");
        while(currentElevatorPos > destination){
            currentElevatorPos--;
            setcurrentElevatorPos(currentElevatorPos);
       //     System.out.print("Elevator: "+ getID() + "->" + currentElevatorPos);
            System.out.print("<-");
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.println();
        setMoving(false);       //reset the moving and availability status
        setAvailable(true);
        System.out.println("Elevator: " + getID() + " reached destination floor: " + getcurrentElevatorPos() + " . Available: " + isAvailable());
        checkOverflows();       //check if there are any free elevators for the overflow requests.
    }

    public synchronized void checkOverflows(){

        if(isAvailable()){
            this.setAvailable(true);
        }
    }

    @Override
    public void run() {
        try {
            random = new Random();      //create a random object
            int passengerCount = random.nextInt(20);    //get an integer with a bound of 20
            passenger = new Passenger();    //create passenger object
            passenger.setPassengers(passengerCount);    //set the number of passengers to the randomly generated int
            goToDestination(currentElevatorPos, requestFromFloor, destination); //call the elevator with its current position, the floor it was requested at and the destination
            System.out.flush();
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
