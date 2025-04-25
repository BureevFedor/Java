package commands;

import data.VehicleSerializable;

public class AddCommand extends AbstractCommand{
    private VehicleSerializable obj;

    public AddCommand(VehicleSerializable obj) {
        this.obj = obj;
    }

    public VehicleSerializable getObj() {
        return obj;
    }
}
