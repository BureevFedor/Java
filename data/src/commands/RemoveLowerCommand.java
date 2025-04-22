package commands;

import data.VehicleSerializable;

public class RemoveLowerCommand extends AbstractCommand{
    private VehicleSerializable obj;

    public RemoveLowerCommand(VehicleSerializable obj) {
        this.obj = obj;
    }

    public VehicleSerializable getObj() {
        return obj;
    }
}
