package client.graphics;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.lang.Math;

import java.util.concurrent.ConcurrentLinkedQueue;

import client.CommandHandler;

public class ObjectViewPanel extends JPanel {
    private static int minSize = 20;
    private static int maxSize = 50;
    private static int maxX = 1325 - maxSize - 5;//662;
    private static int maxY = 420 - maxSize - 5;//210;   
    private static int maxEnginePower = 100;
    private static float maxOrigX = 1000;
    private static long maxOrigY = 1000;
    private static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.PINK};

    VehiclesTableModel model;
    ConcurrentLinkedQueue<GItem> gItems;
    Timer t;

    private class GItem {
        private int id;
        private int x;
        private int y;
        private int size;
        private Color color;
        private float scale;
        private float delta;

        public GItem(VehicleItem item) {
            id = item.getId();
            x = scaleX(item.getX());
            y = scaleY(item.getY());
            size = scaleSize(item.getEnginePower());
            color = (item.getUserId().equals(model.getUserId())) ? Color.WHITE : getColor(item.getUserId());
            scale = 0.1f;
            delta = 0.1f;
            System.out.println("gItem id=" + id + " name=" + item.getName() + " x=" + item.getX() + "->" + x);            
            System.out.println("gItem id=" + id + " name=" + item.getName() + " y=" + item.getY() + "->" + y + "\n");
        }

        public int getId() {
            return id;
        }

        public float getScale() {
            return scale;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return size;
        }

        public void setX(Float x) {
            this.x = scaleX(x);
        }

        public void setY(Long y) {
            this.y = scaleY(y);
        }

        public void setSize(Integer enginePower) {
            this.size = scaleSize(enginePower);
        }

        public void setDelta(float delta) {
            this.delta = delta;
        }

        public void update() {
            if (delta != 0.0f) {
                float newScale = scale + delta;
                if (newScale > 1.0f) {
                    delta = 0.0f;
                    scale = 1.0f;
                } else if (newScale < 0.0f) {
                    delta = 0.0f;
                    scale = 0.0f;
                } else {
                    scale = newScale;
                }
            }
        }

        public void draw(Graphics2D graphics) {
            if (scale > 0.0f) {
                int diameter = (int) (size * scale);
                graphics.setColor(color);
                graphics.fillOval( x, y, diameter, diameter);
                graphics.setColor(Color.BLACK);
                graphics.drawOval( x, y, diameter, diameter);
            }
        }
    }

    public ObjectViewPanel(VehiclesTableModel model, CommandHandler handler) {
        this.model = model;
        gItems = new ConcurrentLinkedQueue<GItem>();
        model.setObjectView(this);

        setBackground(Color.lightGray);
        setBounds(350,455,1325,420);
        setBorder(BorderFactory.createLoweredBevelBorder());

        t = new Timer(100, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("timer");
                for(GItem gItem : gItems) {
                    gItem.update();
                    if (gItem.getScale() == 0.0f) {
                        System.out.println("Removing gItem id=" + gItem.getId());
                        gItems.remove(gItem);
                        System.out.println("GItem removed.");
                    }
                }
                repaint();
            }
        });
        t.start();

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int column = e.getColumn();
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                boolean updateGItem = (column == 2) || (column == 3) || (column == 5);

                System.out.println("tablemodellistener : " + e.getType() + " (" + firstRow + " - " + lastRow + ")");

                if (e.getType() == TableModelEvent.UPDATE) {
                    for(int row = firstRow; row <= lastRow; row++) {
                        System.out.println("UPDATE ROW " + row);
                        VehicleItem item = model.getObjByIndex(row);
                        if(updateGItem) {
                            GItem gItem = get(item.getId());
                            if (gItem != null) {
                                if (column == 2) {
                                    gItem.setX(item.getX());
                                } else if(column == 3) {
                                    gItem.setY(item.getY());
                                } else if(column == 5) {
                                    gItem.setSize(item.getEnginePower());
                                }
                            }
                        }
                        //handler.doUpdate(item.getId(), item.createSerializable());
                        /*
                        GItem gItem = get((Integer) model.getValueAt(row, 0));
                        if (gItem != null) {
                            if (column == 2) {
                                gItem.setX((Float) model.getValueAt(row, column));
                            } else if(column == 3) {
                                gItem.setY((Long) model.getValueAt(row, column));
                            } else if(column == 5) {
                                gItem.setSize((Integer) model.getValueAt(row, column));
                            }
                        }*/
                    }
                }
            }
        });

        addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                //System.out.println("Mouse pressed; # of clicks: " + e.getClickCount() + " " + e);
            }
         
            public void mouseReleased(MouseEvent e) {
                //System.out.println("Mouse released; # of clicks: " + e.getClickCount() + " " + e);
            }
         
            public void mouseEntered(MouseEvent e) {
                //System.out.println("Mouse entered " + e);
            }
         
            public void mouseExited(MouseEvent e) {
                //System.out.println("Mouse exited " + e);
            }

            public void mouseClicked(MouseEvent e) {
                //System.out.println("X: " + e.getX() + " Y: " + e.getY());
                int x = e.getX();
                int y = e.getY();

                for(GItem gItem : gItems) {
                    if( (x >= gItem.getX()) && (x <= (gItem.getX() + gItem.getSize())) && (y >= gItem.getY()) && (y <= (gItem.getY() + gItem.getSize())) )  {
                        //System.out.println("mouse clicked on gItem " + gItem.getId());
                        int id = gItem.getId();
                        VehicleItem item = model.getObjById(id);
                        if(item == null) {
                            JOptionPane.showMessageDialog(null, "Элемент с Id=" + id + " не найден.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean isEdit = item.getUserId() == model.getUserId();
                            VehicleWindow w = new VehicleWindow(item, isEdit);
                            if (w.run()) {
                                if(isEdit) {
                                    handler.doUpdate(id, w.getVehicleItem().createSerializable());
                                }
                            };
                        }
                    }
                }
            }
        });
    }

    public void stop() {
        t.stop();
    }

    private GItem get(int id) {
        for (GItem gItem : gItems) {
            if (gItem.getId() == id) {
                return gItem;
            }
        }
        return null;
    }

    public void add(VehicleItem item) {
        System.out.println("Add gItem " + item.getId());
        gItems.add(new GItem(item));
    }

    public void remove(VehicleItem item) {
        System.out.println("Remove gItem " + item.getId());
        GItem gItem = get(item.getId());
        if (gItem != null) {
            gItem.setDelta(-0.1f);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //System.out.println("paintComponent");
        for(GItem gItem : gItems) {
            gItem.draw(g2d);
        }
    }

    private static int scaleX(Float x) {
        //return maxX + (x.intValue() % maxX);
        float newX = x;
        newX = (newX > 0) ? Math.min(newX, maxOrigX) : Math.max(newX, -maxOrigX);
        newX += maxOrigX;
        newX = newX / (2 * maxOrigX) * (float)maxX;

        return (int) newX + 2;
    }

    private static int scaleY(Long y) {
        //return maxY + (y.intValue() % maxY);
        long newY = y;
        newY = (newY > 0) ? Math.min(newY, maxOrigY) : Math.max(newY, -maxOrigY);
        newY = -newY;
        newY += maxOrigY;
        newY = (newY * (long)maxY) / (2 * maxOrigY);

        return (int) newY + 2;
    }

    private static Color getColor(int id) {
        return colors[id % colors.length];
    }

    private static int scaleSize(int enginePower) {
        if(enginePower > maxEnginePower) {
            return maxSize;
        } else {
            return minSize + enginePower / (maxEnginePower / (maxSize - minSize));
        }
    }
}
