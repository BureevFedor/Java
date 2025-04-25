package commands;

public class FilterStartsWithNameCommand extends AbstractCommand{
    private String name;

    public FilterStartsWithNameCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
