package com.bigseeproduction.game.listener;

import com.bigseeproduction.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 07.04.2015.
 */
public class InputHandler implements KeyListener {

    public List<Key> keys = new ArrayList<Key>();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key pause = new Key();
    public Key debug = new Key();
    public Key enter = new Key();

    public InputHandler(Game game) {
        game.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            pause.toggle(isPressed);
            /*
            if(pause.isPressed() && pause.hasChanged()) {

            }
            */
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enter.toggle(isPressed);
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_F3) {
            debug.toggle(isPressed);
        }
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;
        private boolean hasChanged = false;

        public void toggle(boolean pressed) {
            if (this.pressed != pressed) hasChanged = true;
            this.pressed = pressed;
            if (pressed) numTimesPressed++;
        }

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean hasChanged() {
            boolean temp = hasChanged;
            hasChanged = false;
            return temp;
        }

        public void setChanged(boolean changed) {
            this.hasChanged = changed;
        }
    }
}
