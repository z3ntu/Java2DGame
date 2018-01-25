package com.bigseeproduction.game.listener;

import com.bigseeproduction.game.Game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;

/**
 * Created by Luca on 11.04.2015.
 */
public class WindowHandler implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Game.LOGGER.log(Level.INFO, "Window is closing!");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
