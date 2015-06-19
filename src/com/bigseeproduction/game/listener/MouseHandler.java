package com.bigseeproduction.game.listener;

import com.bigseeproduction.game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;

/**
 * Created by Luca on 11.04.2015.
 */
public class MouseHandler implements MouseListener {

    public MouseHandler(Game game) {
        game.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Game.LOGGER.log(Level.FINEST, "Mouse clicked!");

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
