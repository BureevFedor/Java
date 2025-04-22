package lab3;

import java.util.ArrayList;

import lab3.rooms.AbstractRoom;
import lab3.rooms.Passage;
import lab3.rooms.PrintInterface;

public class Map implements PrintInterface{
    private String description;
    private ArrayList<AbstractRoom> rooms = new ArrayList<AbstractRoom>();
    private ArrayList<Passage> passages = new ArrayList<Passage>();

    public Map(String description) {
        this.description = description;
    }

    public void print() {
        System.out.println(description);
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
        return null;
    }

    public Passage getPassage(int id) {
        for(int i = 0; i < passages.size(); i++) {
            if(id == passages.get(i).getId()) {
                return passages.get(i);
            }
        }
        return null;
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

    public int getPassageIdx(int id, int passageId) {
        int n = 1;
        for(int i = 0; i < passages.size(); i++) {
            if(passages.get(i).checkId(id)) {
                if(passages.get(i).getId() == passageId) {
                    return n;
                }
                n++;
            }
        }
        return 0;
    }
}
