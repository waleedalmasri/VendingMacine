package VendingMachines;

import MoneySlot.MoneySlot;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SnackMachineOperations {
    public void refillStock() throws IOException;

    public void refillBalance() throws IOException;

}
