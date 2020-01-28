/*
Mwape Chintu 2020
 */

import java.util.ArrayList;

public class Controller {

    private static final int LAST = 55;     //top floor
    private static final int FIRST = 0;     //bottom/first floor
    private int currentLocation;
    private int destination;
    private int nearestElevator;
    private ArrayList<Elevator> listOfElevators = new ArrayList<Elevator>();    //create an arraylist of elevators
    private ArrayList<Elevator> availableElevators = new ArrayList<Elevator>(); //create an arraylist of available elevators
    private Elevator elevator;
    private Elevator elevator1;
    private Elevator elevator2;
    private Elevator elevator3;     //declare 7 elevator instances.
    private Elevator elevator4;
    private Elevator elevator5;
    private Elevator elevator6;
    private Elevator elevator7;


    public void checkAvailableElevators(){
        for(int i = 0; i < listOfElevators.size(); i++)
        {
            if(listOfElevators.get(i).isAvailable()){           //get a list of all currently available elevators and add them to the available elevators list
                availableElevators.add(listOfElevators.get(i));
                //System.out.println("Elevator: " + listOfElevators.get(i).getID() + " is available");
            }
        }

    }

    public void addRequest(int currentLocation, int destination) throws InterruptedException {  //makes a request for an elevator.
        if((currentLocation>= FIRST) && (currentLocation<=LAST) && (destination>= FIRST) && (destination<=LAST) ){   //check if the input is valid
            elevator = getNearestElevator(currentLocation); //set the elevator being called to be the nearest available elevator
            if(elevator.getID() == 1){      //if the ID of that elevator is 1 then we know the first elevator is the nearest available
                elevator1.setRequestFromFloor(currentLocation); //get it's current location
                elevator1.setDestination(destination);
                elevator1.start();
            }

            if(elevator.getID() == 2){
                elevator2.setRequestFromFloor(currentLocation);
                elevator2.setDestination(destination);
                elevator2.start();
            }
            if(elevator.getID() == 3){
                elevator1.setRequestFromFloor(currentLocation);
                elevator3.setDestination(destination);
                elevator3.start();
            }

            if(elevator.getID() == 4){
                elevator4.setRequestFromFloor(currentLocation);
                elevator4.setDestination(destination);
                elevator4.start();
            }

            if(elevator.getID() == 5){
                elevator5.setRequestFromFloor(currentLocation);
                elevator5.setDestination(destination);
                elevator5.start();
            }

            if(elevator.getID() == 6){
                elevator6.setRequestFromFloor(currentLocation);
                elevator6.setDestination(destination);
                elevator6.start();
            }

            if(elevator.getID() == 7){
                elevator7.setRequestFromFloor(currentLocation);
                elevator7.setDestination(destination);
                elevator7.start();
            }
        }

        else System.out.println("Invalid request!");

    }

    public Elevator getNearestElevator(int currentLocation) {
        this.currentLocation = currentLocation;
        int nearestCalc;

        checkAvailableElevators();      //first check the currently available elevators

        nearestCalc = availableElevators.get(0).getcurrentElevatorPos();    //set the elevator position of nearest calc to be the first elevator
        elevator = availableElevators.get(0);   //initially set the nearest elevator to be the first
        for(int i = 0; i < availableElevators.size() - 1; i++)
        {
            if(Math.abs(nearestCalc - currentLocation) <= Math.abs(availableElevators.get(i + 1).getcurrentElevatorPos() - currentLocation)){
            }
            else

                elevator = availableElevators.get(i);   //pick the nearest elevator
        }

        System.out.println("Nearest elevator is: " + elevator.getID()); //debugging for showing if the nearest elevator is picked
        availableElevators.remove(elevator);    //remove that elevator from the availability list so the next thread does not choose it
        return elevator;
    }


    public static void main(String[] args){
        Controller controller = new Controller();   //create controller object
        try {
            controller.addRequest(8, 14);   //make a request for an elevator with these params.
        } catch (InterruptedException e) {                            //handle any exceptons
            e.printStackTrace();
        }


        try {
            controller.addRequest(14, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controller.addRequest(1, LAST);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controller.addRequest(23, 33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public Controller(){
        elevator1 = new Elevator(1, FIRST, true, false);    //create 7 elevator objects
        elevator2 = new Elevator(2, 7, true, false);
        elevator3 = new Elevator(3, 14, true, false);
        elevator4 = new Elevator(4, 21, true, false);
        elevator5 = new Elevator(5, 28, true, false);
        elevator6 = new Elevator(6, 35, true, false);
        elevator7 = new Elevator(7, 42, true, false);
        listOfElevators.add(elevator1);
        listOfElevators.add(elevator2);
        listOfElevators.add(elevator3);
        listOfElevators.add(elevator4);
        listOfElevators.add(elevator5);
        listOfElevators.add(elevator6);
        listOfElevators.add(elevator7);
    }

}
