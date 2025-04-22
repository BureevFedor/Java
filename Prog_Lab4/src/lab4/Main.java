package lab4;

import lab4.descriptions.*;
import lab4.rooms.*;

import java.util.Random;

public class Main {

    private static String decoration = "---";

    // Пример внутреннего класса
    static class Atmosphere {
        Random ran = new Random();
        String atmospheres[] = {
            "Жуткие тени сновали по углам, не оставляя нам выбора, кроме как оборачиваться на каждом шагу.",
            "Из-за постоянно падающих капель и перетекающей воды создавалось ощущение, будто мы были в подземной пещере.",
            "В воздухе пахло пирожками. Столь необычный для этого места запах напоминал нам о доме.",
            "Холод пробирал до костей и хотелось забросить эту экспедицию, вернуться и укутаться в тёплый плед.",
            "В воздухе веяло чем-то замогильным.",
            "Казалось, все радостные воспоминания остались далеко в прошлом.",
            "Хочется надеяться, что мы всё-таки когда-нибудь выберемся отсюда!",
            "Было такое ощущение, будто бы время остановилось. Я посмотрел на часы и запомнил время, но когда-то я посмотрел на них снова примерно через час, прошло всего десять минут."
        };
        
        void print() {
            System.out.println(decoration + " " + atmospheres[ran.nextInt(atmospheres.length)]);
        }
    }
    public static void main(String[] args) {

        String decoration = "...";

        // Пример локального класса
        class Sounds {
            Random ran = new Random();
            String sounds[] = {
                "Весёлый птичий щебет внезапно оборвался на самой высокой ноте и смолк.",
                "Лишь тяжёлое дыхание моих уставших товарищей нарушало тишину.",
                "Пронзительный свист ветра не умолкал ни на секунду.",
                "Звук падающих капель порождал странную мелодию, в которой я тщетно пытался уловить ритм, который постоянно менялся.",
                "Внезапно раздался грохот, будто бы гигантская металлическая балка упала на каменный пол с большой высоты.",
                "Кто-то громко хихикнул во тьме.",
                "Свист крыльев летучей мыши пронзил пространство над нашими головами и исчез.",
                "Где-то неподалёку поскрипывала на ветру оконная рама.",
                "Послышалось тихое пение на незнакомом языке. Как оказалось позже, слышал его только я."
            };
            
            void print() {
                System.out.println(decoration + " " + sounds[ran.nextInt(sounds.length)]);
            }
        }

        Atmosphere atm = new Atmosphere();
        Sounds snd = new Sounds();

        Map map = new Map("Старый город.");
        fillMap(map);

        Hero hero = new Hero("A", true, true, 1);

        //Проходы, по которым нужно пройти
        int moves[] = {101, 102, 102, 103, 104};

        if(args.length == 1) {
            switch(args[0]) {
                case "help" :
                    System.out.println("Параметры программы : ");
                    System.out.println("    help                (вывод справки о параметрах программы)");
                    System.out.println("    print               (вывести карту и выйти)");
                    System.out.println("    RoomNotFound        (спровоцировать указанное исключение)");
                    System.out.println("    PassageNotFound     (спровоцировать указанное исключение)");
                    System.out.println("    PassageNotAvailable (спровоцировать указанное исключение)");
                    return;
                case "print" :
                    map.print();
                    return;
                case "RoomNotFound" : 
                    hero.setId(10000);
                    break;
                case "PassageNotFound" : 
                    moves[0] = 400;
                    break;
                case "PassageNotAvailable" : 
                    moves[0] = 104;
                    break;
                default : 
                    break;
            }
        } 

        Passage passage;

        // Начало приключения
        hero.print();
        System.out.println();

        for(int i = 0; i < moves.length; i++) {
            int id = hero.getId();

            // Для активации RoomNotFound exception поставить начальную позицию героя в несуществущую комнату  
            try {
                map.getRoom(id).print();
            } catch (RuntimeException e) {
                System.out.println(e);
                System.out.println("Мы взглянули вокруг и поняли, что находимся в белой пустоте. Как такое оказалось возможным, было вне нашего понимания.");
                return;
            }

            map.printPassages(id);
            System.out.println();
            snd.print();
            atm.print();
            System.out.println();

            // Для активации PassageNotFound exception внести номер несуществующего перехода в moves  
            try {
                passage = map.getPassage(moves[i]);
            } catch (RuntimeException e) {
                System.out.println(e);
                System.out.println("Мы попытались войти в проход и обнаружили, что он не существует. Как?! ");
                return;
            }

            // Для активации PassageNotAvailable exception внести номер перехода, недоступного в текущей комнате, в moves  
            try {
                System.out.println(">>> Делаем переход из комнаты " + id + " по проходу " + map.getPassageIdx(id, moves[i]));
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Мы попытались пройти по несуществующему проходу. Это сломало матрицу, и мы выпали в реальный мир.");
                return;
            }
            System.out.println();

            hero.setId(passage.pass(id, hero.hasRope(), hero.hasLight())); 
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