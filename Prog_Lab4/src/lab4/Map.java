package lab4;

import java.util.ArrayList;
import java.util.function.Consumer;

import lab4.rooms.AbstractRoom;
import lab4.rooms.Passage;
import lab4.rooms.PrintInterface;
import lab4.exceptions.*;

public class Map implements PrintInterface{
    private String description;
    private ArrayList<AbstractRoom> rooms = new ArrayList<AbstractRoom>();
    private ArrayList<Passage> passages = new ArrayList<Passage>();

    public Map(String description) {
        this.description = description;
    }

    public void print() {
        System.out.println();
        System.out.println("Карта : " + description);
        System.out.println();

        // Использование анонимного класса для вывода списка помещений
        rooms.forEach(new Consumer<AbstractRoom>() {
            @Override
            public void accept(AbstractRoom room) {
                System.out.println("Помещенеие [" + room.getId() + "]");
                room.print();
            }
        });

        System.out.println();
        
        // Использование анонимного класса для вывода списка проходов
        passages.forEach(new Consumer<Passage>() {
            @Override
            public void accept(Passage passage) {
                System.out.println("Проход [" + passage.getId() + "]");
                passage.print();
            }
        });

        System.out.println();
    }

    public void addRoom(AbstractRoom room) {
        rooms.add(room);
    }

    public void addPassage(Passage passage) {
        passages.add(passage);
    }

    public AbstractRoom getRoom(int id) {
        for(int i = 0; i < rooms.size(); i++) {
            if(id == rooms.get(i).getId()) {
                return rooms.get(i);
            }
        }
        throw new RoomNotFound(id);
    }

    public Passage getPassage(int id) {
        for(int i = 0; i < passages.size(); i++) {
            if(id == passages.get(i).getId()) {
                return passages.get(i);
            }
        }
        throw new PassageNotFound(id);
    }

    public ArrayList<Passage> getPassages(int id) {
        ArrayList<Passage> result = new ArrayList<Passage>();
        for(int i = 0; i < passages.size(); i++) {
            if(passages.get(i).checkId(id)) {
                result.add(passages.get(i));
            }
        }
        return result;
    }

    public void printPassages(int id) {
        int n = 1;
        System.out.println();
        for(int i = 0; i < passages.size(); i++) {
            if(passages.get(i).checkId(id)) {
                System.out.println("Проход " + n + ": " + passages.get(i).getDescription(id));
                n++;
            }
        }
    }

    public int getPassageIdx(int id, int passageId) throws PassageNotAvailable {
        int n = 1;
        for(int i = 0; i < passages.size(); i++) {
            if(passages.get(i).checkId(id)) {
                if(passages.get(i).getId() == passageId) {
                    return n;
                }
                n++;
            }
        }
        throw new PassageNotAvailable(passageId, id);
    }
}
