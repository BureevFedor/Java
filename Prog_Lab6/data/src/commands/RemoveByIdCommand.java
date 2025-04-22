package commands;

public class RemoveByIdCommand extends AbstractCommand{
    private Integer id;

    public RemoveByIdCommand(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
