package com.TrovãoGameStudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.TrovãoGameStudios.main.Game;
import com.TrovãoGamesStudios.entities.Player;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(1, 1, 100, 8);
		g.setColor(Color.green);
		g.fillRect(1,1, (int)((Game.player.vida/Game.player.maxlife)*100), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,10));
		g.drawString((int)Game.player.vida+ "/" + (int)Game.player.maxlife,2,9);
		
	}
	
}
