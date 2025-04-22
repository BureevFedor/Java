package commands;

import data.VehicleSerializable;

public class UpdateCommand extends AbstractCommand{
    private Integer id;
    private VehicleSerializable obj;

    public UpdateCommand(Integer id, VehicleSerializable obj) {
        this.obj = obj;
        this.id = id;
    }

    public VehicleSerializable getObj() {
        return obj;
    }

    public Integer getId() {
        return id;
    }
}
