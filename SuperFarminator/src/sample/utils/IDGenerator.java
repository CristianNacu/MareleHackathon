package sample.utils;

public class IDGenerator {
    private static int id = 1;
    public static int generateID(){
        return id++;
    }
}
