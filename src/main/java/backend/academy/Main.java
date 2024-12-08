package backend.academy;

import backend.academy.entry.EntryPoint;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        EntryPoint entryPoint = new EntryPoint();
        entryPoint.start();
    }
}
