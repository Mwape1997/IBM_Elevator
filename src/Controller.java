/*
Mwape Chintu 2020
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Controller {

    private static final int LAST = 55;     //top floor
    private static final int FIRST = 0;     //bottom/first floor
    private int currentLocation;
    private int destination;
    private int nearestElevator;
    private ArrayList<Elevator> listOfElevators = new ArrayList<>();    //create an arraylist of elevators
    private ArrayList<Elevator> availableElevators = new ArrayList<>(); //create an arraylist of available elevators
    private Queue<Integer> q = new LinkedList<>();
    private Queue<Integer> overflow = new LinkedList<>();
    private Elevator elevator;
    private Elevator elevator1, elevator2, elevator3, elevator4, elevator5, elevator6, elevator7;   //declare 7 elevator instances.
    private boolean s1, s2, s3, s4, s5, s6, s7 = true;

    private boolean available;
    private int sleepCount = 1000;


    private boolean checkAvailableElevators(){
        available = false;
        for (Elevator listOfElevator : listOfElevators) {
            if (listOfElevator.isAvailable()) {           //get a list of all currently available elevators and add them to the available elevators list
                availableElevators.add(listOfElevator);
                available = true;
                //System.out.println("Elevator: " + listOfElevators.get(i).getID() + " is available");
            }
        }
    return available;
    }

    private synchronized void addRequest(int currentLocation, int destination) throws InterruptedException {  //makes a request for an elevator.
        if(checkAvailableElevators()){  //if there is an available elevator....
            q.add(currentLocation);
            q.add(destination);
            System.out.println("Q elements: " + q);     //print the current elements in the queue
                if((currentLocation>= FIRST) && (currentLocation<=LAST) && (destination>= FIRST) && (destination<=LAST) ){   //check if the input is valid
                    elevator = getNearestElevator(currentLocation); //set the elevator being called to be the nearest available elevator

                    if(elevator.getID() == 1){      //if the ID of that elevator is 1 then we know the first elevator is the nearest available
                        elevator1.setRequestFromFloor(currentLocation); //get it's current location
                        elevator1.setDestination(destination);

                        if(!s1){        //s1-s7 serve as flags to make sure a thread is only run once.
                            elevator1.start();
                            elevator1.join();
                            s1 = !s1;
                        }
                        else{       //if thread is already been run, just call the run method
                            elevator1.run();
                        }

                        q.remove();   //remove the current location from the queue
                        q.remove();   //remove the destination from the queue
                    }
                    else
                    if(elevator.getID() == 2){
                        elevator2.setRequestFromFloor(currentLocation);
                        elevator2.setDestination(destination);
                        if(!s2){
                            elevator2.start();
                            elevator2.join();
                            s2 = !s2;
                        }
                        else{
                            elevator2.run();
                        }
                        q.remove();
                        q.remove();
                    }
                    else
                    if(elevator.getID() == 3){
                        elevator3.setRequestFromFloor(currentLocation);
                        elevator3.setDestination(destination);
                        if(!s3){
                            elevator3.start();
                            elevator3.join();
                            s3 = !s3;
                        }
                        else{
                            elevator3.run();
                        }
                        q.remove();
                        q.remove();
                    }
                    else
                    if(elevator.getID() == 4){
                        elevator4.setRequestFromFloor(currentLocation);
                        elevator4.setDestination(destination);
                        if(!s4){
                            elevator4.start();
                            elevator4.join();
                            s4 = !s4;
                        }
                        else{
                            elevator4.run();
                        }
                        q.remove();
                        q.remove();
                    }
                    else
                    if(elevator.getID() == 5){
                        elevator5.setRequestFromFloor(currentLocation);
                        elevator5.setDestination(destination);
                        if(!s5){
                            elevator5.start();
                            elevator5.join();
                            s5 = !s5;
                        }
                        else{
                            elevator5.run();
                        }
                        q.remove();
                        q.remove();
                    }
                    else
                    if(elevator.getID() == 6){
                        elevator6.setRequestFromFloor(currentLocation);
                        elevator6.setDestination(destination);
                        if(!s6){
                            elevator6.start();
                            elevator6.join();
                            s6 = !s6;
                        }
                        else{
                            elevator6.run();
                        }
                        q.remove();
                        q.remove();
                    }
                    else
                    if(elevator.getID() == 7){
                        elevator7.setRequestFromFloor(currentLocation);
                        elevator7.setDestination(destination);
                        if(!s6){
                            elevator7.start();
                            elevator7.join();
                            s6 = !s6;
                        }
                        else{
                            elevator7.run();
                        }
                        q.remove();
                        q.remove();
                    }

                    System.out.println("Q elements after: " + q);
                }

                else System.out.println("Invalid request!");        //if a floor that is not in range is requested, the request is considered invalid.

        }
        else {
            overflow.add(currentLocation);  //add the overflow request into a queue and wait for the next available elevator
            overflow.add(destination);
            System.out.println("No available elevators at the moment. Please wait...");
            System.out.println("Waiting" + overflow);
            while(!checkAvailableElevators()){
                wait(); //as long as there are no available elevators, make the thread wait.
            }
            addRequest(currentLocation, destination);   //once an elevator is available, call it.
        }

    }

    private Elevator getNearestElevator(int currentLocation) {
        this.currentLocation = currentLocation;
        int nearestCalc;

        checkAvailableElevators();      //first check the currently available elevators

        if(!availableElevators.isEmpty()){
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
        }
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
            controller.addRequest(52, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controller.addRequest(1, LAST);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controller.addRequest(23, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Controller(){
        elevator1 = new Elevator(1, FIRST, true, false);    //create 7 elevator objects
        elevator2 = new Elevator(2, 7, true, false);
        elevator3 = new Elevator(3, 14, true, false);
        elevator4 = new Elevator(4, 21, false, false);
        elevator5 = new Elevator(5, 28, false, false);
        elevator6 = new Elevator(6, 35, false, false);
        elevator7 = new Elevator(7, 42, false, false);
        listOfElevators.add(elevator1);
        listOfElevators.add(elevator2);
        listOfElevators.add(elevator3);
        listOfElevators.add(elevator4);
        listOfElevators.add(elevator5);
        listOfElevators.add(elevator6);
        listOfElevators.add(elevator7);
    }

}
