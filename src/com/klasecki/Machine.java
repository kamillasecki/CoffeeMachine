package com.klasecki;

import java.util.Scanner;

public class Machine {
    private static int water;
    private static int milk;
    private static int coffeeBeans;
    private static int cups;
    private static int money;
    MachineState state;
    Scanner sc = new Scanner(System.in);

    Coffee espresso = new Coffee(250, 0, 16, 4);
    Coffee latte = new Coffee(350, 75, 20, 7);
    Coffee cappuccino = new Coffee(200, 100, 12, 6);

    public Machine(int water, int milk, int coffeeBeans, int cups, int money) {
        Machine.water = water;
        Machine.milk = milk;
        Machine.coffeeBeans = coffeeBeans;
        Machine.cups = cups;
        Machine.money = money;
        state = MachineState.MAIN;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
        Machine.money += money;
    }

    public int getCups() {
        return cups;
    }

    public void addCups(int cups) {
        Machine.cups += cups;
    }

    public int getWater() {
        return water;
    }

    public void addWater(int water) {
        Machine.water += water;
    }

    public int getMilk() {
        return milk;
    }

    public void addMilk(int milk) {
        Machine.milk += milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void addCoffee(int coffee) {
        coffeeBeans += coffee;
    }

    public void start() {
        state = MachineState.MAIN;
        getUserInput();
    }

    public void showMainMenu() {
        state = MachineState.DEFAULT;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        getUserInput();
    }

    public void getUserInput () {

        switch (state) {
            case MAIN:
                showMainMenu();
                break;
            case DEFAULT:
                switch (sc.nextLine()) {
                    case "fill" :
                        fill();
                        getUserInput();
                        break;
                    case "buy" :
                        buy();
                        getUserInput();
                        break;
                    case "take" :
                        takeMoney();
                        getUserInput();
                        break;
                    case "remaining" :
                        printState();
                        getUserInput();
                        break;
                    case "exit" :
                        close();
                        break;
                    default:
                        System.out.println("unrecognised command");
                        state = MachineState.MAIN;
                        getUserInput();
                }
                break;
            case FILL:
                switch (state.getStage()) {
                    case 1: addWater(Integer.parseInt(sc.nextLine()));
                        break;
                    case 2:  addMilk(Integer.parseInt(sc.nextLine()));
                        break;
                    case 3:  addCoffee(Integer.parseInt(sc.nextLine()));
                        break;
                    case 4:  addCups(Integer.parseInt(sc.nextLine()));
                    break;
                }
                break;
            case BUY:
                switch (sc.nextLine()) {
                    case "1" : prepare(espresso);
                    break;
                    case "2" : prepare(latte);
                    break;
                    case "3" : prepare(cappuccino);
                    break;
                    case "back" :
                        state = MachineState.DEFAULT;
                        break;
                    default: System.out.println("Unrecognised option");
                    state = MachineState.MAIN;
                    getUserInput();
                }
                break;
            default: System.out.println("Unrecognised option");
                state = MachineState.MAIN;
                getUserInput();

        }
    }

    private void close() {
        //sc.close();
        state = MachineState.EXIT;
    }


    public void fill() {
        state = MachineState.FILL;
        state.setStage(1);
        System.out.println();
        System.out.println("Write how many ml of water do you want to add:");
        getUserInput();
        state.setStage(2);
        System.out.println("Write how many ml of milk do you want to add:");
        getUserInput();
        state.setStage(3);
        System.out.println("Write how many grams of coffee beans do you want to add:");
        getUserInput();
        state.setStage(4);
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        getUserInput();
        System.out.println();
        state = MachineState.MAIN;
        state.setStage(1);
    }

    public void printState() {
        state = MachineState.REMAINING;
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(getWater() + " of water");
        System.out.println(getMilk() + " of milk");
        System.out.println(getCoffeeBeans() + " of coffee beans");
        System.out.println(getCups() + " of disposable cups");
        System.out.println("$" + getMoney() + " of money");
        System.out.println();
        state = MachineState.MAIN;
    }

    public void buy() {

        state = MachineState.BUY;
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        getUserInput();
    }

    private void prepare(Coffee coffee) {
        String missingComponent = checkMissingComponents(latte);
        if (missingComponent==null) {
            System.out.println("I have enough resources, making you a coffee!");
            System.out.println();
            cups -= 1;
            water -= coffee.getWater();
            milk -= coffee.getMilk();
            coffeeBeans -= coffee.getCoffeeBeans();
            addMoney(coffee.getPrice());
        } else {
            System.out.println("Sorry, not enough " + missingComponent + "!");
            System.out.println();
        }
        state = MachineState.MAIN;
    }

    private String checkMissingComponents(Coffee coffee) {
        if (cups == 0) return "cups";
        if (water <= coffee.getWater()) return "water";
        if (milk <= coffee.getMilk()) return "milk";
        if (coffeeBeans <= coffee.getCoffeeBeans()) return "coffee beans";
        return null;
    }

    public void takeMoney() {
        state = MachineState.TAKE;
        System.out.println("I gave you $" + money);
        System.out.println();
        money = 0;
        state = MachineState.MAIN;
    }
}
