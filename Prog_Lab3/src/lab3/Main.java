package lab3;

import lab3.descriptions.*;
import lab3.rooms.*;

public class Main {
    public static void main(String[] args) {

        Map map = new Map("Старый город.");
        fillMap(map);

        Hero hero = new Hero("A", true, true, 1);
        hero.print();
        System.out.println();

        //Проходы, по которым нужно пройти
        int moves[] = {101, 102, 102, 103, 104};

        for(int i = 0; i < moves.length; i++) {
            int id = hero.getId();
            
            map.getRoom(id).print();
            map.printPassages(id);

            System.out.println();
            System.out.println("### Делаем переход из комнаты " + id + " по проходу " + map.getPassageIdx(id, moves[i]));
            System.out.println();

            hero.setId(map.getPassage(moves[i]).pass(id, hero.hasRope(), hero.hasLight())); 
            hero.print();
            System.out.println();
        }
        
        map.getRoom(hero.getId()).print();
        System.out.println();
        System.out.println("И, наконец, мы поняли, что пришли, куда хотели. Мы увидели ларёк с пирожками, и радости нашей не было предела!");
    }

    private static void fillMap(Map map) {
        String descr, descr1, descr2;

        descr = "Наконец мы напали на целый ряд окон -- в венчавшем 'здание громадном пятиугольнике.";
        map.addRoom(new Gallery(1, descr, GalleryType.GLASS, FloorType.GROUND, WallType.WOODEN));
        
        descr = "Мы перешли в гигантский зал.";
        map.addRoom(new Hall(2, descr, HallType.LARGE, FloorType.STONE, WallType.BASRELIEF, WindowType.STAINED_GLASS));

        descr = "Затем мы дошли до странного моста. Однако, мы решили не идти по нему, и вернулись обратно в зал.";
        map.addRoom(new Bridge(3, descr, BridgeType.getRandom()));

        descr = "Пойдя по этому проходу, мы пришли к лестнице.";
        map.addRoom(new Stairs(4, descr, StairsType.getRandom(), WindowType.getRandom(), WallType.getRandom()));
        
        descr = "С большим трудом мы добрались до конца прохода и вышли в следующее помещение. Наши ноги начинали уставать.";
        switch(RoomType.getRandom()) {
            case HALL : 
                map.addRoom(new Hall(5, descr, HallType.getRandom(), FloorType.getRandom(), WallType.getRandom(), WindowType.getRandom()));
            case GALLERY : 
                map.addRoom(new Gallery(5, descr, GalleryType.getRandom(), FloorType.getRandom(), WallType.getRandom()));
            case STAIRS : 
                map.addRoom(new Stairs(5, descr, StairsType.getRandom(), WindowType.getRandom(), WallType.getRandom()));
            case BRIDGE : 
                map.addRoom(new Bridge(5, descr, BridgeType.getRandom()));
        }

        descr1 = "Cквозь ряд окон просматривалась просторная, хорошо сохранившаяся комната, однако спуститься туда без веревки не представлялось возможным. Постаравшись получше запомнить это место, мы решили вернуться сюда в том случае, если не найдем ничего более доступного.";
        descr2 = "Возле самого потолка мы увидели целый ряд окон в венчавшем здание гигантском пятиугольнике, но подняться туда без приставной лестницы или верёвки невозможно.";
        map.addPassage(new Passage(100, "Проход №100", 1, 2, true, false, descr1, descr2));

        descr1 = "В результате мы отыскали проем в стене с арочным перекрытием, шириной шесть и длиной десять футов -- прежде сюда подходил воздушный мостик, соединявший между собой здания. Не знаю, как раньше, но теперь бы он располагался всего в пяти футах над ледяным покровом. Эти сводчатые проходы соответствовали верхним этажам; сохранился здесь, к счастью, и пол.";
        descr2 = "Тёмное отверстие прохода между двумя деревянными панелями вело обратно к стеклянной галерее, откуда мы только что пришли.";
        map.addPassage(new Passage(101, "Проход №101", 1, 2, false, false, descr1, descr2));

        descr1 = "Узкая тёмная нора в северо-западном углу тоже куда-то вела.";
        descr2 = "Узкая тёмная нора вела обратно в гигантский зал.";
        map.addPassage(new Passage(102, "Проход №102", 2, 3, false, false, descr1, descr2));

        descr1 = "Небольшая неприметная дверца в углу зала была чуть приоткрыта. За ней октрывался тёмный прямой коридор.";
        descr2 = "Тёмный прямой коридор вёл обратно к гигантскому залу.";
        map.addPassage(new Passage(103, "Проход №103", 2, 4, false, false, descr1, descr2));

        descr1 = "Запах горячих пирожков доносился из провала в полу. Мы очень не хотели развязывать верёвку и включать фонарики с едва работающими батареями, но запах был слишком силён.";
        descr2 = "Наевшись пирожков, мы поняли, что нам совсем не хочется возвращаться.";
        map.addPassage(new Passage(104, "Проход №104", 4, 5, true, true, descr1, descr2));
    }
}