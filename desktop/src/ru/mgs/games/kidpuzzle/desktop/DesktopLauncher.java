package ru.mgs.games.kidpuzzle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.mgs.games.kidpuzzle.AdHandler;
import ru.mgs.games.kidpuzzle.KidPuzzleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new KidPuzzleGame(new AdHandler() {
			@Override
			public void showAds(boolean show) {

			}

			@Override
			public void doPay() {

			}

			@Override
			public void processPurchases() {

			}
		}), config);
	}
}
