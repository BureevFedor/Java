package commands;

public class RemoveAtIndexCommand extends AbstractCommand{
    private Integer index;

    public RemoveAtIndexCommand(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
